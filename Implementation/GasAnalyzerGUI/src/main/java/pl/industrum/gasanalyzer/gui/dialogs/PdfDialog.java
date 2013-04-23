package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import pl.industrum.gasanalyzer.i18n.Messages;

public class PdfDialog extends Dialog
{

	protected Object result;
	protected Shell shell;
	private Text textFilePath;
	private Button btnBrowse;
	private Label lblFilePath;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public PdfDialog( Shell parent, int style )
	{
		super( parent, style );
		setText( Messages.getString( "PdfDialog.this.text" ) ); //$NON-NLS-1$
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open()
	{
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while ( !shell.isDisposed() )
		{
			if ( !display.readAndDispatch() )
			{
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents()
	{
		shell = new Shell( getParent(), getStyle() | SWT.DIALOG_TRIM );
		shell.setSize( 450, 300 );
		shell.setText( getText() );
		shell.setLayout( new GridLayout( 3, false ) );

		lblFilePath = new Label( shell, SWT.NONE );
		lblFilePath.setLayoutData( new GridData( SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1 ) );
		lblFilePath
				.setText( Messages.getString( "PdfDialog.lblFilePath.text" ) ); //$NON-NLS-1$

		textFilePath = new Text( shell, SWT.BORDER );
		textFilePath.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 1, 1 ) );

		btnBrowse = new Button( shell, SWT.NONE );
		btnBrowse.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				FileDialog fileDialog = new FileDialog( getParent(), SWT.NONE );
				fileDialog.setText( "Wybierz plik" );
				fileDialog.setFilterExtensions( new String[]
				{ "*.pdf" } );
				String path = fileDialog.open();
				if ( path != null )
					textFilePath.setText( path );
			}
		} );
		btnBrowse.setText( Messages.getString( "PdfDialog.btnBrowse.text" ) ); //$NON-NLS-1$
	}
}
