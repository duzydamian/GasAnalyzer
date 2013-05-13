package pl.industrum.gasanalyzer.elan.alarms;

import java.util.Observer;
import java.util.Timer;

public class Alarm
{
	private Timer timer;
	
	private Integer step;
	private String name;
	private Observer observer;
	
	private boolean running;
	
	public Alarm( String name, Observer observer )
	{
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
	
	public void runWithStep( Integer step )
	{
		this.step = step * 1000;
		
		if( running == true )
		{
			timer.cancel();
		}
		else
		{
			running = true;
		}
		
		timer = new Timer();
		timer.schedule( new AlarmNotifyTask( name, observer ), this.step, this.step );
	}
	
	public void stop()
	{
		if( running == true )
		{
			timer.cancel();
			running = false;
		}
	}
}
