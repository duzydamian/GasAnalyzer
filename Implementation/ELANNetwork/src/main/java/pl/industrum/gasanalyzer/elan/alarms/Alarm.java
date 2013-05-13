package pl.industrum.gasanalyzer.elan.alarms;

import java.util.Observer;
import java.util.Timer;

public class Alarm extends Timer
{
	private Integer step;
	private String name;
	private Observer observer;
	
	private boolean running;
	
	public Alarm( String name, Observer observer )
	{
		super( name, true );
		this.name = name;
		this.observer = observer;
		
		running = false;
	}
	
	public Integer getStep()
	{
		return step;
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	public void runWithStep( Integer newStep )
	{
		this.step = newStep * 1000;
		
		if( running == true )
		{
			this.cancel();
		}
		else
		{
			running = true;
		}
		this.schedule( new AlarmNotifyTask( name, observer ), step, step );
	}
	
	public void stop()
	{
		cancel();
		running = false;
	}
}
