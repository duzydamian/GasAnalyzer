package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.elan.communication.rx.ELANDataParser;
import pl.industrum.gasanalyzer.elan.communication.rx.ELANDeviceInformationParser;
import pl.industrum.gasanalyzer.elan.exceptions.DuplicateDeviceException;
import pl.industrum.gasanalyzer.elan.exceptions.NullDeviceException;
import pl.industrum.gasanalyzer.elan.notifications.ELANMeasurementDeviceNotification;
import pl.industrum.gasanalyzer.elan.notifications.ELANNetworkNotification;
import pl.industrum.gasanalyzer.elan.notifications.ELANRxByteBufferNotification;
import pl.industrum.gasanalyzer.elan.types.ELANVariableDimensionPair;

public class ELANNetwork extends Observable implements Iterable<ELANMeasurementDevice>, Observer
{
	//Total amount of networks ever registered in application
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
	private String port;
	private String name;
	private ELANMeasurementDevice[] measurementDevices;
	private boolean[] measurementDevicesNotifications;
	private ELANConnection connection;
	
	public ELANNetwork( Integer id, String port )
	{
		this.id = id;
		this.name = "";
		this.port = port ;
		this.connection = new ELANConnection();
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
					//Parsing information helper
					ELANDeviceInformationParser parser = new ELANDeviceInformationParser();
					
					//Parser, used only to get source and target address
					//address = channel_address * 16 + component_address ()
					Integer deviceAddress = ( parser.parseAddresses( dataBuffer ) ).get( "source" );
					
					//Add new device
	            	if( measurementDevices[deviceAddress] == null )
	            	{
	            		//Parser, used only to get measured variables list	            		
	            		addDevice( deviceAddress );
	            		Queue<ELANVariableDimensionPair> measuredVariables = parser.parseMeasuredVariables( dataBuffer );
	            		( measurementDevices[deviceAddress] ).getDeviceInformation().setMeasuredVariables( measuredVariables );
	            	}
	            	
	            	//Start data parser thread to avoid frames lost.
	            	ELANDataParser dataParserThread = new ELANDataParser( dataBuffer );
	            	dataParserThread.addObserver( measurementDevices[deviceAddress] );
	            	
	            	Thread thread = new Thread( dataParserThread );
	            	thread.setName( "DATA_PARSER_FROM_NETWORK[" + id.toString() + "]" );
	            	thread.start();
				}
			}
			else if( arg instanceof ELANMeasurementDeviceNotification )
			{
				Integer deviceAddress = ( ( ELANMeasurementDeviceNotification ) arg ).getData();
				measurementDevicesNotifications[ deviceAddress ] = true;
				//Check if all notification received
				if( checkNotifications() )
				{
					setChanged();
					notifyObservers( new ELANNetworkNotification( getPort() ) );
					clearNotifications();
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
		for( int i = 0; i < 13; i++ )
		{
			if( measurementDevices[i] != null )
			{
				measurementDevicesArray.add( measurementDevices[i] );
			}
		}
        Iterator<ELANMeasurementDevice> imeasurementDevices = measurementDevicesArray.iterator();
        
        return imeasurementDevices; 
    }
	
	public void initializeDevicesPool()
	{
		/*
		 * Number of elements is 13 becouse ELAN Network support up to 12 devices 
		 * with adresses 1-12
		 */				
		measurementDevices = new ELANMeasurementDevice[13];
		measurementDevicesNotifications = new boolean[13];
		
		for( int i = 0; i < 13; i++ )
		{
			measurementDevices[i] = null;
			measurementDevicesNotifications[i] = false;
		}
	}
	
	public boolean checkNotifications()
	{
		for( int i = 0; i < 13; i++ )
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
		for( int i = 0; i < 13; i++ )
		{
			measurementDevicesNotifications[i] = false;
		}
	}
	
	public void addDevice( String name, Integer deviceAddress ) throws DuplicateDeviceException
	{
		if( measurementDevices[deviceAddress] == null )
		{
			ELANMeasurementDevice device = new ELANMeasurementDevice( name, deviceAddress );
			device.addObserver( this );
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
			device.addObserver( this );
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
		if( name == "" )
		{
			return "Network " + id.toString();
		}
		else
		{
			return name;
		}
	}

	public String getPort()
	{
		return port;
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
	
	public ELANConnection getConnection()
	{
		return connection;
	}
	
	public Integer getSize()
	{
		Integer size = 0;
		
		for( ELANMeasurementDevice device: measurementDevices )
		{
			if( device != null )
				size++;
		}
		
		return size;
	}
}
