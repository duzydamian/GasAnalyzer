package pl.industrum.gasanalyzer.gui.frames;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import pl.industrum.gasanalyzer.elan.communication.network.ELANMeasurementDevice;
import pl.industrum.gasanalyzer.elan.communication.network.ELANNetwork;
import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxInvalidFrame;
import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class Network extends Composite
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "HH:mm:ss dd/MM/yyyy", Locale.getDefault() );
	
	GridData compositeData;
	private Group grpOneNetwork;
	private Composite body;
	private Label lblState;
	private String networkName;
	private Label lblStateMessage;
	private Label lblDevicesCount;
	private Label lblDevicesCountValue;
	private String[] columns;
	private Table table;
	private GridData tableData;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Network( Composite parent, int style, String name )
	{//FIXME add all device current measurement to this frame
		super( parent, style );
//		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
//				true, false );
//		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );		
		//this.setLayoutData( compositeData );		

		grpOneNetwork = new Group( this, SWT.NONE );
		grpOneNetwork.setText( name );
		grpOneNetwork.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		body = new Composite( grpOneNetwork, SWT.NONE );
		body.setLayout( new GridLayout( 2, false ) );		

		lblState = new Label( body, SWT.NONE );
		lblState.setText( "Stan sieci" );
		lblStateMessage = new Label( body, SWT.NONE );
		lblStateMessage.setText( "Niepołączona" );	
		
		lblDevicesCount = new Label( body, SWT.WRAP );
		GridData gd_lblDevicesCount = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblDevicesCount.widthHint = 100;
		lblDevicesCount.setLayoutData(gd_lblDevicesCount);
		lblDevicesCount.setText( "Liczba urządzeń w sieci" );
		lblDevicesCountValue = new Label( body, SWT.NONE );
		lblDevicesCountValue.setText( "-" );	
		
		columns = new String[] {"Urządzenie", "Timestamp", "Pomiar"};
		
		table = new Table (body, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		tableData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
				true, false );
		tableData.horizontalSpan = 2;
		table.setLayoutData( tableData );
		
		for (int i=0; i<columns.length; i++)
		{
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (columns [i]);
		}
		
		for (int i=0; i<columns.length; i++)
		{
			table.getColumn (i).pack ();
			table.getColumn (i).setMoveable(true);
		}
		
		body.layout();
		layout();
		this.setNetworkName( name );
	}

	@Override
	public void setEnabled( boolean arg0 )
	{
		super.setEnabled( arg0 );
		grpOneNetwork.setEnabled( arg0 );
		body.layout();
		layout();
	}

	/**
	 * @return the deviceName
	 */
	public String getNetworkName()
	{
		return networkName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setNetworkName( String name )
	{
		this.networkName = name;
		this.grpOneNetwork.setText( name );
		body.layout();
		layout();
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public void setNetworkConnected( int networkSize )
	{
		lblStateMessage.setText( "Podłączona" );
		lblDevicesCountValue.setText( String.valueOf( networkSize ) );
		body.layout();
		layout();
	}
	
	public void fillDeviceTableWithMeasure( ELANNetwork elanNetwork )
	{
		//bodyForMeasure.changed( devices );
		for( ELANMeasurementDevice device: elanNetwork )
		{
			TableItem item = new TableItem (table, SWT.NONE);
			item.setText (0, device.getName() );
			item.setText (1, "");
			item.setText (2, "" );				
			for (int i=0; i<columns.length; i++)
			{
				table.getColumn (i).pack ();
				table.getColumn (i).setMoveable(true);
			}
		}
		
		body.layout();
	}

	public void updateState( ELANRxInvalidFrame frame, String name )
	{
		for( TableItem item: table.getItems() )
		{
			item.setText( 2, "błað" );					
		}
	}

	public void updateMeasurment( final ELANRxBroadcastFrame frame, final String name )
	{
		Display.getDefault().asyncExec( new Runnable()
		{
			public void run()
			{
				for( TableItem item: table.getItems() )
				{
					if ( item.getText( 0 ).equalsIgnoreCase( name ) )
					{
						item.setText( 1, dateFormater.format( frame.getTimeStamp() ) );						
						
						String printableSet = "";
						for( ELANMeasurement measurement: frame )
						{
							printableSet += measurement.getMeasuredVariable().getPrintable() + ": ";
							printableSet += measurement.getValue() + " ";
							printableSet += "[" + measurement.getDimension().getPrintable() + "] | ";
						}	
						
						item.setText( 2, printableSet );					
					}		
				}
				
				for (int i=0; i<columns.length; i++)
				{
					table.getColumn (i).pack ();
					table.getColumn (i).setMoveable(true);
				}
			}
		});			
	}
}
