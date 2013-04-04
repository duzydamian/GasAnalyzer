package pl.industrum.gasanalyzer.elan.communication;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

public class ELANRxBufferObserver implements Observer
{
	private Queue<Integer> dataBuffer;
	private Queue<ELANRxFrame> rxFrameBuffer;
	
	public ELANRxBufferObserver()
	{
		rxFrameBuffer = new LinkedList<ELANRxFrame>();
	}
	
	@SuppressWarnings("unchecked")
	public void update( Observable obj, Object arg )
	{		
		if( arg instanceof LinkedList<?> )
		{
			dataBuffer = ( LinkedList<Integer> )arg;
			
			if( dataBuffer.size() > 0 )
			{
            	ELANDataParser rxThread = new ELANDataParser( dataBuffer );
            	rxThread.addObserver( this );
            	new Thread( rxThread ).start();
			}
		}
		else if( arg instanceof ELANRxFrame )
		{
			ELANRxFrame rx = ( ELANRxFrame )arg;
			rxFrameBuffer.add( rx );
		}
		else if(arg instanceof String)
		{
			System.out.println((String)arg);
		}
		else{
			System.out.println("Z else: "+arg);
		}
	}
}
