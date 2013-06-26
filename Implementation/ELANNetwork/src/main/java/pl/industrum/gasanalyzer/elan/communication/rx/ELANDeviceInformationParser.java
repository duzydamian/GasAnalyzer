package pl.industrum.gasanalyzer.elan.communication.rx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.types.ELANDimension;
import pl.industrum.gasanalyzer.elan.types.ELANMeasuredVariable;
import pl.industrum.gasanalyzer.elan.types.ELANVariableDimensionPrecisionTrio;

public class ELANDeviceInformationParser implements ELANParser
{
	public Queue<ELANVariableDimensionPrecisionTrio> parseMeasuredVariables( Queue<Integer> data )
	{
		Queue<Integer> dataTemp = new LinkedList<Integer>();
		dataTemp.addAll(data);
		
		return getMeasuredVariables( trimData( data ) );
	}
	
	public Queue<ELANVariableDimensionPrecisionTrio> getMeasuredVariables( Queue<Integer> trimedData )
	{
		//Create queue to return
		Queue< ELANVariableDimensionPrecisionTrio > measuredVariables = new LinkedList< ELANVariableDimensionPrecisionTrio >();
		
		//Trim source and target addresses
		trimedData.poll();
		trimedData.poll();
		
		//Trim collective state and state information
		Integer testByte = trimedData.poll();
		while( testByte != Integer.valueOf( 'k' ) )
		{
			testByte = trimedData.poll();
		}
		
		//Trim function number
		trimedData.poll();
		
		//Parse measured variables information
		while( trimedData.size() > 0 )
		{
			//Jump over value
			Integer valueByte = trimedData.poll();
			while( valueByte != 0 )
			{
				valueByte = trimedData.poll(); //in last iteration jump over 0
			}
			
			//Jump over dimension
			ELANDimension dimension = ELANDimension.getValue( trimedData.poll() );
			trimedData.poll();
			
			//Get measured variable
			ELANMeasuredVariable measuredVariable = ELANMeasuredVariable.getValue( trimedData.poll() );
			trimedData.poll();
			
			//Add to queue
			ELANVariableDimensionPrecisionTrio variableDimensionPair = new ELANVariableDimensionPrecisionTrio( measuredVariable, dimension, 2 );
			measuredVariables.add( variableDimensionPair );	
		}
		
		return measuredVariables;
	}
	
	public HashMap<String, Integer> parseAddresses( Queue<Integer> data )
	{
		Queue<Integer> dataTemp = new LinkedList<Integer>();
		dataTemp.addAll(data);
		
		return getAddresses( trimData( dataTemp ) );
	}
	
	public HashMap<String, Integer> getAddresses( Queue<Integer> trimedData )
	{
		Integer targetAdress = trimedData.poll();
		Integer sourceAdress = trimedData.poll()/16;
		
		HashMap<String, Integer> addresses = new HashMap<String, Integer>();
		addresses.put( "target", targetAdress );
		addresses.put( "source", sourceAdress );
		
		return addresses;
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
