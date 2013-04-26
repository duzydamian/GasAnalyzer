/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Drag and Drop example snippet: drag a URL between two labels.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet284 {

public static void main (String [] args) {
	
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setText("URLTransfer");
	shell.setLayout(new FillLayout());
	final Label label1 = new Label (shell, SWT.BORDER);
	label1.setText ("http://www.eclipse.org");
	final Label label2 = new Label (shell, SWT.BORDER);
	setDragSource (label1);
	setDropTarget (label2);
	shell.setSize(600, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
public static void setDragSource (final Label label) {
	int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
	final DragSource source = new DragSource (label, operations);
	source.setTransfer(new Transfer[] {URLTransfer.getInstance()});
	source.addDragListener (new DragSourceListener () {
		public void dragStart(DragSourceEvent e) {
		}
		public void dragSetData(DragSourceEvent e) {
			e.data = label.getText();
		}
		public void dragFinished(DragSourceEvent event) {
		}
	});
}

public static void setDropTarget (final Label label) {
	int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
	DropTarget target = new DropTarget(label, operations);
	target.setTransfer(new Transfer[] {URLTransfer.getInstance()});
	target.addDropListener (new DropTargetAdapter() {
		public void dragEnter(DropTargetEvent e) {
			if (e.detail == DND.DROP_NONE)
				e.detail = DND.DROP_LINK;
		}
		public void dragOperationChanged(DropTargetEvent e) {
			if (e.detail == DND.DROP_NONE)
				e.detail = DND.DROP_LINK;
		}
		public void drop(DropTargetEvent event) {
			if (event.data == null) {
				event.detail = DND.DROP_NONE;
				return;
			}
			label.setText(((String) event.data));
		}
	});
}
}
