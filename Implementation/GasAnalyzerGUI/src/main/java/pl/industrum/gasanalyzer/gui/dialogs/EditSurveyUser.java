package pl.industrum.gasanalyzer.gui.dialogs;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.hibernate.model.dictionaries.DegreeDictionary;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.FunctionDictionary;
import pl.industrum.gasanalyzer.hibernate.model.managers.ApplicationUserManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.ApplicationUser;
import pl.industrum.gasanalyzer.model.Degree;
import pl.industrum.gasanalyzer.model.Function;
import pl.industrum.gasanalyzer.types.UsefulColor;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class EditSurveyUser extends Dialog
{
	protected ApplicationUser result;
	protected Shell shell;
	private Combo textTitle;
	private Text textName;
	private Text textSurname;
	private Label lblTitle;
	private Label lblName;
	private Label lblSurname;
	private Button btnOk;
	private Button btnCancel;
	private Display display;
	private Label lblFunction;
	private Combo textFunction;
	private Label icoTitle;
	private Label icoName;
	private Label icoSurname;
	private Label icoFunction;
	private Button btnNewTitle;
	private Button btnNewFunction;
	
	private Vector<Degree> avaibleDegrees;
	private Vector<Function> avaibleFunctions;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public EditSurveyUser( Shell parent, int style )
	{
		super( parent, style );
		setText( Messages.getString( "NewSurveyUser.this.text" ) ); //$NON-NLS-1$
		avaibleDegrees = new Vector<Degree>();
		avaibleFunctions = new Vector<Function>();
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public ApplicationUser open()
	{
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
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
		shell.setSize( 339, 200 );
		shell.setText( getText() );
		shell.setLayout( new GridLayout( 4, false ) );

		lblTitle = new Label( shell, SWT.NONE );
		lblTitle.setText( Messages.getString( "NewSurveyUser.lblTitle.text" ) ); //$NON-NLS-1$

		textTitle = new Combo( shell, SWT.BORDER );
		textTitle.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 1, 1 ) );
		refreshListTitle();
		textTitle.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateTitle();
			}
		} );
		
		btnNewTitle = new Button( shell, SWT.NONE );
		btnNewTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewTitle.setImage( UsefulImage.ADD.getImage() );
		btnNewTitle.setText( Messages
				.getString( "SurveyFrame.btnNewSurveyPlace.text" ) ); //$NON-NLS-1$
		btnNewTitle.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				NewSurveyUserTitle newSurveyUserTitle = new NewSurveyUserTitle( getParent()
						.getShell(), SWT.NONE );
				newSurveyUserTitle.open();

				refreshListTitle();
			}
		} );
		
		icoTitle = new Label(shell, SWT.NONE);

		lblName = new Label( shell, SWT.RIGHT );
		lblName.setText( Messages.getString( "NewSurveyUser.lblName.text" ) ); //$NON-NLS-1$

		textName = new Text( shell, SWT.BORDER );
		textName.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 2, 1 ) );
		textName.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateName();
			}
		} );
		
		icoName = new Label(shell, SWT.NONE);
		icoName.setImage( null );

		lblSurname = new Label( shell, SWT.NONE );
		lblSurname.setText( Messages
				.getString( "NewSurveyUser.lblSurname.text" ) ); //$NON-NLS-1$

		textSurname = new Text( shell, SWT.BORDER );
		textSurname.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 2, 1 ) );
		textSurname.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateSurname();
			}
		} );
		
		icoSurname = new Label(shell, SWT.NONE);

		lblFunction = new Label( shell, SWT.NONE );
		lblFunction.setText( Messages
				.getString( "NewSurveyUser.lblNewLabel.text" ) ); //$NON-NLS-1$

		textFunction = new Combo( shell, SWT.BORDER );
		textFunction.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 1, 1 ) );
		refreshListFunction();
		textFunction.addModifyListener( new ModifyListener()
		{
			
			public void modifyText( ModifyEvent arg0 )
			{
				validateFunction();
			}
		} );
		
		btnNewFunction = new Button( shell, SWT.NONE );
		btnNewFunction.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewFunction.setImage( UsefulImage.ADD.getImage() );
		btnNewFunction.setText( Messages
				.getString( "SurveyFrame.btnNewSurveyPlace.text" ) ); //$NON-NLS-1$
		btnNewFunction.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				NewSurveyUserFunction newSurveyUserFunction = new NewSurveyUserFunction( getParent()
						.getShell(), SWT.NONE );
				newSurveyUserFunction.open();

				refreshListFunction();
			}
		} );
		
		icoFunction = new Label(shell, SWT.NONE);

		new Label( shell, SWT.NONE );

		btnOk = new Button( shell, SWT.NONE );
		btnOk.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false,
				1, 1 ) );
		btnOk.setText( Messages.getString( "NewSurveyUser.btnOk.text" ) ); //$NON-NLS-1$
		btnOk.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				if ( validateAll() )
				{
					saveAction();
					shell.dispose();
				}
			}
		} );

		btnCancel = new Button( shell, SWT.NONE );
		btnCancel.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
				false, 1, 1 ) );
		btnCancel
				.setText( Messages.getString( "NewSurveyUser.btnCancel.text" ) ); //$NON-NLS-1$
		new Label(shell, SWT.NONE);
		btnCancel.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				shell.dispose();
			}
		} );
	}

	protected void refreshListFunction()
	{
		textFunction.removeAll();
		avaibleFunctions.clear();
		
		for( Function function: FunctionDictionary.getAll() )
		{
			textFunction.add( function.getName() );
			avaibleFunctions.add( function );
		}
	}

	protected void refreshListTitle()
	{
		textTitle.removeAll();
		avaibleDegrees.clear();
		
		for( Degree degree: DegreeDictionary.getAll() )
		{
			textTitle.add( degree.getName() );
			avaibleDegrees.add( degree );
		}
	}

	protected void saveAction()
	{
		Integer functionID = avaibleFunctions.get( textFunction.getSelectionIndex() ).getId();
		Integer degreeID = avaibleDegrees.get( textTitle.getSelectionIndex() ).getId();
		result = ApplicationUserManager.getApplicationUser( ApplicationUserManager.addApplicationUser( functionID, degreeID, textName.getText(), textSurname.getText() ) );
	}

	private void validateTitle()
	{
		if ( textTitle.getText().isEmpty() | textTitle.getText() == null )
		{
			setFormFieldWarning( lblTitle, textTitle, icoTitle );
		} else
		{
			setFormFieldOK( lblTitle, textTitle, icoTitle );
		}
	}
	
	private boolean validateName()
	{
		if ( textName.getText().isEmpty() | textName.getText() == null )
		{
			setFormFieldError( lblName, textName, icoName );
			return false;
		} else
		{
			setFormFieldOK( lblName, textName, icoName );
			return true;
		}
	}
	
	private boolean validateSurname()
	{
		if ( textSurname.getText().isEmpty() | textSurname.getText() == null )
		{
			setFormFieldError( lblSurname, textSurname, icoSurname );
			return false;
		} else
		{
			setFormFieldOK( lblSurname, textSurname, icoSurname );
			return true;
		}
	}
	
	private void validateFunction()
	{
		if ( textFunction.getText().isEmpty() | textFunction.getText() == null )
		{
			setFormFieldWarning( lblFunction, textFunction, icoFunction );
		} else
		{
			setFormFieldOK( lblFunction, textFunction, icoFunction );
		}
	}
	private boolean validateAll()
	{
		boolean isValid = true;

		validateTitle();
		
		isValid = validateName();

		isValid = validateSurname();

		validateFunction();
		
		return isValid;
	}

	private void setFormFieldError( Label label, Control textField, Label ico )
	{
		ico.setImage( UsefulImage.ERROR.getImage() );
		ico.getParent().layout();
		textField.setBackground( UsefulColor.RED_ERROR.getColor() );
	}

	private void setFormFieldWarning( Label label, Control textField, Label ico )
	{
		ico.setImage( UsefulImage.WARNING.getImage() );
		ico.getParent().layout();
		textField.setBackground( UsefulColor.YELLOW_WARNING.getColor() );
	}
	
	private void setFormFieldOK( Label label, Control textField, Label ico )
	{
		ico.setImage( UsefulImage.OK.getImage() );
		ico.getParent().layout();
		textField.setBackground( UsefulColor.WHITE.getColor() );
	}
}
