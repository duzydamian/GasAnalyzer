package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TreeItem;
import pl.industrum.gasanalyzer.i18n.Messages;

public abstract class DeviceTree extends Composite
{

	private Tree deviceTree;
	private TreeItem trtmWszystki;
	private TreeItem trtmUltramat;
	private TreeItem trtmUltramat_1;
	private Label lblSurveyStep;
	private Spinner surveyStep;
	private Button btnOk;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DeviceTree( Composite parent, int style )
	{
		super( parent, style );
		setLayout( new GridLayout( 3, false ) );

		deviceTree = new Tree( this, SWT.BORDER );
		deviceTree.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true,
				3, 1 ) );

		trtmWszystki = new TreeItem( deviceTree, SWT.NONE );
		trtmWszystki.setText( Messages
				.getString( "DeviceTree.trtmWszystki.text(java.lang.String)" ) ); //$NON-NLS-1$

		trtmUltramat = new TreeItem( trtmWszystki, SWT.NONE );
		trtmUltramat.setText( Messages
				.getString( "DeviceTree.trtmUltramat.text(java.lang.String)" ) ); //$NON-NLS-1$

		trtmUltramat_1 = new TreeItem( trtmWszystki, SWT.NONE );
		trtmUltramat_1
				.setText( Messages
						.getString( "DeviceTree.trtmUltramat_1.text(java.lang.String)" ) ); //$NON-NLS-1$
		trtmWszystki.setExpanded( true );

		lblSurveyStep = new Label( this, SWT.NONE );
		lblSurveyStep.setText( Messages
				.getString( "DeviceTree.lblSurveyStep.text" ) ); //$NON-NLS-1$

		surveyStep = new Spinner( this, SWT.BORDER );
		surveyStep.setMinimum( 1 );
		surveyStep.setMaximum( 9999 );
		surveyStep.setSelection( 60 );
		surveyStep.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
				false, 1, 1 ) );
		surveyStep.addModifyListener( new ModifyListener()
		{
			public void modifyText( ModifyEvent arg0 )
			{
				btnOk.setEnabled( true );
			}
		} );

		btnOk = new Button( this, SWT.NONE );
		btnOk.setText( Messages.getString( "DeviceTree.btnOk.text" ) ); //$NON-NLS-1$
		btnOk.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				setSurveyStep(surveyStep.getSelection());
				btnOk.setEnabled( false );
			}
		} );
	}

	public abstract void setSurveyStep(int step);
	

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

}
