package pl.industrum.gasanalyzer.elan.alarms;

import java.util.Observable;

import pl.industrum.gasanalyzer.elan.communication.rx.ELANAlarmNotification;

public class AlarmNotificationSender extends Observable
{
	private String clockName;
	
	public AlarmNotificationSender( String clockName )
	{
		this.clockName = clockName;
	}
	
	public void notifyObservers()
	{
		setChanged();
		notifyObservers( new ELANAlarmNotification( clockName ) );
	}
	
	public String getClockName()
	{
		return clockName;
	}
	
	public void setClockName( String clockName )
	{
		this.clockName = clockName;
	}
}
