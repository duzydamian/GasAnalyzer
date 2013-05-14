package pl.industrum.gasanalyzer.gui.frames;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
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
import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;
import pl.industrum.gasanalyzer.elan.types.ELANVariableDimensionPair;
import pl.industrum.gasanalyzer.hibernate.model.managers.DeviceManager;
import pl.industrum.gasanalyzer.hibernate.model.managers.MeasurementSetManager;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.MeasurementSet;
import pl.industrum.gasanalyzer.types.UsefulImage;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public abstract class Device extends Composite
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "HH:mm:ss dd/MM/yyyy", Locale.getDefault() );
	
	private Timer refreshTimer;
	private TimerTask refreshTimerTask;
	private boolean runningRefreshTimer;
	
	GridData tableData;
	private Group grpOneDIvice;
	private CTabFolder tabFolder;
	private Composite currentBody;
	private Label lblState;
	private String deviceName;
	private Integer deviceAddress;
	private Label lblStateMessage;
	private CTabItem tbitmHistory;
	private CTabItem tbitmCurrent;
	private Label lblLastMeasure;
	private Label lblLastMeasureTimeStamp;
	private String[] columns;
	private String[] columnsHistory;
	private Table table;
	private Table tableHistory;
	private Composite historyBody;
	private Integer deviceID ;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Device( Composite parent, int style, ELANMeasurementDevice device )
	{
		super( parent, style );
//		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
//				true, false );
//		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );		
		//this.setLayoutData( compositeData );
		this.deviceName = device.getName() ;
		this.deviceAddress = device.getDeviceAddress();
		pl.industrum.gasanalyzer.model.Device deviceByAddress = DeviceManager.getDeviceByAddress( device.getDeviceAddress() );
		this.deviceID = deviceByAddress.getId();

		grpOneDIvice = new Group( this, SWT.NONE );
		grpOneDIvice.setText( deviceName );
		grpOneDIvice.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		tabFolder = new CTabFolder( grpOneDIvice, SWT.NONE );
		tabFolder.setSimple(false);

		tbitmCurrent = new CTabItem( tabFolder, SWT.NONE );
		tbitmCurrent.setText( Messages.getString( "Device.tbtmBiecy.text" ) ); //$NON-NLS-1$

		currentBody = new Composite( tabFolder, SWT.NONE );
		tbitmCurrent.setControl( currentBody );
		currentBody.setLayout( new GridLayout( 2, false ) );

		lblState = new Label( currentBody, SWT.WRAP );
		lblState.setText( Messages.getString( "Device.lblStan.text" ) );
		lblStateMessage = new Label( currentBody, SWT.NONE );
		lblStateMessage.setText( Messages.getString( "Device.lblOk.text" ) );		
		
		lblLastMeasure = new Label( currentBody, SWT.WRAP );
		lblLastMeasure.setText( Messages.getString( "Device.lblStan.text" ) );
		lblLastMeasureTimeStamp = new Label( currentBody, SWT.NONE );
		lblLastMeasureTimeStamp.setText( "" );
		
		columns = new String[] {"Mierzone", "Wartość", "Jednostka"};
		columnsHistory = new String[] {"Data i godzina", "Pomiar"};
		
		table = new Table (currentBody, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
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
		
		for( ELANVariableDimensionPair measurement: device.getDeviceInformation() )
		{
			TableItem item = new TableItem (table, SWT.NONE);
			item.setText (0, measurement.getVariable().getPrintable() );
			item.setText (1, "0.0");
			item.setText (2, measurement.getDimension().getPrintable() );				
			for (int i=0; i<columns.length; i++)
			{
				table.getColumn (i).pack ();
				table.getColumn (i).setMoveable(true);
			}
			tabFolder.showItem( tbitmCurrent );
			tabFolder.forceFocus();
		}			
		

		tbitmHistory = new CTabItem( tabFolder, SWT.NONE );
		tbitmHistory.setImage( UsefulImage.CALENDAR.getImage() );
		tbitmHistory.setText( Messages.getString( "Device.tbtmNewItem.text" ) ); //$NON-NLS-1$

		historyBody = new Composite( tabFolder, SWT.NONE );
		tbitmHistory.setControl( historyBody );
		
		historyBody.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		tableHistory = new Table (historyBody, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tableHistory.setLinesVisible (true);
		tableHistory.setHeaderVisible (true);
		
		for (int i=0; i<columnsHistory.length; i++)
		{
			TableColumn column = new TableColumn (tableHistory, SWT.NONE);
			column.setText (columnsHistory [i]);
		}
		
		for (int i=0; i<columnsHistory.length; i++)
		{
			tableHistory.getColumn (i).pack ();
			tableHistory.getColumn (i).setMoveable(true);
		}		
		
		refreshDeviceMeasurements();
								
		runningRefreshTimer = false;
	}

	public void stopRefreshTimer()
	{
		System.out.println( "Timer stop" );
		if( runningRefreshTimer == true )
		{
			refreshTimer.cancel();
			runningRefreshTimer = false;
		}
	}
	
	private void startRefreshTimer()
	{		
		int step = getStep() * 1000;
		
		if( runningRefreshTimer == true )
		{
			System.out.println( "Timer stop" );
			refreshTimer.cancel();
			runningRefreshTimer = false;
		}
		else
		{
			System.out.println( "Timer start" );
			runningRefreshTimer = true;			
		}						
		
		System.out.println( "Create timer and task" );
		refreshTimerTask = new TimerTask()
		{
			
			@Override
			public void run()
			{
				Display.getDefault().asyncExec( new Runnable()
				{
					public void run()
					{
						refreshDeviceMeasurements();
					}
				});				
			}
		};		
		refreshTimer = new Timer();
		refreshTimer.schedule( refreshTimerTask, step, step );	
	}
	
	private void refreshDeviceMeasurements()
	{
		//TODO implement browse history
		tableHistory.removeAll();
		
		for( MeasurementSet set: MeasurementSetManager.getAllMeasurementSets( new Date(), getSurveyID(), deviceID, 10 ) )
		{
			TableItem item = new TableItem( tableHistory, SWT.NONE );
			item.setText( 0, dateFormater.format( set.getTimestamp() ) );
			item.setText( 1, set.toString() );
		}
		
		for (int i=0; i<columnsHistory.length; i++)
		{
			TableColumn column = new TableColumn (tableHistory, SWT.NONE);
			column.setText (columnsHistory [i]);
		}
		
		for (int i=0; i<columnsHistory.length; i++)
		{
			tableHistory.getColumn (i).pack ();
			tableHistory.getColumn (i).setMoveable(true);
		}
	}

	@Override
	public void setEnabled( boolean arg0 )
	{
		super.setEnabled( arg0 );
		grpOneDIvice.setEnabled( arg0 );
		tabFolder.setEnabled( arg0 );
	}	
	
	public void updateMeasurment(final ELANRxBroadcastFrame frame)
	{
		Display.getDefault().asyncExec( new Runnable()
		{
			@SuppressWarnings( "deprecation" )
			public void run()
			{
				lblLastMeasureTimeStamp.setText( frame.getTimeStamp().toGMTString() );
				int i = 0;
				for( ELANMeasurement elanMeasurement: frame )
				{
					table.getItem( i ).setText( 1, elanMeasurement.getValue().toString() );
					i++;
				}
			}
		});		
	}
	
	/**
	 * @return the deviceName
	 */
	public String getDeviceName()
	{
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName( String deviceName )
	{
		this.deviceName = deviceName;
	}

	public Integer getDeviceAddress()
	{
		return deviceAddress;
	}

	public void showCurrent()
	{
		tabFolder.showItem( tbitmCurrent );
		tabFolder.forceFocus();
	}
	
	@Override
	public void setVisible( boolean arg0 )
	{
		if( arg0)
		{
			startRefreshTimer();
		}
		else
		{
			stopRefreshTimer();
		}
		
		super.setVisible( arg0 );
	}
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	public abstract Integer getSurveyID();
	public abstract Integer getStep();
}
