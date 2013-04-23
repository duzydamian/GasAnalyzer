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

public class About extends Dialog
{

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public About(Shell parent, int style)
	{
		super(parent, style);
		setText("O programie");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open()
	{
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
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
		shell = new Shell(getParent(), getStyle() | SWT.DIALOG_TRIM);
		shell.setSize(600, 407);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label label = new Label(composite, SWT.CENTER);
		label.setAlignment(SWT.CENTER);
		label.setImage(SWTResourceManager.getImage(About.class, "/pl/industrum/gasanalyzer/gui/SKNIndustrumLogo.png"));
		
		Group grpInformacje = new Group(shell, SWT.NONE);
		grpInformacje.setText("Informacje");
		grpInformacje.setLayout(new GridLayout(2, false));
		
		Label lblAutorzy = new Label(grpInformacje, SWT.NONE);
		lblAutorzy.setText("Autorzy:");
		
		Label lblDamianKarbowiakGrzegorz = new Label(grpInformacje, SWT.NONE);
		lblDamianKarbowiakGrzegorz.setText("Damian Karbowiak, Grzegorz Powała");
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label label_2 = new Label(composite_1, SWT.NONE);
		label_2.setAlignment(SWT.CENTER);
		label_2.setImage(SWTResourceManager.getImage(About.class, "/pl/industrum/gasanalyzer/gui/IMIUELogo.png"));
		
		Label label_3 = new Label(composite_1, SWT.CENTER);
		label_3.setImage(SWTResourceManager.getImage(About.class, "/pl/industrum/gasanalyzer/gui/ZKiWPLogo.png"));
		
		Label label_1 = new Label(composite_1, SWT.CENTER);
		label_1.setImage(SWTResourceManager.getImage(About.class, "/pl/industrum/gasanalyzer/gui/PolslLogo.png"));

	}
}
