package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Label;

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
	private Button btnPdf;
	private Button btnXls;
	private CoolBar bar;
	private CoolItem coolItemBtnPdf;
	private CoolItem coolItemBtnXls;
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

		coolItemBtnRefresh = new CoolItem( bar, SWT.NONE );
		btnRefresh = new Button( bar, SWT.NONE );
		btnRefresh.setToolTipText(Messages.getString("ToolBar.btnRefresh.toolTipText")); //$NON-NLS-1$
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
		});

		coolItemBtnPdf = new CoolItem( bar, SWT.NONE );
		btnPdf = new Button( bar, SWT.PUSH );
		btnPdf.setImage( SWTResourceManager.getImage( ToolBar.class,
				"/pl/industrum/gasanalyzer/gui/pdf.png" ) );
		btnPdf.setToolTipText( "Generuj raport jako PDF" );
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
		btnXls.setToolTipText(Messages.getString("ToolBar.btnXls.toolTipText")); //$NON-NLS-1$
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

	public abstract void refreshPortList();
}
