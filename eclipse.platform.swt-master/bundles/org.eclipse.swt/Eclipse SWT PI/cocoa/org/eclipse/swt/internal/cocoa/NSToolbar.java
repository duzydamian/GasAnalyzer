/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal.cocoa;

public class NSToolbar extends NSObject {

public NSToolbar() {
	super();
}

public NSToolbar(long /*int*/ id) {
	super(id);
}

public NSToolbar(id id) {
	super(id);
}

public NSToolbar initWithIdentifier(NSString identifier) {
	long /*int*/ result = OS.objc_msgSend(this.id, OS.sel_initWithIdentifier_, identifier != null ? identifier.id : 0);
	return result == this.id ? this : (result != 0 ? new NSToolbar(result) : null);
}

public void insertItemWithItemIdentifier(NSString itemIdentifier, long /*int*/ index) {
	OS.objc_msgSend(this.id, OS.sel_insertItemWithItemIdentifier_atIndex_, itemIdentifier != null ? itemIdentifier.id : 0, index);
}

public boolean isVisible() {
	return OS.objc_msgSend_bool(this.id, OS.sel_isVisible);
}

public void removeItemAtIndex(long /*int*/ index) {
	OS.objc_msgSend(this.id, OS.sel_removeItemAtIndex_, index);
}

public void setAllowsUserCustomization(boolean allowCustomization) {
	OS.objc_msgSend(this.id, OS.sel_setAllowsUserCustomization_, allowCustomization);
}

public void setDelegate(id delegate) {
	OS.objc_msgSend(this.id, OS.sel_setDelegate_, delegate != null ? delegate.id : 0);
}

public void setDisplayMode(long /*int*/ displayMode) {
	OS.objc_msgSend(this.id, OS.sel_setDisplayMode_, displayMode);
}

public void setSelectedItemIdentifier(NSString itemIdentifier) {
	OS.objc_msgSend(this.id, OS.sel_setSelectedItemIdentifier_, itemIdentifier != null ? itemIdentifier.id : 0);
}

public void setVisible(boolean shown) {
	OS.objc_msgSend(this.id, OS.sel_setVisible_, shown);
}

}
