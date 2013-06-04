package pl.industrum.gasanalyzer.gui.dialogs;

import java.util.HashMap;
import java.util.Vector;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.hibernate.model.dictionaries.DeviceTypeDictionary;
import pl.industrum.gasanalyzer.hibernate.model.managers.DeviceManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Device;
import pl.industrum.gasanalyzer.model.DeviceType;
import pl.industrum.gasanalyzer.types.UsefulImage;
import pl.industrum.gasanalyzer.xml.XmlCreator;
import pl.industrum.gasanalyzer.xml.XmlParser;

public class DevicePreferences extends Dialog
{
	protected Object result;
	protected Shell shell;
	private Button btnOk;
	private Button btnCancel;
	private Display display;
	private Label icoName;
	
	private Table table;
	private TableEditor editor;

	private Vector<Device> devicesCollection;
	private HashMap<Integer, Device> devicesMap;
	
	private TableColumn addColumn;
	private TableColumn addressColumn;
	private TableColumn nameColumn;
	private TableColumn typeColumn;
	
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
		
		devicesCollection = new Vector<Device>();
		devicesMap = new HashMap<Integer, Device>();
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open()
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
		
		//TODO maybe add column with current set precisions
		addColumn = new TableColumn( table, SWT.NONE );
		addressColumn = new TableColumn( table, SWT.NONE );
		nameColumn = new TableColumn( table, SWT.NONE );
		typeColumn = new TableColumn( table, SWT.NONE );
		
		addColumn.setText( "" );
		addressColumn.setText( "adres" );
		nameColumn.setText( "nazwa" );
		typeColumn.setText( "typ" );
		
		addressColumn.pack();
		nameColumn.pack();
		typeColumn.pack();
		addColumn.pack();
		
		editor = new TableEditor( table );
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		loadDevicesFromDB();
		XmlParser xmlParser = new XmlParser( devicesCollection );
		devicesCollection = xmlParser.getDevicesFromDatabaseWithPrecision();
		addDevicesToTable();
		
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
								MeasurementPreferences preferences = new MeasurementPreferences( getParent(), SWT.NONE, devicesMap.get( index ) );
								HashMap<String, Integer> openResult = preferences.open();
								if ( openResult != null )
								{
									devicesMap.get( index ).setMeasurementPrecisionMap( openResult );
								}								
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
								for( DeviceType type: DeviceTypeDictionary.getAll() )
								{
									deviceType.add( type.getType() );
								}
								
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
	
	private void addDevicesToTable()
	{
		for( Device device: devicesCollection )
		{
			TableItem deviceItem = new TableItem( table, SWT.NONE );
			deviceItem.setImage( 0, UsefulImage.PREFERENCES.getImage() );
			deviceItem.setText( 1, Integer.toString( device.getAddress() ) );
			deviceItem.setText( 2, device.getName() );
			deviceItem.setText( 3, device.getDeviceType().getType() );
		}		
		
		nameColumn.pack();
		addressColumn.pack();
		typeColumn.pack();
		addColumn.pack();
	}
	
	private void loadDevicesFromDB()
	{
		Integer index = 0;
		for( Device device: DeviceManager.getAllDevices() )
		{
			devicesCollection.add( device );
			devicesMap.put( index, device );
			index++;
		}
	}
	
	protected void saveAction()
	{
		//TODO add update devices in DB
		XmlCreator xmlCreator = new XmlCreator( devicesCollection );		
		result = xmlCreator.getXml();	
	}
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
