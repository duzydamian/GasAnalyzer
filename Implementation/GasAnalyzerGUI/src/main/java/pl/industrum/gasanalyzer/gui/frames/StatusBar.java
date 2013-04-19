package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import pl.industrum.gasanalyzer.i18n.Messages;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class StatusBar extends Composite
{
	Label lblStatus;
	ProgressBar progressBar;
	GridData compositeData;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StatusBar(Composite parent, int style)
	{
		super(parent, style);
		compositeData = new GridData(GridData.FILL, GridData.GRAB_VERTICAL, true, false);
		compositeData.horizontalSpan = 6;
		this.setLayout(new FillLayout(SWT.HORIZONTAL));
		this.setLayoutData(compositeData);
		
		lblStatus = new Label(this, SWT.BOLD);
		lblStatus.setText(Messages.getString("StatusBar.lblStatus.text")); //$NON-NLS-1$
		
		progressBar = new ProgressBar(this, SWT.BORDER);
		progressBar.setVisible(false);
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	@SuppressWarnings("unused")
	private void hide()
	{;
		this.setVisible(false);
	}
	
	@SuppressWarnings("unused")
	private void show()
	{
		this.setVisible(true);
	}
	
	public void hideProgressBar()
	{;
		this.progressBar.setVisible(false);
	}
	
	public void showProgressBar()
	{
		this.progressBar.setVisible(true);
	}
	
	public void setProgress(int progress)
	{
		this.progressBar.setVisible(true);
		this.progressBar.setSelection(progress);
	}
	
	public void setStatusText(String status)
	{
		this.lblStatus.setText(status);
	}
}
