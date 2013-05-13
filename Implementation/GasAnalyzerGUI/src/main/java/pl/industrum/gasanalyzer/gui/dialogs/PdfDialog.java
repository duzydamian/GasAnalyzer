package pl.industrum.gasanalyzer.gui.dialogs;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.hibernate.model.managers.ApplicationUserManager;
import pl.industrum.gasanalyzer.hibernate.model.managers.MeasuredObjectManager;
import pl.industrum.gasanalyzer.hibernate.model.managers.PlaceManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.ApplicationUser;
import pl.industrum.gasanalyzer.model.MeasuredObject;
import pl.industrum.gasanalyzer.model.Place;
import pl.industrum.gasanalyzer.model.Survey;
import pl.industrum.gasanalyzer.report.PDFGenerator;
import pl.industrum.gasanalyzer.types.UsefulColor;
import org.eclipse.swt.widgets.ProgressBar;

public class PdfDialog extends Dialog
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "dd/MM/yyyy HH:mm", Locale.getDefault() );
	
	protected Object result;
	protected Shell shell;
	private Text textFilePath;
	private Button btnBrowse;
	private Label lblFilePath;

	private Button btnCancel;
	private Button btnOk;
	private Composite surveyForm;
	private Label lblSurveyName;
	private Text txtSurveyName;
	private Label lblSurveyDate;
	private Text txtSurveyDate;
	private Label lblSurveyUser;
	private Combo listSurveyUser;
	private Label lblSurveyPlace;
	private Combo listSurveyPlace;
	private Label lblSurveyObject;
	private Combo listSurveyObject;
	private Label lblSurveyLoad;
	private Label lblSurveySpecialConditions;
	private StyledText styledTextSurveySpecialConditions;
	private Text textSurveyLoad;
	private Label lblComment;
	private StyledText styledTextComment;
	
	private Vector<ApplicationUser> avaibleUsers;
	private Vector<Place> avaiblePlaces;
	private Vector<MeasuredObject> avaibleObjects;
	private Survey surveyToGenerateReport;
	private Label lblGeneration;
	private ProgressBar progressBar;
	
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
		avaibleUsers = new Vector<ApplicationUser>();
		avaiblePlaces = new Vector<Place>();
		avaibleObjects = new Vector<MeasuredObject>();
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open( Survey survey)
	{
		createContents();
		shell.open();
		shell.layout();
		surveyToGenerateReport = survey;
		loadSurvaeyData( survey );
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
		shell.setSize( 450, 470 );
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
		
		surveyForm = new Composite( shell, SWT.NONE );
		surveyForm.setLayout( new GridLayout( 3, false ) );
		surveyForm.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false,
				false, 3, 1 ) );
		
		lblSurveyName = new Label( surveyForm, SWT.NONE );
		lblSurveyName.setSize( 44, 17 );
		lblSurveyName.setText( Messages
				.getString( "SurveyFrame.lblSurveyName.text" ) ); //$NON-NLS-1$

		txtSurveyName = new Text( surveyForm, SWT.BORDER );
		txtSurveyName.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
				false, 2, 1 ) );
		txtSurveyName.setEnabled( false );

		lblSurveyDate = new Label( surveyForm, SWT.NONE );
		lblSurveyDate.setText( Messages
				.getString( "SurveyFrame.lblSurveyDate.text" ) ); //$NON-NLS-1$

		txtSurveyDate = new Text( surveyForm, SWT.BORDER );
		txtSurveyDate.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
				false, 2, 1 ) );
		txtSurveyDate.setEnabled( false );

		lblSurveyUser = new Label( surveyForm, SWT.NONE );
		lblSurveyUser.setText( Messages
				.getString( "SurveyFrame.lblSurveyUser.text" ) ); //$NON-NLS-1$

		listSurveyUser = new Combo( surveyForm, SWT.NONE );
		listSurveyUser.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				false, false, 2, 1 ) );
		listSurveyUser.setEnabled( false );
		
		lblSurveyPlace = new Label( surveyForm, SWT.NONE );
		lblSurveyPlace.setText( Messages
				.getString( "SurveyFrame.lblSurveyPlace.text" ) ); //$NON-NLS-1$
		
		listSurveyPlace = new Combo( surveyForm, SWT.NONE );
		listSurveyPlace.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				false, false, 2, 1 ) );		
		listSurveyPlace.setEnabled( false );
		
		lblSurveyObject = new Label( surveyForm, SWT.NONE );
		lblSurveyObject.setText( Messages
				.getString( "SurveyFrame.lblSurveyObject.text" ) ); //$NON-NLS-1$

		listSurveyObject = new Combo( surveyForm, SWT.NONE );
		listSurveyObject.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				false, false, 2, 1 ) );
		listSurveyObject.setEnabled( false );
		
		lblSurveyLoad = new Label( surveyForm, SWT.NONE );
		lblSurveyLoad.setText( Messages
				.getString( "SurveyFrame.lblSurveyLoad.text" ) ); //$NON-NLS-1$

		textSurveyLoad = new Text( surveyForm, SWT.BORDER );
		textSurveyLoad.setLayoutData( new GridData( SWT.FILL, SWT.CENTER,
				false, false, 2, 1 ) );
		textSurveyLoad.setEnabled( false );
		
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
		styledTextSurveySpecialConditions.setEnabled( false );
		
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
		styledTextComment.setEnabled( false );
		new Label(surveyForm, SWT.NONE);
		
		lblGeneration = new Label(shell, SWT.NONE);
		lblGeneration.setText(Messages.getString("PdfDialog.lblGenerowanie.text")); //$NON-NLS-1$
		lblGeneration.setVisible( false );
		
		progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
				false, 2, 1 ) );
		progressBar.setVisible( false );
		
		new Label( shell, SWT.NONE );
		
		btnOk = new Button(shell, SWT.RIGHT);
		btnOk.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnOk.setText(Messages.getString("NewSurvey.btnOk.text")); //$NON-NLS-1$
		btnOk.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				if ( !textFilePath.getText().isEmpty() )
				{
					saveAction();
					shell.dispose();
				}
				else
				{
					textFilePath.setBackground( UsefulColor.RED_ERROR.getColor() );
				}
			}
		} );
		
		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCancel.setText(Messages.getString("NewSurvey.btnAnuluj.text")); //$NON-NLS-1$
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		btnCancel.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				shell.dispose();
			}
		} );
		
		refreshListSurveyUser();
		refreshListSurveyPlace();
		refreshListSurveyObject();
	}
	
	protected void saveAction()
	{
		lblGeneration.setVisible( true );
		progressBar.setVisible( true );
		String path = textFilePath.getText();
		PDFGenerator generator = new PDFGenerator()
		{
			@Override
			public void progressIncrement()
			{
				progressBar.setSelection( progressBar.getSelection()+1 );
			}
		};
		generator.create( surveyToGenerateReport, path);
		generator.open( path );
	}
	
	private void loadSurvaeyData(Survey survey)
	{
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
		
		if ( survey.getObject().toString() != null )
		{
			int i = 0;
			for( String objectInList: listSurveyObject.getItems() )
			{
				if( objectInList.equalsIgnoreCase( survey.getObject().toString() ) )
					listSurveyObject.select( i );
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

	private void refreshListSurveyObject()
	{
		listSurveyObject.removeAll();
		avaibleObjects.clear();
		
		for( MeasuredObject object: MeasuredObjectManager.getAllObjects() )
		{
			listSurveyObject.add( object.toString() );
			avaibleObjects.add( object );
		}
	}
}
