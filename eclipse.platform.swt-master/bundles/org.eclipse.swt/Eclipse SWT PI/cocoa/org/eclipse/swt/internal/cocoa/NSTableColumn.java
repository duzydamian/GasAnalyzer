/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal.cocoa;

public class NSTableColumn extends NSObject {

public NSTableColumn() {
	super();
}

public NSTableColumn(long /*int*/ id) {
	super(id);
}

public NSTableColumn(id id) {
	super(id);
}

public NSCell dataCell() {
	long /*int*/ result = OS.objc_msgSend(this.id, OS.sel_dataCell);
	return result != 0 ? new NSCell(result) : null;
}

public NSTableHeaderCell headerCell() {
	long /*int*/ result = OS.objc_msgSend(this.id, OS.sel_headerCell);
	return result != 0 ? new NSTableHeaderCell(result) : null;
}

public NSTableColumn initWithIdentifier(NSString identifier) {
	long /*int*/ result = OS.objc_msgSend(this.id, OS.sel_initWithIdentifier_, identifier != null ? identifier.id : 0);
	return result == this.id ? this : (result != 0 ? new NSTableColumn(result) : null);
}

public long /*int*/ resizingMask() {
	return OS.objc_msgSend(this.id, OS.sel_resizingMask);
}

public void setDataCell(NSCell cell) {
	OS.objc_msgSend(this.id, OS.sel_setDataCell_, cell != null ? cell.id : 0);
}

public void setEditable(boolean flag) {
	OS.objc_msgSend(this.id, OS.sel_setEditable_, flag);
}

public void setHeaderCell(NSCell cell) {
	OS.objc_msgSend(this.id, OS.sel_setHeaderCell_, cell != null ? cell.id : 0);
}

public void setIdentifier(NSString identifier) {
	OS.objc_msgSend(this.id, OS.sel_setIdentifier_, identifier != null ? identifier.id : 0);
}

public void setMinWidth(double /*float*/ minWidth) {
	OS.objc_msgSend(this.id, OS.sel_setMinWidth_, minWidth);
}

public void setResizingMask(long /*int*/ resizingMask) {
	OS.objc_msgSend(this.id, OS.sel_setResizingMask_, resizingMask);
}

public void setWidth(double /*float*/ width) {
	OS.objc_msgSend(this.id, OS.sel_setWidth_, width);
}

public double /*float*/ width() {
	return (double /*float*/)OS.objc_msgSend_fpret(this.id, OS.sel_width);
}

}
