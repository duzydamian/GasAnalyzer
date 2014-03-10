package pl.industrum.gasanalyzer.gui.dialogs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

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
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.hibernate.model.managers.ApplicationUserManager;
import pl.industrum.gasanalyzer.hibernate.model.managers.MeasuredObjectManager;
import pl.industrum.gasanalyzer.hibernate.model.managers.PlaceManager;
import pl.industrum.gasanalyzer.hibernate.model.managers.SurveyManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.ApplicationUser;
import pl.industrum.gasanalyzer.model.MeasuredObject;
import pl.industrum.gasanalyzer.model.Place;
import pl.industrum.gasanalyzer.model.Survey;
import pl.industrum.gasanalyzer.types.UsefulColor;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class EditSurvey extends Dialog
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "dd/MM/yyyy HH:mm", Locale.getDefault() );
	
	protected Survey result;
	protected Shell shell;
	
	GridData surveyFrameData;
	private Label lblSurveyName;
	private Text txtSurveyName;

	private Label lblSurveyDate;
	private Text txtSurveyDate;

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

	private Label lblSurveyObject;

	private Combo listSurveyObject;

	private Label icoSurveyObject;

	private Button btnNewSurveyObject;
	
	private Vector<ApplicationUser> avaibleUsers;
	private Vector<Place> avaiblePlaces;
	private Vector<MeasuredObject> avaibleObjects;
	
	private Integer editedSurveyID;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 * @param survey 
	 */
	public EditSurvey( Shell parent, int style )
	{
		super( parent, style );
		setText( "Edytuj dane pomiaru" ); //$NON-NLS-1$
		avaibleUsers = new Vector<ApplicationUser>();
		avaiblePlaces = new Vector<Place>();
		avaibleObjects = new Vector<MeasuredObject>();
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents()
	{
		shell = new Shell( getParent(), getStyle() | SWT.DIALOG_TRIM );
		shell.setSize( 600, 440 );
		shell.setText( getText() );
		shell.setImage( UsefulImage.EDIT.getImage() );
		
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
		txtSurveyName.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 2, 1 ) );
		txtSurveyName.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				try
				{
					validateName();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
			}
		} );
		
		icoSUrveyName = new Label(surveyForm, SWT.NONE);

		lblSurveyDate = new Label( surveyForm, SWT.NONE );
		lblSurveyDate.setText( Messages
				.getString( "SurveyFrame.lblSurveyDate.text" ) ); //$NON-NLS-1$

		txtSurveyDate = new Text( surveyForm, SWT.BORDER );
		txtSurveyDate.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 1, 1 ) );
		txtSurveyDate.setText( dateFormater.format( new Date() ) );;

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
				txtSurveyDate.setText( dateFormater.format( datePicker.open() ) );
			}
		} );
		
		icoSurveyDate = new Label(surveyForm, SWT.NONE);

		lblSurveyUser = new Label( surveyForm, SWT.NONE );
		lblSurveyUser.setText( Messages
				.getString( "SurveyFrame.lblSurveyUser.text" ) ); //$NON-NLS-1$

		listSurveyUser = new Combo( surveyForm, SWT.NONE | SWT.READ_ONLY );
		listSurveyUser.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				true, false, 1, 1 ) );
		
		refreshListSurveyUser();
		
		listSurveyUser.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				try
				{
					validateUser();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
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
				ApplicationUser user = addSurveyUser.open();

				refreshListSurveyUser();
				listSurveyUser.select( avaibleUsers.indexOf( user ) );
			}
		} );
		
		icoSurveyUser = new Label(surveyForm, SWT.NONE);

		lblSurveyPlace = new Label( surveyForm, SWT.NONE );
		lblSurveyPlace.setText( Messages
				.getString( "SurveyFrame.lblSurveyPlace.text" ) ); //$NON-NLS-1$

		listSurveyPlace = new Combo( surveyForm, SWT.NONE | SWT.READ_ONLY );
		listSurveyPlace.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				true, false, 1, 1 ) );
		refreshListSurveyPlace();
		listSurveyPlace.addModifyListener( new ModifyListener()
		{
			
			private boolean isPlaceSelected;

			public void modifyText( ModifyEvent arg0 )
			{
				try
				{
					isPlaceSelected = validatePlace();
					refreshListSurveyObject( avaiblePlaces.get( listSurveyPlace.getSelectionIndex() ).getId() );
					listSurveyObject.setEnabled( isPlaceSelected );
					btnNewSurveyObject.setEnabled( isPlaceSelected );
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
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
				Place place = newSurveyPlace.open();

				refreshListSurveyPlace();
				listSurveyPlace.select( avaiblePlaces.indexOf( place ) );
			}
		} );
		
		icoSurveyPlace = new Label(surveyForm, SWT.NONE);

		lblSurveyObject = new Label( surveyForm, SWT.NONE );
		lblSurveyObject.setText( Messages
				.getString( "SurveyFrame.lblSurveyObject.text" ) ); //$NON-NLS-1$

		listSurveyObject = new Combo( surveyForm, SWT.NONE | SWT.READ_ONLY );
		listSurveyObject.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				true, false, 1, 1 ) );
		
		listSurveyObject.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				try
				{
					validateObject();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
			}
		} );

		btnNewSurveyObject = new Button( surveyForm, SWT.NONE );
		btnNewSurveyObject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewSurveyObject.setImage( UsefulImage.ADD.getImage() );
		btnNewSurveyObject.setText( Messages
				.getString( "SurveyFrame.btnNewSurveyObject.text" ) ); //$NON-NLS-1$
		btnNewSurveyObject.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				Integer placeID = avaiblePlaces.get( listSurveyPlace.getSelectionIndex() ).getId();
				NewSurveyObject newSurveyObject = new NewSurveyObject( getParent()
						.getShell(), SWT.NONE, placeID );
				MeasuredObject measuredObject = newSurveyObject.open();

				refreshListSurveyObject( avaiblePlaces.get( listSurveyPlace.getSelectionIndex() ).getId() );
				listSurveyObject.select( avaibleObjects.indexOf( measuredObject ) );
			}
		} );
		
		icoSurveyObject = new Label(surveyForm, SWT.NONE);

		lblSurveyLoad = new Label( surveyForm, SWT.NONE );
		lblSurveyLoad.setText( Messages
				.getString( "SurveyFrame.lblSurveyLoad.text" ) ); //$NON-NLS-1$

		textSurveyLoad = new Text( surveyForm, SWT.BORDER );
		textSurveyLoad.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				true, false, 2, 1 ) );
		textSurveyLoad.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				try
				{
					validateLoad();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
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
				SWT.FILL, SWT.CENTER, true, false, 2, 2 );
		gd_styledTextSurveySpecialConditions.heightHint = 48;
		styledTextSurveySpecialConditions.setLayoutData( gd_styledTextSurveySpecialConditions );
		styledTextSurveySpecialConditions.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				try
				{
					validateSpecialConditions();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
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
				true, false, 2, 3 );
		gd_styledTextComment.heightHint = 71;
		styledTextComment.setLayoutData( gd_styledTextComment );
		styledTextComment.addModifyListener( new ModifyListener()
		{			
			public void modifyText( ModifyEvent arg0 )
			{
				try
				{
					validateComment();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
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
	public Survey open( Survey survey )
	{
		createContents();
		
		editedSurveyID = survey.getId();
		
		if ( survey.getName() != null )
		{
			txtSurveyName.setText( survey.getName() );
		}
		
		if ( survey.getTimestamp() != null )
		{
			txtSurveyDate.setText( dateFormater.format( survey.getTimestamp() ) );
		}
		
		if ( survey.getApplicationUser().toString() != null )
		{
			int i = 0;
			for( String userInList: listSurveyUser.getItems() )
			{
				if( userInList.equalsIgnoreCase( survey.getApplicationUser().toString() ) )
					listSurveyUser.select( i );
				i++;
			}			
		}
		
		if ( survey.getObject().getPlace().toString() != null )
		{
			int i = 0;
			for( String placeInList: listSurveyPlace.getItems() )
			{
				if( placeInList.equalsIgnoreCase( survey.getObject().getPlace().toString() ) )
					listSurveyPlace.select( i );
				i++;
			}
		}
		
		if ( survey.getObject().toString() != null )
		{
			refreshListSurveyObject( survey.getObject().getPlace().getId() );
			
			int i = 0;
			for( String objectInList: listSurveyObject.getItems() )
			{
				if( objectInList.equalsIgnoreCase( survey.getObject().toString() ) )
					listSurveyObject.select( i );
				i++;
			}
		}
		
		if ( survey.getLoad() != null )
		{
			textSurveyLoad.setText( survey.getLoad().toString() );
		}
		
		if ( survey.getSpecialConditions() != null )
		{
			styledTextSurveySpecialConditions.setText( survey.getSpecialConditions() );
		}
		
		if ( survey.getComment() != null )
		{
			styledTextComment.setText( survey.getComment() );
		}
		
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
		try
		{		
			Integer userID = avaibleUsers.get( listSurveyUser.getSelectionIndex() ).getId();
			Integer objectID = avaibleObjects.get( listSurveyObject.getSelectionIndex() ).getId();
			Date date = dateFormater.parse( txtSurveyDate.getText() );
			SurveyManager.updateSurvey( editedSurveyID, txtSurveyName.getText(), textSurveyLoad.getText(), styledTextSurveySpecialConditions.getText(), styledTextComment.getText(), objectID, userID, date );
			result = SurveyManager.getSurvey( editedSurveyID );
		}
		catch ( ParseException e )
		{
			e.printStackTrace();
		}
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
	
	@SuppressWarnings( "deprecation" )
	private boolean validateDate()
	{
		try
		{
			Date date = dateFormater.parse( txtSurveyDate.getText() );
			if ( txtSurveyDate.getText().isEmpty() | txtSurveyDate.getText() == null | date.getYear() < 90)
			{			
				setFormFieldError( lblSurveyDate, txtSurveyDate, icoSurveyDate );
				return false;
			} else
			{
				setFormFieldOK( lblSurveyDate, txtSurveyDate, icoSurveyDate );
				return true;
			}
		}
		catch ( ParseException e )
		{
			e.printStackTrace();
			setFormFieldError( lblSurveyDate, txtSurveyDate, icoSurveyDate );
			return false;			
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
	
	private boolean validateObject()
	{
		if ( listSurveyObject.getText().isEmpty() | listSurveyPlace.getText() == null )
		{
			setFormFieldError( lblSurveyObject, listSurveyObject, icoSurveyObject );
			return false;
		} else
		{
			setFormFieldOK( lblSurveyObject, listSurveyObject, icoSurveyObject );
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

		isValid &= validateName();
		
		isValid &= validateDate();

		isValid &= validateUser();
		
		isValid &= validatePlace();
		
		isValid &= validateObject();

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
		listSurveyUser.removeAll();
		avaibleUsers.clear();
		
		for( ApplicationUser user: ApplicationUserManager.getAllApplicationUsers() )
		{
			listSurveyUser.add( user.toString() );
			avaibleUsers.add( user );
		}
	}

	private void refreshListSurveyPlace()
	{
		listSurveyPlace.removeAll();
		avaiblePlaces.clear();
		
		for( Place place: PlaceManager.getAllPlaces() )
		{
			listSurveyPlace.add( place.toString() );
			avaiblePlaces.add( place );
		}
	}

	private void refreshListSurveyObject(int placeID)
	{
		listSurveyObject.removeAll();
		avaibleObjects.clear();
		
		for( MeasuredObject object: MeasuredObjectManager.getObjectsByPlace( placeID ) )
		{
			listSurveyObject.add( object.toString() );
			avaibleObjects.add( object );
		}
	}
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
