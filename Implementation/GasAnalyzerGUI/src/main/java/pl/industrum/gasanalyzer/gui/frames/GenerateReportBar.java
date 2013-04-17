package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import pl.industrum.gasanalyzer.gui.dialogs.PdfDialog;
import pl.industrum.gasanalyzer.gui.dialogs.XlsDialog;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class GenerateReportBar extends Composite
{
	Label lblStatus;
	ProgressBar progressBar;
	GridData compositeData;
	private Label lblPdf;
	private Label lblXls;
	private Button btnPdf;
	private Button btnXls;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public GenerateReportBar(final Composite parent, int style)
	{
		super(parent, style);
		compositeData = new GridData(GridData.FILL, GridData.GRAB_VERTICAL, true, false);
		compositeData.horizontalSpan = 6;
		this.setLayout(new FillLayout(SWT.HORIZONTAL));
		this.setLayoutData(compositeData);
		
		lblStatus = new Label(this, SWT.BOLD);
		lblStatus.setText("Generuj raport: ");
		
		lblPdf = new Label(this, SWT.BOLD);
		lblPdf.setText("Jako PDF: ");
				
		btnPdf = new Button(this, SWT.PUSH);
		btnPdf.setText("PDF");
		btnPdf.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) {	
				PdfDialog pdfDialog = new PdfDialog(parent.getShell(), SWT.NONE);
				pdfDialog.open();
			}
		});
		
		lblXls = new Label(this, SWT.BOLD);
		lblXls.setText("Jako XLS: ");
		
		btnXls = new Button(this, SWT.PUSH);
		btnXls.setText("XLS");
		btnXls.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) {	
				XlsDialog xlsDialog = new XlsDialog(parent.getShell(), SWT.NONE);
				xlsDialog.open();
			}
		});
		
		progressBar = new ProgressBar(this, SWT.BORDER);
		progressBar.setVisible(false);
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	@SuppressWarnings("unused")
	private void hide()
	{;
		this.setVisible(false);
	}
	
	@SuppressWarnings("unused")
	private void show()
	{
		this.setVisible(true);
	}
	
	public void hideProgressBar()
	{;
		this.progressBar.setVisible(false);
	}
	
	public void showProgressBar()
	{
		this.progressBar.setVisible(true);
	}
	
	public void setProgress(int progress)
	{
		this.progressBar.setVisible(true);
		this.progressBar.setSelection(progress);
	}
	
	public void setStatusText(String status)
	{
		this.lblStatus.setText(status);
	}
}
