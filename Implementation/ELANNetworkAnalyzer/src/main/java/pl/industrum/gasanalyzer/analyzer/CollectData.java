/**
 * 
 */
package pl.industrum.gasanalyzer.analyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
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
	static File archiveDirectory;
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
			archiveDirectory = new File( "archive" );			
			if ( !archiveDirectory.exists() )
				archiveDirectory.mkdir();
			
			frameFile = new File( "frame.log" );
			if ( frameFile.exists() )
			{
				archive("frame", frameFile);
			}
				
			hexFile = new File( "hexData.log" );
			intFile = new File( "intData.log" );			
			
			frameOutput = new BufferedWriter(new FileWriter(frameFile));
			frameOutput.write( dateFormater.format( new Date() ) );
			frameOutput.newLine();
			frameOutput.write( "------------------------------------------" );
			frameOutput.newLine();
			frameOutput.flush();
						
			hexOutput = new BufferedWriter(new FileWriter(hexFile));
			hexOutput.write( dateFormater.format( new Date() ) );
			hexOutput.newLine();
			hexOutput.write( "------------------------------------------" );
			hexOutput.newLine();
			hexOutput.flush();
									
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
			while ( ( valueMode == 1 & System.in.available() == 0 ) | ( valueMode == 0 & !timeout ) )
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
	
	private static void archive(final String name, File file)
	{
		FileFilter filter = new FileFilter()
		{
			
			public boolean accept( File arg0 )
			{
				if ( arg0.getName().startsWith( name ))
					return true;
				else
					return false;
			}
		};
		String lastNameNumber = "";
		
		if( archiveDirectory.listFiles( filter ).length > 1 )
		{
			String lastName = archiveDirectory.listFiles()[archiveDirectory.listFiles( filter ).length-1].getName();
			lastNameNumber = lastName.substring( lastName.lastIndexOf( "e" )+1, lastName.lastIndexOf( "." ) );
			//TODO implementacja inkrementacji !
		}
		else if ( archiveDirectory.listFiles( filter ).length == 1 )
			lastNameNumber = "2";
		File frameFileArchived = new File( "archive"+File.separator+name+lastNameNumber+".log" );	
		file.renameTo( frameFileArchived );
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
