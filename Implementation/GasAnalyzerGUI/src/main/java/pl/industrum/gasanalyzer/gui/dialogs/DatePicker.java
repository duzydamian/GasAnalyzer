package pl.industrum.gasanalyzer.gui.dialogs;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import pl.industrum.gasanalyzer.i18n.Messages;

public class DatePicker extends Dialog
{

	protected Date result;
	protected Shell shell;
	private DateTime calendar;
	private Button ok;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DatePicker( Shell parent, int style )
	{
		super( parent, style );
		setText( Messages.getString( "DatePicker.this.text" ) ); //$NON-NLS-1$
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Date open()
	{
		createContents();
		shell.pack();
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
		shell.setLayout( new GridLayout( 1, false ) );

		calendar = new DateTime( shell, SWT.CALENDAR | SWT.BORDER );

		ok = new Button( shell, SWT.PUSH );
		ok.setText( Messages.getString( "DatePicker.ok.text" ) ); //$NON-NLS-1$
		ok.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false ) );
		ok.addSelectionListener( new SelectionAdapter()
		{
			@SuppressWarnings( "deprecation" )
			public void widgetSelected( SelectionEvent e )
			{
				result = new Date( calendar.getYear(), calendar.getMonth(), calendar.getDay(), 12, 0 );
				shell.close();
			}
		} );
		shell.setDefaultButton( ok );
	}

}
