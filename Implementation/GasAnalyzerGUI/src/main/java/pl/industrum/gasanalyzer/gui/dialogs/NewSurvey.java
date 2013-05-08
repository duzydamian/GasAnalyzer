package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.ApplicationUser;
import pl.industrum.gasanalyzer.model.Survey;
import pl.industrum.gasanalyzer.types.UsefulColor;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class NewSurvey extends Dialog
{
	protected Survey result;
	protected Shell shell;
	
	GridData surveyFrameData;
	private Label lblSurveyName;
	private Text txtSurveyName;

	private Label lblSurveyDate;
	private DateTime txtSurveyDate;

	private Label lblSurveyUser;
	private Combo listSurveyUser;
	private Button btnNewSurveyUser;

	private Label lblSurveyPlace;
	private Button btnNewSurveyPlace;
	private Combo listSurveyPlace;

	private Label lblSurveyLoad;
	private Text textSurveyLoad;

	private Label lblSurveySpecialConditions;
	private StyledText styledTextSurveySpecialConditions;

	private Label lblComment;
	private StyledText styledTextComment;

	private Composite surveyForm;
	private Button btnSelectDate;
	private Button btnCancel;
	private Button btnOk;
	private Label icoSUrveyName;
	private Label icoSurveyDate;
	private Label icoSurveyUser;
	private Label icoSurveyPlace;
	private Label icoSurveyLoad;
	private Label icoSurveySpecialConditions;
	private Label icoComment;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public NewSurvey( Shell parent, int style )
	{
		super( parent, style );
		setText( "Dane pomiaru" ); //$NON-NLS-1$
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents()
	{
		shell = new Shell( getParent(), getStyle() | SWT.DIALOG_TRIM );
		shell.setSize( 470, 370 );
		shell.setText( getText() );
		
		surveyFrameData = new GridData( GridData.FILL, GridData.CENTER, true,
				false );
		surveyFrameData.horizontalSpan = 6;
		shell.setLayoutData( surveyFrameData );
		shell.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		surveyForm = new Composite( shell, SWT.NONE );
		surveyForm.setLayout( new GridLayout( 4, false ) );

		lblSurveyName = new Label( surveyForm, SWT.NONE );
		lblSurveyName.setSize( 44, 17 );
		lblSurveyName.setText( Messages
				.getString( "SurveyFrame.lblSurveyName.text" ) ); //$NON-NLS-1$

		txtSurveyName = new Text( surveyForm, SWT.BORDER );
		txtSurveyName.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
				false, 2, 1 ) );
		txtSurveyName.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateName();
			}
		} );
		
		icoSUrveyName = new Label(surveyForm, SWT.NONE);

		lblSurveyDate = new Label( surveyForm, SWT.NONE );
		lblSurveyDate.setText( Messages
				.getString( "SurveyFrame.lblSurveyDate.text" ) ); //$NON-NLS-1$

		txtSurveyDate = new DateTime( surveyForm, SWT.DATE );
		txtSurveyDate.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
				false, 1, 1 ) );

		btnSelectDate = new Button( surveyForm, SWT.NONE );
		btnSelectDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnSelectDate.setImage( UsefulImage.CALENDAR.getImage() );
		btnSelectDate.setText( Messages
				.getString( "SurveyFrame.btnSelectDate.text" ) ); //$NON-NLS-1$
		btnSelectDate.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				DatePicker datePicker = new DatePicker( getParent().getShell(),
						SWT.NONE );
				int[] date = datePicker.open();
				if ( date != null )
					txtSurveyDate.setDate( date[0], date[1], date[2] );
			}
		} );
		
		icoSurveyDate = new Label(surveyForm, SWT.NONE);

		lblSurveyUser = new Label( surveyForm, SWT.NONE );
		lblSurveyUser.setText( Messages
				.getString( "SurveyFrame.lblSurveyUser.text" ) ); //$NON-NLS-1$

		listSurveyUser = new Combo( surveyForm, SWT.NONE );
		listSurveyUser.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				false, false, 1, 1 ) );
		listSurveyUser.add( "Jan Wężyk" );
		listSurveyUser.add( "Kuba guzik" );
		listSurveyUser.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateUser();
			}
		} );

		btnNewSurveyUser = new Button( surveyForm, SWT.NONE );
		btnNewSurveyUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewSurveyUser.setImage( UsefulImage.ADD.getImage() );
		btnNewSurveyUser.setText( Messages
				.getString( "SurveyFrame.btnNewSurveyUser.text" ) ); //$NON-NLS-1$
		btnNewSurveyUser.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				NewSurveyUser addSurveyUser = new NewSurveyUser( getParent()
						.getShell(), SWT.NONE );
				addSurveyUser.open();

				refreshListSurveyUser();
			}
		} );
		
		icoSurveyUser = new Label(surveyForm, SWT.NONE);

		lblSurveyPlace = new Label( surveyForm, SWT.NONE );
		lblSurveyPlace.setText( Messages
				.getString( "SurveyFrame.lblSurveyPlace.text" ) ); //$NON-NLS-1$

		listSurveyPlace = new Combo( surveyForm, SWT.NONE );
		listSurveyPlace.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				false, false, 1, 1 ) );
		listSurveyPlace.add( "Narnia" );
		listSurveyPlace.add( "Mordor" );
		listSurveyPlace.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validatePlace();
			}
		} );

		btnNewSurveyPlace = new Button( surveyForm, SWT.NONE );
		btnNewSurveyPlace.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewSurveyPlace.setImage( UsefulImage.ADD.getImage() );
		btnNewSurveyPlace.setText( Messages
				.getString( "SurveyFrame.btnNewSurveyPlace.text" ) ); //$NON-NLS-1$
		btnNewSurveyPlace.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				NewSurveyPlace newSurveyPlace = new NewSurveyPlace( getParent()
						.getShell(), SWT.NONE );
				newSurveyPlace.open();

				refreshListSurveyPlace();
			}
		} );
		
		icoSurveyPlace = new Label(surveyForm, SWT.NONE);

		lblSurveyLoad = new Label( surveyForm, SWT.NONE );
		lblSurveyLoad.setText( Messages
				.getString( "SurveyFrame.lblSurveyLoad.text" ) ); //$NON-NLS-1$

		textSurveyLoad = new Text( surveyForm, SWT.BORDER );
		textSurveyLoad.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				false, false, 2, 1 ) );
		textSurveyLoad.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateLoad();
			}
		} );
		
		icoSurveyLoad = new Label(surveyForm, SWT.NONE);

		lblSurveySpecialConditions = new Label( surveyForm, SWT.NONE );
		lblSurveySpecialConditions.setText( Messages
				.getString( "SurveyFrame.lblSurveySpecialConditions.text" ) ); //$NON-NLS-1$
		lblSurveySpecialConditions.setLayoutData( new GridData(
				SWT.FILL, SWT.CENTER, false, false, 1, 2 ) );

		styledTextSurveySpecialConditions = new StyledText( surveyForm,
				SWT.BORDER | SWT.WRAP );
		GridData gd_styledTextSurveySpecialConditions = new GridData(
				SWT.FILL, SWT.CENTER, false, false, 2, 2 );
		gd_styledTextSurveySpecialConditions.heightHint = 48;
		styledTextSurveySpecialConditions.setLayoutData( gd_styledTextSurveySpecialConditions );
		styledTextSurveySpecialConditions.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateSpecialConditions();
			}
		} );
		new Label(surveyForm, SWT.NONE);
		
		icoSurveySpecialConditions = new Label(surveyForm, SWT.NONE);

		lblComment = new Label( surveyForm, SWT.NONE );
		lblComment
				.setText( Messages.getString( "SurveyFrame.lblComment.text" ) ); //$NON-NLS-1$
		lblComment.setLayoutData( new GridData(
				SWT.FILL, SWT.CENTER, false, false, 1, 2 ) );

		styledTextComment = new StyledText( surveyForm, SWT.BORDER | SWT.WRAP );
		GridData gd_styledTextComment = new GridData( SWT.FILL, SWT.FILL,
				false, false, 2, 3 );
		gd_styledTextComment.heightHint = 71;
		styledTextComment.setLayoutData( gd_styledTextComment );
		styledTextComment.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateComment();
			}
		} );
		new Label(surveyForm, SWT.NONE);
		
		icoComment = new Label(surveyForm, SWT.NONE);
		new Label(surveyForm, SWT.NONE);
		new Label(surveyForm, SWT.NONE);
		new Label(surveyForm, SWT.NONE);
		
		btnOk = new Button(surveyForm, SWT.RIGHT);
		btnOk.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnOk.setText(Messages.getString("NewSurvey.btnOk.text")); //$NON-NLS-1$
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
		
		btnCancel = new Button(surveyForm, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCancel.setText(Messages.getString("NewSurvey.btnAnuluj.text")); //$NON-NLS-1$
		new Label(surveyForm, SWT.NONE);
		btnCancel.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				shell.dispose();
			}
		} );
	}
	
	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Survey open()
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

	protected void saveAction()
	{
		result = new Survey(1, null, new ApplicationUser(), txtSurveyName.getText(), 1.1, styledTextSurveySpecialConditions.getText(), styledTextComment.getText(), null);
	}

	private boolean validateName()
	{
		if ( txtSurveyName.getText().isEmpty() | txtSurveyName.getText() == null )
		{
			setFormFieldError( lblSurveyName, txtSurveyName, icoSUrveyName );
			return false;
		} else
		{
			setFormFieldOK( lblSurveyName, txtSurveyName, icoSUrveyName );
			return true;
		}
	}
	
	private boolean validateDate()
	{
		if ( txtSurveyDate.getData() == null )
		{
			setFormFieldError( lblSurveyDate, txtSurveyDate, icoSurveyDate );
			return false;
		} else
		{
			setFormFieldOK( lblSurveyDate, txtSurveyDate, icoSurveyDate );
			return true;
		}
	}
	
	private boolean validateUser()
	{
		if ( listSurveyUser.getText().isEmpty() | listSurveyUser.getText() == null )
		{
			setFormFieldError( lblSurveyUser, listSurveyUser, icoSurveyUser );
			return false;
		} else
		{
			setFormFieldOK( lblSurveyUser, listSurveyUser, icoSurveyUser );
			return true;
		}
	}

	private boolean validatePlace()
	{
		if ( listSurveyPlace.getText().isEmpty() | listSurveyPlace.getText() == null )
		{
			setFormFieldError( lblSurveyPlace, listSurveyPlace, icoSurveyPlace );
			return false;
		} else
		{
			setFormFieldOK( lblSurveyPlace, listSurveyPlace, icoSurveyPlace );
			return true;
		}
	}
	
	private void validateLoad()
	{
		if ( textSurveyLoad.getText().isEmpty() | textSurveyLoad.getText() == null )
		{
			setFormFieldWarning( lblSurveyLoad, textSurveyLoad, icoSurveyLoad );
		} else
		{
			setFormFieldOK( lblSurveyLoad, textSurveyLoad, icoSurveyLoad );
		}
	}
	
	private void validateSpecialConditions()
	{
		if ( styledTextSurveySpecialConditions.getText().isEmpty() | styledTextSurveySpecialConditions.getText() == null )
		{
			setFormFieldWarning( lblSurveySpecialConditions, styledTextSurveySpecialConditions, icoSurveySpecialConditions );
		} else
		{
			setFormFieldOK( lblSurveySpecialConditions, styledTextSurveySpecialConditions, icoSurveySpecialConditions );
		}
	}
	
	private void validateComment()
	{
		if ( styledTextComment.getText().isEmpty() | styledTextComment.getText() == null )
		{
			setFormFieldWarning( lblComment, styledTextComment, icoComment );
		} else
		{
			setFormFieldOK( lblComment, styledTextComment, icoComment );
		}
	}
	
	private boolean validateAll()
	{
		boolean isValid = true;

		isValid = validateName();
		
		isValid = validateDate();

		isValid = validateUser();
		
		isValid = validatePlace();

		validateLoad();
		
		validateSpecialConditions();
		
		validateComment();
		
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
	
	private void refreshListSurveyUser()
	{

	}

	private void refreshListSurveyPlace()
	{

	}
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
