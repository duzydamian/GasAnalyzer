/**
 * 
 */
package pl.industrum.gasanalyzer.analyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class CollectData
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "HH:mm:ss:SS dd/MM/yyyy", Locale.getDefault() );

	static boolean timeout;
	public static File frameFile;
	static BufferedWriter frameOutput; 
	public static File hexFile;
	static BufferedWriter hexOutput; 
	public static File intFile;
	static BufferedWriter intOutput; 
	/**
	 * 
	 */
	public CollectData()
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
		
		try
		{
			frameFile = new File( "frame.log" );
			frameOutput = new BufferedWriter(new FileWriter(frameFile));
			frameOutput.write( dateFormater.format( new Date() ) );
			frameOutput.newLine();
			frameOutput.write( "------------------------------------------" );
			frameOutput.newLine();
			frameOutput.flush();
			
			hexFile = new File( "hexData.log" );
			hexOutput = new BufferedWriter(new FileWriter(hexFile));
			hexOutput.write( dateFormater.format( new Date() ) );
			hexOutput.newLine();
			hexOutput.write( "------------------------------------------" );
			hexOutput.newLine();
			hexOutput.flush();
			
			intFile = new File( "intData.log" );			
			intOutput = new BufferedWriter(new FileWriter(intFile));
			intOutput.write( dateFormater.format( new Date() ) );
			intOutput.newLine();
			intOutput.write( "------------------------------------------" );
			intOutput.newLine();
			intOutput.flush();
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		clearSystemIn();
		
		System.out.println( "0: Czasowy" );
		System.out.println( "1: Ciągły (do naciśnięcia dowolnego klawisza)" );
		int valueMode = 0;
		
		do
		{
			System.out.println( "W jakim trybie uruchomić ? [0 lub 1]" );
						
			try
			{
				valueMode = System.in.read() - 48;
			} catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		while ( !( valueMode >= 0  && valueMode <= 1) );
		
		System.out.println( "Próbuję połączyć z " + vectorPortsOnlySerial.get( value ) );
		
		ELANConnection connection = new ELANConnection();
		try
		{
			connection.connect( vectorPortsOnlySerial.get( value ));
			ELANCommunication communication = new ELANCommunication( connection );		
			timer.schedule( timeIsUp, 50000 );

			//int dataPart;
			//while(!timeout)
			clearSystemIn();
			while (System.in.available() == 0)
			{
				//dataPart = connection.read();
				Queue<Integer> readFrame  = communication.readFrame();
				System.out.println( readFrame );
			}
			timer.cancel();
			timeIsUp.cancel();
			connection.disconnect();
			frameOutput.close();
			hexOutput.close();
			intOutput.close();
		}
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void clearSystemIn()
	{
		try
		{
			while (System.in.available() != 0)
			{
				System.in.read();
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	static class TimeIsUp extends TimerTask
	{
		public void run()
		{
			timeout = true;
		}
	}
}
