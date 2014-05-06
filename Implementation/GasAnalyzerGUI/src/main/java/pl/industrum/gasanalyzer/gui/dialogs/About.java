package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.gui.EmailSystem;
import pl.industrum.gasanalyzer.gui.GitRepositoryState;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class About extends Dialog
{

	protected Object result;
	protected Shell shell;
	private Composite composite;
	private Label industrumLogo;
	private Group grpInformation;
	private Label lblAuthors;
	private Label lblDamianKarbowiak;
	private Label lblGrzegorzPowala;
	private Link linkDamianKarbowiak;
	private Link linkGrzegorzPowala;
	private Label lblProjectWebsite;
	private Link linkProjectWebsite;
	private Label lblBranch;
	private Label lblBranchName;
	private Label lblVersion;
	private Label lblVersionValue;
	private Label lblCommitUser;
	private Label lblCommitUserName;
	private Label lblCommitTime;
	private Label lblCommitTimeValue;
	private Label lblCommitId;
	private Label lblCommitIdValue;
	private Label lblBuildUser;
	private Label lblBuildUserName;
	private Label lblBuildTime;
	private Label lblBuildTimeValue;
	private Composite compositeLogos;
	private Label imiueLogo;
	private Label zkiwpLogo;
	private Label polslLogo;
	private GitRepositoryState repositoryState;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public About( Shell parent, int style )
	{
		super( parent, style );
		setText( Messages.getString("About.this.text") ); //$NON-NLS-1$
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
		shell.setSize( 600, 650 );
		shell.setText( getText() );
		shell.setImage( UsefulImage.ABOUT_STAR.getImage() );
		
		shell.setLayout( new FillLayout( SWT.VERTICAL ) );

		composite = new Composite( shell, SWT.NONE );
		composite.setLayout( new FillLayout( SWT.VERTICAL ) );

		industrumLogo = new Label( composite, SWT.CENTER );
		industrumLogo.setAlignment( SWT.CENTER );
		industrumLogo.setImage( UsefulImage.INDUSTRUM_LOGO.getImage() );

		grpInformation = new Group( shell, SWT.NONE );
		grpInformation.setText( Messages.getString("About.grpInformacje.text") ); //$NON-NLS-1$
		grpInformation.setLayout( new GridLayout( 3, false ) );

		lblAuthors = new Label( grpInformation, SWT.NONE );
		lblAuthors.setText( Messages.getString("About.lblAutorzy.text") ); //$NON-NLS-1$

		lblDamianKarbowiak = new Label( grpInformation, SWT.NONE );
		lblDamianKarbowiak.setText( "Damian Karbowiak" );
		
		linkDamianKarbowiak = new Link(grpInformation, SWT.NONE);
		linkDamianKarbowiak.setText("<a>duzydamian@gmail.com</a>");
		linkDamianKarbowiak.addSelectionListener(new SelectionAdapter()
		{
	        @Override
	        public void widgetSelected(SelectionEvent e)
	        {
	        	//System.out.println("You have selected: "+e.text);
	        	new EmailSystem().openMailClient( e.text, "[GasAnalyzer]", "Write body here" );
	        }
	    });
		
		new Label(grpInformation, SWT.NONE);
		
		lblGrzegorzPowala = new Label(grpInformation, SWT.NONE);
		lblGrzegorzPowala.setText("Grzegorz Powała");
		
		linkGrzegorzPowala = new Link(grpInformation, SWT.NONE);
		linkGrzegorzPowala.setText("<a>bananowy.grzesiu@gmail.com</a>");
		linkGrzegorzPowala.addSelectionListener(new SelectionAdapter()
		{
	        @Override
	        public void widgetSelected(SelectionEvent e)
	        {
	        	//System.out.println("You have selected: "+e.text);
	            new EmailSystem().openMailClient( e.text, "[GasAnalyzer]", "Write body here" );
	        }
	    });
		
		//new Label(grpInformation, SWT.HORIZONTAL | SWT.SEPARATOR );
		//new Label(grpInformation, SWT.HORIZONTAL | SWT.SEPARATOR );
		//new Label(grpInformation, SWT.HORIZONTAL | SWT.SEPARATOR );
		
		repositoryState = new GitRepositoryState();		
		
		lblProjectWebsite = new Label(grpInformation, SWT.NONE);
		lblProjectWebsite.setText(Messages.getString("About.lblStronaProjektu.text")); //$NON-NLS-1$
		
		linkProjectWebsite = new Link(grpInformation, SWT.NONE);
		linkProjectWebsite.setText("<a href=\"https://github.com/duzydamian/GasAnalyzer/\">GitHub</a>");
		linkProjectWebsite.addSelectionListener(new SelectionAdapter()			
		{
	        @Override
	        public void widgetSelected(SelectionEvent e)
	        {
	               //System.out.println("You have selected: "+e.text);
	               Program.launch( e.text );
	        }
	    });
		new Label(grpInformation, SWT.NONE);		
		
		lblBranch = new Label( grpInformation, SWT.NONE );
		lblBranch.setText( "Gałąź" );
		lblBranchName = new Label( grpInformation, SWT.NONE );
		lblBranchName.setText( repositoryState.getBranch() );
		new Label(grpInformation, SWT.NONE);

		lblVersion = new Label( grpInformation, SWT.NONE );
		lblVersion.setText( "Wersja" );
		lblVersionValue = new Label( grpInformation, SWT.NONE );
		lblVersionValue.setText( repositoryState.getDescribe() );
		new Label(grpInformation, SWT.NONE);
		
		lblCommitUser = new Label( grpInformation, SWT.NONE );
		lblCommitUser.setText( "Autor ostatniej zmiany:" );
		lblCommitUserName = new Label( grpInformation, SWT.NONE );
		lblCommitUserName.setText( repositoryState.getCommitUserName() );
		new Label(grpInformation, SWT.NONE);
		
		lblCommitTime = new Label( grpInformation, SWT.NONE );
		lblCommitTime.setText( "Data ostatniej zmiany:" );
		lblCommitTimeValue = new Label( grpInformation, SWT.NONE );
		lblCommitTimeValue.setText( repositoryState.getCommitTime() );
		new Label(grpInformation, SWT.NONE);		
		
		lblBuildUser = new Label( grpInformation, SWT.NONE );
		lblBuildUser.setText( "Autor kompilacji:" );
		lblBuildUserName = new Label( grpInformation, SWT.NONE );
		lblBuildUserName.setText( repositoryState.getBuildUserName() );
		new Label(grpInformation, SWT.NONE);
		
		lblBuildTime = new Label( grpInformation, SWT.NONE );
		lblBuildTime.setText( "Data kompilacji:" );
		lblBuildTimeValue = new Label( grpInformation, SWT.NONE );
		lblBuildTimeValue.setText( repositoryState.getBuildTime() );
		new Label(grpInformation, SWT.NONE);
		
		lblCommitId= new Label( grpInformation, SWT.NONE );
		lblCommitId.setText( "Commit ID" );
		lblCommitIdValue = new Label( grpInformation, SWT.NONE );
		lblCommitIdValue.setText( repositoryState.getCommitId() );
		new Label(grpInformation, SWT.NONE);
		
		compositeLogos = new Composite( shell, SWT.NONE );
		compositeLogos.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		polslLogo = new Label( compositeLogos, SWT.CENTER );
		polslLogo.setImage( UsefulImage.POLSL_LOGO.getImage() );
		
		imiueLogo = new Label( compositeLogos, SWT.CENTER );
		imiueLogo.setImage( UsefulImage.IMIUE_LOGO.getImage() );

		zkiwpLogo = new Label( compositeLogos, SWT.CENTER );
		zkiwpLogo.setImage( UsefulImage.ZKIWP_LOGO.getImage() );		
	}
}
