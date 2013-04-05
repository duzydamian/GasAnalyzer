package pl.industrum.gasanalyzer.elan.communication;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

public class ELANRxBufferObserver implements Observer
{
	private Queue<Integer> dataBuffer;
	
	public ELANRxBufferObserver()
	{
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
				dataBuffer = ( LinkedList<Integer> ) arg;
				
				//Last time check dataBuffer non-zero size condition.
				if( dataBuffer.size() > 0 )
				{
					//Start data parser thread to avoid frames lost.
	            	ELANDataParser dataParserThread = new ELANDataParser( dataBuffer );
	            	ELANFrameCreationObserver dataParserObserver = new ELANFrameCreationObserver();
	            	dataParserThread.addObserver( dataParserObserver );
	            	
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
}
