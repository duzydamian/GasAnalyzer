package pl.industrum.gasanalyzer.elan.frames;

import java.util.Queue;

import pl.industrum.gasanalyzer.elan.types.ELANCollectiveChannelState;

public class ELANRxInvalidFrame extends ELANRxFrame
{
	private Queue<ELANCollectiveChannelState> collectiveChannelState;
	
	public ELANRxInvalidFrame( Integer sourceAdress, Integer targetAdress, Queue<ELANCollectiveChannelState> collectiveChannelStateQueue )
	{
		//ELANRxFrame.valid set to false
		super( sourceAdress, targetAdress, false );
		
		this.collectiveChannelState = collectiveChannelStateQueue;
	}
	
	public Queue<ELANCollectiveChannelState> getCollectiveChannelState()
	{
		return collectiveChannelState;
	}
}
