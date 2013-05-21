package pl.industrum.gasanalyzer.elan.frames;

import java.util.Date;

import pl.industrum.gasanalyzer.elan.types.ELANChannelState;

public class ELANRxFrame extends ELANFrame
{
	private Date timeStamp;
	private boolean valid;
	private ELANChannelState channelState;
	
	public ELANRxFrame( boolean valid ) 
	{
		super();
		this.timeStamp = new Date();
		this.valid = valid;
	}
	
	public ELANChannelState getChannelState()
	{
		return channelState;
	}
	
	public void setChannelState( ELANChannelState channelState )
	{
		this.channelState = channelState;
	}
	
	public Date getTimeStamp() 
	{
		return timeStamp;
	}
	
	public boolean isValid()
	{
		return valid;
	}
}
