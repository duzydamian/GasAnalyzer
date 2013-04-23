package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
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

import pl.industrum.gasanalyzer.gui.SWTResourceManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.types.UsefulColor;

public class NewSurveyUser extends Dialog
{
	protected Object result;
	protected Shell shell;
	private Text textTitle;
	private Text textName;
	private Text textSurname;
	private CLabel lblTitle;
	private CLabel lblName;
	private CLabel lblSurname;
	private Button btnOk;
	private Button btnCancel;
	private Display display;
	private CLabel lblFunction;
	private Text textFunction;

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
		shell.setSize(300, 200);
		shell.setText(getText());
		shell.setLayout(new GridLayout(3, false));
		
		lblTitle = new CLabel(shell, SWT.NONE);
		lblTitle.setText(Messages.getString("NewSurveyUser.lblTitle.text"));		 //$NON-NLS-1$
		
		textTitle = new Text(shell, SWT.BORDER);
		textTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblName = new CLabel(shell, SWT.RIGHT);
		lblName.setText(Messages.getString("NewSurveyUser.lblName.text")); //$NON-NLS-1$
		
		textName = new Text(shell, SWT.BORDER);
		textName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblSurname = new CLabel(shell, SWT.NONE);
		lblSurname.setText(Messages.getString("NewSurveyUser.lblSurname.text")); //$NON-NLS-1$
		
		textSurname = new Text(shell, SWT.BORDER);
		textSurname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));		
		
		lblFunction = new CLabel(shell, SWT.NONE);
		lblFunction.setText(Messages.getString("NewSurveyUser.lblNewLabel.text")); //$NON-NLS-1$
		
		textFunction = new Text(shell, SWT.BORDER);
		textFunction.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
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
		
		if( textTitle.getText().isEmpty() | textTitle.getText() == null )
		{
			setFormFieldWarning(lblTitle, textTitle);
		}
		else
		{
			clearFormField(lblTitle, textTitle);
		}
		
		if( textName.getText().isEmpty() | textName.getText() == null )
		{
			setFormFieldError(lblName, textName);
			isValid = false;
		}
		else
		{
			clearFormField(lblName, textName);
		}
		
		if( textSurname.getText().isEmpty() | textSurname.getText() == null )
		{
			setFormFieldError(lblSurname, textSurname);
			isValid = false;
		}
		else
		{
			clearFormField(lblSurname, textSurname);
		}
		
		if( textFunction.getText().isEmpty() | textFunction.getText() == null )
		{
			setFormFieldWarning(lblFunction, textFunction);
		}
		else
		{
			clearFormField(lblFunction, textFunction);
		}
		
		return isValid;
	}
	
	private void setFormFieldError(CLabel label, Text textField)
	{
		label.setImage(SWTResourceManager.getImage(NewSurveyUser.class, "/pl/industrum/gasanalyzer/gui/remove.png"));
		label.getParent().layout();
		textField.setBackground(UsefulColor.RED_ERROR.getColor());
	}
	
	private void setFormFieldWarning(CLabel label, Text textField)
	{
		label.setImage(SWTResourceManager.getImage(NewSurveyUser.class, "/pl/industrum/gasanalyzer/gui/warning.png"));
		label.getParent().layout();
		textField.setBackground(UsefulColor.YELLOW_WARNING.getColor());
	}
	
	private void clearFormField(CLabel label, Text textField)
	{
		label.setImage(null);
		textField.setBackground(UsefulColor.WHITE.getColor());
	}
}
