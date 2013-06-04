package pl.industrum.gasanalyzer.gui.dialogs;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
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

import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Function;
import pl.industrum.gasanalyzer.model.MeasurementVariable;
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
	private TableEditor editor;
	
	private HashMap<String, Integer> variables;
	private HashMap<String, Integer> tempResults;
	private HashMap<Integer, MeasurementVariable> precisionMap;
	
	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DevicePreferences( Shell parent, int style )
	{
		super( parent, style );
		setText( "Preferencje urządzeń" );
		
		variables = new HashMap<String, Integer>();
		tempResults = new HashMap<String, Integer>();
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
		shell.setSize( 500, 600 );
		shell.setText( getText() );
		shell.setLayout( new GridLayout( 5, false ) );
		
		table = new Table( shell, SWT.FULL_SELECTION | SWT.HIDE_SELECTION );
		table.setHeaderVisible( true );
		table.setLinesVisible( true );
		

		final TableColumn addColumn = new TableColumn( table, SWT.NONE );
		final TableColumn addressColumn = new TableColumn( table, SWT.NONE );
		final TableColumn nameColumn = new TableColumn( table, SWT.NONE );
		final TableColumn typeColumn = new TableColumn( table, SWT.NONE );
		final TableColumn precisionColumn = new TableColumn( table, SWT.NONE );
		
		addColumn.setText( "" );
		addressColumn.setText( "adres" );
		nameColumn.setText( "nazwa" );
		typeColumn.setText( "typ" );
		precisionColumn.setText( "dokładność" );
		
		addressColumn.pack();
		nameColumn.pack();
		typeColumn.pack();
		precisionColumn.pack();
		addColumn.pack();
		
		editor = new TableEditor( table );
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		TableItem device = new TableItem( table, SWT.NONE );
		device.setImage( 0, UsefulImage.PREFERENCES.getImage() );
		device.setText( 1, "12" );
		device.setText( 2, "Device 0" );
		device.setText( 3, "Ultramat 23" );
		device.setText( 4, "2" );
		
		
		TableItem device2 = new TableItem( table, SWT.NONE );
		device2.setImage( 0, UsefulImage.PREFERENCES.getImage() );
		device2.setText( 1, "9" );
		device2.setText( 2, "Device 1" );
		device2.setText( 3, "Ultramat 23/a" );
		
		//device2.setForeground( 4, new Color( display, new RGB( 100, 100, 100 ) ) );
		device2.setBackground( 4, new Color( display, new RGB( 100, 100, 100 ) ) );
		device2.setText( 4, "" );
		
		nameColumn.pack();
		addressColumn.pack();
		precisionColumn.pack();
		typeColumn.pack();
		addColumn.pack();
		
		GridData gd_tableEditor = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1);
		table.setLayoutData(gd_tableEditor);
		
		table.addListener( SWT.MouseDown, new Listener ()
		{

			public void handleEvent( Event event )
			{
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point( event.x, event.y );
				int index = table.getTopIndex();
				while ( index < table.getItemCount() )
				{
					boolean visible = false;
					final TableItem item = table.getItem( index );
					for ( int i=0; i<table.getColumnCount(); i++ )
					{
						Rectangle rect = item.getBounds( i );
						if ( rect.contains( pt ) )
						{
							final int column = i;						
							
							if( column == 0 )
							{
								tempResults.clear();
								MeasurementPreferences preferences = new MeasurementPreferences( getParent(), SWT.NONE );
								tempResults.putAll( preferences.open( variables ) ); 
							}
							else if( column == 2 )
							{
								final Text text = new Text( table, SWT.NONE );
								Listener textListener = new Listener()
								{
									public void handleEvent ( final Event e )
									{
										switch( e.type )
										{
											case SWT.FocusOut:
												item.setText( column, text.getText () );
												text.dispose();
												break;
											case SWT.Traverse:
												switch( e.detail )
												{
													case SWT.TRAVERSE_RETURN:
														item.setText (column, text.getText ());
													case SWT.TRAVERSE_ESCAPE:
														text.dispose ();
														e.doit = false;
												}
												break;
										}
									}
								};
								
								text.addListener ( SWT.FocusOut, textListener );
								text.addListener ( SWT.Traverse, textListener );
								editor.setEditor ( text, item, i );
								text.setText ( item.getText( i ) );
								text.selectAll();
								text.setFocus();
							}
							else if( column == 3)
							{
								final Combo deviceType = new Combo( table, SWT.NONE );
								deviceType.add( "Ultramat23" );
								deviceType.add( "Ultramat23/a" );
								
								Listener textListener = new Listener()
								{
									public void handleEvent ( final Event e )
									{
										switch( e.type )
										{
											case SWT.FocusOut:
												item.setText( column, deviceType.getText() );
												deviceType.dispose();
												break;
											case SWT.Traverse:
												switch( e.detail )
												{
													case SWT.TRAVERSE_RETURN:
														item.setText (column, deviceType.getText() );
													case SWT.TRAVERSE_ESCAPE:
														deviceType.dispose ();
														e.doit = false;
												}
												break;
										}
									}
								};
								
								deviceType.addListener ( SWT.FocusOut, textListener );
								deviceType.addListener ( SWT.Traverse, textListener );
								editor.setEditor ( deviceType, item, i );
								deviceType.setText( item.getText( i ) );
								deviceType.setFocus();
							}
							else
							{
								final Spinner precisionEditor = new Spinner( table, SWT.NONE );
								if( !item.getText( column ).contentEquals( "" ) )
								{
									if( column == 1 )
									{
										precisionEditor.setMaximum( 12 );
										precisionEditor.setMinimum( 1 );
									}
									else
									{
										precisionEditor.setMaximum( 4 );
										precisionEditor.setMinimum( 0 );
									}
									Listener textListener = new Listener()
									{
										public void handleEvent ( final Event e )
										{
											switch( e.type )
											{
												case SWT.FocusOut:
													item.setText( column, Integer.toString( precisionEditor.getSelection() ) );
													precisionEditor.dispose();
													break;
												case SWT.Traverse:
													switch( e.detail )
													{
														case SWT.TRAVERSE_RETURN:
															item.setText (column, Integer.toString( precisionEditor.getSelection() ) );
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
									editor.setEditor ( precisionEditor, item, i );
									precisionEditor.setSelection( Integer.parseInt( item.getText( i ) ) );
									precisionEditor.setFocus();
								}
							}
							return;
						}
						if (!visible && rect.intersects( clientArea ) )
						{
							visible = true;
						}
					}
					if ( !visible ) 
					{
						return;
					}
					index++;
				}
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

	private void buildDataStructure()
	{
		
	}
	
	private boolean validateAll()
	{
		boolean isValid = true;
		
		//isValid = validateName();
		
		return isValid;
	}
}
