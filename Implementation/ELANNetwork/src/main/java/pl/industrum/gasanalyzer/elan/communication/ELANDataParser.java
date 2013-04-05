package pl.industrum.gasanalyzer.elan.communication;

import java.util.Iterator;
import java.util.Observable;
import java.util.Queue;

public class ELANDataParser extends Observable implements Runnable
{
	private Queue<Integer> dataBuffer;
	private ELANRxFrame rxFrame;
	
	public ELANDataParser( Queue<Integer> dataBuffer )
	{
		this.dataBuffer = dataBuffer;
		rxFrame = new ELANRxFrame( dataBuffer );
	}
	
//	public ELANDataParser( ELANRxFrame frame )
//	{
//		//this.dataBuffer = dataBuffer;
//		rxFrame = frame;
//	}	
	
	public void run()
	{
		Iterator<Integer> dataIterator = dataBuffer.iterator();
		
		while( dataIterator.hasNext() )
		{
			
		}
		
		notifyObservers( rxFrame );
	}
}
