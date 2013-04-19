package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import pl.industrum.gasanalyzer.i18n.Messages;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class Device extends Composite
{
	GridData compositeData;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Device(Composite parent, int style, String name)
	{
		super(parent, style);
		compositeData = new GridData(GridData.FILL, GridData.GRAB_VERTICAL, true, false);
		compositeData.horizontalSpan = 6;
		this.setLayout(new FillLayout(SWT.HORIZONTAL));
		this.setLayoutData(compositeData);
		
		Group grpName = new Group(this, SWT.NONE);
		grpName.setText(name);
		grpName.setLayout(new GridLayout(2, false));
		
		Label lblStan = new Label(grpName, SWT.NONE);
		lblStan.setText(Messages.getString("Device.lblStan.text")); //$NON-NLS-1$
		
		Label lblOk = new Label(grpName, SWT.NONE);
		lblOk.setText(Messages.getString("Device.lblOk.text")); //$NON-NLS-1$
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}		
}
