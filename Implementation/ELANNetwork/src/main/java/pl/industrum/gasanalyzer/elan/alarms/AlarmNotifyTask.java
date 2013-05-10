package pl.industrum.gasanalyzer.elan.alarms;

import java.util.Observer;
import java.util.TimerTask;

public class AlarmNotifyTask extends TimerTask
{
	public AlarmNotificationSender sender;
	
	public AlarmNotifyTask( String clockName, Observer observer )
	{
		sender = new AlarmNotificationSender( clockName );
		sender.addObserver( observer );
	}
	
	@Override
	public void run()
	{
		sender.notifyObservers();
	}

}
