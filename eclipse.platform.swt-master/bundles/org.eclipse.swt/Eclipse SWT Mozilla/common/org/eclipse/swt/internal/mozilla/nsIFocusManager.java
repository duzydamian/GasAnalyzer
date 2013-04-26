/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Mozilla Communicator client code, released March 31, 1998.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by Netscape are Copyright (C) 1998-1999
 * Netscape Communications Corporation.  All Rights Reserved.
 *
 * Contributor(s):
 *
 * IBM
 * -  Binding to permit interfacing between Mozilla and SWT
 * -  Copyright (C) 2011, 2012 IBM Corp.  All Rights Reserved.
 *
 * ***** END LICENSE BLOCK ***** */
package org.eclipse.swt.internal.mozilla;

public class nsIFocusManager extends nsISupports {

	static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + (IsXULRunner10 ? 18 : 17);

	public static final String NS_IFOCUSMANAGER_IID_STR =
			"cd6040a8-243f-412a-8a16-0bf2aa1083b9";
	
	public static final String NS_IFOCUSMANAGER_10_IID_STR =
			"51db277b-7ee7-4bce-9b84-fd2efcd2c8bd";

	public static final nsID NS_IFOCUSMANAGER_IID =
		new nsID(NS_IFOCUSMANAGER_IID_STR);
	
	public static final nsID NS_IFOCUSMANAGER_10_IID =
			new nsID(NS_IFOCUSMANAGER_10_IID_STR);

	public nsIFocusManager(long /*int*/ address) {
		super(address);
	}

	public int GetActiveWindow(long /*int*/[] aActiveWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), aActiveWindow);
	}

	public int SetActiveWindow(long /*int*/ aActiveWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), aActiveWindow);
	}

	public int GetFocusedWindow(long /*int*/[] aFocusedWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), aFocusedWindow);
	}

	public int SetFocusedWindow(long /*int*/ aFocusedWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), aFocusedWindow);
	}

	public int GetFocusedElement(long /*int*/[] aFocusedElement) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), aFocusedElement);
	}

	public int GetLastFocusMethod(long /*int*/ window, int[] _retval) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), window, _retval);
	}

	public int SetFocus(long /*int*/ aElement, int aFlags) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), aElement, aFlags);
	}

	public int MoveFocus(long /*int*/ aWindow, long /*int*/ aStartElement, int aType, int aFlags, long /*int*/[] _retval) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), aWindow, aStartElement, aType, aFlags, _retval);
	}

	public int ClearFocus(long /*int*/ aWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), aWindow);
	}

	public int GetFocusedElementForWindow(long /*int*/ aWindow, int aDeep, long /*int*/[] aFocusedWindow, long /*int*/[] _retval) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), aWindow, aDeep, aFocusedWindow, _retval);
	}

	public int MoveCaretToFocus(long /*int*/ aWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), aWindow);
	}

	public static final int FLAG_RAISE = 1;
	public static final int FLAG_NOSCROLL = 2;
	public static final int FLAG_NOSWITCHFRAME = 4;
	public static final int FLAG_BYMOUSE = 4096;
	public static final int FLAG_BYKEY = 8192;
	public static final int FLAG_BYMOVEFOCUS = 16384;
	public static final int MOVEFOCUS_FORWARD = 1;
	public static final int MOVEFOCUS_BACKWARD = 2;
	public static final int MOVEFOCUS_FORWARDDOC = 3;
	public static final int MOVEFOCUS_BACKWARDDOC = 4;
	public static final int MOVEFOCUS_FIRST = 5;
	public static final int MOVEFOCUS_LAST = 6;
	public static final int MOVEFOCUS_ROOT = 7;
	public static final int MOVEFOCUS_CARET = 8;

	public int WindowRaised(long /*int*/ aWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), aWindow);
	}

	public int WindowLowered(long /*int*/ aWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), aWindow);
	}

	public int ContentRemoved(long /*int*/ aDocument, long /*int*/ aElement) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 14, getAddress(), aDocument, aElement);
	}

	public int WindowShown(long /*int*/ aWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 15, getAddress(), aWindow);
	}

	public int WindowHidden(long /*int*/ aWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 16, getAddress(), aWindow);
	}

	public int FireDelayedEvents(long /*int*/ aDocument) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 17, getAddress(), aDocument);
	}
	
	public int FocusPlugin(long /*int*/ aPlugin) {
		if (!IsXULRunner10) return XPCOM.NS_COMFALSE;
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 18, getAddress(), aPlugin);
	}
}
