package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.hibernate.model.managers.PlaceManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Place;
import pl.industrum.gasanalyzer.types.UsefulColor;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class EditSurveyPlace extends Dialog
{
	protected Place result;
	protected Shell shell;
	private Text textName;	
	private Label lblName;
	private Button btnOk;
	private Button btnCancel;
	private Display display;
	private Label icoName;
	private Label icoAddress;
	private Text textAddress;
	private Label lblAddress;
	private Label lblCity;
	private Text textCity;
	private Label icoCity;
	private Label lblPostCode;
	private Text textPostCode;
	private Label icoPostCode;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public EditSurveyPlace( Shell parent, int style )
	{
		super( parent, style );
		setText( Messages.getString( "NewSurveyPlace.this.text" ) ); //$NON-NLS-1$
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Place open()
	{
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
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
		shell.setSize( 260, 195 );
		shell.setText( getText() );
		shell.setLayout( new GridLayout( 4, false ) );

		lblName = new Label( shell, SWT.RIGHT );
		lblName.setText( Messages.getString( "NewSurveyPlace.lblName.text" ) ); //$NON-NLS-1$

		textName = new Text( shell, SWT.BORDER );
		textName.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 2, 1 ) );
		textName.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateName();
			}
		} );
		
		icoName = new Label(shell, SWT.NONE);
		icoName.setImage( null );
//////////////////////////////////
		lblCity = new Label( shell, SWT.RIGHT );
		lblCity.setText( Messages.getString( "NewSurveyPlace.lblCity.text" ) ); //$NON-NLS-1$

		textCity = new Text( shell, SWT.BORDER );
		textCity.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 2, 1 ) );
		textCity.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateCity();
			}
		} );
		
		icoCity = new Label(shell, SWT.NONE);
		icoCity.setImage( null );
///////////////////////////////////
		lblPostCode = new Label( shell, SWT.RIGHT );
		lblPostCode.setText( Messages.getString( "NewSurveyPlace.lblPostCode.text" ) ); //$NON-NLS-1$

		textPostCode = new Text( shell, SWT.BORDER );
		textPostCode.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 2, 1 ) );
		textPostCode.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validatePostCode();
			}
		} );
		
		icoPostCode = new Label(shell, SWT.NONE);
		icoPostCode.setImage( null );
//////////////////////////////////////////////////////////////
		lblAddress = new Label( shell, SWT.RIGHT );
		lblAddress.setText( Messages.getString( "NewSurveyPlace.lblAddress.text" ) ); //$NON-NLS-1$

		textAddress = new Text( shell, SWT.BORDER );
		textAddress.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 2, 1 ) );
		textAddress.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateAddress();
			}
		} );
		
		icoAddress = new Label(shell, SWT.NONE);
		icoAddress.setImage( null );
		new Label( shell, SWT.NONE );

		btnOk = new Button( shell, SWT.NONE );
		btnOk.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false,
				1, 1 ) );
		btnOk.setText( Messages.getString( "NewSurveyUser.btnOk.text" ) ); //$NON-NLS-1$
		btnOk.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				if ( validateAll() )
				{
					saveAction();
					shell.dispose();
				}
			}
		} );

		btnCancel = new Button( shell, SWT.NONE );
		btnCancel.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
				false, 1, 1 ) );
		btnCancel
				.setText( Messages.getString( "NewSurveyUser.btnCancel.text" ) ); //$NON-NLS-1$
		new Label(shell, SWT.NONE);
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
		result = PlaceManager.getPlace( PlaceManager.addPlace( textName.getText(), textCity.getText(), textPostCode.getText(), textAddress.getText() ) );
	}
	
	private boolean validateName()
	{
		if ( textName.getText().isEmpty() | textName.getText() == null )
		{
			setFormFieldError( lblName, textName, icoName );
			return false;
		} else
		{
			setFormFieldOK( lblName, textName, icoName );
			return true;
		}
	}

	private boolean validateAddress()
	{
		if ( textAddress.getText().isEmpty() | textAddress.getText() == null )
		{
			setFormFieldWarning( lblAddress, textAddress, icoAddress );
			return false;
		} else
		{
			setFormFieldOK( lblAddress, textAddress, icoAddress );
			return true;
		}
	}

	private boolean validatePostCode()
	{
		if ( textPostCode.getText().isEmpty() | textPostCode.getText() == null )
		{
			setFormFieldWarning( lblPostCode, textPostCode, icoPostCode );
			return false;
		} else
		{
			setFormFieldOK( lblPostCode, textPostCode, icoPostCode );
			return true;
		}
	}

	private boolean validateCity()
	{
		if ( textCity.getText().isEmpty() | textCity.getText() == null )
		{
			setFormFieldWarning( lblCity, textCity, icoCity );
			return false;
		} else
		{
			setFormFieldOK( lblCity, textCity, icoCity );
			return true;
		}
	}
	
	private boolean validateAll()
	{
		boolean isValid = true;
		
		isValid = validateName();
		
		isValid = validateCity();
		
		isValid = validatePostCode();
		
		isValid = validateAddress();
		
		return isValid;
	}

	private void setFormFieldError( Label label, Control textField, Label ico )
	{
		ico.setImage( UsefulImage.ERROR.getImage() );
		ico.getParent().layout();
		textField.setBackground( UsefulColor.RED_ERROR.getColor() );
	}
	
	private void setFormFieldWarning( Label label, Control textField, Label ico )
	{
		ico.setImage( UsefulImage.WARNING.getImage() );
		ico.getParent().layout();
		textField.setBackground( UsefulColor.YELLOW_WARNING.getColor() );
	}
	
	private void setFormFieldOK( Label label, Control textField, Label ico )
	{
		ico.setImage( UsefulImage.OK.getImage() );
		ico.getParent().layout();
		textField.setBackground( UsefulColor.WHITE.getColor() );
	}
}

