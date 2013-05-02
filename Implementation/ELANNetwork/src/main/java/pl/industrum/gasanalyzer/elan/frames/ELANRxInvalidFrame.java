package pl.industrum.gasanalyzer.elan.frames;

import java.util.Queue;

import pl.industrum.gasanalyzer.elan.types.ELANCollectiveChannelState;

public class ELANRxInvalidFrame extends ELANRxFrame
{
	private Queue<ELANCollectiveChannelState> collectiveChannelState;
	
	public ELANRxInvalidFrame( Queue<ELANCollectiveChannelState> collectiveChannelStateQueue )
	{
		//ELANRxFrame.valid set to false
		super( false );
		
		this.collectiveChannelState = collectiveChannelStateQueue;
	}
	
	public Queue<ELANCollectiveChannelState> getCollectiveChannelState()
	{
		return collectiveChannelState;
	}
}
