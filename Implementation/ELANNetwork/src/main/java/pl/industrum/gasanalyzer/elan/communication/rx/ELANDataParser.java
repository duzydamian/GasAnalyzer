package pl.industrum.gasanalyzer.elan.communication.rx;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.frames.ELANRxFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxInvalidFrame;
import pl.industrum.gasanalyzer.elan.types.ELANChannelState;
import pl.industrum.gasanalyzer.elan.types.ELANCollectiveChannelState;

public class ELANDataParser extends Observable implements Runnable
{
	private Queue<Integer> dataBuffer;
	private ELANRxFrame rxFrame;
	
	public ELANDataParser( Queue<Integer> dataBuffer )
	{
		this.dataBuffer = dataBuffer;
	}
	
	public void run()
	{
		//First cut off header and footer of frame
		dataBuffer = trimData( dataBuffer );
		
		Integer targetAdress = dataBuffer.poll();
		Integer sourceAdress = dataBuffer.poll();
		
		//collective channel state (1 byte)
		Integer collectiveChannelStateByte = dataBuffer.poll();
		//channel state (could be few bytes, every byte means different option)
		Integer channelStateByte;
		
		//If the collective state is 0, the transmitted measured values are valid.
		if( collectiveChannelStateByte == 0 )
		{
			//
		}
		else
		{
			ELANCollectiveChannelState collectiveChannelState = 
					new ELANCollectiveChannelState( collectiveChannelStateByte );
			
			rxFrame = new ELANRxInvalidFrame( sourceAdress, targetAdress, collectiveChannelState );
			
			channelStateByte = dataBuffer.poll();
			while( channelStateByte != Integer.valueOf( 'k' ) ) //for example (must predict all function letters)
			{
				rxFrame.addChannelState( ELANChannelState.values()[ channelStateByte ] );
			}
		}
		
		setChanged();
		notifyObservers( rxFrame );
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
