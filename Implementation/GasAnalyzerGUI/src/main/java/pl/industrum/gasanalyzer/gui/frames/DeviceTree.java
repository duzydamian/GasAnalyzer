package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TreeItem;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.i18n.Messages;

public abstract class DeviceTree extends Composite
{

	private Tree deviceTree;	
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
		deviceTree.addListener (SWT.MenuDetect, new Listener () {
			public void handleEvent (Event event) {
				Menu menu = new Menu (deviceTree.getShell(), SWT.POP_UP);
				MenuItem item = new MenuItem (menu, SWT.PUSH);
				item.setText ("Połącz");
				item.addListener (SWT.Selection, new Listener () {
					public void handleEvent (Event e) {
						if( deviceTree.getSelection().length > 0 )
						{
							for( TreeItem treeItem: deviceTree.getSelection() )
							{
								System.out.println( treeItem.getText() );
								connectToDevice(treeItem.getText());								
							}							
						}
					}
				});
				menu.setLocation (event.x, event.y);
				menu.setVisible (true);
				while (!menu.isDisposed () && menu.isVisible ()) {
					if (!deviceTree.getDisplay().readAndDispatch ()) deviceTree.getDisplay().sleep ();
				}
				menu.dispose ();
			}
		});

		for( String port: ELANConnection.vectorPorts() )
		{
			TreeItem item = new TreeItem( deviceTree, SWT.COLOR_GRAY );
			item.setText( port );
		}
					
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

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public abstract void setSurveyStep(int step);
	public abstract void connectToDevice(String port);
}
