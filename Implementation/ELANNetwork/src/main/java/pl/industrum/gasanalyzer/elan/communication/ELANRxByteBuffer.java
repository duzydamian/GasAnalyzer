package pl.industrum.gasanalyzer.elan.communication;

import java.util.Observable;
import java.util.Queue;

public class ELANRxByteBuffer extends Observable implements Runnable
{	
	private ELANCommunication communication;
	
	public ELANRxByteBuffer()
	{
		//Open port and start communication.
		communication = new ELANCommunication();
	}
	
	public void run()
	{
		while( !Thread.currentThread().isInterrupted() )
		{
			try
			{				
				//Read all frames and send every correct frame to ELANRxBufferObserver.
	        	Queue<Integer> frame = communication.readFrame();
	        	setChanged();
	        	notifyObservers( frame );	        	
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
        }
	}
}
