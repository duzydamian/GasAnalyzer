package pl.industrum.gasanalyzer.elan.communication.rx;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxInvalidFrame;
import pl.industrum.gasanalyzer.elan.notifications.ELANDataParserNotification;
import pl.industrum.gasanalyzer.elan.types.ELANChannelState;
import pl.industrum.gasanalyzer.elan.types.ELANCollectiveChannelState;
import pl.industrum.gasanalyzer.elan.types.ELANDimension;
import pl.industrum.gasanalyzer.elan.types.ELANMeasuredVariable;
import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;

public class ELANDataParser extends Observable implements Runnable, ELANParser
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
		String dataBufferCopy = dataBuffer.toString();
		dataBuffer = trimData( dataBuffer );
		try
		{
		//collective channel state (1 byte)
		Integer collectiveChannelStateByte = dataBuffer.poll();
		if( collectiveChannelStateByte == null)
			return;
		
		//If the collective state is 0, the transmitted measured values are valid.
		if( collectiveChannelStateByte == 0 )
		{
			rxFrame = new ELANRxBroadcastFrame();
		}
		else
		{
			Queue<ELANCollectiveChannelState> collectiveChannelStateQueue = ELANCollectiveChannelState.getStates(collectiveChannelStateByte);
			
			rxFrame = new ELANRxInvalidFrame( collectiveChannelStateQueue );
		}
		
		//channel state (could be few bytes, every byte means different option)
		Integer channelStateByte =  dataBuffer.poll();
		rxFrame.setChannelState( ELANChannelState.values()[ channelStateByte ] );
		
		//function letter
		Integer functionLetter = dataBuffer.poll();
		if( functionLetter == Integer.valueOf( 'k' ) )	
		{
			if( rxFrame.isValid() )
			{
				Integer kFunctionNumber = dataBuffer.poll();
				if( kFunctionNumber == 2 )
				{
					while( dataBuffer.size() > 0 )
					{
						String doubleAsString = "";
						Integer valueByte = dataBuffer.poll();
						while( valueByte != 0 )
						{
							doubleAsString += Character.toString( ( char )valueByte.intValue() );
							valueByte = dataBuffer.poll(); //in last iteration jump over 0
						}
						ELANDimension dimension = ELANDimension.getValue( dataBuffer.poll() );
						dataBuffer.poll(); //jump over 0
						ELANMeasuredVariable measuredVariable = ELANMeasuredVariable.getValue( dataBuffer.poll() );
						dataBuffer.poll(); //jump over 0
						
						//create measurement object
						ELANMeasurement measurement = new ELANMeasurement( dimension, measuredVariable, Double.parseDouble( doubleAsString ) );
						try
						{
							//add measurement object to frame collection
							( ( ELANRxBroadcastFrame )rxFrame ).addMeasurement(measurement);
						}
						catch( Exception e)
						{
						}
					}	
				}
			}
			else
			{
				
			}
			
			setChanged();
			notifyObservers( new ELANDataParserNotification( rxFrame ) );
		}
		}
		catch (Exception e)
		{
			System.out.println( dataBufferCopy );
			System.err.println( dataBufferCopy );
			e.printStackTrace();
		}
	}
	
	public Queue<Integer> trimData( Queue<Integer> data )
	{
		Queue<Integer> trimedQueue = new LinkedList<Integer>();
		
		//Poll header bytes
		data.poll();
		data.poll();
		
		//Trim source and target address
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
