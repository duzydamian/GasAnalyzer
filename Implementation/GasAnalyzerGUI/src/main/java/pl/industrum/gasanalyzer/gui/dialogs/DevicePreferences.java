package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Function;
import pl.industrum.gasanalyzer.types.UsefulColor;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class DevicePreferences extends Dialog
{
	protected Function result;
	protected Shell shell;
	private Button btnOk;
	private Button btnCancel;
	private Display display;
	private Label icoName;
	
	private Table table;
	private TableEditor tableEditor;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DevicePreferences( Shell parent, int style )
	{
		super( parent, style );
		setText( Messages.getString( "Preferencje urządzeń" ) );
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
		shell.setSize( 365, 187 );
		shell.setText( getText() );
		shell.setLayout( new GridLayout( 5, false ) );
		
		table = new Table( shell, SWT.FULL_SELECTION | SWT.HIDE_SELECTION );
		
		TableColumn nameColumn = new TableColumn( table, SWT.NONE );
		TableColumn addressColumn = new TableColumn( table, SWT.NONE );
		
		nameColumn.pack();
		addressColumn.pack();
		
		TableItem device = new TableItem( table, SWT.NONE );
		device.setText( new String[] {"Device 0", "12"} );
		
		tableEditor = new TableEditor( table );
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.grabHorizontal = true;
		tableEditor.minimumWidth = 50;
		
		final int EDIT_TABLE_COLUMNS = 2;
		
		GridData gd_tableEditor = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1);
		gd_tableEditor.widthHint = 229;
		table.setLayoutData(gd_tableEditor);
		
		table.addSelectionListener( new SelectionAdapter()
		{
            public void widgetSelected( SelectionEvent e )
            {
                    // Clean up any previous editor control
                    Control oldEditor = tableEditor.getEditor();
                    if ( oldEditor != null ) oldEditor.dispose();
    
                    // Identify the selected row
                    TableItem item = ( TableItem )e.item;
                    if ( item == null ) return;
    
                    // The control that will be the editor must be a child of the Table
                    Text newEditor = new Text( table, SWT.NONE );
                    newEditor.setText( item.getText( EDIT_TABLE_COLUMNS ) );
                    newEditor.addModifyListener( new ModifyListener()
                    {
                            public void modifyText( ModifyEvent e )
                            {
                                    Text text = ( Text )tableEditor.getEditor();
                                    tableEditor.getItem().setText( EDIT_TABLE_COLUMNS, text.getText() );
                            }
                    });
                    newEditor.selectAll();
                    newEditor.setFocus();
                    tableEditor.setEditor( newEditor, item, EDIT_TABLE_COLUMNS );
            }
		});
		
		
		icoName = new Label(shell, SWT.NONE);
		icoName.setImage( null );
				
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
									//saveAction();
									shell.dispose();
								}
							}
						} );
				
						btnCancel = new Button( shell, SWT.NONE );
						btnCancel.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false,
								false, 1, 1 ) );
						btnCancel
								.setText( Messages.getString( "NewSurveyUser.btnCancel.text" ) ); //$NON-NLS-1$
						btnCancel.addSelectionListener( new SelectionAdapter()
						{
							@Override
							public void widgetSelected( SelectionEvent arg0 )
							{
								shell.dispose();
							}
						} );
								new Label(shell, SWT.NONE);
								new Label(shell, SWT.NONE);
	}

//	protected void saveAction()
//	{
//		result = FunctionDictionary.get( FunctionDictionary.add( textName.getText() ) );
//	}
//	
//	private boolean validateName()
//	{
//		if ( textName.getText().isEmpty() | textName.getText() == null )
//		{
//			setFormFieldError( lblName, textName, icoName );
//			return false;
//		} else
//		{
//			setFormFieldOK( lblName, textName, icoName );
//			return true;
//		}
//	}

	private boolean validateAll()
	{
		boolean isValid = true;
		
		//isValid = validateName();
		
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
