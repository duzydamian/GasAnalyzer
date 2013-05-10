package pl.industrum.gasanalyzer.gui.dialogs;

import java.text.SimpleDateFormat;
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
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.hibernate.model.managers.SurveyManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Survey;

public class OpenSurvey extends Dialog
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

	private Label lblSurveyPlace;
	private Combo listSurveyPlace;

	private Label lblSurveyLoad;
	private Text textSurveyLoad;

	private Label lblSurveySpecialConditions;
	private StyledText styledTextSurveySpecialConditions;

	private Label lblComment;
	private StyledText styledTextComment;

	private Composite surveyForm;
	private Button btnCancel;
	private Button btnOk;
	
	private Combo comboAllSurvey;
	private Vector<Survey> avaibleSurveys;
	
	private Label lblSurveyObject;
	private Combo listSurveyObject;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public OpenSurvey( Shell parent, int style )
	{
		super( parent, style );
		setText( "Dane pomiaru" ); //$NON-NLS-1$
		avaibleSurveys = new Vector<Survey>();
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents()
	{
		shell = new Shell( getParent(), getStyle() | SWT.DIALOG_TRIM );
		shell.setSize( 350, 449 );
		shell.setText( getText() );
		
		surveyFrameData = new GridData( GridData.FILL, GridData.CENTER, true,
				false );
		surveyFrameData.horizontalSpan = 6;
		shell.setLayoutData( surveyFrameData );
		shell.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		surveyForm = new Composite( shell, SWT.NONE );
		surveyForm.setLayout( new GridLayout( 3, false ) );

		comboAllSurvey = new Combo( surveyForm, SWT.BORDER );
		comboAllSurvey.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
				false, 3, 1 ) );
		comboAllSurvey.addModifyListener( new ModifyListener()
		{			
			public void modifyText( ModifyEvent arg0 )
			{
				loadSurvaeyData( avaibleSurveys.get( comboAllSurvey.getSelectionIndex() ) );
			}
		} );
		loadSurveys();
		
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
		
		new Label(surveyForm, SWT.NONE);
		
		btnOk = new Button(surveyForm, SWT.RIGHT);
		btnOk.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnOk.setText(Messages.getString("NewSurvey.btnOk.text")); //$NON-NLS-1$
		btnOk.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				if ( !txtSurveyName.getText().isEmpty() )
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
	
	private void loadSurveys()
	{
		for( Survey survey: SurveyManager.getAllSurveys() )
		{
			comboAllSurvey.add( survey.getName() );
			avaibleSurveys.add( survey );
		}
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
		result = avaibleSurveys.get( comboAllSurvey.getSelectionIndex() );
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
		
//		if ( survey.getApplicationUser() != null )
//		{
//			listSurveyUser.setText( survey.getApplicationUser().toString() );
//		}
//		
//		if ( survey.getObject().getPlace() != null )
//		{
//			listSurveyPlace.setText( survey.getObject().getPlace().toString() );
//		}
//		
//		if ( survey.getObject() != null )
//		{
//			listSurveyObject.setText( survey.getObject().toString() );
//		}
		
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
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
