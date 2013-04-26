/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.widgets;


import org.eclipse.swt.*;
import org.eclipse.swt.internal.*;
import org.eclipse.swt.internal.gtk.*;

/**
 * Instances of this class allow the user to navigate
 * the file system and select or enter a file name.
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>SAVE, OPEN, MULTI</dd>
 * <dt><b>Events:</b></dt>
 * <dd>(none)</dd>
 * </dl>
 * <p>
 * Note: Only one of the styles SAVE and OPEN may be specified.
 * </p><p>
 * IMPORTANT: This class is <em>not</em> intended to be subclassed.
 * </p>
 * 
 * @see <a href="http://www.eclipse.org/swt/snippets/#filedialog">FileDialog snippets</a>
 * @see <a href="http://www.eclipse.org/swt/examples.php">SWT Example: ControlExample, Dialog tab</a>
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further information</a>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class FileDialog extends Dialog {
	String [] filterNames = new String [0];
	String [] filterExtensions = new String [0];
	String filterPath = "";
	String fileName = "";
	String[] fileNames = new String [0];
	String fullPath = "";
	int filterIndex = -1;
	boolean overwrite = false;
	boolean uriMode;
	long /*int*/ handle;
	static final char SEPARATOR = System.getProperty ("file.separator").charAt (0);
	static final char EXTENSION_SEPARATOR = ';';
	static final char FILE_EXTENSION_SEPARATOR = '.';
	
/**
 * Constructs a new instance of this class given only its parent.
 *
 * @param parent a shell which will be the parent of the new instance
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 *    <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed subclass</li>
 * </ul>
 */
public FileDialog (Shell parent) {
	this (parent, SWT.APPLICATION_MODAL);
}
/**
 * Constructs a new instance of this class given its parent
 * and a style value describing its behavior and appearance.
 * <p>
 * The style value is either one of the style constants defined in
 * class <code>SWT</code> which is applicable to instances of this
 * class, or must be built by <em>bitwise OR</em>'ing together 
 * (that is, using the <code>int</code> "|" operator) two or more
 * of those <code>SWT</code> style constants. The class description
 * lists the style constants that are applicable to the class.
 * Style bits are also inherited from superclasses.
 * </p>
 *
 * @param parent a shell which will be the parent of the new instance
 * @param style the style of dialog to construct
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 *    <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed subclass</li>
 * </ul>
 * 
 * @see SWT#SAVE
 * @see SWT#OPEN
 * @see SWT#MULTI
 */
