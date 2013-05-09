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

import pl.industrum.gasanalyzer.hibernate.model.managers.MeasuredObjectManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.MeasuredObject;
import pl.industrum.gasanalyzer.types.UsefulColor;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class NewSurveyObject extends Dialog
{
	protected MeasuredObject result;
	protected Shell shell;
	private Text textName;	
	private Label lblName;
	private Button btnOk;
	private Button btnCancel;
	private Display display;
	private Label icoName;
	private Integer placeID;
	private Label icoDesciption;
	private Text textDesciption;
	private Label lblDesciption;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public NewSurveyObject( Shell parent, int style, Integer givenPlaceID )
	{
		super( parent, style );
		setText( Messages.getString( "NewSurveyObject.this.text" ) ); //$NON-NLS-1$
		placeID = givenPlaceID;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public MeasuredObject open()
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
		shell.setSize( 255, 135 );
		shell.setText( getText() );
		shell.setLayout( new GridLayout( 4, false ) );

		lblName = new Label( shell, SWT.RIGHT );
		lblName.setText( Messages.getString( "NewSurveyObject.lblName.text" ) ); //$NON-NLS-1$

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

		lblDesciption = new Label( shell, SWT.RIGHT );
		lblDesciption.setText( Messages.getString( "NewSurveyObject.lblDescription.text" ) ); //$NON-NLS-1$

		textDesciption = new Text( shell, SWT.BORDER );
		textDesciption.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 2, 1 ) );
		textDesciption.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateDesciption();
			}
		} );
		
		icoDesciption = new Label(shell, SWT.NONE);
		icoDesciption.setImage( null );
		
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
		result = MeasuredObjectManager.getObject( MeasuredObjectManager.addObject( textName.getText(), textDesciption.getText(), placeID ) );
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

	private boolean validateDesciption()
	{
		if ( textDesciption.getText().isEmpty() | textDesciption.getText() == null )
		{
			setFormFieldWarning( lblDesciption, textDesciption, icoDesciption );
			return false;
		} else
		{
			setFormFieldOK( lblDesciption, textDesciption, icoDesciption );
			return true;
		}
	}
	
	private boolean validateAll()
	{
		boolean isValid = true;
		
		isValid = validateName();
		
		isValid = validateDesciption();
		
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