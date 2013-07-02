package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.gui.EmailSystem;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.types.UsefulImage;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.StyledText;

public class SendExceptionCatched extends Dialog
{

	protected Object result;
	protected Shell shell;
	
	private Composite surveyForm;
	GridData surveyFrameData;
	
	private Exception exceptionToSend;
	private Button btnCancel;
	private Button btnOk;
	private StyledText styledText;
	private Label lblBlaBla;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public SendExceptionCatched( Shell parent, int style, Exception exception )
	{
		super( parent, style );		
		setText( Messages.getString( "SendExceptionCatched.this.text" ) ); //$NON-NLS-1$
		exceptionToSend = exception;
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
		shell.setSize( 490, 370 );
		shell.setText( getText() );
		shell.setImage( UsefulImage.SEND_MAIL.getImage() );
		
		surveyFrameData = new GridData( GridData.FILL, GridData.CENTER, true,
				false );
		surveyFrameData.horizontalSpan = 6;
		shell.setLayoutData( surveyFrameData );
		shell.setLayout( new FillLayout( SWT.HORIZONTAL ) );
		
		surveyForm = new Composite( shell, SWT.NONE );
		GridLayout gl_surveyForm = new GridLayout( 2, false );
		gl_surveyForm.marginHeight = 10;
		surveyForm.setLayout( gl_surveyForm );
		
		lblBlaBla = new Label(surveyForm, SWT.WRAP);
		lblBlaBla.setText(Messages.getString("SendExceptionCatched.lblHeader.text"));
		lblBlaBla.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false, 2, 1));
		
		styledText = new StyledText(surveyForm, SWT.BORDER);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
		
		styledText.append( "Tutaj mozesz dodać coś od siebie");
		styledText.append( "\n\n__________________________________________________________" + 
							"\n(Proszę nie usuwać poniższego fragmentu, może on być pomocny przy rozwiązywaniu problemu)\n" );
		styledText.append( exceptionToSend.toString() + "\n" );				
		if( exceptionToSend.getMessage() != null)
			styledText.append( exceptionToSend.getMessage() + "\n" );
		for( StackTraceElement element: exceptionToSend.getStackTrace() )
		{
			styledText.append( element.toString() + "\n" );
		}		
		
		btnOk = new Button(surveyForm, SWT.RIGHT);
		btnOk.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnOk.setText(Messages.getString("SendExceptionCatched.btnOk.text")); //$NON-NLS-1$
		btnOk.setImage( UsefulImage.SEND_MAIL.getImage() );
		btnOk.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				saveAction();
				shell.dispose();
			}
		} );
		
		btnCancel = new Button(surveyForm, SWT.NONE);
		btnCancel.setText(Messages.getString("SendExceptionCatched.btnCancel.text")); //$NON-NLS-1$
		btnCancel.setImage( UsefulImage.CANCEL.getImage() );
		btnCancel.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				shell.dispose();
			}
		} );
	}

	protected void saveAction()
	{
		new EmailSystem().sendExceptionNotification( styledText.getText() );
		System.out.println( "Mail send" );
	}
}