public FileDialog (Shell parent, int style) {
	super (parent, checkStyle (parent, style));
	checkSubclass ();
}
String computeResultChooserDialog () {
	/* MULTI is only valid if the native dialog's action is Open */
	fullPath = null;
	if ((style & SWT.MULTI) != 0) {
		long /*int*/ list = 0;
		if (uriMode) {
			list = OS.gtk_file_chooser_get_uris (handle);
		} else {
			list = OS.gtk_file_chooser_get_filenames (handle);
		}
		int listLength = OS.g_slist_length (list);
		fileNames = new String [listLength];
		long /*int*/ current = list;
		int writePos = 0;
		for (int i = 0; i < listLength; i++) {
			long /*int*/ name = OS.g_slist_data (current);
			long /*int*/ utf8Ptr = 0;
			if (uriMode) {
				utf8Ptr = name;
			} else {
				utf8Ptr = OS.g_filename_to_utf8 (name, -1, null, null, null);
				if (utf8Ptr == 0) utf8Ptr = OS.g_filename_display_name (name);
			}
			if (name != utf8Ptr) OS.g_free (name);
			if (utf8Ptr != 0) {
				long /*int*/ [] items_written = new long /*int*/ [1];
				long /*int*/ utf16Ptr = OS.g_utf8_to_utf16 (utf8Ptr, -1, null, items_written, null);
				OS.g_free (utf8Ptr);
				if (utf16Ptr != 0) {
					int clength = (int)/*64*/items_written [0];
					char [] chars = new char [clength];
					OS.memmove (chars, utf16Ptr, clength * 2);
					OS.g_free (utf16Ptr);
					fullPath = new String (chars);
					fileNames [writePos++] = fullPath.substring (fullPath.lastIndexOf (SEPARATOR) + 1);
				}
			}
			current = OS.g_slist_next (current);
		}
		if (writePos != 0 && writePos != listLength) {
			String [] validFileNames = new String [writePos];
			System.arraycopy (fileNames, 0, validFileNames, 0, writePos);
			fileNames = validFileNames;
		}
		OS.g_slist_free (list);
	} else {
		long /*int*/ utf8Ptr = 0;
		if (uriMode) {
			utf8Ptr = OS.gtk_file_chooser_get_uri (handle);
		} else {
			long /*int*/ path = OS.gtk_file_chooser_get_filename (handle);
			if (path != 0) {
				utf8Ptr = OS.g_filename_to_utf8 (path, -1, null, null, null);
				if (utf8Ptr == 0) utf8Ptr = OS.g_filename_display_name (path);
				if (path != utf8Ptr) OS.g_free (path);
			}
		}
		if (utf8Ptr != 0) {
			long /*int*/ [] items_written = new long /*int*/ [1];
			long /*int*/ utf16Ptr = OS.g_utf8_to_utf16 (utf8Ptr, -1, null, items_written, null);
			OS.g_free (utf8Ptr);
			if (utf16Ptr != 0) {
				int clength = (int)/*64*/items_written [0];
				char [] chars = new char [clength];
				OS.memmove (chars, utf16Ptr, clength * 2);
				OS.g_free (utf16Ptr);
				fullPath = new String (chars);
				fileNames = new String [1];
				fileNames[0] = fullPath.substring (fullPath.lastIndexOf (SEPARATOR) + 1);
			}
		}
	}
	filterIndex = -1;
	long /*int*/ filter = OS.gtk_file_chooser_get_filter (handle);
	if (filter != 0) {
		long /*int*/ filterNamePtr = OS.gtk_file_filter_get_name (filter);
		if (filterNamePtr != 0) {
			int length = OS.strlen (filterNamePtr);
			byte[] buffer = new byte [length];
			OS.memmove (buffer, filterNamePtr, length);
			//OS.g_free (filterNamePtr); //GTK owns this pointer - do not free
			String filterName = new String (Converter.mbcsToWcs (null, buffer));
			for (int i = 0; i < filterExtensions.length; i++) {
				if (filterNames.length > 0) {
					if (filterNames[i].equals(filterName)) {
						filterIndex = i;
						break;
					}
				} else {
					if (filterExtensions[i].equals(filterName)) {
						filterIndex = i;
						break;
					}
				}
			}
		}
	}
	if (fullPath != null) {
		int separatorIndex = fullPath.lastIndexOf (SEPARATOR);
		fileName = fullPath.substring (separatorIndex + 1);
		filterPath = fullPath.substring (0, separatorIndex);
		int fileExtensionIndex = fileName.indexOf(FILE_EXTENSION_SEPARATOR);
		if ((style & SWT.SAVE) != 0 && fileExtensionIndex == -1 && filterIndex != -1) {
			if (filterExtensions.length > filterIndex) {
				String selection = filterExtensions [filterIndex];
				int length = selection.length ();
				int index = selection.indexOf (EXTENSION_SEPARATOR);
				if (index == -1) index = length;
				String extension = selection.substring (0, index).trim ();
				if (!extension.equals ("*") && !extension.equals ("*.*")) {
					if (extension.startsWith ("*.")) extension = extension.substring (1);
					fullPath = fullPath + extension;
				}	
			}
		}
	}
	return fullPath;
}

/**
 * Returns the path of the first file that was
 * selected in the dialog relative to the filter path, or an
 * empty string if no such file has been selected.
 * 
 * @return the relative path of the file
 */
public String getFileName () {
	return fileName;
}
/**
 * Returns a (possibly empty) array with the paths of all files
 * that were selected in the dialog relative to the filter path.
 * 
 * @return the relative paths of the files
 */
public String [] getFileNames () {
	return fileNames;
}
/**
 * Returns the file extensions which the dialog will
 * use to filter the files it shows.
 *
 * @return the file extensions filter
 */
