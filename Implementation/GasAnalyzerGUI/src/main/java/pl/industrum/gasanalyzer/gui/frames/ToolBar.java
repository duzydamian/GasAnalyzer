package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Label;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.gui.SWTResourceManager;
import pl.industrum.gasanalyzer.gui.dialogs.PdfDialog;
import pl.industrum.gasanalyzer.gui.dialogs.XlsDialog;
import pl.industrum.gasanalyzer.i18n.Messages;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public abstract class ToolBar extends Composite
{	
	GridData compositeData;
	Label lblGenerate;
	Button btnConnect;
	Combo portsList;
	private Label lblPdf;
	private Label lblXls;
	private Button btnPdf;
	private Button btnXls;
	private CoolBar bar;
	private CoolItem coolItemLblGenerate;
	private CoolItem coolItemBtnPdf;
	private CoolItem coolItemBtnXls;
	private CoolItem coolItemLblPdf;
	private CoolItem coolItemLblXls;
	private CoolItem coolItemLblPort;
	private CoolItem coolItemPortsList;
	private CoolItem coolItemBtnConnect;
	private Label lblPort;
	private CoolItem coolItemBtnRefresh;
	private Button btnRefresh;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ToolBar(final Composite parent, int style)
	{
		super(parent, style);
		compositeData = new GridData(GridData.FILL, GridData.GRAB_VERTICAL, true, false);
		compositeData.horizontalSpan = 6;
		this.setLayout(new FillLayout(SWT.HORIZONTAL));
		this.setLayoutData(compositeData);
		
		bar = new CoolBar(this, SWT.NONE);
		
		coolItemLblPort = new CoolItem(bar, SWT.NONE);		
		lblPort = new Label(bar, SWT.NONE);
		lblPort.setText(Messages.getString("GasAnalyzerMainWindow.lblNewLabel.text"));
		coolItemLblPort.setPreferredSize (coolItemLblPort.computeSize (lblPort.computeSize (SWT.DEFAULT, SWT.DEFAULT).x, lblPort.computeSize (SWT.DEFAULT, SWT.DEFAULT).y));
		coolItemLblPort.setControl(lblPort);		
		
		coolItemPortsList = new CoolItem(bar, SWT.NONE);		
		portsList = new Combo(bar, SWT.BORDER);		
		coolItemPortsList.setPreferredSize (coolItemPortsList.computeSize (portsList.computeSize (SWT.DEFAULT, SWT.DEFAULT).x, portsList.computeSize (SWT.DEFAULT, SWT.DEFAULT).y));
		coolItemPortsList.setControl(portsList);
		
		coolItemBtnRefresh = new CoolItem(bar, SWT.NONE);		
		btnRefresh = new Button(bar, SWT.NONE);
		btnRefresh.setImage(SWTResourceManager.getImage(ToolBar.class, "/pl/industrum/gasanalyzer/gui/odswiez.png"));
		coolItemBtnRefresh.setPreferredSize (coolItemBtnRefresh.computeSize (btnRefresh.computeSize (SWT.DEFAULT, SWT.DEFAULT).x, btnRefresh.computeSize (SWT.DEFAULT, SWT.DEFAULT).y));
		coolItemBtnRefresh.setControl(btnRefresh);
		

		btnRefresh.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				refreshPortList();
			}
		});
		
		coolItemBtnConnect = new CoolItem(bar, SWT.NONE);		
		btnConnect = new Button(bar, SWT.TOGGLE);
		btnConnect.setText(Messages.getString("GasAnalyzerMainWindow.connect.text")); //$NON-NLS-1$
		coolItemBtnConnect.setPreferredSize (coolItemBtnConnect.computeSize (btnConnect.computeSize (SWT.DEFAULT, SWT.DEFAULT).x, btnConnect.computeSize (SWT.DEFAULT, SWT.DEFAULT).y));
		coolItemBtnConnect.setControl(btnConnect);		
		
		btnConnect.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				boolean state = btnConnect.getSelection();
		        connectDisconnect(state);
			}
		});
		
		coolItemLblGenerate = new CoolItem(bar, SWT.NONE);		
		lblGenerate = new Label(bar, SWT.BOLD);
		lblGenerate.setText(Messages.getString("GenerateReportBar.lblStatus.text")); //$NON-NLS-1$
		coolItemLblGenerate.setPreferredSize (coolItemLblGenerate.computeSize (lblGenerate.computeSize (SWT.DEFAULT, SWT.DEFAULT).x, lblGenerate.computeSize (SWT.DEFAULT, SWT.DEFAULT).y));
		coolItemLblGenerate.setControl(lblGenerate);		
		
		coolItemLblPdf = new CoolItem(bar, SWT.NONE);		
		lblPdf = new Label(bar, SWT.BOLD);
		lblPdf.setText(Messages.getString("GenerateReportBar.lblPdf.text")); //$NON-NLS-1$
		coolItemLblPdf.setPreferredSize (coolItemLblPdf.computeSize (lblPdf.computeSize (SWT.DEFAULT, SWT.DEFAULT).x, lblPdf.computeSize (SWT.DEFAULT, SWT.DEFAULT).y));
		coolItemLblPdf.setControl(lblPdf);		
		
		coolItemBtnPdf = new CoolItem(bar, SWT.NONE);		
		btnPdf = new Button(bar, SWT.PUSH);
		btnPdf.setText(Messages.getString("GenerateReportBar.btnPdf.text")); //$NON-NLS-1$
		coolItemBtnPdf.setPreferredSize (coolItemBtnPdf.computeSize (btnPdf.computeSize (SWT.DEFAULT, SWT.DEFAULT).x, btnPdf.computeSize (SWT.DEFAULT, SWT.DEFAULT).y));
		coolItemBtnPdf.setControl(btnPdf);
		
		btnPdf.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) {	
				PdfDialog pdfDialog = new PdfDialog(parent.getShell(), SWT.NONE);
				pdfDialog.open();
			}
		});
		
		coolItemLblXls = new CoolItem(bar, SWT.NONE);		
		lblXls = new Label(bar, SWT.BOLD);
		lblXls.setText(Messages.getString("GenerateReportBar.lblXls.text")); //$NON-NLS-1$
		coolItemLblXls.setPreferredSize (coolItemLblXls.computeSize (lblXls.computeSize (SWT.DEFAULT, SWT.DEFAULT).x, lblXls.computeSize (SWT.DEFAULT, SWT.DEFAULT).y));
		coolItemLblXls.setControl(lblXls);
		
		
		coolItemBtnXls = new CoolItem(bar, SWT.NONE);		
		btnXls = new Button(bar, SWT.PUSH);
		btnXls.setText(Messages.getString("GenerateReportBar.btnXls.text")); //$NON-NLS-1$
		coolItemBtnXls.setPreferredSize (coolItemBtnXls.computeSize (btnXls.computeSize (SWT.DEFAULT, SWT.DEFAULT).x, btnXls.computeSize (SWT.DEFAULT, SWT.DEFAULT).y));
		coolItemBtnXls.setControl(btnXls);
		
		btnXls.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{	
				XlsDialog xlsDialog = new XlsDialog(parent.getShell(), SWT.NONE);
				xlsDialog.open();
			}
		});
		
		refreshPortList();
		
		bar.pack();
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void refreshPortList()
	{
		portsList.removeAll();
		
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
			btnConnect.setEnabled(false);
		}
	}
	
	public void setConnect()
	{
		portsList.setEnabled(true);	
		btnConnect.setText("Połącz");
	}
	
	public void setDisconnect()
	{
		portsList.setEnabled(true);	
		btnConnect.setText("Połącz");
	}
	
	public String getSelectedPort()
	{
		return portsList.getItem(portsList.getSelectionIndex());
	}
	
	public abstract void connectDisconnect(boolean state);
}
