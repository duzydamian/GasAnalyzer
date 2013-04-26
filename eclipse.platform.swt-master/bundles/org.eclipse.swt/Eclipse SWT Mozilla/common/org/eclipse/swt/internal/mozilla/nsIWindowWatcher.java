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
 * -  Copyright (C) 2003, 2011 IBM Corp.  All Rights Reserved.
 *
 * ***** END LICENSE BLOCK ***** */
package org.eclipse.swt.internal.mozilla;

public class nsIWindowWatcher extends nsISupports {

	static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 11;

	public static final String NS_IWINDOWWATCHER_IID_STR =
		"002286a8-494b-43b3-8ddd-49e3fc50622b";

	public static final nsID NS_IWINDOWWATCHER_IID =
		new nsID(NS_IWINDOWWATCHER_IID_STR);

	public nsIWindowWatcher(long /*int*/ address) {
		super(address);
	}

	public int OpenWindow(long /*int*/ aParent, byte[] aUrl, byte[] aName, byte[] aFeatures, long /*int*/ aArguments, long /*int*/[] _retval) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), aParent, aUrl, aName, aFeatures, aArguments, _retval);
	}

	public int RegisterNotification(long /*int*/ aObserver) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), aObserver);
	}

	public int UnregisterNotification(long /*int*/ aObserver) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), aObserver);
	}

	public int GetWindowEnumerator(long /*int*/[] _retval) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), _retval);
	}

	public int GetNewPrompter(long /*int*/ aParent, long /*int*/[] _retval) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), aParent, _retval);
	}

	public int GetNewAuthPrompter(long /*int*/ aParent, long /*int*/[] _retval) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), aParent, _retval);
	}

	public int SetWindowCreator(long /*int*/ creator) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), creator);
	}

	public int GetChromeForWindow(long /*int*/ aWindow, long /*int*/[] _retval) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), aWindow, _retval);
	}

	public int GetWindowByName(char[] aTargetName, long /*int*/ aCurrentWindow, long /*int*/[] _retval) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), aTargetName, aCurrentWindow, _retval);
	}

	public int GetActiveWindow(long /*int*/[] aActiveWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), aActiveWindow);
	}

	public int SetActiveWindow(long /*int*/ aActiveWindow) {
		return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), aActiveWindow);
	}
}