public String [] getFilterExtensions () {
	return filterExtensions;
}
/**
 * Get the 0-based index of the file extension filter
 * which was selected by the user, or -1 if no filter
 * was selected.
 * <p>
 * This is an index into the FilterExtensions array and
 * the FilterNames array.
 * </p>
 *
 * @return index the file extension filter index
 * 
 * @see #getFilterExtensions
 * @see #getFilterNames
 * 
 * @since 3.4
 */
public int getFilterIndex () {
	return filterIndex;
}
/**
 * Returns the names that describe the filter extensions
 * which the dialog will use to filter the files it shows.
 *
 * @return the list of filter names
 */
public String [] getFilterNames () {
	return filterNames;
}
/**
 * Returns the directory path that the dialog will use, or an empty
 * string if this is not set.  File names in this path will appear
 * in the dialog, filtered according to the filter extensions.
 *
 * @return the directory path string
 * 
 * @see #setFilterExtensions
 */
public String getFilterPath () {
	return filterPath;
}
/**
 * Returns the flag that the dialog will use to
 * determine whether to prompt the user for file
 * overwrite if the selected file already exists.
 *
 * @return true if the dialog will prompt for file overwrite, false otherwise
 * 
 * @since 3.4
 */
public boolean getOverwrite () {
	return overwrite;
}
/**
 * Makes the dialog visible and brings it to the front
 * of the display.
 *
 * @return a string describing the absolute path of the first selected file,
 *         or null if the dialog was cancelled or an error occurred
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the dialog has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the dialog</li>
 * </ul>
 */
