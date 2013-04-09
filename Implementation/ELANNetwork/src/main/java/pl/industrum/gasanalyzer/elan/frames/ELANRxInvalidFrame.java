package pl.industrum.gasanalyzer.elan.frames;

import pl.industrum.gasanalyzer.elan.types.ELANCollectiveChannelState;

public class ELANRxInvalidFrame extends ELANRxFrame
{
	private ELANCollectiveChannelState collectiveChannelState;
	
	public ELANRxInvalidFrame( Integer sourceAdress, Integer targetAdress, ELANCollectiveChannelState collectiveChannelState )
	{
		//ELANRxFrame.valid set to false
		super( sourceAdress, targetAdress, false );
		
		this.collectiveChannelState = collectiveChannelState;
	}
	
	public ELANCollectiveChannelState getCollectiveChannelState()
	{
		return collectiveChannelState;
	}
}
