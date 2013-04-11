package pl.industrum.gasanalyzer.elan.communication.rx;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxFrame;
import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;

public class ELANFrameCreationObserver implements Observer
{
	private Queue<ELANRxFrame> rxFrameBuffer;

	public ELANFrameCreationObserver()
	{
		rxFrameBuffer = new LinkedList<ELANRxFrame>();
	}
	
	public void update( Observable obj, Object arg )
	{					
		try
		{
			if( arg instanceof ELANRxFrame )
			{
				ELANRxFrame rx = ( ELANRxFrame )arg;
				rxFrameBuffer.add( rx );
				if( rx.isValid() )
				{
					for (ELANMeasurement measurement : (ELANRxBroadcastFrame)rx)
					{
						System.out.println(measurement.toString());
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
}
