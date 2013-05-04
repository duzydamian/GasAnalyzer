package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.elan.communication.network.ELANMeasurementDevice;
import pl.industrum.gasanalyzer.gui.ELANConnectionWrapper;
import pl.industrum.gasanalyzer.gui.dialogs.NetworkScan;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.types.UsefulColor;
import pl.industrum.gasanalyzer.types.UsefulImage;

public abstract class DeviceTree extends Composite
{

	private Tree deviceTree;	
	private Label lblSurveyStep;
	private Spinner surveyStep;
	private Button btnOk;
	private Image imageDisconnect;
	private Image imageConnect;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DeviceTree( final Composite parent, int style )
	{
		super( parent, style );
		setLayout( new GridLayout( 3, false ) );

		imageDisconnect = UsefulImage.DISCONNECT.getImage();
		imageConnect = UsefulImage.CONNECT.getImage();
		
		deviceTree = new Tree( this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		deviceTree.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true,
				3, 1 ) );
		deviceTree.addListener (SWT.MenuDetect, new Listener () {
			public void handleEvent (Event event) {
				if( deviceTree.getSelection().length == 1 )
				{
					final TreeItem treeItem = deviceTree.getSelection()[0];
					if ( treeItem.getParentItem() != null )
					{
						return;
					}
					Menu menu = new Menu (deviceTree.getShell(), SWT.POP_UP);
					MenuItem item = new MenuItem (menu, SWT.PUSH);
					final boolean connect = (treeItem.getImage() == imageDisconnect );
					if ( connect )
					{
						item.setText ("Połącz z "+treeItem.getText());
						item.setImage( imageConnect );
					}
					else
					{
						item.setText ("Rozłącz z "+treeItem.getText());
						item.setImage( imageDisconnect );
					}
					item.addListener (SWT.Selection, new Listener () {
						public void handleEvent (Event e) {
								if ( connect )
								{									
									if ( connectWithNetwork(treeItem.getText()) )
									{
										setStatusBarInformation( 10, "Łączenie z "+treeItem.getText() );
										NetworkScan scan = new NetworkScan( parent.getShell(), SWT.NONE );
										scan.open();
										if ( getGUIConnectionWrapper().getNetwork( treeItem.getText() ).getSize() > 0 )
										{
											int networkSize = getGUIConnectionWrapper().getNetwork( treeItem.getText() ).getSize();
											
											for( ELANMeasurementDevice device: getGUIConnectionWrapper().getNetwork( treeItem.getText() ) )
											{
												TreeItem itemTreeItem = new TreeItem( treeItem, SWT.COLOR_GRAY );
												itemTreeItem.setText( device.getName() );
												addDeviceToDeviceCollection(device);
												layout();
											}
											
											
											String oldName = treeItem.getText(); 
											String newName = getGUIConnectionWrapper().getNetwork( treeItem.getText() ).getName() +" [" + getGUIConnectionWrapper().getNetwork( treeItem.getText() ).getPort() + "]";
											setStatusBarInformation( 100, "Połączono z "+ newName );
											treeItem.setText( newName );
											renameNetwork( oldName, newName );
											setNetworkConnected( networkSize, treeItem.getText() );
											treeItem.setImage( imageConnect );
											treeItem.setForeground( UsefulColor.BLACK.getColor() );
											treeItem.setExpanded( true );
											setStatusBarInformation( -1, "");
										}
										else
										{
											//TODO
										}
									}
									
								}
								else
								{
									String port = treeItem.getText();
									port = port.substring( port.indexOf( "[" )+1, port.indexOf( "]" ) );
									disconnectFromDevice( port );
									treeItem.removeAll();
									treeItem.setText( port );
									treeItem.setImage( imageDisconnect );
									treeItem.setForeground( UsefulColor.GRAY_DISCONNECT.getColor() );
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
			}
		});
		
		deviceTree.addListener( SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				if( deviceTree.getSelection().length == 1 )
				{
					final TreeItem treeItem = deviceTree.getSelection()[0];
					if ( treeItem.getParentItem() != null )
					{
						setSelectedDeviceVisible( treeItem.getText() );
					}
					else
					{
						setSelectedNetworkVisible( treeItem.getText() );
					}
				}
			}
		} );
		
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

	public void refreshTree()
	{
		for( String port: ELANConnection.vectorPorts() )
		{
			boolean toAdd = true;
			for( TreeItem item: deviceTree.getItems() )
			{
				if( item.getText().contains( port ));
					toAdd = false;
			}
			
			if ( toAdd )
			{
				TreeItem item = new TreeItem( deviceTree, SWT.NONE );
				item.setText( port );
				item.setImage( imageDisconnect );
				item.setForeground( UsefulColor.GRAY_DISCONNECT.getColor() );
				addNetworkToNetworkCollection( port );
			}
		}
	}
	
	@Override
	public void setEnabled( boolean arg0 )
	{		
		super.setEnabled( arg0 );
		deviceTree.setEnabled( arg0 );
		surveyStep.setEnabled( arg0 );
		btnOk.setEnabled( arg0 );
	}
	public abstract void setSurveyStep(int step);
	public abstract boolean connectWithNetwork(String port);
	public abstract void disconnectFromDevice( String text );	
	public abstract ELANConnectionWrapper getGUIConnectionWrapper();
	public abstract void addDeviceToDeviceCollection( ELANMeasurementDevice device );
	public abstract void addNetworkToNetworkCollection(String networkName);
	public abstract void setSelectedDeviceVisible(String text );
	public abstract void setSelectedNetworkVisible(String text );
	public abstract void renameNetwork( String oldName, String newName );
	public abstract void setNetworkConnected( int networkSize, String name );	
	public abstract void setStatusBarInformation( int progress, String statusMessage );	
}
