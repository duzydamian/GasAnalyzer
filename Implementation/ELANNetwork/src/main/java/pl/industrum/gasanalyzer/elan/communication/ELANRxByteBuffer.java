package pl.industrum.gasanalyzer.elan.communication;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class ELANRxByteBuffer extends Observable implements Runnable
{	
	private Queue<Integer> dataBuffer;
	ELANCommunication communication;
	
	public ELANRxByteBuffer()
	{
		dataBuffer = new LinkedList<Integer>();
	}
	
	public void run()
	{
		communication = new ELANCommunication();
		while( !Thread.currentThread().isInterrupted() )
		{
			try
			{				
	        	Queue<Integer> frame = communication.readFrame();
	        	
	        		
	        		//wait for the end of frame... some kind of check here
	        		//...
	        		//...
	        		//when frame is complete
	        		setChanged();
	        		notifyObservers( frame );	        	
			}
			catch( Exception e )
			{
				
			}
        }
	}
}
