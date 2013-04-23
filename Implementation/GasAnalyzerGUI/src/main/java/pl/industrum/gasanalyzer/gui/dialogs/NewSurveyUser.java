package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

public class NewSurveyUser extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text textTitle;
	private Text textName;
	private Text textSurname;
	private Label lblTitle;
	private Label lblName;
	private Label lblSurname;
	private Button btnOk;
	private Button btnCancel;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NewSurveyUser(Shell parent, int style) {
		super(parent, style);
		setText("Nowy użytkownik");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle() | SWT.DIALOG_TRIM);
		shell.setSize(300, 165);
		shell.setText(getText());
		shell.setLayout(new GridLayout(3, false));
		
		lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setText("Tytuł naukowy");		
		
		textTitle = new Text(shell, SWT.BORDER);
		textTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblName = new Label(shell, SWT.NONE);
		lblName.setText("Imię");
		
		textName = new Text(shell, SWT.BORDER);
		textName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblSurname = new Label(shell, SWT.NONE);
		lblSurname.setText("Nazwisko");
		
		textSurname = new Text(shell, SWT.BORDER);
		textSurname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));		
		
		new Label(shell, SWT.NONE);
		
		btnOk = new Button(shell, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnOk.setText("OK");
				
		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCancel.setText("Anuluj");
	}

}