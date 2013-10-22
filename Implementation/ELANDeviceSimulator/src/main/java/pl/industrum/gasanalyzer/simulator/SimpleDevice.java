/**
 * 
 */
package pl.industrum.gasanalyzer.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

import pl.industrum.gasanalyzer.elan.communication.ELANCommunication;
import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.elan.helpers.ELANCRC16;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class SimpleDevice
{
	/**
	 * 
	 */
	public SimpleDevice()
	{ }

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{			
		Runtime runtime = Runtime.getRuntime();
		long totalMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		long maxMemory = runtime.maxMemory();
		int availableProcessors = runtime.availableProcessors();
		try
		{
			Process exec = runtime.exec( "/home/duzydamian/Pulpit/tty0tty-1.2/pts/tty0tty" );
			
			 BufferedReader in = new BufferedReader(  
                     new InputStreamReader(exec.getInputStream()));  
			 String line = null;  
			 while ((line = in.readLine()) != null) {  
			     System.out.println(line);  
			 } 
		}
		catch ( IOException e1 )
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Vector<String> vectorPortsOnlySerial = ELANConnection.vectorPortsOnlySerial();
		int iterator = 0;
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
		
		
		try
		{
			while( true )
			{
				communication.writeFrame( randFrame() );
				//frame cyclic every 10s
				Thread.sleep( 10000 );
			}			
		}
		catch ( InterruptedException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connection.disconnect();
	}

	private static Queue<Integer> randFrame()
	{
		Random r = new Random();
		double nextDouble = r.nextDouble();
		String s = String.valueOf( nextDouble );
		String s2 = String.valueOf( 21+nextDouble );
		
		Queue<Integer> frame = new LinkedList<Integer>();
		//sof
		frame.add( 16 );
		frame.add( 1 );
		//dest adr
		frame.add( 240 );
		//src ard
		frame.add( 32 );		
		//collective state
		frame.add( 0 );
		//channel state
		frame.add( 4 );
		//command k,2 - Read measured value(s) of the channel
		frame.add( 107 );
		frame.add( 2 );
		
		//data
		for( char ch: s.toCharArray() )
		{
			frame.add( Integer.valueOf( ch ) );
		}
		frame.add( 0 );
		frame.add( 10 );
		frame.add( 0 );
		frame.add( 2 );
		frame.add( 0 );
		for( char ch: s2.toCharArray() )
		{
			frame.add( Integer.valueOf( ch ) );
		}
		frame.add( 0 );
		frame.add( 10 );
		frame.add( 0 );
		frame.add( 12 );
		frame.add( 0 );
		//eof
		frame.add( 16 );
		frame.add( 3 );
		
		int crc16 = ELANCRC16.countCRC16( frame );
		int crc16High = crc16 / 256;
		int crc16Low = crc16 % 256;
		
		frame.add( crc16Low );
		frame.add( crc16High );
		
		System.out.println( frame );
		return frame;
	}

}
