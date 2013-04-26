/*******************************************************************************
 * Copyright (c) 2005, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.browser;

import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.internal.*;
import org.eclipse.swt.internal.mozilla.*;
import org.eclipse.swt.internal.win32.*;
import org.eclipse.swt.widgets.*;

class MozillaDelegate {
	Browser browser;
	Vector childWindows = new Vector (9);
	static long /*int*/ MozillaProc;
	static Callback SubclassProc;
	
MozillaDelegate (Browser browser) {
	super ();
	this.browser = browser;
}

static Browser findBrowser (long /*int*/ handle) {
	Display display = Display.getCurrent ();
	return (Browser)display.findWidget (handle);
}

static String getCacheParentPath () {
	/* Use the character encoding for the default locale */
	TCHAR buffer = new TCHAR (0, OS.MAX_PATH);
	if (OS.SHGetFolderPath (0, OS.CSIDL_LOCAL_APPDATA, 0, OS.SHGFP_TYPE_CURRENT, buffer) == OS.S_OK) {
		return buffer.toString (0, buffer.strlen ()) + Mozilla.SEPARATOR_OS + "eclipse"; //$NON-NLS-1$
	}
	return getProfilePath ();
}

static String[] getJSLibraryNames () {
	return new String[] {"mozjs.dll", "xul.dll"}; //$NON-NLS-1$ //$NON-NLS-2$
}

static String getJSLibraryName_Pre4 () {
	return "js3250.dll"; //$NON-NLS-1$
}

static String getLibraryName () {
	return "xpcom.dll"; //$NON-NLS-1$
}

static String getProfilePath () {
	String baseDir;
	/* Use the character encoding for the default locale */
	TCHAR buffer = new TCHAR (0, OS.MAX_PATH);
	if (OS.SHGetFolderPath (0, OS.CSIDL_APPDATA, 0, OS.SHGFP_TYPE_CURRENT, buffer) == OS.S_OK) {
		baseDir = buffer.toString (0, buffer.strlen ());
	} else {
		baseDir = System.getProperty("user.home"); //$NON-NLS-1$
	}
	return baseDir + Mozilla.SEPARATOR_OS + "Mozilla" + Mozilla.SEPARATOR_OS + "eclipse"; //$NON-NLS-1$ //$NON-NLS-2$
}

static String getSWTInitLibraryName () {
	return "swt-xulrunner"; //$NON-NLS-1$
}

static void loadAdditionalLibraries (String mozillaPath) {
}

static char[] mbcsToWcs (String codePage, byte[] buffer) {
	char[] chars = new char[buffer.length];
	int charCount = OS.MultiByteToWideChar (OS.CP_ACP, OS.MB_PRECOMPOSED, buffer, buffer.length, chars, chars.length);
	if (charCount == chars.length) return chars;
	char[] result = new char[charCount];
	System.arraycopy (chars, 0, result, 0, charCount);
	return result;
}

static boolean needsSpinup () {
	return false;
}

static byte[] wcsToMbcs (String codePage, String string, boolean terminate) {
	int byteCount;
	char[] chars = new char[string.length()];
	string.getChars (0, chars.length, chars, 0);
	byte[] bytes = new byte[byteCount = chars.length * 2 + (terminate ? 1 : 0)];
	byteCount = OS.WideCharToMultiByte (OS.CP_ACP, 0, chars, chars.length, bytes, byteCount, null, null);
	if (terminate) {
		byteCount++;
	} else {
		if (bytes.length != byteCount) {
			byte[] result = new byte[byteCount];
			System.arraycopy (bytes, 0, result, 0, byteCount);
			bytes = result;
		}
	}
	return bytes;
}

static long /*int*/ windowProc (long /*int*/ hwnd, long /*int*/ msg, long /*int*/ wParam, long /*int*/ lParam) {
	switch ((int)/*64*/msg) {
		case OS.WM_ERASEBKGND:
			RECT rect = new RECT ();
			OS.GetClientRect (hwnd, rect);
			OS.FillRect (wParam, rect, OS.GetSysColorBrush (OS.COLOR_WINDOW));
			break;
	}
	return OS.CallWindowProc (MozillaProc, hwnd, (int)/*64*/msg, wParam, lParam);
}

void addWindowSubclass () {
	long /*int*/ hwndChild = OS.GetWindow (browser.handle, OS.GW_CHILD);
	if (SubclassProc == null) {
		SubclassProc = new Callback (MozillaDelegate.class, "windowProc", 4); //$NON-NLS-1$
		MozillaProc = OS.GetWindowLongPtr (hwndChild, OS.GWL_WNDPROC);
	}
	OS.SetWindowLongPtr (hwndChild, OS.GWL_WNDPROC, SubclassProc.getAddress ());
}

