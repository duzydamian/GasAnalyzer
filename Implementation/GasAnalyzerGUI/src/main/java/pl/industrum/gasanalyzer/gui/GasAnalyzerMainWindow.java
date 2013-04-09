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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;

public class GasAnalyzerMainWindow {

	protected Shell shlGasAnalyzer;	
	
	/**
	 * GUI Element's 
	 */
	StyledText styledText;
	Button connect;
	CCombo portsList;
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
		
		portsList = new CCombo(shlGasAnalyzer, SWT.BORDER);
		portsList.setBounds(46, 10, 85, 29);
		
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
			

		Label lblNewLabel = new Label(shlGasAnalyzer, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 30, 17);
		lblNewLabel.setText("Port:");
		
		final Button btnOut = new Button(shlGasAnalyzer, SWT.RADIO);
		btnOut.setBounds(232, 10, 50, 22);
		btnOut.setText("out");
		btnOut.setSelection(true);
		
		final Button btnOkno = new Button(shlGasAnalyzer, SWT.RADIO);
		btnOkno.setBounds(288, 10, 63, 22);
		btnOkno.setText("okno");
		
		final Button btnPlik = new Button(shlGasAnalyzer, SWT.RADIO);
		btnPlik.setBounds(357, 10, 81, 22);
		btnPlik.setText("plik");
		
		connect = new Button(shlGasAnalyzer, SWT.TOGGLE);
		connect.setBounds(137, 10, 92, 29);
		connect.setText("Połącz");
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
			        	ELANConnection.getInstance().connect(portsList.getItem(portsList.getSelectionIndex()));
			        	portsList.setEnabled(false);
			        	connect.setText("Rozłącz");
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
		
		styledText = new StyledText(shlGasAnalyzer, SWT.BORDER | SWT.V_SCROLL);		
		styledText.setAlignment(SWT.CENTER);
		styledText.setBounds(10, 45, 428, 186);		
	}
}
