/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.browser;

import org.eclipse.swt.*;
import org.eclipse.swt.internal.*;
import org.eclipse.swt.internal.mozilla.*;
import org.eclipse.swt.widgets.*;

class PromptAuth2 {
	XPCOMObject supports;
	XPCOMObject promptAuth;
	int refCount = 0;
	long /*int*/ parent;
	
PromptAuth2 () {
	createCOMInterfaces ();
}

int AddRef () {
	refCount++;
	return refCount;
}

void createCOMInterfaces () {
	/* Create each of the interfaces that this object implements */
	supports = new XPCOMObject (new int[] {2, 0, 0}) {
		public long /*int*/ method0 (long /*int*/[] args) {return QueryInterface (args[0], args[1]);}
		public long /*int*/ method1 (long /*int*/[] args) {return AddRef ();}
		public long /*int*/ method2 (long /*int*/[] args) {return Release ();}
	};
	
	promptAuth = new XPCOMObject (new int[] {2, 0, 0, 4, 6}) {
		public long /*int*/ method0 (long /*int*/[] args) {return QueryInterface (args[0], args[1]);}
		public long /*int*/ method1 (long /*int*/[] args) {return AddRef ();}
		public long /*int*/ method2 (long /*int*/[] args) {return Release ();}
		public long /*int*/ method3 (long /*int*/[] args) {return PromptAuth (args[0], (int)/*64*/args[1], args[2], args[3]);}
		public long /*int*/ method4 (long /*int*/[] args) {return AsyncPromptAuth (args[0], args[1], args[2], (int)/*64*/args[3], args[4], args[5]);}
	};
}

void disposeCOMInterfaces () {
	if (supports != null) {
		supports.dispose ();
		supports = null;
	}	
	if (promptAuth != null) {
		promptAuth.dispose ();
		promptAuth = null;
	}
}

long /*int*/ getAddress () {
	return promptAuth.getAddress ();
}

int QueryInterface (long /*int*/ riid, long /*int*/ ppvObject) {
	if (riid == 0 || ppvObject == 0) return XPCOM.NS_ERROR_NO_INTERFACE;
	nsID guid = new nsID ();
	XPCOM.memmove (guid, riid, nsID.sizeof);
	
	if (guid.Equals (nsISupports.NS_ISUPPORTS_IID)) {
		XPCOM.memmove (ppvObject, new long /*int*/[] {supports.getAddress ()}, C.PTR_SIZEOF);
		AddRef ();
		return XPCOM.NS_OK;
	}
	if (guid.Equals (nsIAuthPrompt2.NS_IAUTHPROMPT2_IID)) {
		XPCOM.memmove (ppvObject, new long /*int*/[] {promptAuth.getAddress ()}, C.PTR_SIZEOF);
		AddRef ();
		return XPCOM.NS_OK;
	}

	XPCOM.memmove (ppvObject, new long /*int*/[] {0}, C.PTR_SIZEOF);
	return XPCOM.NS_ERROR_NO_INTERFACE;
}
        	
int Release () {
	refCount--;
	if (refCount == 0) disposeCOMInterfaces ();
	return refCount;
}

Browser getBrowser () {
	if (parent == 0) return null;
	return Mozilla.getBrowser (parent);
}

void setParent(long /*int*/ aParent) {
	parent = aParent;
}

int PromptAuth(long /*int*/ aChannel, int level, long /*int*/ authInfo, long /*int*/ _retval) {
	nsIAuthInformation auth = new nsIAuthInformation (authInfo);

	Browser browser = getBrowser ();
	if (browser != null) {
		Mozilla mozilla = (Mozilla)browser.webBrowser;
		/*
		 * Do not invoke the listeners if this challenge has been failed too many
		 * times because a listener is likely giving incorrect credentials repeatedly
		 * and will do so indefinitely.
		 */
		if (mozilla.authCount++ < 3) {
			for (int i = 0; i < mozilla.authenticationListeners.length; i++) {
				AuthenticationEvent event = new AuthenticationEvent (browser);
				event.location = mozilla.lastNavigateURL;
				mozilla.authenticationListeners[i].authenticate (event);
				if (!event.doit) {
					XPCOM.memmove (_retval, new boolean[] {false});
					return XPCOM.NS_OK;
				}
				if (event.user != null && event.password != null) {
					nsEmbedString string = new nsEmbedString (event.user);
					int rc = auth.SetUsername (string.getAddress ());
					if (rc != XPCOM.NS_OK) SWT.error (rc);
					string.dispose ();
					string = new nsEmbedString (event.password);
					rc = auth.SetPassword (string.getAddress ());
					if (rc != XPCOM.NS_OK) SWT.error (rc);
					string.dispose ();
					XPCOM.memmove (_retval, new boolean[] {true});
					return XPCOM.NS_OK;
				}
			}
		}
	}

	/* no listener handled the challenge, so show an authentication dialog */

	String checkLabel = null;
	boolean[] checkValue = new boolean[1];
	String[] userLabel = new String[1], passLabel = new String[1];

	String title = SWT.getMessage ("SWT_Authentication_Required"); //$NON-NLS-1$

	/* get initial username and password values */

	long /*int*/ ptr = XPCOM.nsEmbedString_new ();
	int rc = auth.GetUsername (ptr);
	if (rc != XPCOM.NS_OK) SWT.error (rc);
	int length = XPCOM.nsEmbedString_Length (ptr);
	long /*int*/ buffer = XPCOM.nsEmbedString_get (ptr);
	char[] chars = new char[length];
	XPCOM.memmove (chars, buffer, length * 2);
	userLabel[0] = new String (chars);
	XPCOM.nsEmbedString_delete (ptr);

	ptr = XPCOM.nsEmbedString_new ();
	rc = auth.GetPassword (ptr);
	if (rc != XPCOM.NS_OK) SWT.error (rc);
	length = XPCOM.nsEmbedString_Length (ptr);
	buffer = XPCOM.nsEmbedString_get (ptr);
	chars = new char[length];
	XPCOM.memmove (chars, buffer, length * 2);
	passLabel[0] = new String (chars);
	XPCOM.nsEmbedString_delete (ptr);

	/* compute the message text */

	ptr = XPCOM.nsEmbedString_new ();
	rc = auth.GetRealm (ptr);
	if (rc != XPCOM.NS_OK) SWT.error (rc);
	length = XPCOM.nsEmbedString_Length (ptr);
	buffer = XPCOM.nsEmbedString_get (ptr);
	chars = new char[length];
	XPCOM.memmove (chars, buffer, length * 2);
	String realm = new String (chars);
	XPCOM.nsEmbedString_delete (ptr);

	nsIChannel channel = new nsIChannel (aChannel);
	long /*int*/[] uri = new long /*int*/[1];
	rc = channel.GetURI (uri);
	if (rc != XPCOM.NS_OK) SWT.error (rc);
	if (uri[0] == 0) Mozilla.error (XPCOM.NS_NOINTERFACE);

	nsIURI nsURI = new nsIURI (uri[0]);
	long /*int*/ host = XPCOM.nsEmbedCString_new ();
	rc = nsURI.GetHost (host);
	if (rc != XPCOM.NS_OK) SWT.error (rc);
	length = XPCOM.nsEmbedCString_Length (host);
	buffer = XPCOM.nsEmbedCString_get (host);
	byte[] bytes = new byte[length];
	XPCOM.memmove (bytes, buffer, length);
	String hostString = new String (bytes);
	XPCOM.nsEmbedCString_delete (host);
	nsURI.Release ();

	String message;
	if (realm.length () > 0 && hostString.length () > 0) {
		message = Compatibility.getMessage ("SWT_Enter_Username_and_Password", new String[] {realm, hostString}); //$NON-NLS-1$
	} else {
		message = ""; //$NON-NLS-1$
	}

	/* open the prompter */
	Shell shell = browser == null ? new Shell () : browser.getShell ();
	PromptDialog dialog = new PromptDialog (shell);
	boolean[] result = new boolean[1];
	dialog.promptUsernameAndPassword (title, message, checkLabel, userLabel, passLabel, checkValue, result);

	XPCOM.memmove (_retval, result);
	if (result[0]) {	/* User selected OK */
		nsEmbedString string = new nsEmbedString (userLabel[0]);
		rc = auth.SetUsername(string.getAddress ());
		if (rc != XPCOM.NS_OK) SWT.error (rc);
		string.dispose ();
		
		string = new nsEmbedString (passLabel[0]);
		rc = auth.SetPassword(string.getAddress ());
		if (rc != XPCOM.NS_OK) SWT.error (rc);
		string.dispose ();
	}

	return XPCOM.NS_OK;
}

int AsyncPromptAuth(long /*int*/ aChannel, long /*int*/ aCallback, long /*int*/ aContext, int level, long /*int*/ authInfo, long /*int*/ _retval) {
	return XPCOM.NS_ERROR_NOT_IMPLEMENTED;
}
}