int createBaseWindow (nsIBaseWindow baseWindow) {
	return baseWindow.Create ();
}

long /*int*/ getHandle () {
	return browser.handle;
}

long /*int*/ getSiteWindow () {
	/*
	* As of XULRunner 4, XULRunner's printing facilities on Windows destroy
	* the HWND that is returned from here once the print dialog is dismissed
	* (originating bug: https://bugzilla.mozilla.org/show_bug.cgi?id=588735 ).
	* For this scenario it is now expected that the handle that is returned
	* here is a child of the browser handle, not the browser handle itself.
	*
	* The other scenario that requests this handle is the Mozilla.getBrowser()
	* implementation.  This method's GetSiteWindow() invocation is surrounded
	* by boolean flags to help differentiate it from the printing scenario,
	* since Mozilla.getBrowser() does not destroy the handle it receives back.
	*
	* All children that are created here are stored and then destroyed once
	* the current page is left.  This is guard code that should only be needed
	* if Mozilla.getSiteWindow() is ever invoked by a path other than one of
	* the two described above. 
	*/
	if (Mozilla.IsPre_4 || Mozilla.IsGettingSiteWindow) {
		return getHandle ();
	}

	Composite child = new Composite (browser, SWT.NONE);
	childWindows.addElement (child);
	return child.handle;
}

void handleFocus () {
}

void handleMouseDown () {
}

boolean hookEnterExit () {
	return true;
}

void init () {
	if (!Mozilla.IsPre_4) {
		/*
		* In XULRunner versions > 4, sending WM_GETDLGCODE to a WM_KEYDOWN's MSG hwnd answers 0
		* instead of the expected DLGC_WANTALLKEYS.  This causes the default traversal framework
		* perform traversals outside of the Browser when it should not.  Hook a Traverse listener
		* to work around these problems.
		*/
		browser.addListener (SWT.Traverse, new Listener () {
			public void handleEvent (Event event) {
				switch (event.detail) {
					case SWT.TRAVERSE_RETURN:
					case SWT.TRAVERSE_ESCAPE: {
						/* always veto the traversal */
						event.doit = false;
						break;
					}
					case SWT.TRAVERSE_TAB_NEXT:
					case SWT.TRAVERSE_TAB_PREVIOUS: {
						/* veto the traversal whenever an element in the browser has focus */
						long /*int*/[] result = new long /*int*/[1];
						int rc = XPCOM.NS_GetServiceManager (result);
						if (rc != XPCOM.NS_OK) Mozilla.error (rc);
						if (result[0] == 0) Mozilla.error (XPCOM.NS_NOINTERFACE);
						nsIServiceManager serviceManager = new nsIServiceManager (result[0]);
						result[0] = 0;
						byte[] aContractID = MozillaDelegate.wcsToMbcs (null, XPCOM.NS_FOCUSMANAGER_CONTRACTID, true);
						rc = serviceManager.GetServiceByContractID (aContractID, nsIFocusManager.NS_IFOCUSMANAGER_10_IID, result);
						serviceManager.Release ();

						if (rc == XPCOM.NS_OK && result[0] != 0) {
							nsIFocusManager focusManager = new nsIFocusManager (result[0]);
							result[0] = 0;
							rc = focusManager.GetFocusedElement (result);
							focusManager.Release ();
							event.doit = result[0] == 0;
							if (rc == XPCOM.NS_OK && result[0] != 0) {
								new nsISupports (result[0]).Release ();
							}
						}
						break;
					}
				}
			}
		});

		/* children created in getSiteHandle() should be destroyed whenever a page is left */
		browser.addLocationListener (new LocationAdapter () {
			public void changing (LocationEvent event) {
				Enumeration enumeration = childWindows.elements ();
				while (enumeration.hasMoreElements ()) {
					((Composite)enumeration.nextElement ()).dispose ();
				}
				childWindows.clear ();
			}
		});
	}
}

void onDispose (long /*int*/ embedHandle) {
	removeWindowSubclass ();
	childWindows = null;
	browser = null;
}

void removeWindowSubclass () {
	if (SubclassProc == null) return;
	long /*int*/ hwndChild = OS.GetWindow (browser.handle, OS.GW_CHILD);
	OS.SetWindowLongPtr (hwndChild, OS.GWL_WNDPROC, MozillaProc);
}

boolean sendTraverse () {
	return false;
}

void setSize (long /*int*/ embedHandle, int width, int height) {
}
}
