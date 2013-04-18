package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TreeItem;

public class DeviceTree extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DeviceTree(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		
		Label lblSurveyStep = new Label(this, SWT.NONE);
		lblSurveyStep.setText("Krok");
		
		Spinner surveyStep = new Spinner(this, SWT.BORDER);
		surveyStep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnOk = new Button(this, SWT.NONE);
		btnOk.setText("OK");
		
		Tree deviceTree = new Tree(this, SWT.BORDER);
		deviceTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		
		TreeItem trtmWszystki = new TreeItem(deviceTree, SWT.NONE);
		trtmWszystki.setText("Wszystkie");
		
		TreeItem trtmUltramat = new TreeItem(trtmWszystki, SWT.NONE);
		trtmUltramat.setText("Ultramat23");
		
		TreeItem trtmUltramat_1 = new TreeItem(trtmWszystki, SWT.NONE);
		trtmUltramat_1.setText("Ultramat6");
		trtmWszystki.setExpanded(true);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
