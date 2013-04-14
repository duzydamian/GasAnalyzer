package pl.industrum.gasanalyzer.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.elan.types.ELANConnectionState;

public class GasAnalyzerMainWindow {

	protected Shell shlGasAnalyzer;	
	
	/**
	 * GUI Element's 
	 */
	StyledText styledText;
	Button connect;
	CCombo portsList;
	Label lblStatus;
	ProgressBar progressBar;
	boolean outSelected;
	

	public GasAnalyzerMainWindow() {
		super();
		outSelected = false;
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
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
		shlGasAnalyzer.setSize(450, 300);
		shlGasAnalyzer.setText("Gas Analyzer");
		shlGasAnalyzer.setLayout(new GridLayout(6, false));
		
		Menu menu = new Menu(shlGasAnalyzer, SWT.BAR);
		shlGasAnalyzer.setMenuBar(menu);
		
		MenuItem mntmPlik = new MenuItem(menu, SWT.CASCADE);
		mntmPlik.setText("Plik");
		
		Menu menu_1 = new Menu(mntmPlik);
		mntmPlik.setMenu(menu_1);
		
		MenuItem mntmWyjcie = new MenuItem(menu_1, SWT.NONE);
		mntmWyjcie.setText("Wyjście");
		
		MenuItem mntmEdycja = new MenuItem(menu, SWT.CASCADE);
		mntmEdycja.setText("Edycja");
		
		Menu menu_2 = new Menu(mntmEdycja);
		mntmEdycja.setMenu(menu_2);
		
		MenuItem mntmPomoc = new MenuItem(menu, SWT.CASCADE);
		mntmPomoc.setText("Pomoc");
		
		Menu menu_3 = new Menu(mntmPomoc);
		mntmPomoc.setMenu(menu_3);
		
		MenuItem mntmO = new MenuItem(menu_3, SWT.NONE);
		mntmO.setText("O programie");

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
		        									styledText.setText(styledText.getText()+new String(character));
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
		        		progressBar.setSelection(50);
			        	ELANConnectionState connectionState = ELANConnection.getInstance().connect(portsList.getItem(portsList.getSelectionIndex()));
			        	if (connectionState.isConnected())
			        	{
			        	progressBar.setSelection(75);
			        	portsList.setEnabled(false);
			        	progressBar.setSelection(90);
			        	connect.setText("Rozłącz");
			        	progressBar.setSelection(100);
			        	lblStatus.setText("Status: " + connectionState.getMessage());
			        	}
			        	progressBar.setSelection(0);
		        	}
		        	else
		        	{
		        		ELANConnection.getInstance().disconnect();
			        	portsList.setEnabled(true);	
			        	connect.setText("Połącz");
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
		
		
		GridData compositeData = new GridData(GridData.FILL, GridData.GRAB_VERTICAL, true, false);
		compositeData.horizontalSpan = 6;
		Composite composite = new Composite(shlGasAnalyzer, SWT.BORDER);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite.setLayoutData(compositeData);
		
		lblStatus = new Label(composite, SWT.NONE);
		lblStatus.setText("Status: ");
		
		progressBar = new ProgressBar(composite, SWT.NONE);
	}
}
