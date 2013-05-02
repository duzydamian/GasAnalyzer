package pl.industrum.gasanalyzer.elan.frames;

import java.util.ArrayList;
import java.util.Date;

import pl.industrum.gasanalyzer.elan.types.ELANChannelState;

public class ELANRxFrame extends ELANFrame
{
	private Date timeStamp;
	private boolean valid;
	private ArrayList<ELANChannelState> channelStateCollection;
	
	public ELANRxFrame( boolean valid ) 
	{
		super();
		this.timeStamp = new Date();
		this.valid = valid;
		this.channelStateCollection = new ArrayList<ELANChannelState>();
	}
	
	public void addChannelState( ELANChannelState channelState )
	{
		channelStateCollection.add( channelState );
	}
	
	public ArrayList<ELANChannelState> getChannelStateCollection()
	{
		return channelStateCollection;
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
