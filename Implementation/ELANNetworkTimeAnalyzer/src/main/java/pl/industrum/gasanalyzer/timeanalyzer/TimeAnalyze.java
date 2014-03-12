/**
 * 
 */
package pl.industrum.gasanalyzer.timeanalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Vector;

import pl.industrum.gasanalyzer.elan.communication.ELANCommunication;
import pl.industrum.gasanalyzer.elan.communication.ELANConnection;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class TimeAnalyze
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "HH:mm:ss:SS dd/MM/yyyy", Locale.getDefault() );
	
	static boolean timeout;
	public static File timeFile;
	static BufferedWriter timeOutput;
	
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
	try
	{	
		Vector<String> vectorPortsOnlySerial = ELANConnection.vectorPortsOnlySerial();
		int iterator = 0;
		timeout = false;
		//Timer timer = new Timer("Reading time");
		//TimeIsUp timeIsUp = new TimeIsUp();		
					
		timeFile = new File( "time.log" );	

		timeOutput = new BufferedWriter(new FileWriter(timeFile));	
		timeOutput.write( dateFormater.format( new Date() ) );		
		timeOutput.newLine();
		timeOutput.write( "------------------------------------------" );
		timeOutput.newLine();
		timeOutput.write( "					POMIARY					 " );
		timeOutput.newLine();
		timeOutput.write( "------------------------------------------" );
		timeOutput.newLine();
		timeOutput.flush();		
		
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
		//timer.schedule( timeIsUp, 50000 );

		//int dataPart;
		//while(!timeout)
		Queue<Integer> writeFrame = new LinkedList<Integer>();
		writeFrame.add( 0x10 );
		writeFrame.add( 0x01 );
		writeFrame.add( 0xA0 );
		writeFrame.add( 0xE0 );
		writeFrame.add( 0x6B );
		//writeFrame.add( 0x06 ); //device info
		writeFrame.add( 0x02 ); //pomiary
		writeFrame.add( 0x10 );
		writeFrame.add( 0x03 );
		//writeFrame.add( 0x79 ); //device info
		//writeFrame.add( 0x55 ); //device info
		writeFrame.add( 0x38 ); //pomiary
		writeFrame.add( 0x94 ); //pomiary
		
		int  previousCharacter = -1;
    	int  curentCharacter = -1;
		
		for ( int i = 0; i < 3; i++ )
		{
			communication.writeFrame( writeFrame );
			long nanoTime = System.nanoTime();			
			long currentTimeMillis = System.currentTimeMillis();			
			curentCharacter = connection.read();
			long nanoTime2 = System.nanoTime();
			long currentTimeMillis2 = System.currentTimeMillis();						
			do
    		{   
    			previousCharacter = curentCharacter;  
    			//Read next character from new frame
    			curentCharacter = connection.read();	
    			//Add current character to collected frame    			
    		}
    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
			long nanoDiff = nanoTime2 - nanoTime;
			long miliDiff = currentTimeMillis2 - currentTimeMillis;
			timeOutput.write( "Czas nano: " + nanoDiff );
			timeOutput.write( "Czas nano: " + miliDiff );
			System.out.println( "Czas nano: " + nanoDiff );
			System.out.println( "Czas mili: " + miliDiff );
		}
		timeOutput.write( "------------------------------------------" );
		timeOutput.newLine();
		//timer.cancel();
		//timeIsUp.cancel();
		connection.disconnect();
	}
	catch ( IOException e1 )
	{
		e1.printStackTrace();
	}		
	}
	
/*	static class TimeIsUp extends TimerTask
	{
		public void run()
		{
			timeout = true;
		}
	}*/
}
