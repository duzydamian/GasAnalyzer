package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.communication.rx.ELANDataParser;
import pl.industrum.gasanalyzer.elan.exceptions.DuplicateDeviceException;
import pl.industrum.gasanalyzer.elan.exceptions.NullDeviceException;

public class ELANNetwork implements Iterable<ELANMeasurementDevice>, Observer
{
	private String name;
	private ELANMeasurementDevice[] measurementDevices;
	
	public ELANNetwork( String name )
	{
		this.name = name;
		initializeDevicesPool();
	}
	
	@SuppressWarnings("unchecked")
	public void update( Observable obj, Object arg )
	{					
		try
		{
			//Receive information and data from ELANRxByteBuffer,
			//it means that full and correct frame got in.
			if( arg instanceof LinkedList<?> )
			{
				Queue<Integer> dataBuffer = ( LinkedList<Integer> ) arg;
				
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
			measurementDevicesArray.add(measurementDevices[i]);
		}
        Iterator<ELANMeasurementDevice> imeasurementDevices = measurementDevicesArray.iterator();
        
        return imeasurementDevices; 
    }
	
	public void initializeDevicesPool()
	{
		measurementDevices = new ELANMeasurementDevice[12];
		for( int i = 0; i < 12; i++ )
		{
			measurementDevices[i] = null;
		}
	}
	
	public void addDevice( String name, Integer deviceAddress ) throws DuplicateDeviceException
	{
		if( measurementDevices[deviceAddress.intValue()] == null )
		{
			ELANMeasurementDevice device = new ELANMeasurementDevice( name, deviceAddress );
			measurementDevices[deviceAddress.intValue()] = device;
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
	
	public String getName()
	{
		return this.name;
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
