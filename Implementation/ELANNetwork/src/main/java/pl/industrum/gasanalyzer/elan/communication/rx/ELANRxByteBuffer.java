package pl.industrum.gasanalyzer.elan.communication.rx;

import java.util.Observable;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.communication.ELANCommunication;
import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.elan.notifications.ELANRxByteBufferNotification;

public class ELANRxByteBuffer extends Observable implements Runnable
{	
	private ELANCommunication communication;
	
	public ELANRxByteBuffer( ELANConnection connection )
	{
		//Open port and start communication.
		communication = new ELANCommunication( connection );
	}
	
	public void run()
	{
		while( !Thread.currentThread().isInterrupted() )
		{			
			try
			{				
				//Read all frames and send every correct frame to ELANRxBufferObserver.
	        	Queue<Integer> frame = communication.readFrame();
	        	if(frame != null)
	        	{	        		
		        	setChanged();
		        	notifyObservers( new ELANRxByteBufferNotification( frame ) );
	        	}
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
        }
	}
}
