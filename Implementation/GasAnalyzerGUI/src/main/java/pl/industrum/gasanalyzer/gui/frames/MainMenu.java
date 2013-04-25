package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import pl.industrum.gasanalyzer.gui.SWTResourceManager;
import pl.industrum.gasanalyzer.gui.dialogs.About;
import pl.industrum.gasanalyzer.gui.dialogs.NewSurvey;
import pl.industrum.gasanalyzer.i18n.Messages;

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
		mntmNewSurvey.setImage( SWTResourceManager.getImage( MainMenu.class,
				"/pl/industrum/gasanalyzer/gui/add.png" ) );
		mntmNewSurvey.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				NewSurvey newSurvey = new NewSurvey( getShell(), SWT.NONE );
				newSurvey.open();
				newSurveyCreated();
				enableSurvey();
			}
		} );
		
		MenuItem mntmExit = new MenuItem( menuFile, SWT.NONE );
		mntmExit.setText( Messages.getString( "MainMenu.Exit" ) ); //$NON-NLS-1$
		mntmExit.setImage( SWTResourceManager.getImage( MainMenu.class,
				"/pl/industrum/gasanalyzer/gui/shutdown.png" ) );
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
		
		mntmSurvey = new MenuItem( this, SWT.CASCADE );
		mntmSurvey.setText( Messages.getString( "MainMenu.Survey" ) ); //$NON-NLS-1$
		mntmSurvey.setEnabled( false );
		
		Menu menuSurvey = new Menu( mntmSurvey );
		mntmSurvey.setMenu( menuSurvey );

		MenuItem mntmPreferences = new MenuItem( menuSurvey, SWT.NONE );
		mntmPreferences.setImage( SWTResourceManager.getImage( MainMenu.class,
				"/pl/industrum/gasanalyzer/gui/ustawienia.png" ) );
		mntmPreferences.setText( Messages.getString( "MainMenu.Preferences" ) ); //$NON-NLS-1$
		mntmPreferences.setAccelerator( SWT.MOD1 + 'P' );

		mntmNetwork = new MenuItem( this, SWT.CASCADE );
		mntmNetwork.setText( Messages.getString( "MainMenu.Network" ) ); //$NON-NLS-1$
		mntmNetwork.setEnabled( false );

		Menu menuNetwork = new Menu( mntmNetwork );
		mntmNetwork.setMenu( menuNetwork );

		MenuItem mntmConnect = new MenuItem( menuNetwork, SWT.NONE );
		mntmConnect.setText( Messages.getString( "MainMenu.Connect" ) ); //$NON-NLS-1$
		
		MenuItem mntmDisconnect = new MenuItem( menuNetwork, SWT.NONE );
		mntmDisconnect.setText( Messages.getString( "MainMenu.Disconnect" ) ); //$NON-NLS-1$
		
		MenuItem mntmRefresh = new MenuItem( menuNetwork, SWT.NONE );
		mntmRefresh.setText( Messages.getString( "MainMenu.Refresh" ) ); //$NON-NLS-1$
		mntmRefresh.setImage( SWTResourceManager.getImage( MainMenu.class,
				"/pl/industrum/gasanalyzer/gui/odswiez.png" ) );
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

		MenuItem mntmAbout = new MenuItem( menuHelp, SWT.NONE );
		mntmAbout.setText( Messages.getString( "MainMenu.About" ) ); //$NON-NLS-1$
		mntmAbout.setImage( SWTResourceManager.getImage( MainMenu.class,
				"/pl/industrum/gasanalyzer/gui/aboutmini.png" ) );
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
	
	public abstract void newSurveyCreated();
	
	protected void checkSubclass()
	{
	}
}
