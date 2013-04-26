/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others. All rights reserved.
 * The contents of this file are made available under the terms
 * of the GNU Lesser General Public License (LGPL) Version 2.1 that
 * accompanies this distribution (lgpl-v21.txt).  The LGPL is also
 * available at http://www.gnu.org/licenses/lgpl.html.  If the version
 * of the LGPL at http://www.gnu.org is different to the version of
 * the LGPL accompanying this distribution and there is any conflict
 * between the two license versions, the terms of the LGPL accompanying
 * this distribution shall govern.
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal.gnome;

import org.eclipse.swt.internal.*;

public class GNOME extends Platform {
	static {
		Library.loadLibrary("swt-gnome");
	}

public static final int GNOME_FILE_DOMAIN_PIXMAP = 4;
public static final int GNOME_ICON_LOOKUP_FLAGS_NONE = 0;
public static final int GNOME_PARAM_NONE = 0;
public static final int GNOME_VFS_MIME_APPLICATION_ARGUMENT_TYPE_URIS = 0;
public static final int GNOME_VFS_MIME_IDENTICAL = 1;
public static final int GNOME_VFS_MIME_PARENT = 2;
public static final int GNOME_VFS_MIME_UNRELATED = 0;
public static final int GNOME_VFS_OK = 0;
public static final int GNOME_VFS_MAKE_URI_DIR_NONE = 0;
public static final int GNOME_VFS_MAKE_URI_DIR_HOMEDIR = 1<<0;
public static final int GNOME_VFS_MAKE_URI_DIR_CURRENT = 1<<1;

/** 64 bit */
public static final native int GnomeVFSMimeApplication_sizeof();

/** Natives */

/**
 * @param icon_theme cast=(GnomeIconTheme *)
 * @param thumbnail_factory cast=(GnomeThumbnailFactory *)
 * @param file_uri cast=(const char *)
 * @param custom_icon cast=(const char *)
 * @param file_info cast=(GnomeVFSFileInfo *)
 * @param mime_type cast=(const char *)
 * @param flags cast=(GnomeIconLookupFlags)
 * @param result cast=(GnomeIconLookupResultFlags *)
 */
public static final native long /*int*/ _gnome_icon_lookup(long /*int*/ icon_theme, long /*int*/ thumbnail_factory, byte[] file_uri, byte[] custom_icon, long /*int*/ file_info, byte[] mime_type, int flags, int[] result);
public static final long /*int*/ gnome_icon_lookup(long /*int*/ icon_theme, long /*int*/ thumbnail_factory, byte[] file_uri, byte[] custom_icon, long /*int*/ file_info, byte[] mime_type, int flags, int[] result) {
	lock.lock();
	try {
		return _gnome_icon_lookup(icon_theme, thumbnail_factory, file_uri, custom_icon, file_info, mime_type, flags, result);
	} finally {
		lock.unlock();
	}
}
/**
 * @param theme cast=(GnomeIconTheme *)
 * @param icon_name cast=(const char *)
 * @param icon_data cast=(const GnomeIconData **)
 */
public static final native long /*int*/ _gnome_icon_theme_lookup_icon(long /*int*/ theme, long /*int*/ icon_name, int size, long /*int*/[] icon_data, int[] base_size);
public static final long /*int*/ gnome_icon_theme_lookup_icon(long /*int*/ theme, long /*int*/ icon_name, int size, long /*int*/[] icon_data, int[] base_size) {
	lock.lock();
	try {
		return _gnome_icon_theme_lookup_icon(theme, icon_name, size, icon_data, base_size);
	} finally {
		lock.unlock();
	}
}
public static final native long /*int*/ _gnome_icon_theme_new();
public static final long /*int*/ gnome_icon_theme_new() {
	lock.lock();
	try {
		return _gnome_icon_theme_new();
	} finally {
		lock.unlock();
	}
}

/** @param uri cast=(const char *) */
public static final native long /*int*/ _gnome_vfs_get_mime_type(long /*int*/ uri);
public static final long /*int*/ gnome_vfs_get_mime_type(long /*int*/ uri) {
	lock.lock();
	try {
		return _gnome_vfs_get_mime_type(uri);
	} finally {
		lock.unlock();
	}
}

public static final native boolean _gnome_vfs_init();
public static final boolean gnome_vfs_init() {
	lock.lock();
	try {
		return _gnome_vfs_init();
	} finally {
		lock.unlock();
	}
}
/** @param uri cast=(const char *) */
public static final native long /*int*/ _gnome_vfs_make_uri_from_input(byte[] uri);
public static final long /*int*/ gnome_vfs_make_uri_from_input(byte[] uri) {
	lock.lock();
	try {
		return _gnome_vfs_make_uri_from_input(uri);
	} finally {
		lock.unlock();
	}
}
/**
 * @method flags=dynamic
 * @param uri cast=(const char *)
 */
public static final native long /*int*/ _gnome_vfs_make_uri_from_input_with_dirs(byte[] uri, int dirs);
public static final long /*int*/ gnome_vfs_make_uri_from_input_with_dirs(byte[] uri, int dirs) {
	lock.lock();
	try {
		return _gnome_vfs_make_uri_from_input_with_dirs(uri, dirs);
	} finally {
		lock.unlock();
	}
}
/** @param application cast=(GnomeVFSMimeApplication *) */
public static final native void _gnome_vfs_mime_application_free(long /*int*/ application);
public static final void gnome_vfs_mime_application_free(long /*int*/ application) {
	lock.lock();
	try {
		_gnome_vfs_mime_application_free(application);
	} finally {
		lock.unlock();
	}
}
/** @param command_string cast=(const char *) */
public static final native boolean _gnome_vfs_is_executable_command_string(byte[] command_string);
public static final boolean gnome_vfs_is_executable_command_string(byte[] command_string) {
	lock.lock();
	try {
		return _gnome_vfs_is_executable_command_string(command_string);
	} finally {
		lock.unlock();
	}
}
/**
 * @method flags=dynamic
 * @param application cast=(GnomeVFSMimeApplication *)
 * @param uris cast=(GList *)
 */
public static final native int _gnome_vfs_mime_application_launch(long /*int*/ application, long /*int*/ uris);
public static final int gnome_vfs_mime_application_launch(long /*int*/ application, long /*int*/ uris) {	
	lock.lock();
	try {
		return _gnome_vfs_mime_application_launch(application, uris);
	} finally {
		lock.unlock();
	}
}

/** @param mimeType cast=(const char *) */
public static final native long /*int*/ _gnome_vfs_mime_get_default_application(byte[] mimeType);
public static final long /*int*/ gnome_vfs_mime_get_default_application(byte[] mimeType) {
	lock.lock();
	try {
		return _gnome_vfs_mime_get_default_application(mimeType);
	} finally {
		lock.unlock();
	}
}

/** @param file cast=(const char *) */
public static final native long /*int*/ _gnome_vfs_mime_type_from_name(byte[] file);
public static final long /*int*/ gnome_vfs_mime_type_from_name(byte[] file) {
	lock.lock();
	try {
		return _gnome_vfs_mime_type_from_name(file);
	} finally {
		lock.unlock();
	}
}
/** 
 * @param mime_type cast=(const char *)
 * @param base_mime_type cast=(const char *) 
 */
public static final native long /*int*/ _gnome_vfs_mime_type_get_equivalence(long /*int*/ mime_type, byte [] base_mime_type);
public static final long /*int*/ gnome_vfs_mime_type_get_equivalence(long /*int*/ mime_type, byte [] base_mime_type) {
	lock.lock();
	try {
		return _gnome_vfs_mime_type_get_equivalence(mime_type, base_mime_type);
	} finally {
		lock.unlock();
	}
}
/**
 * @method flags=dynamic
 * @param url cast=(const char *)
 */
public static final native int _gnome_vfs_url_show(long /*int*/ url);
public static final int gnome_vfs_url_show(long /*int*/ url) {
	lock.lock();
	try {
		return _gnome_vfs_url_show(url);
	} finally {
		lock.unlock();
	}
}
/**
 * @param dest cast=(void *),flags=no_in
 * @param src cast=(const void *)
 * @param count cast=(size_t)
 */
public static final native void memmove (GnomeVFSMimeApplication dest, long /*int*/ src, long /*int*/ count);
}
