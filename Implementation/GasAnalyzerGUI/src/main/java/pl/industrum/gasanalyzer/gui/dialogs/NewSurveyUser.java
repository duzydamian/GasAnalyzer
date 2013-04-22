package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.types.UsefulColor;

public class NewSurveyUser extends Dialog
{
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
	private Display display;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NewSurveyUser(Shell parent, int style)
	{
		super(parent, style);
		setText(Messages.getString("NewSurveyUser.this.text")); //$NON-NLS-1$
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
		display = getParent().getDisplay();
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
		shell.setSize(300, 165);
		shell.setText(getText());
		shell.setLayout(new GridLayout(3, false));
		
		lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setText(Messages.getString("NewSurveyUser.lblTitle.text"));		 //$NON-NLS-1$
		
		textTitle = new Text(shell, SWT.BORDER);
		textTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblName = new Label(shell, SWT.NONE);
		lblName.setText(Messages.getString("NewSurveyUser.lblName.text")); //$NON-NLS-1$
		
		textName = new Text(shell, SWT.BORDER);
		textName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblSurname = new Label(shell, SWT.NONE);
		lblSurname.setText(Messages.getString("NewSurveyUser.lblSurname.text")); //$NON-NLS-1$
		
		textSurname = new Text(shell, SWT.BORDER);
		textSurname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));		
		
		new Label(shell, SWT.NONE);
		
		btnOk = new Button(shell, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnOk.setText(Messages.getString("NewSurveyUser.btnOk.text")); //$NON-NLS-1$
		btnOk.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (validate())
				{
					saveAction();
					shell.dispose();
				}
			}
		});
				
		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCancel.setText(Messages.getString("NewSurveyUser.btnCancel.text")); //$NON-NLS-1$
		btnCancel.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				shell.dispose();
			}
		});
	}

	protected void saveAction()
	{
		System.out.println(textTitle.getText()+" "+textName.getText()+" "+textSurname.getText());
	}

	private boolean validate()
	{
		boolean isValid = true;
		
		if( textName.getText().isEmpty() | textName.getText() == null )
		{
			textName.setBackground(UsefulColor.RED_ERROR.getColor());
			isValid = false;
		}
		else
		{
			textName.setBackground(UsefulColor.WHITE.getColor());
		}
		
		if( textSurname.getText().isEmpty() | textSurname.getText() == null )
		{
			textSurname.setBackground(UsefulColor.RED_ERROR.getColor());
			isValid = false;
		}
		else
		{
			textSurname.setBackground(UsefulColor.WHITE.getColor());
		}
		
		return isValid;
	}
}
