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
	Button btnConnect;
	Combo portsList;
	private Button btnPdf;
	private Button btnXls;
	private CoolBar bar;
	private CoolItem coolItemBtnPdf;
	private CoolItem coolItemBtnXls;
	private CoolItem coolItemLblPort;
	private CoolItem coolItemPortsList;
	private CoolItem coolItemBtnConnect;
	private Label lblPort;
	private CoolItem coolItemBtnRefresh;
	private Button btnRefresh;
	private CoolItem coolItem;
	private Label lblNewLabel;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ToolBar( final Composite parent, int style )
	{
		super( parent, style );
		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
				true, false );
		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );
		this.setLayoutData( compositeData );

		bar = new CoolBar( this, SWT.NONE );

		coolItemLblPort = new CoolItem( bar, SWT.NONE );
		lblPort = new Label( bar, SWT.NONE );
		lblPort.setText( Messages
				.getString( "GasAnalyzerMainWindow.lblNewLabel.text" ) );
		coolItemLblPort.setPreferredSize( coolItemLblPort.computeSize(
				lblPort.computeSize( SWT.DEFAULT, SWT.DEFAULT ).x,
				lblPort.computeSize( SWT.DEFAULT, SWT.DEFAULT ).y ) );
		coolItemLblPort.setControl( lblPort );

		coolItemPortsList = new CoolItem( bar, SWT.NONE );
		portsList = new Combo( bar, SWT.BORDER );
		coolItemPortsList.setPreferredSize( coolItemPortsList.computeSize(
				portsList.computeSize( SWT.DEFAULT, SWT.DEFAULT ).x,
				portsList.computeSize( SWT.DEFAULT, SWT.DEFAULT ).y ) );
		coolItemPortsList.setControl( portsList );

		coolItemBtnRefresh = new CoolItem( bar, SWT.NONE );
		btnRefresh = new Button( bar, SWT.NONE );
		btnRefresh.setImage( SWTResourceManager.getImage( ToolBar.class,
				"/pl/industrum/gasanalyzer/gui/odswiez.png" ) );
		coolItemBtnRefresh.setPreferredSize( coolItemBtnRefresh.computeSize(
				btnRefresh.computeSize( SWT.DEFAULT, SWT.DEFAULT ).x,
				btnRefresh.computeSize( SWT.DEFAULT, SWT.DEFAULT ).y ) );
		coolItemBtnRefresh.setControl( btnRefresh );

		btnRefresh.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				refreshPortList();
			}
		} );

		coolItemBtnConnect = new CoolItem( bar, SWT.NONE );
		btnConnect = new Button( bar, SWT.TOGGLE );
		btnConnect.setText( Messages
				.getString( "GasAnalyzerMainWindow.connect.text" ) ); //$NON-NLS-1$
		coolItemBtnConnect.setPreferredSize( coolItemBtnConnect.computeSize(
				btnConnect.computeSize( SWT.DEFAULT, SWT.DEFAULT ).x,
				btnConnect.computeSize( SWT.DEFAULT, SWT.DEFAULT ).y ) );
		coolItemBtnConnect.setControl( btnConnect );

		btnConnect.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				boolean state = btnConnect.getSelection();
				connectDisconnect( state );
			}
		} );

		coolItemBtnPdf = new CoolItem( bar, SWT.NONE );
		btnPdf = new Button( bar, SWT.PUSH );
		btnPdf.setImage( SWTResourceManager.getImage( ToolBar.class,
				"/pl/industrum/gasanalyzer/gui/pdf.png" ) );
		coolItemBtnPdf.setPreferredSize( coolItemBtnPdf.computeSize(
				btnPdf.computeSize( SWT.DEFAULT, SWT.DEFAULT ).x,
				btnPdf.computeSize( SWT.DEFAULT, SWT.DEFAULT ).y ) );
		coolItemBtnPdf.setControl( btnPdf );

		btnPdf.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				PdfDialog pdfDialog = new PdfDialog( parent.getShell(),
						SWT.NONE );
				pdfDialog.open();
			}
		} );

		coolItemBtnXls = new CoolItem( bar, SWT.NONE );
		btnXls = new Button( bar, SWT.PUSH );
		btnXls.setImage( SWTResourceManager.getImage( ToolBar.class,
				"/pl/industrum/gasanalyzer/gui/excel.png" ) );
		coolItemBtnXls.setPreferredSize( coolItemBtnXls.computeSize(
				btnXls.computeSize( SWT.DEFAULT, SWT.DEFAULT ).x,
				btnXls.computeSize( SWT.DEFAULT, SWT.DEFAULT ).y ) );
		coolItemBtnXls.setControl( btnXls );

		btnXls.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				XlsDialog xlsDialog = new XlsDialog( parent.getShell(),
						SWT.NONE );
				xlsDialog.open();
			}
		} );

		refreshPortList();

		bar.pack();
		
		coolItem = new CoolItem(bar, SWT.NONE);
		
		lblNewLabel = new Label(bar, SWT.NONE);
		coolItem.setControl(lblNewLabel);
		lblNewLabel.setText(Messages.getString("ToolBar.lblNewLabel.text")); //$NON-NLS-1$
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	private void refreshPortList()
	{
		portsList.removeAll();

		for( String port: ELANConnection.vectorPorts() )
		{
			portsList.add( port );
		}

		if ( portsList.getItemCount() > 0 )
		{
			portsList.select( 0 );
		} else
		{
			btnConnect.setEnabled( false );
		}
	}

	public void setConnect()
	{
		portsList.setEnabled( true );
		btnConnect.setText( "Połącz" );
	}

	public void setDisconnect()
	{
		portsList.setEnabled( true );
		btnConnect.setText( "Połącz" );
	}

	public String getSelectedPort()
	{
		return portsList.getItem( portsList.getSelectionIndex() );
	}

	public abstract void connectDisconnect( boolean state );
}
