package pl.industrum.gasanalyzer.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.elan.types.ELANConnectionState;
import pl.industrum.gasanalyzer.gui.frames.GenerateReportBar;
import pl.industrum.gasanalyzer.gui.frames.MainMenu;
import pl.industrum.gasanalyzer.gui.frames.StatusBar;
import pl.industrum.gasanalyzer.gui.frames.SurveyFrame;

public class GasAnalyzerMainWindow implements Observer
{

	protected Shell shlGasAnalyzer;	
	
	/**
	 * GUI Element's 
	 */
	StyledText styledText;
	Button connect;
	CCombo portsList;
	MainMenu menu;
	SurveyFrame surveyFrame;
	StatusBar statusBar;
	boolean outSelected;

	private GenerateReportBar generateReportBar;
	
	private ELANConnectionWrapper connectionWrapper;
	

	public GasAnalyzerMainWindow()
	{
		super();
		outSelected = false;
		connectionWrapper = new ELANConnectionWrapper();
	}
	
	public void update( Observable obj, Object arg )
	{
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		shlGasAnalyzer.addListener(SWT.Close, new Listener()
		{
	        public void handleEvent(Event event)
	        {
	          MessageBox messageBox = new MessageBox(shlGasAnalyzer, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
	          messageBox.setText("Zakończ");
	          messageBox.setMessage("Czy na pewno chcesz zamknąć aplikację?");
	          if (messageBox.open() == SWT.YES)
	          {
	        	  statusBar.showProgressBar();
	        	  statusBar.setProgress(50);
	        	  ELANConnection.getInstance().disconnect();
	        	  statusBar.setProgress(100);
	        	  portsList.setEnabled(true);	
	        	  connect.setText("Połącz");
	        	  statusBar.setProgress(0);
	        	  statusBar.hideProgressBar();
	        	  event.doit = true;
	          }	          
	          else
	          {
	        	  event.doit = false;
	          }
	        }
		});
	        
		shlGasAnalyzer.open();
		shlGasAnalyzer.layout();
		while (!shlGasAnalyzer.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shlGasAnalyzer = new Shell();
		shlGasAnalyzer.setSize(650, 500);
		shlGasAnalyzer.setText("Gas Analyzer");
		shlGasAnalyzer.setLayout(new GridLayout(6, false));
		
		menu = new MainMenu(shlGasAnalyzer, SWT.BAR);
		shlGasAnalyzer.setMenuBar(menu);			
		
		surveyFrame = new SurveyFrame(shlGasAnalyzer, SWT.BORDER);		
		
		GridData connectBarData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		connectBarData.horizontalSpan = 6;
		
		Composite connectBar = new Composite(shlGasAnalyzer, SWT.BORDER);
		connectBar.setLayout(new FillLayout(SWT.HORIZONTAL));		
		connectBar.setLayoutData(connectBarData);
		
		Label lblNewLabel = new Label(connectBar, SWT.NONE);
		lblNewLabel.setText("Port:");
		
		portsList = new CCombo(connectBar, SWT.BORDER);
		
		for (String port: ELANConnection.vectorPorts()) 
		{
			portsList.add(port);
		}
		
		if (portsList.getItemCount()>0)
		{
			portsList.select(0);
		}
		else
		{
			connect.setEnabled(false);
		}
		
		connect = new Button(connectBar, SWT.TOGGLE);
		connect.setText("Połącz");
		
		final Button btnOut = new Button(connectBar, SWT.RADIO);
		btnOut.setText("out");
		btnOut.setSelection(true);
		
		final Button btnOkno = new Button(connectBar, SWT.RADIO);
		btnOkno.setText("okno");
		
		final Button btnPlik = new Button(connectBar, SWT.RADIO);
		btnPlik.setText("plik");
				
		connect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				boolean state = connect.getSelection();
		        try
		        {
		        	if (!outSelected)
		        	{
		        		if (btnOut.getSelection())
		        		{
		        			
		        		}
		        		else if (btnOkno.getSelection())
		        		{
		        	        System.setOut(new PrintStream(new OutputStream()
		        	        {	
		        				@Override
		        				public void write(final int arg0) throws IOException {
		        					Display.getDefault().syncExec( 
		        							new Runnable() 
		        							{
		        								public void run()
		        								{
		        									byte[] character = new byte[1];
		        									character[0] = (byte) arg0;
		        									styledText.append(new String(character));
		        									styledText.setTopIndex(styledText.getLineCount() - 1);
		        								}
		        							});										
		        				}
		        			}));
		        		}
		        		else if (btnPlik.getSelection())
		        		{
		        			try 
		        			{
		        				System.setErr(new PrintStream("err.log"));
		        				System.setOut(new PrintStream("out.log"));
		        				styledText.setText("Pomiary są zapisywane do pliku out.log...");
		        			} 
		        			catch (FileNotFoundException e1) 
		        			{
		        				e1.printStackTrace();
		        			} 
		        		}
		        		outSelected = true;
		        	}
		        	
		        	if (state)
		        	{
		        		statusBar.showProgressBar();
		        		statusBar.setProgress(50);
		        		//Begin connection
		        		
			        	//End connection
			        	if (connectionState.isConnected())
			        	{
			        		statusBar.setProgress(75);
				        	portsList.setEnabled(false);
				        	portsList.setVisible(false);			        	
				        	statusBar.setProgress(90);
				        	connect.setText("Rozłącz");
				        	statusBar.setProgress(100);
				        	statusBar.setStatusText("Status: " + connectionState.getMessage());
			        	}
			        	statusBar.setProgress(0);
			        	statusBar.hideProgressBar();
		        	}
		        	else
		        	{
		        		statusBar.showProgressBar();
		        		statusBar.setProgress(50);
		        		disconnect();
		        		statusBar.setProgress(100);
			        	portsList.setEnabled(true);	
			        	connect.setText("Połącz");
			        	statusBar.setProgress(0);
			        	statusBar.hideProgressBar();
		        	}
		        }
		        catch ( Exception e )
		        {
		            e.printStackTrace();
		        }
			}
		});
		
		GridData styledTextData = new GridData(GridData.FILL, GridData.FILL, true, true);
		styledTextData.horizontalSpan = 6;
		
		styledText = new StyledText(shlGasAnalyzer, SWT.BORDER | SWT.V_SCROLL);
		styledText.setAlignment(SWT.CENTER);
		styledText.setLayoutData(styledTextData);
		
		generateReportBar = new GenerateReportBar(shlGasAnalyzer, SWT.BORDER);
		generateReportBar.redraw(); //dodane bo sie czepiał, ze zmienna nie użyta
		
		statusBar = new StatusBar(shlGasAnalyzer, SWT.BORDER);		
	}	
}