public String open () {
		return openChooserDialog ();
}
String openChooserDialog () {
	byte [] titleBytes = Converter.wcsToMbcs (null, title, true);
	int action = (style & SWT.SAVE) != 0 ?
		OS.GTK_FILE_CHOOSER_ACTION_SAVE :
		OS.GTK_FILE_CHOOSER_ACTION_OPEN;
	long /*int*/ shellHandle = parent.topHandle ();
	Display display = parent != null ? parent.getDisplay (): Display.getCurrent ();
	if (display.getDismissalAlignment() == SWT.RIGHT) {
		handle = OS.gtk_file_chooser_dialog_new (titleBytes, shellHandle, action, OS.GTK_STOCK_CANCEL (), OS.GTK_RESPONSE_CANCEL, OS.GTK_STOCK_OK (), OS.GTK_RESPONSE_OK, 0);
	} else {
		handle = OS.gtk_file_chooser_dialog_new (titleBytes, shellHandle, action, OS.GTK_STOCK_OK (), OS.GTK_RESPONSE_OK, OS.GTK_STOCK_CANCEL (), OS.GTK_RESPONSE_CANCEL, 0);
	}
	if (handle == 0) error (SWT.ERROR_NO_HANDLES);
	OS.gtk_window_set_modal (handle, true);
	if (OS.GTK_VERSION >= OS.VERSION (2, 10, 0)) {
		long /*int*/ group = OS.gtk_window_get_group(0);
		OS.gtk_window_group_add_window (group, handle);
	}
	long /*int*/ pixbufs = OS.gtk_window_get_icon_list (shellHandle);
	if (pixbufs != 0) {
		OS.gtk_window_set_icon_list (handle, pixbufs);
		OS.g_list_free (pixbufs);
	}
	if (uriMode) {
		OS.gtk_file_chooser_set_local_only (handle, false);
	}
	presetChooserDialog ();
	display.addIdleProc ();
	String answer = null;
	Dialog oldModal = null;
	if (OS.gtk_window_get_modal (handle)) {
		oldModal = display.getModalDialog ();
		display.setModalDialog (this);
	}
	int signalId = 0;
	long /*int*/ hookId = 0;
	if ((style & SWT.RIGHT_TO_LEFT) != 0) {
		signalId = OS.g_signal_lookup (OS.map, OS.GTK_TYPE_WIDGET());
		hookId = OS.g_signal_add_emission_hook (signalId, 0, display.emissionProc, handle, 0);
	}	
	int response = OS.gtk_dialog_run (handle);
	/*
	* This call to gdk_threads_leave() is a temporary work around
	* to avoid deadlocks when gdk_threads_init() is called by native
	* code outside of SWT (i.e AWT, etc). It ensures that the current
	* thread leaves the GTK lock acquired by the function above. 
	*/
	OS.gdk_threads_leave();
	if ((style & SWT.RIGHT_TO_LEFT) != 0) {
		OS.g_signal_remove_emission_hook (signalId, hookId);
	}
	if (OS.gtk_window_get_modal (handle)) {
		display.setModalDialog (oldModal);
	}
	if (response == OS.GTK_RESPONSE_OK) {
		answer = computeResultChooserDialog ();
	}
	display.removeIdleProc ();
	OS.gtk_widget_destroy (handle);
	return answer;
}
void presetChooserDialog () {
	/* MULTI is only valid if the native dialog's action is Open */   
	if ((style & (SWT.SAVE | SWT.MULTI)) == SWT.MULTI) {
		OS.gtk_file_chooser_set_select_multiple (handle, true);
	}
	if (filterPath == null) filterPath = "";
	if (fileName == null) fileName = "";
	if ((style & SWT.SAVE) != 0) {
		if (filterPath.length () > 0) {
			if (uriMode) {
				byte [] buffer = Converter.wcsToMbcs (null, filterPath, true);
				OS.gtk_file_chooser_set_current_folder_uri (handle, buffer);
			} else {
				/* filename must be a full path */
				byte [] buffer = Converter.wcsToMbcs (null, SEPARATOR + filterPath, true);
				
				/*
				* Bug in GTK. GtkFileChooser may crash on GTK versions 2.4.10 to 2.6
				* when setting a file name that is not a true canonical path. 
				* The fix is to use the canonical path.
				*/
				long /*int*/ ptr = OS.realpath (buffer, null);
				OS.gtk_file_chooser_set_current_folder (handle, ptr);
				OS.g_free (ptr);
			}
		}
		if (fileName.length () > 0) {
			byte [] buffer = Converter.wcsToMbcs (null, fileName, true);
			OS.gtk_file_chooser_set_current_name (handle, buffer);
		}
	} else {
		StringBuffer stringBuffer = new StringBuffer();
		if (filterPath.length () > 0) {
			if (!uriMode) {
				/* filename must be a full path */
				stringBuffer.append(SEPARATOR);
			}
			stringBuffer.append(filterPath);
			stringBuffer.append(SEPARATOR);
		}
		if (fileName.length () > 0) {
			stringBuffer.append(fileName);
		}
		byte [] buffer = Converter.wcsToMbcs (null, stringBuffer.toString(), true);
		if (uriMode) {
			OS.gtk_file_chooser_set_uri (handle, buffer);
		} else {
			/*
			* Bug in GTK. GtkFileChooser may crash on GTK versions 2.4.10 to 2.6
			* when setting a file name that is not a true canonical path. 
			* The fix is to use the canonical path.
			*/
			long /*int*/ ptr = OS.realpath (buffer, null);
			if (ptr != 0) {
				if (fileName.length() > 0) {
					OS.gtk_file_chooser_set_filename (handle, ptr);
				} else { 
					OS.gtk_file_chooser_set_current_folder (handle, ptr);	
				}
				OS.g_free (ptr);
			}
		}
	}
	
	/* Set overwrite mode */
	if ((style & SWT.SAVE) != 0) {
		if (OS.GTK_VERSION >= OS.VERSION (2, 8, 0)) {
			OS.gtk_file_chooser_set_do_overwrite_confirmation (handle, overwrite);
		}
	}

	/* Set the extension filters */
	if (filterNames == null) filterNames = new String [0];
	if (filterExtensions == null) filterExtensions = new String [0];
	long /*int*/ initialFilter = 0;
	for (int i = 0; i < filterExtensions.length; i++) {
		if (filterExtensions [i] != null) {
			long /*int*/ filter = OS.gtk_file_filter_new ();
			if (filterNames.length > i && filterNames [i] != null) {
				byte [] name = Converter.wcsToMbcs (null, filterNames [i], true);
				OS.gtk_file_filter_set_name (filter, name);
			} else {
				byte [] name = Converter.wcsToMbcs (null, filterExtensions [i], true);
				OS.gtk_file_filter_set_name (filter, name);
			}
			int start = 0;
			int index = filterExtensions [i].indexOf (EXTENSION_SEPARATOR);
			while (index != -1) {
				String current = filterExtensions [i].substring (start, index);
				byte [] filterString = Converter.wcsToMbcs (null, current, true);
				OS.gtk_file_filter_add_pattern (filter, filterString);
				start = index + 1;
				index = filterExtensions [i].indexOf (EXTENSION_SEPARATOR, start);
			}
			String current = filterExtensions [i].substring (start);
			byte [] filterString = Converter.wcsToMbcs (null, current, true);
			OS.gtk_file_filter_add_pattern (filter, filterString);
			OS.gtk_file_chooser_add_filter (handle, filter);
			if (i == filterIndex) {
				initialFilter = filter;
			}
		}
	}
	if (initialFilter != 0) {
		OS.gtk_file_chooser_set_filter(handle, initialFilter);
	}
	fullPath = null;
	fileNames = new String [0];
}
/**
 * Set the initial filename which the dialog will
 * select by default when opened to the argument,
 * which may be null.  The name will be prefixed with
 * the filter path when one is supplied.
 * 
 * @param string the file name
 */
