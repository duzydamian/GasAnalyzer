package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxFrame;
import pl.industrum.gasanalyzer.elan.notifications.ELANDataParserNotification;
import pl.industrum.gasanalyzer.elan.notifications.ELANMeasurementDeviceNotification;
import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;

public class ELANMeasurementDevice extends Observable implements Observer
{
	//Frames buffers
	private Queue<ELANRxFrame> rxFrameBuffer;
	//TODO should be used correct buffer type to each frame type 
	//private Queue<ELANRxBroadcastFrame> rxBroadcastFrameBuffer; 
	private ELANRxBroadcastFrame rxBroadcastFrameForSnaphot;
	
	private ELANMeasurementDeviceInformation deviceInformation;
	
	public ELANMeasurementDevice( String name, Integer deviceAddress )
	{
		deviceInformation = new ELANMeasurementDeviceInformation();
		setName( name );
		setDeviceAddress( deviceAddress );
		rxFrameBuffer = new LinkedList<ELANRxFrame>();
	}
	
	public ELANMeasurementDevice( Integer deviceAddress )
	{
		deviceInformation = new ELANMeasurementDeviceInformation();
		setDeviceAddress( deviceAddress );
		rxFrameBuffer = new LinkedList<ELANRxFrame>();
	}
	
	public void update( Observable obj, Object arg )
	{					
		try
		{
			if( arg instanceof ELANDataParserNotification )
			{
				ELANRxFrame rx = ( ( ELANDataParserNotification ) arg ).getData();
				
				if( rx.isValid() )
				{
					//Notify Network about received measurement frame
					if( isEmpty() )
					{
						rxFrameBuffer.add( rx );
						//FIXME we have only ELANRxBroadcastFrame for now
						rxBroadcastFrameForSnaphot = ( ELANRxBroadcastFrame ) rx;
						setChanged();
						notifyObservers( new ELANMeasurementDeviceNotification( getDeviceAddress() ) );
					}
					else
					{
						//Add frame to buffer
						rxFrameBuffer.add( rx );
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

	public boolean isEmpty()
	{
		if( rxFrameBuffer.peek() != null )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public ELANRxFrame pollAndClear()
	{
		ELANRxFrame frame = poll();
		rxFrameBuffer.clear();
		return frame;
	}
	
	public ELANRxFrame poll()
	{
		ELANRxFrame frame = rxFrameBuffer.poll();
		return frame;
	}
	
	public ELANMeasurementDeviceInformation getDeviceInformation()
	{
		return this.deviceInformation;
	}
	
	
	public String getName()
	{
		return getDeviceInformation().getName();
	}

	public void setName( String name )
	{
		getDeviceInformation().setName( name );
	}

	public ELANDeviceType getDeviceType()
	{
		return getDeviceInformation().getDeviceType();
	}

	public void setDeviceType( ELANDeviceType deviceType )
	{
		getDeviceInformation().setDeviceType( deviceType );
	}

	public Integer getDeviceAddress()
	{
		return getDeviceInformation().getDeviceAddress();
	}

	public void setDeviceAddress( Integer deviceAddress )
	{
		getDeviceInformation().setDeviceAddress( deviceAddress );
	}
	
	public ELANRxFrame getSnapshot()
	{
		return rxBroadcastFrameForSnaphot;
	}
}
