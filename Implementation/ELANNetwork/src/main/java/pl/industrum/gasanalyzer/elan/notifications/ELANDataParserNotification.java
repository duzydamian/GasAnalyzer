package pl.industrum.gasanalyzer.elan.notifications;

import pl.industrum.gasanalyzer.elan.frames.ELANRxFrame;

public class ELANDataParserNotification implements ELANNotification
{
	private ELANRxFrame rxFrame;
	
	public ELANDataParserNotification( ELANRxFrame rxFrame )
	{
		this.rxFrame = rxFrame;
	}
	
	public ELANRxFrame getData()
	{
		return rxFrame;
	}
}
