package pl.industrum.gasanalyzer.elan.communication;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class ELANDataParser extends Observable implements Runnable
{
	private Queue<Integer> dataBuffer;
	private ELANRxFrame rxFrame;
	
	public ELANDataParser( Queue<Integer> dataBuffer )
	{
		this.dataBuffer = dataBuffer;
		//rxFrame = new ELANRxFrame( dataBuffer );
	}
	
	public void run()
	{
		//First cut off header and footer of frame
		dataBuffer = trimData( dataBuffer );
		
		//Check ELAN function type
		//...
		//e.g. FunctionResolver.resolve( int function )
		//...
		//...
		//Depending on called function read data
//		Iterator<Integer> dataIterator = dataBuffer.iterator();
		
//		while( dataIterator.hasNext() )
//		{			
//			ELANMeasuredVariable[] keys = ELANMeasuredVariable.values();
//			
//			switch (keys[1]) {
//			case CO:
//									
//				break;
//
//			default:
//				break;
//			}
//		}
		for (Integer i : dataBuffer)
			System.out.print(i+", ");
		System.out.println();
		//setChanged();
		//notifyObservers( rxFrame );
	}
	
	public Queue<Integer> trimData( Queue<Integer> data )
	{
		Queue<Integer> trimedQueue = new LinkedList<Integer>();
		
		//Poll header bytes
		data.poll();
		data.poll();
		
		//Temporary variables
		Integer elem;
		Integer nextElem;
		Iterator<Integer> dataIterator = data.iterator();
		
		while( dataIterator.hasNext() )
		{
			elem = data.poll();
			if( elem != 16 )
			{
				trimedQueue.add( elem );
			}
			else
			{
				nextElem = data.peek();
				if( nextElem == 3 )
				{
					break;
				}
				else
				{
					trimedQueue.add( elem );
				}
			}
		}
		return trimedQueue;
	}
}
