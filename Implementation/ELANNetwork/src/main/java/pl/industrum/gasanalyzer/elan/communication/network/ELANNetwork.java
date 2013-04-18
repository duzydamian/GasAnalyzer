package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.communication.rx.ELANDataParser;

public class ELANNetwork implements Iterable<ELANMeasurementDevice>, Observer
{
	private String name;
	private ArrayList<ELANMeasurementDevice> measurementDevices;
	
	public ELANNetwork( String name )
	{
		this.name = name;
		this.measurementDevices = new ArrayList<ELANMeasurementDevice>( 255 );
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
					Integer deviceAddress = ELANDataParser.getAddresses( dataBuffer )[1];
					
					//Start data parser thread to avoid frames lost.
	            	ELANDataParser dataParserThread = new ELANDataParser( dataBuffer );
	            	dataParserThread.addObserver( this.measurementDevices.get( deviceAddress ) );
	            	
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
        Iterator<ELANMeasurementDevice> imeasurementDevices = measurementDevices.iterator();
        return imeasurementDevices; 
    }
	
	public boolean addDevice( String name, Integer deviceAddress )
	{
		if( measurementDevices.get( deviceAddress ) != null )
		{
			ELANMeasurementDevice device = new ELANMeasurementDevice( name, deviceAddress );
			measurementDevices.add( deviceAddress, device );
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean addDevice( Integer deviceAddress )
	{
		if( measurementDevices.get( deviceAddress ) != null )
		{
			ELANMeasurementDevice device = new ELANMeasurementDevice( deviceAddress );
			measurementDevices.add( deviceAddress, device );
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean removeDevice( Integer deviceAddress )
	{
		return measurementDevices.remove( deviceAddress );
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public ELANMeasurementDevice getDevice( int i )
	{
		return measurementDevices.get( i );
	}

}
