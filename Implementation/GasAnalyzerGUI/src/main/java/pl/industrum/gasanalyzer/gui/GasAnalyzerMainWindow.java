package pl.industrum.gasanalyzer.gui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.communication.network.ELANMeasurementDevice;
import pl.industrum.gasanalyzer.elan.communication.network.ELANNetwork;
import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxFrame;
import pl.industrum.gasanalyzer.elan.notifications.ELANNetworkNotification;
import pl.industrum.gasanalyzer.elan.types.ELANConnectionState;
import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;
import pl.industrum.gasanalyzer.gui.frames.DeviceCollection;
import pl.industrum.gasanalyzer.gui.frames.DeviceTree;
import pl.industrum.gasanalyzer.gui.frames.MainMenu;
import pl.industrum.gasanalyzer.gui.frames.NetworkCollection;
import pl.industrum.gasanalyzer.gui.frames.StatusBar;
import pl.industrum.gasanalyzer.gui.frames.ToolBar;
import pl.industrum.gasanalyzer.i18n.Messages;

public class GasAnalyzerMainWindow implements Observer
{
	private ELANConnectionWrapper connectionWrapper;
	protected Shell shlGasAnalyzer;

	/**
	 * GUI Element's
	 */
	StyledText styledText;
	MainMenu menu;
	StatusBar statusBar;

	private ToolBar toolBar;
	private Composite composite;
	private Composite composite2;

	private DeviceTree deviceTree;
	private DeviceCollection deviceCollection;
	private NetworkCollection networkCollection;

	public GasAnalyzerMainWindow( boolean forwarding )
	{
		super();
		connectionWrapper = new ELANConnectionWrapper();
		if ( forwarding )
			setForwarding();
	}

