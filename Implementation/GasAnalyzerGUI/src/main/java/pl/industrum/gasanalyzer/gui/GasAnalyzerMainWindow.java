package pl.industrum.gasanalyzer.gui;

import java.io.FileNotFoundException;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
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
import pl.industrum.gasanalyzer.gui.frames.Device;
import pl.industrum.gasanalyzer.gui.frames.DeviceTree;
import pl.industrum.gasanalyzer.gui.frames.MainMenu;
import pl.industrum.gasanalyzer.gui.frames.StatusBar;
import pl.industrum.gasanalyzer.gui.frames.SurveyFrame;
import pl.industrum.gasanalyzer.gui.frames.ToolBar;
import pl.industrum.gasanalyzer.i18n.Messages;

public class GasAnalyzerMainWindow implements Observer
{
	private ELANConnectionWrapper connectionWrapper;
	protected Shell shlGasAnalyzer;
	boolean outSelected;

	/**
	 * GUI Element's
	 */
	StyledText styledText;
	MainMenu menu;
	SurveyFrame surveyFrame;
	StatusBar statusBar;

	private ToolBar toolBar;
	private Composite composite;

	private DeviceTree deviceTree;
	private Button btnOut;
	private Button btnOkno;
	private Button btnPlik;
	private Device device;

	public GasAnalyzerMainWindow()
	{
		super();
		outSelected = false;
		connectionWrapper = new ELANConnectionWrapper();
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

		menu = new MainMenu( shlGasAnalyzer, SWT.BAR );
		shlGasAnalyzer.setMenuBar( menu );

		toolBar = new ToolBar( shlGasAnalyzer, SWT.BORDER )
		{
			@Override
			public void refreshPortList()
			{
				deviceTree.refreshTree();
			}
		};

		surveyFrame = new SurveyFrame( shlGasAnalyzer, SWT.BORDER )
		{
			@Override
			public void resize()
			{
				layout();
				getParent().layout();
			}
		};

		GridData connectBarData = new GridData( GridData.FILL, GridData.CENTER,
				true, false );
		connectBarData.horizontalSpan = 6;

		Composite connectBar = new Composite( shlGasAnalyzer, SWT.BORDER );
		connectBar.setLayout( new FillLayout( SWT.HORIZONTAL ) );
		connectBar.setLayoutData( connectBarData );

		btnOut = new Button( connectBar, SWT.RADIO );
		btnOut.setText( Messages
				.getString( "GasAnalyzerMainWindow.btnOut.text" ) ); //$NON-NLS-1$
		btnOut.setSelection( true );

		btnOkno = new Button( connectBar, SWT.RADIO );
		btnOkno.setText( Messages
				.getString( "GasAnalyzerMainWindow.btnOkno.text" ) ); //$NON-NLS-1$

		btnPlik = new Button( connectBar, SWT.RADIO );
		btnPlik.setText( Messages
				.getString( "GasAnalyzerMainWindow.btnPlik.text" ) ); //$NON-NLS-1$				

		GridData compositeData = new GridData( GridData.FILL, GridData.FILL,
				true, true );
		compositeData.horizontalSpan = 6;
		composite = new Composite( shlGasAnalyzer, SWT.NONE );
		composite.setLayout( new FillLayout( SWT.HORIZONTAL ) );
		composite.setLayoutData( compositeData );

		deviceTree = new DeviceTree( composite, SWT.V_SCROLL )
		{
			@Override
			public void setSurveyStep( int step )
			{
				//TODO
			}

			@Override
			public boolean connectWithNetwork(String port)
			{
				try
				{
					if ( !outSelected )
					{
						if ( btnOut.getSelection() )
						{

						} else if ( btnOkno.getSelection() )
						{
							System.setOut( new PrintStream( new OutputStream()
							{
								@Override
								public void write( final int arg0 )
										throws IOException
								{
									Display.getDefault().syncExec(
											new Runnable()
											{
												public void run()
												{
													byte[] character = new byte[1];
													character[0] = ( byte )arg0;
													styledText
															.append( new String(
																	character ) );
													styledText
															.setTopIndex( styledText
																	.getLineCount() - 1 );
												}
											} );
								}
							} ) );
						} else if ( btnPlik.getSelection() )
						{
							try
							{
								System.setErr( new PrintStream( "err.log" ) );
								System.setOut( new PrintStream( "out.log" ) );
								styledText
										.setText( "Pomiary są zapisywane do pliku out.log..." );
							} catch ( FileNotFoundException e1 )
							{
								e1.printStackTrace();
							}
						}
						outSelected = true;
					}
				} catch ( Exception e )
				{
					e.printStackTrace();
				}
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
		};
		deviceTree.redraw();

		device = new Device( composite, SWT.NONE, "Test" );

		styledText = new StyledText( composite, SWT.BORDER | SWT.V_SCROLL );
		styledText.setAlignment( SWT.CENTER );
		styledText.setWordWrap( true );

		statusBar = new StatusBar( shlGasAnalyzer, SWT.BORDER );
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
					System.out.println(frame.getTimeStamp());
					for( ELANMeasurement elanMeasurement: frame )
					{
						System.out.println(elanMeasurement.toString());
					}
				}
			}			
		}		
	}
}