package pl.industrum.gasanalyzer.gui;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import pl.industrum.gasanalyzer.elan.communication.network.ELANMeasurementDevice;
import pl.industrum.gasanalyzer.elan.communication.network.ELANNetwork;
import pl.industrum.gasanalyzer.elan.exceptions.NullDeviceException;
import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxInvalidFrame;
import pl.industrum.gasanalyzer.elan.notifications.ELANMeasurementDeviceNotification;
import pl.industrum.gasanalyzer.elan.notifications.ELANNetworkNotification;
import pl.industrum.gasanalyzer.elan.types.ELANBufferType;
import pl.industrum.gasanalyzer.elan.types.ELANCollectiveChannelState;
import pl.industrum.gasanalyzer.elan.types.ELANConnectionState;
import pl.industrum.gasanalyzer.gui.dialogs.PdfDialog;
import pl.industrum.gasanalyzer.gui.dialogs.XlsDialog;
import pl.industrum.gasanalyzer.gui.frames.DeviceCollection;
import pl.industrum.gasanalyzer.gui.frames.DeviceTree;
import pl.industrum.gasanalyzer.gui.frames.MainMenu;
import pl.industrum.gasanalyzer.gui.frames.NetworkCollection;
import pl.industrum.gasanalyzer.gui.frames.Problems;
import pl.industrum.gasanalyzer.gui.frames.StatusBar;
import pl.industrum.gasanalyzer.gui.frames.ToolBar;
import pl.industrum.gasanalyzer.hibernate.model.managers.DeviceManager;
import pl.industrum.gasanalyzer.hibernate.model.managers.MeasurementSnapshotManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Device;
import pl.industrum.gasanalyzer.model.Survey;
import pl.industrum.gasanalyzer.types.Error;
import pl.industrum.gasanalyzer.types.Warning;

public class GasAnalyzerMainWindow implements Observer
{
	private ELANConnectionWrapper connectionWrapper;
	private String nextSnapshotComment;
	
	private Survey currentSurveyObject;
	protected Shell shlGasAnalyzer;

	/**
	 * GUI Element's
	 */
	private MainMenu menu;
	private StatusBar statusBar;

	private ToolBar toolBar;
	private SashForm sashDeviceTreeNetworkDevice;
	private SashForm sashELANNetworkProblems;

	private DeviceTree deviceTree;
	private DeviceCollection deviceCollection;
	private NetworkCollection networkCollection;
	private Problems problems;

	public GasAnalyzerMainWindow( )
	{
		super();
		connectionWrapper = new ELANConnectionWrapper();	
		nextSnapshotComment = "";
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
					networkCollection.setVisible( false );
					deviceCollection.setVisible( false );
					statusBar.setStatusText( "Trwa rozłączanie sieci" );
					statusBar.showProgressBar();
					statusBar.setProgress( 50 );
					for( ELANNetwork iter: connectionWrapper )
					{
						statusBar.setStatusText( "Trwa rozłączanie z " + iter.getName());
						iter.stopAlarming();
						iter.getConnection().disconnect();
					}
					statusBar.setStatusText( "Trwa zamykanie aplikacji" );
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
		
		sashELANNetworkProblems.layout();
		sashDeviceTreeNetworkDevice.layout();
		
		shlGasAnalyzer.setMaximized( true );
		problems.showWarning();
		
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
		//shlGasAnalyzer.setSize( 650, 500 );
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
			public void newSurveyCreated(Survey survey)
			{
				currentSurveyObject = survey;
				enableSurveyMainWIndow();				
			}

			@Override
			public Survey getSurveyToEdit()
			{
				return currentSurveyObject;
			}		
		};
		
		shlGasAnalyzer.setMenuBar( menu );

		toolBar = new ToolBar( shlGasAnalyzer, SWT.NONE )
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
			public void newSurveyCreated(Survey survey)
			{
				currentSurveyObject = survey;
				enableSurveyMainWIndow();
			}

			@Override
			public Survey getSurveyToEdit()
			{
				return currentSurveyObject;
			}

			@Override
			public void generatePDFReport()
			{
				PdfDialog pdfDialog = new PdfDialog( getShell(), SWT.NONE );
				pdfDialog.open( currentSurveyObject );
			}

