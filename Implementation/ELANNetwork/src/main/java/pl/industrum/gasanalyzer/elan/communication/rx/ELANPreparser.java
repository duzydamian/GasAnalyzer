package pl.industrum.gasanalyzer.elan.communication.rx;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ELANPreparser implements ELANParser
{

	public Queue<Integer> trimData( Queue<Integer> data )
	{
		Queue<Integer> trimedQueue = new LinkedList<Integer>();

		Iterator<Integer> dataIterator = data.iterator();
		
		while( dataIterator.hasNext() )
		{
			Integer elem = data.poll();
			if( elem == 16 )
			{
				if( data.peek() != 16)
				{
					trimedQueue.add( elem );
				}
			}
			else
			{
				trimedQueue.add( elem );
			}
		}
		return trimedQueue;
	}

}
