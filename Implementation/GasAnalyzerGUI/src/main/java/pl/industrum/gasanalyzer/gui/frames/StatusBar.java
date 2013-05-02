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
	private Label lblStatus;
	private ProgressBar progressBar;
	private GridData compositeData;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public StatusBar( Composite parent, int style )
	{
		super( parent, style );
		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
				true, false );
		compositeData.horizontalSpan = 6;
		this.setLayoutData( compositeData );
		setLayout(new FillLayout(SWT.HORIZONTAL));

		lblStatus = new Label( this, SWT.NONE );
		lblStatus.setText( "" ); //$NON-NLS-1$
		
		progressBar = new ProgressBar( this, SWT.BORDER );
		progressBar.setVisible( false );
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	@SuppressWarnings( "unused" )
	private void hide()
	{
		this.setVisible( false );
	}

	@SuppressWarnings( "unused" )
	private void show()
	{
		this.setVisible( true );
	}

	public void hideProgressBar()
	{
		this.progressBar.setVisible( false );
	}

	public void showProgressBar()
	{
		this.progressBar.setVisible( true );
	}

	public void setProgress( int progress )
	{
		this.progressBar.setVisible( true );
		this.progressBar.setSelection( progress );
	}

	public void setStatusText( String status )
	{
		this.lblStatus.setText( Messages.getString( "StatusBar.lblStatus.text" ) + status );
	}
}
