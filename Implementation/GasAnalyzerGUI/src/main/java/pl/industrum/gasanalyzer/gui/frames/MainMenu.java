package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import pl.industrum.gasanalyzer.gui.EmailSystem;
import pl.industrum.gasanalyzer.gui.dialogs.About;
import pl.industrum.gasanalyzer.gui.dialogs.DevicePreferences;
import pl.industrum.gasanalyzer.gui.dialogs.EditSurvey;
import pl.industrum.gasanalyzer.gui.dialogs.EditSurveyUserFunction;
import pl.industrum.gasanalyzer.gui.dialogs.EditSurveyUserTitle;
import pl.industrum.gasanalyzer.gui.dialogs.NewSurvey;
import pl.industrum.gasanalyzer.gui.dialogs.OpenSurvey;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Survey;
import pl.industrum.gasanalyzer.types.UsefulImage;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public abstract class MainMenu extends Menu
{

	private MenuItem mntmSurvey;
	private MenuItem mntmNetwork;

	/**
	 * @param arg0
	 * @param arg1
	 * @wbp.parser.entryPoint
	 */
	public MainMenu( Decorations arg0, int arg1 )
	{
		super( arg0, arg1 );

	//_________________File___________________________________________________
		MenuItem mntmfile = new MenuItem( this, SWT.CASCADE );
		mntmfile.setText( Messages.getString( "MainMenu.File" ) ); //$NON-NLS-1$
		
		Menu menuFile = new Menu( mntmfile );
		mntmfile.setMenu( menuFile );

		MenuItem mntmNewSurvey = new MenuItem( menuFile, SWT.NONE );
		mntmNewSurvey.setText( Messages.getString( "MainMenu.NewSurvey" ) );
		mntmNewSurvey.setImage( UsefulImage.NEW_SURVEY.getImage() );
		mntmNewSurvey.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				NewSurvey newSurvey = new NewSurvey( getShell(), SWT.NONE );
				Survey survey = newSurvey.open();
				if ( survey != null )
				{
					newSurveyCreated(survey);
					enableSurvey();
				}				
			}
		} );
		
		MenuItem mntmOpenSurvey = new MenuItem( menuFile, SWT.NONE );
		mntmOpenSurvey.setText( Messages.getString( "MainMenu.OpenSurvey" ) );
		mntmOpenSurvey.setImage( UsefulImage.OPEN_SURVEY.getImage() );
		mntmOpenSurvey.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				OpenSurvey newSurvey = new OpenSurvey( getShell(), SWT.NONE );
				Survey survey = newSurvey.open();
				if ( survey != null )
				{
					newSurveyCreated(survey);
					enableSurvey();
				}				
			}
		} );
		
		new MenuItem( menuFile, SWT.SEPARATOR );
		
		MenuItem mntmExit = new MenuItem( menuFile, SWT.NONE );
		mntmExit.setText( Messages.getString( "MainMenu.Exit" ) ); //$NON-NLS-1$
		mntmExit.setImage( UsefulImage.SHUTDOWN.getImage() );
		mntmExit.setAccelerator( SWT.MOD1 + 'Q' );
		mntmExit.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				closeApplication();
			}
		} );
		
	//_________________Edit___________________________________________________
		MenuItem mntmEdit = new MenuItem( this, SWT.CASCADE );
		mntmEdit.setText( Messages.getString( "MainMenu.Edit" ) ); //$NON-NLS-1$

		Menu menuEdit = new Menu( mntmEdit );
		mntmEdit.setMenu( menuEdit );
		
		MenuItem mntmEditTitle = new MenuItem( menuEdit, SWT.NONE );
		mntmEditTitle.setText( "Edytuj tytuły" );
		mntmEditTitle.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				EditSurveyUserFunction editSurveyUserFunction = new EditSurveyUserFunction( getShell(), SWT.NONE );
				editSurveyUserFunction.open();
			}
		} );
		
		MenuItem mntmEditFunction = new MenuItem( menuEdit, SWT.NONE );
		mntmEditFunction.setText( "Edytuj funkcje" );
		mntmEditFunction.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				EditSurveyUserTitle editSurveyUserTitle = new EditSurveyUserTitle( getShell(), SWT.NONE );
				editSurveyUserTitle.open();
			}
		} );
		
		//_________________Survey___________________________________________________		
		mntmSurvey = new MenuItem( this, SWT.CASCADE );
		mntmSurvey.setText( Messages.getString( "MainMenu.Survey" ) ); //$NON-NLS-1$
		mntmSurvey.setEnabled( false );
		
		Menu menuSurvey = new Menu( mntmSurvey );
		mntmSurvey.setMenu( menuSurvey );

		MenuItem mntmEditDevice = new MenuItem( menuSurvey, SWT.NONE );
		mntmEditDevice.setText( "Edytuj urządzenia" );
		mntmEditDevice.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				DevicePreferences preferences = new DevicePreferences( getShell(), SWT.NONE );
				preferences.open();
			}
		} );
		
		MenuItem mntmPreferences = new MenuItem( menuSurvey, SWT.NONE );
		mntmPreferences.setImage( UsefulImage.PREFERENCES.getImage() );
		mntmPreferences.setText( Messages.getString( "MainMenu.Preferences" ) ); //$NON-NLS-1$
		mntmPreferences.setAccelerator( SWT.MOD1 + 'P' );
		mntmPreferences.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				Survey survey = getSurveyToEdit();
				EditSurvey editSurvey = new EditSurvey( getShell(), SWT.NONE );
				survey = editSurvey.open( survey );
				if ( survey != null )
				{
					newSurveyCreated(survey);
					enableSurvey();
				}				
			}
		} );

		//_________________Network___________________________________________________		
		mntmNetwork = new MenuItem( this, SWT.CASCADE );
		mntmNetwork.setText( Messages.getString( "MainMenu.Network" ) ); //$NON-NLS-1$
		mntmNetwork.setEnabled( false );

		Menu menuNetwork = new Menu( mntmNetwork );
		mntmNetwork.setMenu( menuNetwork );

		MenuItem mntmConnect = new MenuItem( menuNetwork, SWT.CASCADE );
		mntmConnect.setText( Messages.getString( "MainMenu.Connect" ) ); //$NON-NLS-1$
		
		MenuItem mntmDisconnect = new MenuItem( menuNetwork, SWT.CASCADE );
		mntmDisconnect.setText( Messages.getString( "MainMenu.Disconnect" ) ); //$NON-NLS-1$
		
		MenuItem mntmRefresh = new MenuItem( menuNetwork, SWT.NONE );
		mntmRefresh.setText( Messages.getString( "MainMenu.Refresh" ) ); //$NON-NLS-1$
		mntmRefresh.setImage( UsefulImage.REFRESH.getImage() );
		mntmRefresh.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				refreshDeviceTree();
			}
		} );
		
	//_________________Help___________________________________________________		
		MenuItem mntmHelp = new MenuItem( this, SWT.CASCADE );
		mntmHelp.setText( Messages.getString( "MainMenu.Help" ) ); //$NON-NLS-1$

		Menu menuHelp = new Menu( mntmHelp );
		mntmHelp.setMenu( menuHelp );

		MenuItem mntmBug = new MenuItem( menuHelp, SWT.NONE );
		mntmBug.setText( Messages.getString( "MainMenu.Bug" ) ); //$NON-NLS-1$
		mntmBug.setImage( UsefulImage.NEW_MAIL.getImage() );
		mntmBug.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				new EmailSystem().sendBugNotification();
			}
		} );
		
		MenuItem mntmSuggestion = new MenuItem( menuHelp, SWT.NONE );
		mntmSuggestion.setText( Messages.getString( "MainMenu.Suggestion" ) ); //$NON-NLS-1$
		mntmSuggestion.setImage( UsefulImage.NEW_MAIL.getImage() );
		mntmSuggestion.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				new EmailSystem().sendSuggestionNotification();
			}
		} );
		
		new MenuItem( menuHelp, SWT.SEPARATOR );
		
		MenuItem mntmAbout = new MenuItem( menuHelp, SWT.NONE );
		mntmAbout.setText( Messages.getString( "MainMenu.About" ) ); //$NON-NLS-1$
		mntmAbout.setImage( UsefulImage.ABOUT_STAR.getImage() );
		mntmAbout.setAccelerator( SWT.MOD1 + 'O' );		
		mntmAbout.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				About about = new About( getShell(), SWT.NONE );
				about.open();
			}
		} );
	}

	public void enableSurvey()
	{
		mntmSurvey.setEnabled( true );
		mntmNetwork.setEnabled( true );		
	}
	
	public abstract void closeApplication();
	public abstract void refreshDeviceTree();	
	public abstract void newSurveyCreated(Survey survey);
	public abstract Survey getSurveyToEdit();
	
	protected void checkSubclass()
	{
	}
}
