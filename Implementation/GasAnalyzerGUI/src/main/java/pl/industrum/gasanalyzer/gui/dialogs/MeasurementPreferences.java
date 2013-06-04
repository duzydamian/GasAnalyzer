package pl.industrum.gasanalyzer.gui.dialogs;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.hibernate.model.dictionaries.MeasurementVariableDictionary;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Function;
import pl.industrum.gasanalyzer.model.MeasurementVariable;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class MeasurementPreferences extends Dialog
{
	protected HashMap<String, Integer> result;
	protected Shell shell;
	private Button btnOk;
	private Button btnCancel;
	private Display display;
	private Label icoName;
	
	private Table table;
	private TableEditor editor;
	
	private HashMap<String, Integer> variables;
	
	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public MeasurementPreferences( Shell parent, int style )
	{
		super( parent, style );
		setText( "Preferencje urządzeń" );
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public HashMap<String, Integer> open( HashMap<String, Integer> variables )
	{
		this.variables = new HashMap<String, Integer>();
		this.variables.putAll( variables );
		
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
		shell.setSize( 400, 300 );
		shell.setText( getText() );
		shell.setLayout( new GridLayout( 5, false ) );
		
		table = new Table( shell, SWT.FULL_SELECTION | SWT.HIDE_SELECTION | SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL );
		table.setHeaderVisible( true );
		table.setLinesVisible( true );
		
		for( MeasurementVariable variable : MeasurementVariableDictionary.getAll() )
		{
			String name = variable.getName();
			if( !name.isEmpty() && !name.equalsIgnoreCase( "no component" ) )
			{
				TableItem item = new TableItem (table, SWT.NONE);
				item.setText ( name );
			}
		}

		
		Rectangle clientArea = shell.getClientArea();
		table.setBounds ( clientArea.x, clientArea.y, 100, 100 );
		table.addListener ( SWT.Selection, new Listener ()
		{
			public void handleEvent ( Event event )
			{
				if( event.detail == SWT.CHECK )
				{
					String name = event.item.toString();
					if( variables.containsKey( name ) )
					{
						if( variables.get( name ) == 0 )
						{
							variables.remove( name );
							variables.put( name, 1 );
						}
						else
						{
							variables.remove( name );
							variables.put( name, 0 );
						}
					}
					else
					{
						variables.put( name, 1 );
					}
				}
			}
		});
		
		GridData gd_tableEditor = new GridData( SWT.FILL, SWT.FILL, true, true, 5, 1 );
		table.setLayoutData( gd_tableEditor );
		
		icoName = new Label( shell, SWT.NONE );
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

	protected void saveAction()
	{
		result = variables;
	}
	
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
}