public void setFileName (String string) {
	fileName = string;
}
/**
 * Set the file extensions which the dialog will
 * use to filter the files it shows to the argument,
 * which may be null.
 * <p>
 * The strings are platform specific. For example, on
 * some platforms, an extension filter string is typically
 * of the form "*.extension", where "*.*" matches all files.
 * For filters with multiple extensions, use semicolon as
 * a separator, e.g. "*.jpg;*.png".
 * </p>
 *
 * @param extensions the file extension filter
 * 
 * @see #setFilterNames to specify the user-friendly
 * names corresponding to the extensions
 */
public void setFilterExtensions (String [] extensions) {
	filterExtensions = extensions;
}
/**
 * Set the 0-based index of the file extension filter
 * which the dialog will use initially to filter the files
 * it shows to the argument.
 * <p>
 * This is an index into the FilterExtensions array and
 * the FilterNames array.
 * </p>
 *
 * @param index the file extension filter index
 * 
 * @see #setFilterExtensions
 * @see #setFilterNames
 * 
 * @since 3.4
 */
public void setFilterIndex (int index) {
	filterIndex = index;
}
/**
 * Sets the names that describe the filter extensions
 * which the dialog will use to filter the files it shows
 * to the argument, which may be null.
 * <p>
 * Each name is a user-friendly short description shown for
 * its corresponding filter. The <code>names</code> array must
 * be the same length as the <code>extensions</code> array.
 * </p>
 *
 * @param names the list of filter names, or null for no filter names
 * 
 * @see #setFilterExtensions
 */
public void setFilterNames (String [] names) {
	filterNames = names;
}
/**
 * Sets the directory path that the dialog will use
 * to the argument, which may be null. File names in this
 * path will appear in the dialog, filtered according
 * to the filter extensions. If the string is null,
 * then the operating system's default filter path
 * will be used.
 * <p>
 * Note that the path string is platform dependent.
 * For convenience, either '/' or '\' can be used
 * as a path separator.
 * </p>
 *
 * @param string the directory path
 * 
 * @see #setFilterExtensions
 */
public void setFilterPath (String string) {
	filterPath = string;
}

/**
 * Sets the flag that the dialog will use to
 * determine whether to prompt the user for file
 * overwrite if the selected file already exists.
 *
 * @param overwrite true if the dialog will prompt for file overwrite, false otherwise
 * 
 * @since 3.4
 */
public void setOverwrite (boolean overwrite) {
	this.overwrite = overwrite;
}
/* Sets URI Mode.
 * 
 * When the FileDialog is in URI mode it returns
 * a URI (instead of a file name) for the following
 * methods: open() and getFilterPath().
 * The input argment for setFilterPath() should also 
 * be a URI.
 */
/*public*/ void setURIMode (boolean uriMode) {
	this.uriMode = uriMode;
}
}