	/**
	 * Open the window.
	 */
	public void open()
	{
		Display display = Display.getDefault();
		createContents();

		shlGasAnalyzer.addListener( SWT.Close, new Listener()
		{
			public void handleEvent( Event event )
			{
				MessageBox messageBox = new MessageBox( shlGasAnalyzer, SWT.YES
						| SWT.NO | SWT.ICON_QUESTION );
				messageBox.setText( "Zakończ" );
				messageBox
						.setMessage( "Czy na pewno chcesz zamknąć aplikację?" );
				if ( messageBox.open() == SWT.YES )
				{
					statusBar.showProgressBar();
					statusBar.setProgress( 50 );
					for( ELANNetwork iter: connectionWrapper )
					{
						iter.getConnection().disconnect();
					}
					event.doit = true;
				} else
				{
					event.doit = false;
				}
			}
		} );

		shlGasAnalyzer.pack();
		shlGasAnalyzer.open();
		shlGasAnalyzer.layout();
		while ( !shlGasAnalyzer.isDisposed() )
		{
			if ( !display.readAndDispatch() )
			{
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents()
	{
		shlGasAnalyzer = new Shell();
		shlGasAnalyzer.setSize( 650, 500 );
		shlGasAnalyzer.setText( Messages
				.getString( "GasAnalyzerMainWindow.shlGasAnalyzer.text" ) ); //$NON-NLS-1$
		shlGasAnalyzer.setLayout( new GridLayout( 6, false ) );

		menu = new MainMenu( shlGasAnalyzer, SWT.BAR )
		{
			@Override
			public void refreshDeviceTree()
			{
				deviceTree.refreshTree();
			}

			@Override
			public void closeApplication()
			{
				shlGasAnalyzer.close();
			}

			@Override
			public void newSurveyCreated()
			{
				enableSurveyMainWIndow();				
			}		
		};
		
		shlGasAnalyzer.setMenuBar( menu );

		toolBar = new ToolBar( shlGasAnalyzer, SWT.NONE )
		{
			@Override
			public void refreshPortList()
			{
				deviceTree.refreshTree();
			}

			@Override
			public void closeApplication()
			{
				shlGasAnalyzer.close();
			}
		};	

		GridData compositeData = new GridData( GridData.FILL, GridData.FILL,
				true, true );
		compositeData.horizontalSpan = 6;
		composite = new Composite( shlGasAnalyzer, SWT.NONE );
		composite.setLayout( new FillLayout( SWT.HORIZONTAL ) );
		composite.setLayoutData( compositeData );

		deviceTree = new DeviceTree( composite, SWT.NONE )
		{
			@Override
			public void setSurveyStep( int step )
			{
				//TODO
			}

			@Override
			public boolean connectWithNetwork(String port)
			{				
				ELANConnectionState connectionState = connect( port );
				return connectionState.isConnected();
			}

			@Override
			public void disconnectFromDevice( String text )
			{
				disconnect( text );
			}

			@Override
			public ELANConnectionWrapper getGUIConnectionWrapper()
			{
				return getConnectionWrapper();
			}

			@Override
			public void addDeviceToDeviceCollection(String deviceName)
			{
				deviceCollection.addDevice( deviceName );
			}

			@Override
			public void setSelectedDeviceVisible( String text )
			{
				networkCollection.setVisible( false );
				deviceCollection.setVisible( true );
				deviceCollection.setVisibleDivice( text );
				composite.layout();
			}

			@Override
			public void addNetworkToNetworkCollection( String networkName )
			{
				networkCollection.addNetwork( networkName );
			}

			@Override
			public void setSelectedNetworkVisible( String text )
			{
				deviceCollection.setVisible( false );
				networkCollection.setVisible( true );
				networkCollection.setVisibleNetwork( text );
				composite.layout();
			}

			@Override
			public void renameNetwork( String oldName, String newName )
			{
				networkCollection.renameNetwork( oldName, newName );
			}	
		};
		deviceTree.setEnabled( false );

		networkCollection = new NetworkCollection( composite, SWT.NONE, "" );
		networkCollection.setEnabled( false );
		deviceCollection = new DeviceCollection( composite, SWT.NONE, "Test" );
		deviceCollection.setEnabled( false );

		GridData compositeData2 = new GridData( GridData.FILL, GridData.FILL,
				true, true );
		compositeData2.horizontalSpan = 6;
		composite2 = new Composite( shlGasAnalyzer, SWT.NONE );
		composite2.setLayout( new FillLayout( SWT.HORIZONTAL ) );
		composite2.setLayoutData( compositeData2 );
		
		styledText = new StyledText( composite2, SWT.V_SCROLL );
		styledText.setWordWrap( true );
		styledText.setEnabled( false );

		statusBar = new StatusBar( shlGasAnalyzer, SWT.BORDER );		
	}
	
	public void enableSurveyMainWIndow()
	{
		toolBar.enableSurvey();
		deviceTree.setEnabled( true );
		deviceTree.refreshTree();
		networkCollection.setEnabled( true );
		deviceCollection.setEnabled( true );
		styledText.setEnabled( true );
	}

	public ELANConnectionState connect(String port)
	{
		return connectionWrapper.connectWithNetwork( port, this );		
	}
	
	public void disconnect(String port)
	{
		connectionWrapper.getNetwork( port ).getConnection().disconnect();
	}
	
	/**
	 * @return the connectionWrapper
	 */
	public ELANConnectionWrapper getConnectionWrapper()
	{
		return connectionWrapper;
	}
	
	private void setForwarding()
	{
		System.setOut( new PrintStream( new OutputStream()
		{
			@Override
			public void write( final int arg0 ) throws IOException
			{
				Display.getDefault().syncExec( new Runnable()
				{
					public void run()
					{
						byte[] character = new byte[1];
						character[0] = ( byte )arg0;
						styledText.append( new String( character ) );
						styledText.setTopIndex( styledText.getLineCount() - 1 );
					}
				});
			}
		} ) );
	}
	
	public void update( Observable obj, Object arg )
	{
		if( arg instanceof ELANNetworkNotification )
		{
			ELANNetworkNotification notification = ( ELANNetworkNotification )arg;
			for( ELANMeasurementDevice device: connectionWrapper.getNetwork( notification.getData() ) )
			{
				if ( device != null )
				{
					ELANRxFrame poll = device.pollAndClear();
					ELANRxBroadcastFrame frame = ( ELANRxBroadcastFrame )poll;
					System.out.println(frame.getSourceAdress() + " @ " + frame.getTimeStamp());
					for( ELANMeasurement elanMeasurement: frame )
					{
						System.out.println(elanMeasurement.toString());
					}
				}
			}			
		}		
	}
}