			@Override
			public void generateXLSReport()
			{
				XlsDialog xlsDialog = new XlsDialog( getShell(), SWT.NONE );
				xlsDialog.open( currentSurveyObject );
			}
		};	

		sashELANNetworkProblems = new SashForm(shlGasAnalyzer,SWT.VERTICAL);
		GridData  compositeData = new GridData( GridData.FILL, GridData.FILL,
				true, true );
		compositeData.horizontalSpan = 6;
		sashELANNetworkProblems.setLayoutData( compositeData );
		sashDeviceTreeNetworkDevice = new SashForm(sashELANNetworkProblems,SWT.HORIZONTAL);
		
		sashDeviceTreeNetworkDevice.setLayout( new GridLayout( 3, false ) );
		sashDeviceTreeNetworkDevice.setLayoutData( compositeData );		

		deviceTree = new DeviceTree( sashDeviceTreeNetworkDevice, SWT.NONE )
		{
			@Override
			public void setSurveyStep( int step )
			{
				for( ELANNetwork network: connectionWrapper )
				{
					network.startAlarmingWithStep( step );
				}
			}

			@Override
			public ELANConnectionState connectWithNetwork(String port)
			{				
				ELANConnectionState connectionState = connect( port );
				return connectionState;
			}

			@Override
			public void disconnectFromDevice( String text )
			{
				disconnect( text );
				deviceCollection.removeAllDevice();
			}

			@Override
			public ELANConnectionWrapper getGUIConnectionWrapper()
			{
				return getConnectionWrapper();
			}

			@Override
			public void addDeviceToDeviceCollection( ELANMeasurementDevice device, String port, TreeItem treeItem )
			{				
				try
				{
					deviceCollection.addDevice( device, treeItem );
					//TODO change method
					Device deviceByAddress = DeviceManager.getDeviceByAddress( device.getDeviceAddress() );
					connectionWrapper.getNetwork( port ).getDevice( device.getDeviceAddress() ).getDeviceInformation().setDeviceIDInDatabase( deviceByAddress.getId() );
				} catch ( NullDeviceException e )
				{
					e.printStackTrace();
				}
			}

			@Override
			public void setSelectedDeviceVisible( String text )
			{
				networkCollection.setVisible( false );
				deviceCollection.setVisible( true );
				deviceCollection.setVisibleDivice( text );
				
				sashDeviceTreeNetworkDevice.layout();
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
				
				sashDeviceTreeNetworkDevice.layout();
			}

			@Override
			public void renameNetwork( String oldName, String newName )
			{
				networkCollection.renameNetwork( oldName, newName );				
			}

			@Override
			public void setNetworkConnected( int networkSize, String name, ELANNetwork elanNetwork )
			{
				networkCollection.setNetworkConnected( networkSize, name, elanNetwork );
			}
			
			@Override
			public void setNetworkDisconnected( String name )
			{
				networkCollection.setNetworkDisconnected( name );
			}

			@Override
			public void setStatusBarInformation( int progress,
					String statusMessage )
			{
				if ( progress == -1 )
				{
					statusBar.hideProgressBar();
				}
				else
				{
					statusBar.setProgress( progress );
					statusBar.setStatusText( statusMessage );
				}				
			}

			@Override
			public void noDeviceFound( String source )
			{
				problems.addWarning( Warning.NO_DEVICE, source );				
			}

			@Override
			public void connectionProblem( String source, ELANConnectionState connectWithNetworkState )
			{
				Error error = Error.CONNECTION_PROBLEM;
				error.setDescription( connectWithNetworkState.getMessage() );
				problems.addError( error, source );
			}

			@Override
			public void setMeasurementComment( String comment )
			{
				nextSnapshotComment = comment;
			}

			@Override
			public void stopSurveyAlarming()
			{
				for( ELANNetwork network: connectionWrapper )
				{
					network.stopAlarming();
				}
			}

			@Override
			public void startSurveyAlarming( int step )
			{
				for( ELANNetwork network: connectionWrapper )
				{
					network.startAlarmingWithStep( step );
				}
				
				deviceCollection.updateHistoryRefreshStep( step );
			}	
		};
		deviceTree.setEnabled( false );
		deviceTree.setLayoutData( new GridData( GridData.FILL, GridData.FILL, false, true ) );
		networkCollection = new NetworkCollection( sashDeviceTreeNetworkDevice, SWT.NONE, "" );
		networkCollection.setEnabled( false );
		networkCollection.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
		deviceCollection = new DeviceCollection( sashDeviceTreeNetworkDevice, SWT.NONE, "Test" )
		{
			@Override
			public Integer getSurveyIDFromGUI()
			{
				return currentSurveyObject.getId();
			}

			@Override
			public Integer getStepFromGUI()
			{
				return deviceTree.getStep();
			}

			@Override
			public void addErrorToProblems( ELANCollectiveChannelState collectiveChannelState, String deviceName2 )
			{
				problems.addError( Error.DEVICE_ERROR, deviceName2 );
			}

			@Override
			public void addWarningToProblems( ELANCollectiveChannelState collectiveChannelState, String deviceName2 )
			{
				problems.addWarning( Warning.DEVICE_WARNING, deviceName2 );
			}			
		};
		deviceCollection.setEnabled( false );
		deviceCollection.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );

		problems = new Problems( sashELANNetworkProblems, SWT.NONE )
		{
			@Override
			public void layoutMainWindow()
			{
				sashELANNetworkProblems.layout();
			}		
		};		

		statusBar = new StatusBar( shlGasAnalyzer, SWT.BORDER );	
		
		sashELANNetworkProblems.setWeights( new int[] {80,20} );
		sashDeviceTreeNetworkDevice.setWeights(new int[] {10,20,20});		
	}
	
	public void enableSurveyMainWIndow()
	{
		menu.enableSurvey();
		toolBar.enableSurvey();
		deviceTree.setEnabled( true );		
		networkCollection.setEnabled( true );
		deviceCollection.setEnabled( true );
		deviceTree.refreshTree();		
	}

	public ELANConnectionState connect(String port)
	{		
		return connectionWrapper.connectWithNetwork( port, this );		
	}
	
	public void disconnect(String port)
	{
		connectionWrapper.getNetwork( port ).stopAlarming();
		connectionWrapper.getNetwork( port ).getConnection().disconnect();		
	}
	
	/**
	 * @return the connectionWrapper
	 */
	public ELANConnectionWrapper getConnectionWrapper()
	{
		return connectionWrapper;
	}
	

	public void update( Observable obj, Object arg )
	{
		if( arg instanceof ELANMeasurementDeviceNotification )
		{
			try
			{
				ELANMeasurementDeviceNotification notification = ( ELANMeasurementDeviceNotification )arg;
				Integer deviceAddress = notification.getData().getDeviceAddress();
				String networkPort = notification.getData().getNetworkPort();
				ELANBufferType bufferType = notification.getData().getBufferType();
				ELANMeasurementDevice device;
				
				device = connectionWrapper.getNetwork( networkPort ).getDevice( deviceAddress );
	
				ELANRxFrame frame;
				try
				{
					switch ( bufferType )
					{
						case INVALID_FRAME:
						{
							frame = ( ELANRxInvalidFrame ) device.pollAndClear( bufferType );
							deviceCollection.updateStateForDevice( device.getDeviceAddress(), ( ELANRxInvalidFrame )frame );
							networkCollection.updateStateForDevice( networkPort, device.getName(), ( ELANRxInvalidFrame )frame );
							break;
						}
						case BROADCAST_FRAME:
						{
							frame = ( ELANRxBroadcastFrame ) device.pollAndClear( bufferType );
							deviceCollection.updateMeasurmentForDevice( device.getDeviceAddress(), ( ELANRxBroadcastFrame )frame );
							networkCollection.updateMeasurmentForDevice( networkPort, device.getName(), ( ELANRxBroadcastFrame )frame );
							break;
						}
					}
				} 
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			} 
			catch ( NullDeviceException e )
			{
				e.printStackTrace();
			}
		}		
		else if( arg instanceof ELANNetworkNotification )
		{
			ELANNetworkNotification notification = ( ELANNetworkNotification )arg;
			MeasurementSnapshotManager.addMeasurementSnapshot( currentSurveyObject.getId(), new Date(), connectionWrapper.getNetwork( notification.getData() ), nextSnapshotComment );
			
			if ( !nextSnapshotComment.isEmpty() )
			{
				deviceTree.enableNextComment();
				nextSnapshotComment = "";
			}		
		}				
	}
}
