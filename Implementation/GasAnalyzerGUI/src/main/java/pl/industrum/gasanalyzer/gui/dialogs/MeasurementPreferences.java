package pl.industrum.gasanalyzer.gui.dialogs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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

import pl.industrum.gasanalyzer.hibernate.model.dictionaries.MeasurementVariableDictionary;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Device;
import pl.industrum.gasanalyzer.model.MeasurementVariable;

public class MeasurementPreferences extends Dialog
{
	protected Device result;
	protected Shell shell;
	private Button btnOk;
	private Button btnCancel;
	private Display display;
	private Label icoName;

	private Table table;
	private TableEditor editor;

	private Device device;
	private TableColumn nameColumn;
	private TableColumn precisionColumn;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public MeasurementPreferences( Shell parent, int style, Device device )
	{
		super( parent, style );
		setText( "Preferencje urządzeń" );
		this.device = device;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Device open()
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
		shell.setSize( 400, 300 );
		shell.setText( getText() );
		shell.setLayout( new GridLayout( 5, false ) );

		table = new Table( shell, SWT.FULL_SELECTION | SWT.HIDE_SELECTION
				| SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL );
		table.setHeaderVisible( true );
		table.setLinesVisible( true );
		
		nameColumn = new TableColumn( table, SWT.NONE );
		precisionColumn = new TableColumn( table, SWT.NONE );
		
		nameColumn.setText( "nazwa" );
		precisionColumn.setText( "precyzja" );

		for( MeasurementVariable variable: MeasurementVariableDictionary
				.getAll() )
		{
			String name = variable.getName();
			if ( !name.isEmpty() && !name.equalsIgnoreCase( "no component" ) )
			{
				TableItem item = new TableItem( table, SWT.NONE );
				item.setText( 0, name );
				if( device.getMeasurementPrecisionMap().containsKey( name ) )
				{
					item.setText( 1, device.getMeasurementPrecisionMap().get( name ).toString() );
				}
				else
				{
					item.setBackground( 1, new Color( display, new RGB( 100, 100, 100 ) ) );
					item.setText( 1, "" );
				}
			}
		}

		Rectangle clientArea = shell.getClientArea();
		table.setBounds( clientArea.x, clientArea.y, 100, 100 );
		table.addListener( SWT.Selection, new Listener()
		{
			public void handleEvent( Event event )
			{
				if ( event.detail == SWT.CHECK )
				{
					String name = event.item.toString();
					if ( device.getMeasurementPrecisionMap().containsKey( name ) )
					{
						if ( device.getMeasurementPrecisionMap().get( name ) == 0 )
						{
							device.getMeasurementPrecisionMap().remove( name );
							device.getMeasurementPrecisionMap().put( name, 1 );
						} 
						else
						{
							device.getMeasurementPrecisionMap().remove( name );
							device.getMeasurementPrecisionMap().put( name, 0 );
						}
					} 
					else
					{
						device.getMeasurementPrecisionMap().put( name, 1 );
					}
				}
				else
				{
					final TableItem item = ( TableItem )event.item;
					
					final Spinner precisionEditor = new Spinner( table, SWT.NONE );
					precisionEditor.setMaximum( 4 );
					precisionEditor.setMinimum( 0 );
					
					Listener textListener = new Listener()
					{
						public void handleEvent ( final Event e )
						{
							switch( e.type )
							{
								case SWT.FocusOut:
									item.setText( 1, Integer.toString( precisionEditor.getSelection() ) );
									precisionEditor.dispose();
									break;
								case SWT.Traverse:
									switch( e.detail )
									{
										case SWT.TRAVERSE_RETURN:
											item.setText (1, Integer.toString( precisionEditor.getSelection() ) );
										case SWT.TRAVERSE_ESCAPE:
											precisionEditor.dispose ();
											e.doit = false;
									}
									break;
							}
						}
					};
					
					precisionEditor.addListener ( SWT.FocusOut, textListener );
					precisionEditor.addListener ( SWT.Traverse, textListener );
					editor.setEditor ( precisionEditor, item, 1 );
					precisionEditor.setSelection( Integer.parseInt( item.getText( 1 ) ) );
					precisionEditor.setFocus();
				}
			}
		} );

		GridData gd_tableEditor = new GridData( SWT.FILL, SWT.FILL, true, true,
				5, 1 );
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
		new Label( shell, SWT.NONE );
		new Label( shell, SWT.NONE );
	}

	protected void saveAction()
	{
		result = device;
	}

	// private boolean validateName()
	// {
	// if ( textName.getText().isEmpty() | textName.getText() == null )
	// {
	// setFormFieldError( lblName, textName, icoName );
	// return false;
	// } else
	// {
	// setFormFieldOK( lblName, textName, icoName );
	// return true;
	// }
	// }

	private boolean validateAll()
	{
		boolean isValid = true;

		// isValid = validateName();

		return isValid;
	}
}
