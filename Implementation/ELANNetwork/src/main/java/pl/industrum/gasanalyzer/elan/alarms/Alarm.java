package pl.industrum.gasanalyzer.elan.alarms;

import java.util.Observer;
import java.util.Timer;

public class Alarm extends Timer
{
	private Integer step;
	private String name;
	private Observer observer;
	
	public Alarm( String name, Observer observer )
	{
		super( name, true );
		this.name = name;
		this.step = 500;
		this.observer = observer;
	}
	
	public Integer getStep()
	{
		return step;
	}
	
	public void setStep( Integer step )
	{
		this.step = step;
		cancel();
		schedule( new AlarmNotifyTask( name, observer ), step, step );
	}
}
