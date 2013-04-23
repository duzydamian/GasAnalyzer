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
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.elan.types.ELANConnectionState;
import pl.industrum.gasanalyzer.gui.frames.Device;
import pl.industrum.gasanalyzer.gui.frames.DeviceTree;
import pl.industrum.gasanalyzer.gui.frames.MainMenu;
import pl.industrum.gasanalyzer.gui.frames.StatusBar;
import pl.industrum.gasanalyzer.gui.frames.SurveyFrame;
import pl.industrum.gasanalyzer.gui.frames.ToolBar;
import pl.industrum.gasanalyzer.i18n.Messages;
import org.eclipse.swt.widgets.Label;

public class GasAnalyzerMainWindow implements Observer
{
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
					//ELANConnection.getInstance().disconnect();
					statusBar.setProgress( 100 );
					toolBar.setConnect();
					statusBar.setProgress( 0 );
					statusBar.hideProgressBar();
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
			public void connectDisconnect( boolean state )
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

					if ( state )
					{
						statusBar.showProgressBar();
						statusBar.setProgress( 50 );
						ELANConnectionState connectionState = ELANConnectionState.CONNECTED;
//						ELANConnectionState connectionState = ELANConnection
//								.getInstance().connect(
//										toolBar.getSelectedPort() );
						if ( connectionState.isConnected() )
						{
							statusBar.setProgress( 75 );
							toolBar.setDisconnect();
							statusBar.setProgress( 90 );
							statusBar.setProgress( 100 );
							statusBar.setStatusText( "Status: "
									+ connectionState.getMessage() );
						}
						statusBar.setProgress( 0 );
						statusBar.hideProgressBar();
					} else
					{
						statusBar.showProgressBar();
						statusBar.setProgress( 50 );
//						ELANConnection.getInstance().disconnect();
						statusBar.setProgress( 100 );
						toolBar.setConnect();
						statusBar.setProgress( 0 );
						statusBar.hideProgressBar();
					}
				} catch ( Exception e )
				{
					e.printStackTrace();
				}
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
		new Label(shlGasAnalyzer, SWT.NONE);
		new Label(shlGasAnalyzer, SWT.NONE);
		new Label(shlGasAnalyzer, SWT.NONE);
		new Label(shlGasAnalyzer, SWT.NONE);

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

		deviceTree = new DeviceTree( composite, SWT.V_SCROLL );
		new Label(deviceTree, SWT.NONE);
		new Label(deviceTree, SWT.NONE);
		deviceTree.redraw();

		device = new Device( composite, SWT.NONE, "Test" );
		device.redraw();

		styledText = new StyledText( composite, SWT.BORDER | SWT.V_SCROLL );
		styledText.setAlignment( SWT.CENTER );
		styledText.setWordWrap( true );

		statusBar = new StatusBar( shlGasAnalyzer, SWT.BORDER );
	}

	public void update( Observable arg0, Object arg1 )
	{
		// TODO Auto-generated method stub
		
	}
}