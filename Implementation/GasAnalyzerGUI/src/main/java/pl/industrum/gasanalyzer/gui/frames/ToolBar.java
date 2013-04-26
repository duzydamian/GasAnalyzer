package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;

import pl.industrum.gasanalyzer.gui.dialogs.PdfDialog;
import pl.industrum.gasanalyzer.gui.dialogs.XlsDialog;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.types.UsefulImage;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public abstract class ToolBar extends Composite
{
	GridData compositeData;
	private ToolItem btnPdf;
	private ToolItem btnXls;
	private org.eclipse.swt.widgets.ToolBar bar;
	private ToolItem btnRefresh;
	private ToolItem btnExit;
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param btnExit 
	 */
	public ToolBar( final Composite parent, int style )
	{
		super( parent, style );
		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
				true, false );
		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );
		this.setLayoutData( compositeData );

		bar = new org.eclipse.swt.widgets.ToolBar( this, SWT.NONE );

		btnExit = new ToolItem( bar, SWT.PUSH );
		btnExit.setToolTipText(Messages.getString("ToolBar.btnExit.toolTipText")); //$NON-NLS-1$
		btnExit.setImage( UsefulImage.SHUTDOWN.getImage() );

		btnExit.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				closeApplication();
			}
		});
		
		new ToolItem( bar, SWT.SEPARATOR );
		
		btnRefresh = new ToolItem( bar, SWT.NONE );
		btnRefresh.setToolTipText(Messages.getString("ToolBar.btnRefresh.toolTipText")); //$NON-NLS-1$
		btnRefresh.setImage( UsefulImage.REFRESH.getImage() );

		btnRefresh.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				refreshPortList();
			}
		});
		btnRefresh.setEnabled( false );

		new ToolItem( bar, SWT.SEPARATOR );
		
		btnPdf = new ToolItem( bar, SWT.PUSH );
		btnPdf.setImage( UsefulImage.PDF.getImage() );
		btnPdf.setToolTipText( Messages.getString("ToolBar.btnPdf.toolTipText") ); //$NON-NLS-1$

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
		btnPdf.setEnabled( false );

		btnXls = new ToolItem( bar, SWT.PUSH );
		btnXls.setToolTipText(Messages.getString("ToolBar.btnXls.toolTipText")); //$NON-NLS-1$
		btnXls.setImage( UsefulImage.EXCEL.getImage() );

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
		btnXls.setEnabled( false );

		bar.pack();
	}

	public void enableSurvey()
	{
		btnRefresh.setEnabled( true );
		btnXls.setEnabled( true );
		btnPdf.setEnabled( true );
	}
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public abstract void refreshPortList();
	public abstract void closeApplication();
}
