package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxFrame;
import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;
import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;

public class ELANMeasurementDevice implements Observer
{
	private String name; //optional
	private ELANDeviceType deviceType;
	private Integer deviceAddress;
	
	private Queue<ELANRxFrame> rxFrameBuffer;
	
	public ELANMeasurementDevice( String name, Integer deviceAddress )
	{
		this.setName( name );
		this.setDeviceAddress( deviceAddress );
		this.rxFrameBuffer = new LinkedList<ELANRxFrame>();
	}
	
	public ELANMeasurementDevice( Integer deviceAddress )
	{
		this.setDeviceAddress( deviceAddress );
		rxFrameBuffer = new LinkedList<ELANRxFrame>();
	}
	
	@SuppressWarnings("deprecation")
	public void update( Observable obj, Object arg )
	{					
		try
		{
			if( arg instanceof ELANRxFrame )
			{
				ELANRxFrame rx = ( ELANRxFrame )arg;
				
				if( rx.isValid() )
				{
					rxFrameBuffer.add( rx );
					System.out.println(rx.getTimeStamp().toLocaleString());
					for (ELANMeasurement measurement : (ELANRxBroadcastFrame)rx)
					{
						System.out.print(measurement.toString()+" || ");
					}					
					System.out.println();
					System.out.println();
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

	public boolean isEmpty()
	{
		if( rxFrameBuffer.size() > 0 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public ELANRxFrame poll()
	{
		return rxFrameBuffer.poll();
	}
	
	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public ELANDeviceType getDeviceType()
	{
		return deviceType;
	}

	public void setDeviceType( ELANDeviceType deviceType )
	{
		//!!!empty for now, it is needed to ask the device for its type
	}

	public Integer getDeviceAddress()
	{
		return deviceAddress;
	}

	public void setDeviceAddress( Integer deviceAddress )
	{
		this.deviceAddress = deviceAddress;
	}
	
	
}
