package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.communication.rx.ELANDataParser;
import pl.industrum.gasanalyzer.elan.exceptions.DuplicateDeviceException;
import pl.industrum.gasanalyzer.elan.exceptions.NullDeviceException;
import pl.industrum.gasanalyzer.elan.notifications.ELANMeasurementDeviceNotification;
import pl.industrum.gasanalyzer.elan.notifications.ELANNetworkNotification;
import pl.industrum.gasanalyzer.elan.notifications.ELANRxByteBufferNotification;

public class ELANNetwork extends Observable implements Iterable<ELANMeasurementDevice>, Observer
{
	//Total amount of networks registered in application 
	//even if removed!
	private static Integer networksCounter = 0;
	
	public static void incNetworksCounter()
	{
		networksCounter++;
	}
	
	public static void resetNetworksCounter()
	{
		networksCounter = 0;
	}
	
	public static Integer getNetworksCounter()
	{
		return networksCounter;
	}
	
	//Non static part of ELANNetwork
	private Integer id;
	private String name;
	private ELANMeasurementDevice[] measurementDevices;
	private boolean[] measurementDevicesNotifications;
	
	public ELANNetwork( String name, Integer id )
	{
		this.name = name;
		this.id = id;
		initializeDevicesPool();
	}
	
	public ELANNetwork( Integer id )
	{
		this.id = id;
		initializeDevicesPool();
	}
	
	public void update( Observable obj, Object arg )
	{					
		try
		{
			//Receive information and data from ELANRxByteBuffer,
			//it means that full and correct frame got in.
			if( arg instanceof ELANRxByteBufferNotification )
			{
				Queue<Integer> dataBuffer = ( ( ELANRxByteBufferNotification ) arg ).getData();
				
				//Last time check dataBuffer non-zero size condition.
				if( dataBuffer.size() > 0 )
				{
					//Preparser, used only to get source and target address
					//address = channel_address * 16 + component_address ()
					Integer deviceAddress = ( ELANDataParser.preparser( dataBuffer )[1] )/16;
					
					//Start data parser thread to avoid frames lost.
	            	ELANDataParser dataParserThread = new ELANDataParser( dataBuffer );
	            	if( measurementDevices[deviceAddress] == null )
	            	{
	            		addDevice( deviceAddress );
	            	}
	            	dataParserThread.addObserver( measurementDevices[deviceAddress] );
	            	
	            	Thread thread = new Thread( dataParserThread );
	            	thread.start();
				}
				else if( arg instanceof ELANMeasurementDeviceNotification )
				{
					Integer deviceAddress = ( ( ELANMeasurementDeviceNotification ) arg ).getData();
					measurementDevicesNotifications[ deviceAddress ] = true;
					//Check if all notification received
					if( checkNotifications() )
					{
						setChanged();
						notifyObservers( new ELANNetworkNotification( getName() ) );
						clearNotifications();
					}
				}
			}
			else
			{
				//ERROR
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Iterator<ELANMeasurementDevice> iterator() 
	{        
		ArrayList<ELANMeasurementDevice> measurementDevicesArray = new ArrayList<ELANMeasurementDevice>();
		for( int i = 0; i < 12; i++ )
		{
			measurementDevicesArray.add( measurementDevices[i] );
		}
        Iterator<ELANMeasurementDevice> imeasurementDevices = measurementDevicesArray.iterator();
        
        return imeasurementDevices; 
    }
	
	public void initializeDevicesPool()
	{
		measurementDevices = new ELANMeasurementDevice[12];
		measurementDevicesNotifications = new boolean[12];
		
		for( int i = 0; i < 12; i++ )
		{
			measurementDevices[i] = null;
			measurementDevicesNotifications[i] = false;
		}
	}
	
	public boolean checkNotifications()
	{
		for( int i = 0; i < 12; i++ )
		{
			if( measurementDevices[i] != null && measurementDevicesNotifications[i] == false )
			{
				return false;
			}
		}
		return true;
	}
	
	public void clearNotifications()
	{
		for( int i = 0; i < 12; i++ )
		{
			measurementDevicesNotifications[i] = false;
		}
	}
	
	public void addDevice( String name, Integer deviceAddress ) throws DuplicateDeviceException
	{
		if( measurementDevices[deviceAddress] == null )
		{
			ELANMeasurementDevice device = new ELANMeasurementDevice( name, deviceAddress );
			measurementDevices[deviceAddress] = device;
			( measurementDevices[deviceAddress] ).addObserver( this );
		}
		else
		{
			throw new DuplicateDeviceException( "Trying to duplicate device" );
		}
	}
	
	public void addDevice( Integer deviceAddress ) throws DuplicateDeviceException
	{
		if( measurementDevices[deviceAddress] == null )
		{
			ELANMeasurementDevice device = new ELANMeasurementDevice( deviceAddress );
			measurementDevices[deviceAddress] = device;
		}
		else
		{
			throw new DuplicateDeviceException( "Trying to duplicate device" );
		}
	}
	
	public void removeDevice( Integer deviceAddress ) throws NullDeviceException
	{
		if( measurementDevices[deviceAddress] != null )
		{
			measurementDevices[deviceAddress] = null;
		}
		else
		{
			throw new NullDeviceException( "There is no device with ID=" + deviceAddress.toString() + " in " + getName() + " network." );
		}
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void rename( String name )
	{
		this.name = name;
	}
	
	public ELANMeasurementDevice getDevice( Integer deviceAddress ) throws NullDeviceException
	{
		ELANMeasurementDevice device = measurementDevices[deviceAddress];
		
		if( device != null )
		{
			return device;
		}
		else
		{
			throw new NullDeviceException( "There is no device with ID=" + deviceAddress.toString() + " in " + getName() + " network." );
		}
	}
}
