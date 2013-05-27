package pl.industrum.gasanalyzer.gui.dialogs;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
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

import pl.industrum.gasanalyzer.hibernate.model.dictionaries.FunctionDictionary;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Function;
import pl.industrum.gasanalyzer.types.UsefulColor;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class EditSurveyUserFunction extends Dialog
{
	protected Function result;
	protected Shell shell;
	private Text textName;	
	private Label lblName;
	private Button btnOk;
	private Button btnCancel;
	private Display display;
	private Label icoName;
	private Combo comboAllSurveyUserFunction;
	protected Vector<Function> avaibleSurveyUserFunctions;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public EditSurveyUserFunction( Shell parent, int style )
	{
		super( parent, style );
		setText( Messages.getString("EditSurveyUserFunction.this.text") ); //$NON-NLS-1$ //$NON-NLS-1$
		
		avaibleSurveyUserFunctions = new Vector<Function>();
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Function open()
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
		shell.setSize( 250, 135 );
		shell.setText( getText() );
		shell.setLayout( new GridLayout( 4, false ) );

		comboAllSurveyUserFunction = new Combo( shell, SWT.BORDER );
		comboAllSurveyUserFunction.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true,
				false, 4, 1 ) );
		comboAllSurveyUserFunction.addModifyListener( new ModifyListener()
		{			
			public void modifyText( ModifyEvent arg0 )
			{
				loadSurveyUserFunctionData( avaibleSurveyUserFunctions.get( comboAllSurveyUserFunction.getSelectionIndex() ) );
			}
		} );
		comboAllSurveyUserFunction.addTraverseListener( new TraverseListener()
		{			
			public void keyTraversed( TraverseEvent arg0 )
			{
				if ( arg0.detail == SWT.TRAVERSE_RETURN )
				{
					saveAction();
					shell.dispose();
				}				
			}
		} );
		loadSurveyUserFunctions();
		
		lblName = new Label( shell, SWT.RIGHT );
		lblName.setText( Messages.getString( "NewSurveyUserFunction.lblName.text" ) ); //$NON-NLS-1$

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

	private void loadSurveyUserFunctions()
	{
		for( Function function: FunctionDictionary.getAll() )
		{
			comboAllSurveyUserFunction.add( function.getName() );
			avaibleSurveyUserFunctions.add( function );
		}
	}
	
	private void loadSurveyUserFunctionData( Function function )
	{
		if ( function.getName() != null )
		{
			textName.setText( function.getName() );
		}				
	}
	
	protected void saveAction()
	{
		//TODO implement update in database		
		//result = FunctionDictionary.get( FunctionDictionary.add( textName.getText() ) );
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

	private boolean validateAll()
	{
		boolean isValid = true;
		
		isValid = validateName();
		
		return isValid;
	}

	private void setFormFieldError( Label label, Control textField, Label ico )
	{
		ico.setImage( UsefulImage.ERROR.getImage() );
		ico.getParent().layout();
		textField.setBackground( UsefulColor.RED_ERROR.getColor() );
	}
	
	private void setFormFieldOK( Label label, Control textField, Label ico )
	{
		ico.setImage( UsefulImage.OK.getImage() );
		ico.getParent().layout();
		textField.setBackground( UsefulColor.WHITE.getColor() );
	}
}
