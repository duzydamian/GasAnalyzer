package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxInvalidFrame;
import pl.industrum.gasanalyzer.elan.notifications.ELANDataParserNotification;
import pl.industrum.gasanalyzer.elan.notifications.ELANMeasurementDeviceNotification;
import pl.industrum.gasanalyzer.elan.types.ELANBufferType;
import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;
import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;

public class ELANMeasurementDevice extends Observable implements Observer
{
	//Frames buffers
	private Queue<ELANRxInvalidFrame> rxInvalidFrameBuffer;
	//TODO should be used correct buffer type to each frame type 
	private Queue<ELANRxBroadcastFrame> rxBroadcastFrameBuffer; 
	private ELANRxBroadcastFrame rxBroadcastFrameForSnaphot;
	
	private ELANMeasurementDeviceInformation deviceInformation;
	
	public ELANMeasurementDevice( String name, Integer deviceAddress )
	{
		deviceInformation = new ELANMeasurementDeviceInformation();
		setName( name );
		setDeviceAddress( deviceAddress );
		rxInvalidFrameBuffer = new LinkedList<ELANRxInvalidFrame>();
		rxBroadcastFrameBuffer = new LinkedList<ELANRxBroadcastFrame>();
	}
	
	public ELANMeasurementDevice( Integer deviceAddress )
	{
		deviceInformation = new ELANMeasurementDeviceInformation();
		setDeviceAddress( deviceAddress );
		rxInvalidFrameBuffer = new LinkedList<ELANRxInvalidFrame>();
		rxBroadcastFrameBuffer = new LinkedList<ELANRxBroadcastFrame>();
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
					//Add frame to broadcasts buffer
					for( ELANMeasurement measurement : ( ( ELANRxBroadcastFrame )rx ) )
					{
						measurement.setPrecision( deviceInformation.getVariablePrecision( measurement.getMeasuredVariable() ) );
					}
					
					rxBroadcastFrameBuffer.add( ( ELANRxBroadcastFrame )rx );
					rxBroadcastFrameForSnaphot = ( ELANRxBroadcastFrame ) rx;

					//Notify Network about received frame
					setChanged();
					notifyObservers( new ELANMeasurementDeviceNotification( "unknown", getDeviceAddress(), ELANBufferType.BROADCAST_FRAME ) );
					//if( isEmpty( ELANBufferType.BROADCAST_FRAME ) )
					//{
						//setChanged();
						//notifyObservers( new ELANMeasurementDeviceNotification( "unknown", getDeviceAddress() ) );
					//}
				}
				else
				{
					//Add frame to invalids buffer
					rxInvalidFrameBuffer.add( ( ELANRxInvalidFrame ) rx );
					
					//Notify Network about received frame
					setChanged();
					notifyObservers( new ELANMeasurementDeviceNotification( "unknown", getDeviceAddress(), ELANBufferType.INVALID_FRAME ) );
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

	public boolean isEmpty( ELANBufferType type ) throws Exception
	{
		switch ( type )
		{
			case INVALID_FRAME:
			{
				if( rxInvalidFrameBuffer.peek() != null )
				{
					return false;
				}
				else
				{
					return true;
				}
			}
			case BROADCAST_FRAME:
			{
				if( rxBroadcastFrameBuffer.peek() != null )
				{
					return false;
				}
				else
				{
					return true;
				}
			}
			default:
			{
				throw new Exception("Invalid buffer type!");
			}
		}
	}
	
	public ELANRxFrame pollAndClear( ELANBufferType type ) throws Exception
	{
		switch ( type )
		{
			case INVALID_FRAME:
			{
				ELANRxInvalidFrame frame = ( ELANRxInvalidFrame ) poll( type );
				rxInvalidFrameBuffer.clear();
				return frame;
			}
			case BROADCAST_FRAME:
			{
					ELANRxBroadcastFrame frame = ( ELANRxBroadcastFrame ) poll( type );
					rxBroadcastFrameBuffer.clear();
					return frame;
			}
			default:
			{
				throw new Exception("Invalid buffer type!");
			}
		}
	}
	
	public ELANRxFrame poll( ELANBufferType type ) throws Exception
	{
		switch ( type )
		{
			case INVALID_FRAME:
			{
				return rxInvalidFrameBuffer.poll();
			}
			case BROADCAST_FRAME:
			{
				return rxBroadcastFrameBuffer.poll();
			}
			default:
			{
				throw new Exception("Invalid buffer type!");
			}
		}
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
	
	public Queue<ELANRxBroadcastFrame> getRxBroadcastFrameBuffer()
	{
		return rxBroadcastFrameBuffer;
	}
}
