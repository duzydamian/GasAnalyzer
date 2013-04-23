package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.gui.SWTResourceManager;
import org.eclipse.swt.widgets.Link;
import pl.industrum.gasanalyzer.i18n.Messages;

public class About extends Dialog
{

	protected Object result;
	protected Shell shell;

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
		shell.setSize( 600, 407 );
		shell.setText( getText() );
		shell.setLayout( new FillLayout( SWT.VERTICAL ) );

		Composite composite = new Composite( shell, SWT.NONE );
		composite.setLayout( new FillLayout( SWT.VERTICAL ) );

		Label label = new Label( composite, SWT.CENTER );
		label.setAlignment( SWT.CENTER );
		label.setImage( SWTResourceManager.getImage( About.class,
				"/pl/industrum/gasanalyzer/gui/SKNIndustrumLogo.png" ) );

		Group grpInformacje = new Group( shell, SWT.NONE );
		grpInformacje.setText( Messages.getString("About.grpInformacje.text") ); //$NON-NLS-1$
		grpInformacje.setLayout( new GridLayout( 3, false ) );

		Label lblAutorzy = new Label( grpInformacje, SWT.NONE );
		lblAutorzy.setText( Messages.getString("About.lblAutorzy.text") ); //$NON-NLS-1$

		Label lblDamianKarbowiakGrzegorz = new Label( grpInformacje, SWT.NONE );
		lblDamianKarbowiakGrzegorz
				.setText( "Damian Karbowiak" );
		
		Link link = new Link(grpInformacje, SWT.NONE);
		link.setText("<a>duzydamian@gmail.com</a>");
		new Label(grpInformacje, SWT.NONE);
		
		Label lblGrzegorzPowaa = new Label(grpInformacje, SWT.NONE);
		lblGrzegorzPowaa.setText("Grzegorz Powała");
		
		Link link_1 = new Link(grpInformacje, SWT.NONE);
		link_1.setText("<a>bananowy.grzesiu@gmail.com</a>");
		
		Label lblStronaProjektu = new Label(grpInformacje, SWT.NONE);
		lblStronaProjektu.setText(Messages.getString("About.lblStronaProjektu.text")); //$NON-NLS-1$
		
		Link link_2 = new Link(grpInformacje, SWT.NONE);
		link_2.setText("<a href=\"https://github.com/duzydamian/GasAnalyzer\">GitHub</a>");
		new Label(grpInformacje, SWT.NONE);

		Composite composite_1 = new Composite( shell, SWT.NONE );
		composite_1.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		Label label_2 = new Label( composite_1, SWT.NONE );
		label_2.setAlignment( SWT.CENTER );
		label_2.setImage( SWTResourceManager.getImage( About.class,
				"/pl/industrum/gasanalyzer/gui/IMIUELogo.png" ) );

		Label label_3 = new Label( composite_1, SWT.CENTER );
		label_3.setImage( SWTResourceManager.getImage( About.class,
				"/pl/industrum/gasanalyzer/gui/ZKiWPLogo.png" ) );

		Label label_1 = new Label( composite_1, SWT.CENTER );
		label_1.setImage( SWTResourceManager.getImage( About.class,
				"/pl/industrum/gasanalyzer/gui/PolslLogo.png" ) );

	}
}
