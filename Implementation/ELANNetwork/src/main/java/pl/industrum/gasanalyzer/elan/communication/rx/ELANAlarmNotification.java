package pl.industrum.gasanalyzer.elan.communication.rx;

import pl.industrum.gasanalyzer.elan.notifications.ELANNotification;

public class ELANAlarmNotification implements ELANNotification
{
	private String clockName;
	
	public ELANAlarmNotification( String clockName )
	{
		this.clockName = clockName;
	}
	
	public String getData()
	{
		return clockName;
	}
}
