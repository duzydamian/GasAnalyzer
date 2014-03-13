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
import java.util.Queue;
import java.util.Vector;

import pl.industrum.gasanalyzer.elan.helpers.ELANCRC16;
import pl.industrum.gasanalyzer.elan.using.Main;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class TimeAnalyze
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "HH:mm:ss:SS dd/MM/yyyy", Locale.getDefault() );
	
	static boolean timeout;
	public static File timeFile;
	public static File timeNanoFile;
	static BufferedWriter timeOutput;
	static BufferedWriter timeNanoOutput;
	
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
		long nanoTime = 0, miliDiff = 0, currentTimeMillis = 0,nanoTime2 = 0,currentTimeMillis2 = 0, nanoDiff = 0;
		double nanoAvg = 0, miliAvg = 0;
		long nanoMin = 0, miliMin = 0;
		long nanoMax = 0, miliMax = 0;
		int count = 0;
		
		timeFile = new File( "time.log" );
		timeNanoFile = new File( "timeNano.log" );	
		
		timeOutput = new BufferedWriter(new FileWriter(timeFile));	
		timeOutput.write( dateFormater.format( new Date() ) );		
		timeOutput.newLine();
		timeOutput.write( "------------------------------------------" );
		timeOutput.newLine();
		timeOutput.write( "				POMIARY	MILIS				 " );
		timeOutput.newLine();
		timeOutput.write( "------------------------------------------" );
		timeOutput.newLine();
		timeOutput.flush();		
		
		timeNanoOutput = new BufferedWriter(new FileWriter(timeNanoFile));	
		timeNanoOutput.write( dateFormater.format( new Date() ) );		
		timeNanoOutput.newLine();
		timeNanoOutput.write( "------------------------------------------" );
		timeNanoOutput.newLine();
		timeNanoOutput.write( "				POMIARY	NANO				 " );
		timeNanoOutput.newLine();
		timeNanoOutput.write( "------------------------------------------" );
		timeNanoOutput.newLine();
		timeNanoOutput.flush();
		
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
			connection.connect( vectorPortsOnlySerial.get( value ));
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		Queue<Integer> writeFrame = new LinkedList<Integer>();		
		writeFrame.add( 0x10 );
		writeFrame.add( 0x01 );
		writeFrame.add( 0xA0 );
		writeFrame.add( 0xE0 );
		writeFrame.add( 0x6B );
		//writeFrame.add( 0x06 ); //device info
		writeFrame.add( 0x02 ); //pomiary
		//writeFrame.add( 0x01 ); //coś
		writeFrame.add( 0x10 );
		writeFrame.add( 0x03 );
		//writeFrame.add( 0x79 ); //device info
		//writeFrame.add( 0x55 ); //device info
		writeFrame.add( 0x38 ); //pomiary
		writeFrame.add( 0x94 ); //pomiary
		//writeFrame.add( 0xC8 ); //coś
		//writeFrame.add( 0x94 ); //coś

		Queue<Integer> data = new LinkedList<Integer>();
		int  previousCharacter;
		int  curentCharacter;
		int CRCLow;
		int CRCHigh;		
		int CRC;
		
		for ( int i = 0; i < 1000; i++ )
		{			
			for( Integer value2: writeFrame )
			{
				connection.write( value2 );
			}
			nanoTime = System.nanoTime();			
			currentTimeMillis = System.currentTimeMillis();
						
	    	previousCharacter = -1;
	    	//Read first character from new frame
	    	curentCharacter = connection.read();
			nanoTime2 = System.nanoTime();
			currentTimeMillis2 = System.currentTimeMillis();	
	    	while(curentCharacter!=-1)
	    	{
	    		//loop reading until detect start of frame 16,1
	    		do
	    		{   
	    			previousCharacter = curentCharacter;  
	    			//Read next character from new frame
	    			curentCharacter = connection.read();	
	    		}
	    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
	    		
	    		//Add start of frame detect in previous loop
	    		data.add(previousCharacter);
	    		data.add(curentCharacter);
	    		
	    		do
	    		{   
	    			previousCharacter = curentCharacter;  
	    			//Read next character from new frame
	    			curentCharacter = connection.read();	
	    			//Add current character to collected frame
	    			data.add(curentCharacter);
	    		}
	    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
	    		
	    		if ( Main.isDebug() )
				{
	    			System.out.println( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );
	            	System.out.println( data );
				}    		        	
	        	
	    		//Add CRC16 to frame
				CRCLow = connection.read();
				CRCHigh = connection.read();		
				CRC = ( CRCHigh << 8 ) + CRCLow;
				
				if( ELANCRC16.checkCRC16( data, CRC ) )
				{
					count++;
					miliDiff = currentTimeMillis2 - currentTimeMillis;
					nanoDiff = nanoTime2 - nanoTime;
					if (miliAvg == 0)
					{
						miliAvg = miliMin = miliMax = miliDiff;
					}
					else
					{
						miliAvg = (miliAvg + miliDiff);
						if ( miliMin > miliDiff )
						{
							miliMin = miliDiff;
						}
						if ( miliMax < miliDiff )
						{
							miliMax = miliDiff;
						}
					}
					
					if (nanoAvg == 0)
					{
						nanoAvg = nanoMin = nanoMax = nanoDiff;
					}
					else
					{
						nanoAvg = (nanoAvg + nanoDiff);
						if ( nanoMin > nanoDiff )
						{
							nanoMin = nanoDiff;
						}
						if ( nanoMax < nanoDiff )
						{
							nanoMax = nanoDiff;
						}
					}
					
					timeOutput.write( miliDiff +"" );
					timeOutput.newLine();
					timeNanoOutput.write( nanoDiff +"" );
					timeNanoOutput.newLine();
					//System.out.println( "Czas mili: " + miliDiff );
					//System.out.println( "Czas nano: " + nanoDiff );
					data.clear();
					break;					
				}
				else
				{
					System.out.println( "CC error: " );
					data.clear();
					break;
				}
	    	}			
		}
	
		timeOutput.write( "------------------------------------------" );
		timeOutput.newLine();
		timeOutput.write( "Count: " + count );timeOutput.newLine();
		timeOutput.write( "Avg: " + miliAvg );timeOutput.newLine();
		timeOutput.write( "Avg/count: " + miliAvg/count );timeOutput.newLine();
		timeOutput.write( "Min: " + miliMin );timeOutput.newLine();
		timeOutput.write( "Max:"  + miliMax );timeOutput.newLine();
		timeOutput.write( "------------------------------------------" );
		timeOutput.flush();
		
		timeNanoOutput.write( "------------------------------------------" );
		timeNanoOutput.newLine();
		timeNanoOutput.write( "Count:: " + count );timeNanoOutput.newLine();
		timeNanoOutput.write( "Avg: " + nanoAvg );timeNanoOutput.newLine();
		timeNanoOutput.write( "Avg/count: " + nanoAvg/count );timeNanoOutput.newLine();
		timeNanoOutput.write( "Min: " + nanoMin );timeNanoOutput.newLine();
		timeNanoOutput.write( "Max:"  + nanoMax );timeNanoOutput.newLine();
		timeNanoOutput.write( "------------------------------------------" );				
		timeNanoOutput.flush();
		System.out.println( "KONIEC" );
		connection.disconnect();
	}
	catch ( IOException e1 )
	{
		e1.printStackTrace();
	}		
	
	return;
	}	
}
