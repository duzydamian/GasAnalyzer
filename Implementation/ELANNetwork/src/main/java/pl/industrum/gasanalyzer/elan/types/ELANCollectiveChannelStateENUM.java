
package pl.industrum.gasanalyzer.elan.types;

import java.util.LinkedList;
import java.util.Queue;

public enum ELANCollectiveChannelStateENUM
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
	
	private ELANCollectiveChannelStateENUM(String stateValue)
	{
		value = Integer.parseInt(stateValue, 2);
	}	
	
	private static int getValue(ELANCollectiveChannelStateENUM stateENUM)
	{
		return stateENUM.value;
	}
	
	public static Queue<ELANCollectiveChannelStateENUM> getStates(int channelAnswer)
	{
		Queue<ELANCollectiveChannelStateENUM> states = new LinkedList<ELANCollectiveChannelStateENUM>();
			
		if(channelAnswer == getValue(TRANSMITTED_MEASRED_VALUES_VALID))
		{
			states.add(TRANSMITTED_MEASRED_VALUES_VALID);
		}
		else
		{
			for(ELANCollectiveChannelStateENUM stateENUM: ELANCollectiveChannelStateENUM.values())
			{
				if ( (getValue(stateENUM) & channelAnswer) > 0)
					states.add(stateENUM);
			}
		}
		return states; 
	}
}
