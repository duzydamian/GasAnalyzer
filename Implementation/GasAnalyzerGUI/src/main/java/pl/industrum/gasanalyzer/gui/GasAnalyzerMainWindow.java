package pl.industrum.gasanalyzer.gui;

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

	protected Shell shell;	
	
	/**
	 * GUI Element's 
	 */
	StyledText styledText;
	Button connect;
	CCombo portsList;
	

	public GasAnalyzerMainWindow() {
		super();
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
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
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
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
		
		portsList = new CCombo(shell, SWT.BORDER);
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
			

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 30, 17);
		lblNewLabel.setText("Port:");
        //System.setErr();
//        System.setOut(new PrintStream(new OutputStream() {
//			
//			@Override
//			public void write(int arg0) throws IOException {				
//				//if(arg0<100){
//					byte[] character = new byte[1];
//					character[0] = (byte) arg0;
//					styledText.setText(styledText.getText()+new String(character));
//				//}								
//			}
//		}));
		
		connect = new Button(shell, SWT.TOGGLE);
		connect.setBounds(137, 10, 88, 29);
		connect.setText("Połącz");
		connect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				boolean state = connect.getSelection();
		        try
		        {
		        	if(state)
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
		
		styledText = new StyledText(shell, SWT.BORDER);
		styledText.setBounds(10, 45, 428, 186);
						
		ELANConnection.listPorts();
        
	}
}
