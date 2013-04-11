
package pl.industrum.gasanalyzer.elan.types;

import java.util.LinkedList;
import java.util.Queue;

public enum ELANCollectiveChannelState
{
	TRANSMITTED_MEASRED_VALUES_VALID("00000000"),
	ERROR("000000001"),
	MAINTENANCE_REQUEST("00000010"),
	NOT_READY("00000100"), //not measure
	MAINTENANCE_SWITCH_ON("00001000"),
	FUNCTION_CHECK_ON("00010000"),
	COMMAND_NOT_ACCEPTED("00100000"),
	LIMIT_ALARM("01000000"),
	ZERO("10000000");
	
	private int value;
	
	private ELANCollectiveChannelState(String stateValue)
	{
		value = Integer.parseInt(stateValue, 2);
	}	
	
	private static int getValue(ELANCollectiveChannelState stateENUM)
	{
		return stateENUM.value;
	}
	
	public static Queue<ELANCollectiveChannelState> getStates(int channelAnswer)
	{
		Queue<ELANCollectiveChannelState> states = new LinkedList<ELANCollectiveChannelState>();
			
		if(channelAnswer == getValue(TRANSMITTED_MEASRED_VALUES_VALID))
		{
			states.add(TRANSMITTED_MEASRED_VALUES_VALID);
		}
		else
		{
			for(ELANCollectiveChannelState stateENUM: ELANCollectiveChannelState.values())
			{
				if ( (getValue(stateENUM) & channelAnswer) > 0)
					states.add(stateENUM);
			}
		}
		return states; 
	}
}
