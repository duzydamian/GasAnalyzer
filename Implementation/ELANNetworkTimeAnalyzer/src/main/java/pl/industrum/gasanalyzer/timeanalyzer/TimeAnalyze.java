/**
 * 
 */
package pl.industrum.gasanalyzer.timeanalyzer;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import pl.industrum.gasanalyzer.elan.communication.ELANCommunication;
import pl.industrum.gasanalyzer.elan.communication.ELANConnection;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class TimeAnalyze
{

	static boolean timeout;
	/**
	 * 
	 */
	public TimeAnalyze()
	{ }

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		Vector<String> vectorPortsOnlySerial = ELANConnection.vectorPortsOnlySerial();
		int iterator = 0;
		timeout = false;
		Timer timer = new Timer("Reading time");
		TimeIsUp timeIsUp = new TimeIsUp();
				
		for( String port: vectorPortsOnlySerial )
		{
			System.out.println( iterator + " : " + port );
			iterator++;
		}
		
		int value = 0;
		
		do
		{
			System.out.println( "Z którym urządzeniem połączyć ? [0-" + (vectorPortsOnlySerial.size()-1) + "]" );
						
			try
			{
				value = System.in.read() - 48;
			} catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		while ( !( value >= 0  && value <= vectorPortsOnlySerial.size()-1) );
		
		System.out.println( "Próbuję połączyć z " + vectorPortsOnlySerial.get( value ) );
		
		ELANConnection connection = new ELANConnection();
		try
		{
			connection.connect( vectorPortsOnlySerial.get( value ),  new Observer()
			{
				
				public void update( Observable o, Object arg )
				{
					// TODO Auto-generated method stub
				}
			});
		}
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ELANCommunication communication = new ELANCommunication( connection );		
		timer.schedule( timeIsUp, 50000 );

		//int dataPart;
		while(!timeout)
		{
			//dataPart = connection.read();
			Queue<Integer> readFrame = communication.readFrame();
			System.out.println( readFrame );
		}
		timer.cancel();
		timeIsUp.cancel();
		connection.disconnect();
	}
	
	static class TimeIsUp extends TimerTask
	{
		public void run()
		{
			timeout = true;
		}
	}
}
