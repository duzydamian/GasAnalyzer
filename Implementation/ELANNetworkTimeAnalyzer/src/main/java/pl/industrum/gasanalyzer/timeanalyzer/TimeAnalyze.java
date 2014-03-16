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
 *10
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
		
		long nanoTimeS = 0, nanoTimeA = 0, nanoTimeR = 0, nanoDiffA = 0, nanoDiffR = 0; //start, acknowledge, response, acknowledge, response
		long currentTimeMillisS = 0, currentTimeMillisA = 0, currentTimeMillisR, miliDiffA = 0, miliDiffR = 0; //start, acknowledge, response, acknowledge, response

		double nanoAvgA = 0, miliAvgA = 0; //statistic for acknowledge		
		long nanoMinA = 0, miliMinA = 0;		
		long nanoMaxA = 0, miliMaxA = 0;
		
		double nanoAvgR = 0, miliAvgR = 0; //statistic for response		
		long nanoMinR = 0, miliMinR = 0;		
		long nanoMaxR = 0, miliMaxR = 0;
		
		int count = 0; //common count of samples 
				
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
		timeNanoOutput.write( "				POMIARY	NANOS				 " );
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
		Queue<Integer> data = new LinkedList<Integer>();
		int  previousCharacter;
		int  curentCharacter;
		int CRCLow;
		int CRCHigh;		
		int CRC;
		
		for ( int repeat = 1; repeat < 4; repeat++ )
		{	
			writeFrame.clear();
			writeFrame.add( 0x10 ); //Command ''k', 2 Read all measured values of the channel
			writeFrame.add( 0x01 );
			writeFrame.add( 0xA0 );
			writeFrame.add( 0xE0 );
			writeFrame.add( 0x6B ); //k
			writeFrame.add( 0x02 );
			writeFrame.add( 0x10 );
			writeFrame.add( 0x03 );
			writeFrame.add( 0x38 );
			writeFrame.add( 0x94 );
			
			nanoTimeS = 0; nanoTimeA = 0; nanoTimeR = 0; nanoDiffA = 0; nanoDiffR = 0; //start, acknowledge, response, acknowledge, response
			currentTimeMillisS = 0; currentTimeMillisA = 0; currentTimeMillisR = 0; miliDiffA = 0; miliDiffR = 0; //start, acknowledge, response, acknowledge, response

			nanoAvgA = 0; miliAvgA = 0; //statistic for acknowledge		
			nanoMinA = 0; miliMinA = 0;		
			nanoMaxA = 0; miliMaxA = 0;
			
			nanoAvgR = 0; miliAvgR = 0; //statistic for response		
			nanoMinR = 0; miliMinR = 0;		
			nanoMaxR = 0; miliMaxR = 0;
			
			count = 0; //common count of samples 
			
			System.out.println( repeat + " 1" );
			timeOutput.write( "Command ''k', 2 Read all measured values of the channel" );
			timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine();
			timeNanoOutput.newLine();
			timeNanoOutput.write( "Command ''k', 2 Read all measured values of the channel" );
			timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );
			timeNanoOutput.newLine();
			
			for ( int i = 0; i < 1000; i++ )
			{			
				for( Integer value2: writeFrame )
				{
					connection.write( value2 );
				}
				nanoTimeS = System.nanoTime();			
				currentTimeMillisS = System.currentTimeMillis();
							
		    	previousCharacter = -1;
		    	
		    	curentCharacter = connection.read(); //Read first character from new frame
				nanoTimeA = System.nanoTime();
				currentTimeMillisA = System.currentTimeMillis();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				nanoTimeR = System.nanoTime();
				currentTimeMillisR = System.currentTimeMillis();
		    	
				while(curentCharacter!=-1)
		    	{		    		
		    		do //loop reading until detect start of frame 16,1
		    		{   
		    			previousCharacter = curentCharacter;  
		    			curentCharacter = connection.read(); //Read next character from new frame	
		    		}
		    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
		    				    		
		    		data.add(previousCharacter); //Add start of frame detect in previous loop
		    		data.add(curentCharacter);
		    		
		    		do
		    		{   
		    			previousCharacter = curentCharacter;  		    			
		    			curentCharacter = connection.read(); //Read next character from new frame			    			
		    			data.add(curentCharacter); //Add current character to collected frame
		    		}
		    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
		    		
		    		if ( Main.isDebug() )
					{
		    			System.out.println( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );
		            	System.out.println( data );
					}    		        	
		        			    		
					CRCLow = connection.read(); //Add CRC16 to frame
					CRCHigh = connection.read();		
					CRC = ( CRCHigh << 8 ) + CRCLow;
					
					if( ELANCRC16.checkCRC16( data, CRC ) )
					{
						count++;
						miliDiffA = currentTimeMillisA - currentTimeMillisS;
						nanoDiffA = nanoTimeA - nanoTimeS;
						
						miliDiffR = currentTimeMillisR - currentTimeMillisS;
						nanoDiffR = nanoTimeR - nanoTimeS;
						
						//update statistic acknowledge
						if (miliAvgA == 0)
						{
							miliAvgA = miliMinA = miliMaxA = miliDiffA;
						}
						else
						{
							miliAvgA += miliDiffA;
							if ( miliMinA > miliDiffA )
							{
								miliMinA = miliDiffA;
							}
							if ( miliMaxA < miliDiffA )
							{
								miliMaxA = miliDiffA;
							}
						}
						
						if (nanoAvgA == 0)
						{
							nanoAvgA = nanoMinA = nanoMaxA = nanoDiffA;
						}
						else
						{
							nanoAvgA += nanoDiffA;
							if ( nanoMinA > nanoDiffA )
							{
								nanoMinA = nanoDiffA;
							}
							if ( nanoMaxA < nanoDiffA )
							{
								nanoMaxA = nanoDiffA;
							}
						}
						
						//update statistic response
						if (miliAvgR == 0)
						{
							miliAvgR = miliMinR = miliMaxR = miliDiffR;
						}
						else
						{
							miliAvgR += miliDiffR;
							if ( miliMinR > miliDiffR )
							{
								miliMinR = miliDiffR;
							}
							if ( miliMaxR < miliDiffR )
							{
								miliMaxR = miliDiffR;
							}
						}
						
						if (nanoAvgR == 0)
						{
							nanoAvgR = nanoMinR = nanoMaxR = nanoDiffR;
						}
						else
						{
							nanoAvgR += nanoDiffR;
							if ( nanoMinR > nanoDiffR )
							{
								nanoMinR = nanoDiffR;
							}
							if ( nanoMaxR < nanoDiffR )
							{
								nanoMaxR = nanoDiffR;
							}
						}
						
						timeOutput.write( miliDiffA + " " + miliDiffR );
						timeOutput.newLine();
						timeNanoOutput.write( nanoDiffA + " " + nanoDiffR );
						timeNanoOutput.newLine();
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
		
			timeOutput.write( "------------------------------------------" ); timeOutput.newLine();
			timeOutput.write( "Count: " + count ); timeOutput.newLine();
			timeOutput.write( "Avg: " + miliAvgA + " " + miliAvgR ); timeOutput.newLine();
			timeOutput.write( "Avg/count: " + miliAvgA/count + " " + miliAvgR/count ); timeOutput.newLine();
			timeOutput.write( "Min: " + miliMinA + " " +  miliMinR ); timeOutput.newLine();
			timeOutput.write( "Max:"  + miliMaxA + " " + miliMaxR ); timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine(); timeOutput.flush();
			
			timeNanoOutput.write( "------------------------------------------" ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Count:: " + count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg: " + nanoAvgA + " " + nanoAvgR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg/count: " + nanoAvgA/count + " " + nanoAvgR/count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Min: " + nanoMinA + " " + nanoMinR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Max:"  + nanoMaxA + " " + nanoMaxR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );				
			timeNanoOutput.newLine(); timeNanoOutput.flush();
			
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			nanoTimeS = 0; nanoTimeA = 0; nanoTimeR = 0; nanoDiffA = 0; nanoDiffR = 0; //start, acknowledge, response, acknowledge, response
			currentTimeMillisS = 0; currentTimeMillisA = 0; currentTimeMillisR = 0; miliDiffA = 0; miliDiffR = 0; //start, acknowledge, response, acknowledge, response

			nanoAvgA = 0; miliAvgA = 0; //statistic for acknowledge		
			nanoMinA = 0; miliMinA = 0;		
			nanoMaxA = 0; miliMaxA = 0;
			
			nanoAvgR = 0; miliAvgR = 0; //statistic for response		
			nanoMinR = 0; miliMinR = 0;		
			nanoMaxR = 0; miliMaxR = 0;
			
			count = 0; //common count of samples
			
			System.out.println( repeat + " 2" );
			timeOutput.write( "Command ''k', 2 Read all measured values of the channel with acknowledge" );
			timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine();
			timeNanoOutput.newLine();
			timeNanoOutput.write( "Command ''k', 2 Read all measured values of the channel with acknowledge" );
			timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );
			timeNanoOutput.newLine();
			
			for ( int i = 0; i < 1000; i++ )
			{			
				for( Integer value2: writeFrame )
				{
					connection.write( value2 );
				}
				nanoTimeS = System.nanoTime();			
				currentTimeMillisS = System.currentTimeMillis();
							
		    	previousCharacter = -1;
		    	
		    	curentCharacter = connection.read(); //Read first character from new frame
				nanoTimeA = System.nanoTime();
				currentTimeMillisA = System.currentTimeMillis();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				nanoTimeR = System.nanoTime();
				currentTimeMillisR = System.currentTimeMillis();
		    	
				while(curentCharacter!=-1)
		    	{		    		
		    		do //loop reading until detect start of frame 16,1
		    		{   
		    			previousCharacter = curentCharacter;  
		    			curentCharacter = connection.read(); //Read next character from new frame	
		    		}
		    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
		    				    		
		    		data.add(previousCharacter); //Add start of frame detect in previous loop
		    		data.add(curentCharacter);
		    		
		    		do
		    		{   
		    			previousCharacter = curentCharacter;  		    			
		    			curentCharacter = connection.read(); //Read next character from new frame			    			
		    			data.add(curentCharacter); //Add current character to collected frame
		    		}
		    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
		    		
		    		if ( Main.isDebug() )
					{
		    			System.out.println( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );
		            	System.out.println( data );
					}    		        	
		        			    		
					CRCLow = connection.read(); //Add CRC16 to frame
					CRCHigh = connection.read();		
					CRC = ( CRCHigh << 8 ) + CRCLow;
					
					connection.write( 0x10 );
					connection.write( 0x06 );
					
					if( ELANCRC16.checkCRC16( data, CRC ) )
					{
						count++;
						miliDiffA = currentTimeMillisA - currentTimeMillisS;
						nanoDiffA = nanoTimeA - nanoTimeS;
						
						miliDiffR = currentTimeMillisR - currentTimeMillisS;
						nanoDiffR = nanoTimeR - nanoTimeS;
						
						//update statistic acknowledge
						if (miliAvgA == 0)
						{
							miliAvgA = miliMinA = miliMaxA = miliDiffA;
						}
						else
						{
							miliAvgA += miliDiffA;
							if ( miliMinA > miliDiffA )
							{
								miliMinA = miliDiffA;
							}
							if ( miliMaxA < miliDiffA )
							{
								miliMaxA = miliDiffA;
							}
						}
						
						if (nanoAvgA == 0)
						{
							nanoAvgA = nanoMinA = nanoMaxA = nanoDiffA;
						}
						else
						{
							nanoAvgA += nanoDiffA;
							if ( nanoMinA > nanoDiffA )
							{
								nanoMinA = nanoDiffA;
							}
							if ( nanoMaxA < nanoDiffA )
							{
								nanoMaxA = nanoDiffA;
							}
						}
						
						//update statistic response
						if (miliAvgR == 0)
						{
							miliAvgR = miliMinR = miliMaxR = miliDiffR;
						}
						else
						{
							miliAvgR += miliDiffR;
							if ( miliMinR > miliDiffR )
							{
								miliMinR = miliDiffR;
							}
							if ( miliMaxR < miliDiffR )
							{
								miliMaxR = miliDiffR;
							}
						}
						
						if (nanoAvgR == 0)
						{
							nanoAvgR = nanoMinR = nanoMaxR = nanoDiffR;
						}
						else
						{
							nanoAvgR += nanoDiffR;
							if ( nanoMinR > nanoDiffR )
							{
								nanoMinR = nanoDiffR;
							}
							if ( nanoMaxR < nanoDiffR )
							{
								nanoMaxR = nanoDiffR;
							}
						}
						
						timeOutput.write( miliDiffA + " " + miliDiffR );
						timeOutput.newLine();
						timeNanoOutput.write( nanoDiffA + " " + nanoDiffR );
						timeNanoOutput.newLine();
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
				
			timeOutput.write( "------------------------------------------" ); timeOutput.newLine();
			timeOutput.write( "Count: " + count ); timeOutput.newLine();
			timeOutput.write( "Avg: " + miliAvgA + " " + miliAvgR ); timeOutput.newLine();
			timeOutput.write( "Avg/count: " + miliAvgA/count + " " + miliAvgR/count ); timeOutput.newLine();
			timeOutput.write( "Min: " + miliMinA + " " +  miliMinR ); timeOutput.newLine();
			timeOutput.write( "Max:"  + miliMaxA + " " + miliMaxR ); timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine(); timeOutput.flush();
			
			timeNanoOutput.write( "------------------------------------------" ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Count:: " + count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg: " + nanoAvgA + " " + nanoAvgR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg/count: " + nanoAvgA/count + " " + nanoAvgR/count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Min: " + nanoMinA + " " + nanoMinR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Max:"  + nanoMaxA + " " + nanoMaxR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );				
			timeNanoOutput.newLine(); timeNanoOutput.flush();
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			System.out.println( repeat + " 3" );
			writeFrame.clear();
			writeFrame.add( 0x10 ); //Command 'k', 6 Read channel version
			writeFrame.add( 0x01 );
			writeFrame.add( 0xA0 );
			writeFrame.add( 0xE0 );
			writeFrame.add( 0x6B ); //k
			writeFrame.add( 0x06 );
			writeFrame.add( 0x10 );
			writeFrame.add( 0x03 );
			writeFrame.add( 0x79 );
			writeFrame.add( 0x55 );
			
			nanoTimeS = 0; nanoTimeA = 0; nanoTimeR = 0; nanoDiffA = 0; nanoDiffR = 0; //start, acknowledge, response, acknowledge, response
			currentTimeMillisS = 0; currentTimeMillisA = 0; currentTimeMillisR = 0; miliDiffA = 0; miliDiffR = 0; //start, acknowledge, response, acknowledge, response

			nanoAvgA = 0; miliAvgA = 0; //statistic for acknowledge		
			nanoMinA = 0; miliMinA = 0;		
			nanoMaxA = 0; miliMaxA = 0;
			
			nanoAvgR = 0; miliAvgR = 0; //statistic for response		
			nanoMinR = 0; miliMinR = 0;		
			nanoMaxR = 0; miliMaxR = 0;
			
			count = 0; //common count of samples			
			
			timeOutput.write( "Command 'k', 6 Read channel version" );
			timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine();
			timeNanoOutput.newLine();
			timeNanoOutput.write( "Command 'k', 6 Read channel version" );
			timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );
			timeNanoOutput.newLine();
			
			for ( int i = 0; i < 1000; i++ )
			{			
				for( Integer value2: writeFrame )
				{
					connection.write( value2 );
				}
				nanoTimeS = System.nanoTime();			
				currentTimeMillisS = System.currentTimeMillis();
							
		    	previousCharacter = -1;
		    	
		    	curentCharacter = connection.read(); //Read first character from new frame
				nanoTimeA = System.nanoTime();
				currentTimeMillisA = System.currentTimeMillis();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				nanoTimeR = System.nanoTime();
				currentTimeMillisR = System.currentTimeMillis();
		    	
				while(curentCharacter!=-1)
		    	{		    		
		    		do //loop reading until detect start of frame 16,1
		    		{   
		    			previousCharacter = curentCharacter;  
		    			curentCharacter = connection.read(); //Read next character from new frame	
		    		}
		    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
		    				    		
		    		data.add(previousCharacter); //Add start of frame detect in previous loop
		    		data.add(curentCharacter);
		    		
		    		do
		    		{   
		    			previousCharacter = curentCharacter;  		    			
		    			curentCharacter = connection.read(); //Read next character from new frame			    			
		    			data.add(curentCharacter); //Add current character to collected frame
		    		}
		    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
		    		
		    		if ( Main.isDebug() )
					{
		    			System.out.println( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );
		            	System.out.println( data );
					}    		        	
		        			    		
					CRCLow = connection.read(); //Add CRC16 to frame
					CRCHigh = connection.read();		
					CRC = ( CRCHigh << 8 ) + CRCLow;
					
					if( ELANCRC16.checkCRC16( data, CRC ) )
					{
						count++;
						miliDiffA = currentTimeMillisA - currentTimeMillisS;
						nanoDiffA = nanoTimeA - nanoTimeS;
						
						miliDiffR = currentTimeMillisR - currentTimeMillisS;
						nanoDiffR = nanoTimeR - nanoTimeS;
						
						//update statistic acknowledge
						if (miliAvgA == 0)
						{
							miliAvgA = miliMinA = miliMaxA = miliDiffA;
						}
						else
						{
							miliAvgA += miliDiffA;
							if ( miliMinA > miliDiffA )
							{
								miliMinA = miliDiffA;
							}
							if ( miliMaxA < miliDiffA )
							{
								miliMaxA = miliDiffA;
							}
						}
						
						if (nanoAvgA == 0)
						{
							nanoAvgA = nanoMinA = nanoMaxA = nanoDiffA;
						}
						else
						{
							nanoAvgA += nanoDiffA;
							if ( nanoMinA > nanoDiffA )
							{
								nanoMinA = nanoDiffA;
							}
							if ( nanoMaxA < nanoDiffA )
							{
								nanoMaxA = nanoDiffA;
							}
						}
						
						//update statistic response
						if (miliAvgR == 0)
						{
							miliAvgR = miliMinR = miliMaxR = miliDiffR;
						}
						else
						{
							miliAvgR += miliDiffR;
							if ( miliMinR > miliDiffR )
							{
								miliMinR = miliDiffR;
							}
							if ( miliMaxR < miliDiffR )
							{
								miliMaxR = miliDiffR;
							}
						}
						
						if (nanoAvgR == 0)
						{
							nanoAvgR = nanoMinR = nanoMaxR = nanoDiffR;
						}
						else
						{
							nanoAvgR += nanoDiffR;
							if ( nanoMinR > nanoDiffR )
							{
								nanoMinR = nanoDiffR;
							}
							if ( nanoMaxR < nanoDiffR )
							{
								nanoMaxR = nanoDiffR;
							}
						}
						
						timeOutput.write( miliDiffA + " " + miliDiffR );
						timeOutput.newLine();
						timeNanoOutput.write( nanoDiffA + " " + nanoDiffR );
						timeNanoOutput.newLine();
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
		
			timeOutput.write( "------------------------------------------" ); timeOutput.newLine();
			timeOutput.write( "Count: " + count ); timeOutput.newLine();
			timeOutput.write( "Avg: " + miliAvgA + " " + miliAvgR ); timeOutput.newLine();
			timeOutput.write( "Avg/count: " + miliAvgA/count + " " + miliAvgR/count ); timeOutput.newLine();
			timeOutput.write( "Min: " + miliMinA + " " +  miliMinR ); timeOutput.newLine();
			timeOutput.write( "Max:"  + miliMaxA + " " + miliMaxR ); timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine(); timeOutput.flush();
			
			timeNanoOutput.write( "------------------------------------------" ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Count:: " + count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg: " + nanoAvgA + " " + nanoAvgR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg/count: " + nanoAvgA/count + " " + nanoAvgR/count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Min: " + nanoMinA + " " + nanoMinR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Max:"  + nanoMaxA + " " + nanoMaxR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );				
			timeNanoOutput.newLine(); timeNanoOutput.flush();			

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			System.out.println( repeat + " 4" );
			writeFrame.clear();
			writeFrame.add( 0x10 ); //Command 'k', 1 Read measured value of one component (1)
			writeFrame.add( 0x01 );
			writeFrame.add( 0xA0 );
			writeFrame.add( 0xE0 );
			writeFrame.add( 0x6B ); //k
			writeFrame.add( 0x01 );
			writeFrame.add( 0x10 );
			writeFrame.add( 0x03 );
			writeFrame.add( 0xC8 );
			writeFrame.add( 0x94 );
			
			nanoTimeS = 0; nanoTimeA = 0; nanoTimeR = 0; nanoDiffA = 0; nanoDiffR = 0; //start, acknowledge, response, acknowledge, response
			currentTimeMillisS = 0; currentTimeMillisA = 0; currentTimeMillisR = 0; miliDiffA = 0; miliDiffR = 0; //start, acknowledge, response, acknowledge, response

			nanoAvgA = 0; miliAvgA = 0; //statistic for acknowledge		
			nanoMinA = 0; miliMinA = 0;		
			nanoMaxA = 0; miliMaxA = 0;
			
			nanoAvgR = 0; miliAvgR = 0; //statistic for response		
			nanoMinR = 0; miliMinR = 0;		
			nanoMaxR = 0; miliMaxR = 0;
			
			count = 0; //common count of samples
			
			timeOutput.write( "Command 'k', 1 Read measured value of one component (1)" );
			timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine();
			timeNanoOutput.newLine();
			timeNanoOutput.write( "Command 'k', 1 Read measured value of one component (1)" );
			timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );
			timeNanoOutput.newLine();
			
			for ( int i = 0; i < 1000; i++ )
			{			
				for( Integer value2: writeFrame )
				{
					connection.write( value2 );
				}
				nanoTimeS = System.nanoTime();			
				currentTimeMillisS = System.currentTimeMillis();
							
		    	previousCharacter = -1;
		    	
		    	curentCharacter = connection.read(); //Read first character from new frame
				nanoTimeA = System.nanoTime();
				currentTimeMillisA = System.currentTimeMillis();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				nanoTimeR = System.nanoTime();
				currentTimeMillisR = System.currentTimeMillis();
		    	
				while(curentCharacter!=-1)
		    	{		    		
		    		do //loop reading until detect start of frame 16,1
		    		{   
		    			previousCharacter = curentCharacter;  
		    			curentCharacter = connection.read(); //Read next character from new frame	
		    		}
		    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
		    				    		
		    		data.add(previousCharacter); //Add start of frame detect in previous loop
		    		data.add(curentCharacter);
		    		
		    		do
		    		{   
		    			previousCharacter = curentCharacter;  		    			
		    			curentCharacter = connection.read(); //Read next character from new frame			    			
		    			data.add(curentCharacter); //Add current character to collected frame
		    		}
		    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
		    		
		    		if ( Main.isDebug() )
					{
		    			System.out.println( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );
		            	System.out.println( data );
					}    		        	
		        			    		
					CRCLow = connection.read(); //Add CRC16 to frame
					CRCHigh = connection.read();		
					CRC = ( CRCHigh << 8 ) + CRCLow;
					
					if( ELANCRC16.checkCRC16( data, CRC ) )
					{
						count++;
						miliDiffA = currentTimeMillisA - currentTimeMillisS;
						nanoDiffA = nanoTimeA - nanoTimeS;
						
						miliDiffR = currentTimeMillisR - currentTimeMillisS;
						nanoDiffR = nanoTimeR - nanoTimeS;
						
						//update statistic acknowledge
						if (miliAvgA == 0)
						{
							miliAvgA = miliMinA = miliMaxA = miliDiffA;
						}
						else
						{
							miliAvgA += miliDiffA;
							if ( miliMinA > miliDiffA )
							{
								miliMinA = miliDiffA;
							}
							if ( miliMaxA < miliDiffA )
							{
								miliMaxA = miliDiffA;
							}
						}
						
						if (nanoAvgA == 0)
						{
							nanoAvgA = nanoMinA = nanoMaxA = nanoDiffA;
						}
						else
						{
							nanoAvgA += nanoDiffA;
							if ( nanoMinA > nanoDiffA )
							{
								nanoMinA = nanoDiffA;
							}
							if ( nanoMaxA < nanoDiffA )
							{
								nanoMaxA = nanoDiffA;
							}
						}
						
						//update statistic response
						if (miliAvgR == 0)
						{
							miliAvgR = miliMinR = miliMaxR = miliDiffR;
						}
						else
						{
							miliAvgR += miliDiffR;
							if ( miliMinR > miliDiffR )
							{
								miliMinR = miliDiffR;
							}
							if ( miliMaxR < miliDiffR )
							{
								miliMaxR = miliDiffR;
							}
						}
						
						if (nanoAvgR == 0)
						{
							nanoAvgR = nanoMinR = nanoMaxR = nanoDiffR;
						}
						else
						{
							nanoAvgR += nanoDiffR;
							if ( nanoMinR > nanoDiffR )
							{
								nanoMinR = nanoDiffR;
							}
							if ( nanoMaxR < nanoDiffR )
							{
								nanoMaxR = nanoDiffR;
							}
						}
						
						timeOutput.write( miliDiffA + " " + miliDiffR );
						timeOutput.newLine();
						timeNanoOutput.write( nanoDiffA + " " + nanoDiffR );
						timeNanoOutput.newLine();
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
		
			timeOutput.write( "------------------------------------------" ); timeOutput.newLine();
			timeOutput.write( "Count: " + count ); timeOutput.newLine();
			timeOutput.write( "Avg: " + miliAvgA + " " + miliAvgR ); timeOutput.newLine();
			timeOutput.write( "Avg/count: " + miliAvgA/count + " " + miliAvgR/count ); timeOutput.newLine();
			timeOutput.write( "Min: " + miliMinA + " " +  miliMinR ); timeOutput.newLine();
			timeOutput.write( "Max:"  + miliMaxA + " " + miliMaxR ); timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine(); timeOutput.flush();
			
			timeNanoOutput.write( "------------------------------------------" ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Count:: " + count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg: " + nanoAvgA + " " + nanoAvgR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg/count: " + nanoAvgA/count + " " + nanoAvgR/count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Min: " + nanoMinA + " " + nanoMinR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Max:"  + nanoMaxA + " " + nanoMaxR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );				
			timeNanoOutput.newLine(); timeNanoOutput.flush();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			System.out.println( repeat + " 5" );
			writeFrame.clear();
			writeFrame.add( 0x10 ); //Command 'k', 1 Read measured value of one component (2)
			writeFrame.add( 0x01 );
			writeFrame.add( 0xA1 );
			writeFrame.add( 0xE0 );
			writeFrame.add( 0x6B ); //k
			writeFrame.add( 0x01 );
			writeFrame.add( 0x10 );
			writeFrame.add( 0x03 );
			writeFrame.add( 0xC9 );
			writeFrame.add( 0x45 );
			
			nanoTimeS = 0; nanoTimeA = 0; nanoTimeR = 0; nanoDiffA = 0; nanoDiffR = 0; //start, acknowledge, response, acknowledge, response
			currentTimeMillisS = 0; currentTimeMillisA = 0; currentTimeMillisR = 0; miliDiffA = 0; miliDiffR = 0; //start, acknowledge, response, acknowledge, response

			nanoAvgA = 0; miliAvgA = 0; //statistic for acknowledge		
			nanoMinA = 0; miliMinA = 0;		
			nanoMaxA = 0; miliMaxA = 0;
			
			nanoAvgR = 0; miliAvgR = 0; //statistic for response		
			nanoMinR = 0; miliMinR = 0;		
			nanoMaxR = 0; miliMaxR = 0;
			
			count = 0; //common count of samples
			
			timeOutput.write( "Command 'k', 1 Read measured value of one component (2)" );
			timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine();
			timeNanoOutput.newLine();
			timeNanoOutput.write( "Command 'k', 1 Read measured value of one component (2)" );
			timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );
			timeNanoOutput.newLine();
			
			for ( int i = 0; i < 1000; i++ )
			{			
				for( Integer value2: writeFrame )
				{
					connection.write( value2 );
				}
				nanoTimeS = System.nanoTime();			
				currentTimeMillisS = System.currentTimeMillis();
							
		    	previousCharacter = -1;
		    	
		    	curentCharacter = connection.read(); //Read first character from new frame
				nanoTimeA = System.nanoTime();
				currentTimeMillisA = System.currentTimeMillis();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				nanoTimeR = System.nanoTime();
				currentTimeMillisR = System.currentTimeMillis();
		    	
				while(curentCharacter!=-1)
		    	{		    		
		    		do //loop reading until detect start of frame 16,1
		    		{   
		    			previousCharacter = curentCharacter;  
		    			curentCharacter = connection.read(); //Read next character from new frame	
		    		}
		    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
		    				    		
		    		data.add(previousCharacter); //Add start of frame detect in previous loop
		    		data.add(curentCharacter);
		    		
		    		do
		    		{   
		    			previousCharacter = curentCharacter;  		    			
		    			curentCharacter = connection.read(); //Read next character from new frame			    			
		    			data.add(curentCharacter); //Add current character to collected frame
		    		}
		    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
		    		
		    		if ( Main.isDebug() )
					{
		    			System.out.println( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );
		            	System.out.println( data );
					}    		        	
		        			    		
					CRCLow = connection.read(); //Add CRC16 to frame
					CRCHigh = connection.read();		
					CRC = ( CRCHigh << 8 ) + CRCLow;
					
					if( ELANCRC16.checkCRC16( data, CRC ) )
					{
						count++;
						miliDiffA = currentTimeMillisA - currentTimeMillisS;
						nanoDiffA = nanoTimeA - nanoTimeS;
						
						miliDiffR = currentTimeMillisR - currentTimeMillisS;
						nanoDiffR = nanoTimeR - nanoTimeS;
						
						//update statistic acknowledge
						if (miliAvgA == 0)
						{
							miliAvgA = miliMinA = miliMaxA = miliDiffA;
						}
						else
						{
							miliAvgA += miliDiffA;
							if ( miliMinA > miliDiffA )
							{
								miliMinA = miliDiffA;
							}
							if ( miliMaxA < miliDiffA )
							{
								miliMaxA = miliDiffA;
							}
						}
						
						if (nanoAvgA == 0)
						{
							nanoAvgA = nanoMinA = nanoMaxA = nanoDiffA;
						}
						else
						{
							nanoAvgA += nanoDiffA;
							if ( nanoMinA > nanoDiffA )
							{
								nanoMinA = nanoDiffA;
							}
							if ( nanoMaxA < nanoDiffA )
							{
								nanoMaxA = nanoDiffA;
							}
						}
						
						//update statistic response
						if (miliAvgR == 0)
						{
							miliAvgR = miliMinR = miliMaxR = miliDiffR;
						}
						else
						{
							miliAvgR += miliDiffR;
							if ( miliMinR > miliDiffR )
							{
								miliMinR = miliDiffR;
							}
							if ( miliMaxR < miliDiffR )
							{
								miliMaxR = miliDiffR;
							}
						}
						
						if (nanoAvgR == 0)
						{
							nanoAvgR = nanoMinR = nanoMaxR = nanoDiffR;
						}
						else
						{
							nanoAvgR += nanoDiffR;
							if ( nanoMinR > nanoDiffR )
							{
								nanoMinR = nanoDiffR;
							}
							if ( nanoMaxR < nanoDiffR )
							{
								nanoMaxR = nanoDiffR;
							}
						}
						
						timeOutput.write( miliDiffA + " " + miliDiffR );
						timeOutput.newLine();
						timeNanoOutput.write( nanoDiffA + " " + nanoDiffR );
						timeNanoOutput.newLine();
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
		
			timeOutput.write( "------------------------------------------" ); timeOutput.newLine();
			timeOutput.write( "Count: " + count ); timeOutput.newLine();
			timeOutput.write( "Avg: " + miliAvgA + " " + miliAvgR ); timeOutput.newLine();
			timeOutput.write( "Avg/count: " + miliAvgA/count + " " + miliAvgR/count ); timeOutput.newLine();
			timeOutput.write( "Min: " + miliMinA + " " +  miliMinR ); timeOutput.newLine();
			timeOutput.write( "Max:"  + miliMaxA + " " + miliMaxR ); timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine(); timeOutput.flush();
			
			timeNanoOutput.write( "------------------------------------------" ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Count:: " + count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg: " + nanoAvgA + " " + nanoAvgR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg/count: " + nanoAvgA/count + " " + nanoAvgR/count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Min: " + nanoMinA + " " + nanoMinR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Max:"  + nanoMaxA + " " + nanoMaxR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );				
			timeNanoOutput.newLine(); timeNanoOutput.flush();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			System.out.println( repeat + " 6" );
			writeFrame.clear();
			writeFrame.add( 0x10 ); //Command 'k', 1 Read measured value of one component (8)
			writeFrame.add( 0x01 );
			writeFrame.add( 0xA7 );
			writeFrame.add( 0xE0 );
			writeFrame.add( 0x6B ); //k
			writeFrame.add( 0x01 );
			writeFrame.add( 0x10 );
			writeFrame.add( 0x03 );
			writeFrame.add( 0xC9 );
			writeFrame.add( 0x23 );
			
			nanoTimeS = 0; nanoTimeA = 0; nanoTimeR = 0; nanoDiffA = 0; nanoDiffR = 0; //start, acknowledge, response, acknowledge, response
			currentTimeMillisS = 0; currentTimeMillisA = 0; currentTimeMillisR = 0; miliDiffA = 0; miliDiffR = 0; //start, acknowledge, response, acknowledge, response

			nanoAvgA = 0; miliAvgA = 0; //statistic for acknowledge		
			nanoMinA = 0; miliMinA = 0;		
			nanoMaxA = 0; miliMaxA = 0;
			
			nanoAvgR = 0; miliAvgR = 0; //statistic for response		
			nanoMinR = 0; miliMinR = 0;		
			nanoMaxR = 0; miliMaxR = 0;
			
			count = 0; //common count of samples
			
			timeOutput.write( "Command 'k', 1 Read measured value of one component (8)" );
			timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine();
			timeNanoOutput.newLine();
			timeNanoOutput.write( "Command 'k', 1 Read measured value of one component (8)" );
			timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );
			timeNanoOutput.newLine();
			
			for ( int i = 0; i < 1000; i++ )
			{			
				for( Integer value2: writeFrame )
				{
					connection.write( value2 );
				}
				nanoTimeS = System.nanoTime();			
				currentTimeMillisS = System.currentTimeMillis();
							
		    	previousCharacter = -1;
		    	
		    	curentCharacter = connection.read(); //Read first character from new frame
				nanoTimeA = System.nanoTime();
				currentTimeMillisA = System.currentTimeMillis();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				nanoTimeR = System.nanoTime();
				currentTimeMillisR = System.currentTimeMillis();
		    	
				while(curentCharacter!=-1)
		    	{		    		
		    		do //loop reading until detect start of frame 16,1
		    		{   
		    			previousCharacter = curentCharacter;  
		    			curentCharacter = connection.read(); //Read next character from new frame	
		    		}
		    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
		    				    		
		    		data.add(previousCharacter); //Add start of frame detect in previous loop
		    		data.add(curentCharacter);
		    		
		    		do
		    		{   
		    			previousCharacter = curentCharacter;  		    			
		    			curentCharacter = connection.read(); //Read next character from new frame			    			
		    			data.add(curentCharacter); //Add current character to collected frame
		    		}
		    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
		    		
		    		if ( Main.isDebug() )
					{
		    			System.out.println( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );
		            	System.out.println( data );
					}    		        	
		        			    		
					CRCLow = connection.read(); //Add CRC16 to frame
					CRCHigh = connection.read();		
					CRC = ( CRCHigh << 8 ) + CRCLow;
					
					if( ELANCRC16.checkCRC16( data, CRC ) )
					{
						count++;
						miliDiffA = currentTimeMillisA - currentTimeMillisS;
						nanoDiffA = nanoTimeA - nanoTimeS;
						
						miliDiffR = currentTimeMillisR - currentTimeMillisS;
						nanoDiffR = nanoTimeR - nanoTimeS;
						
						//update statistic acknowledge
						if (miliAvgA == 0)
						{
							miliAvgA = miliMinA = miliMaxA = miliDiffA;
						}
						else
						{
							miliAvgA += miliDiffA;
							if ( miliMinA > miliDiffA )
							{
								miliMinA = miliDiffA;
							}
							if ( miliMaxA < miliDiffA )
							{
								miliMaxA = miliDiffA;
							}
						}
						
						if (nanoAvgA == 0)
						{
							nanoAvgA = nanoMinA = nanoMaxA = nanoDiffA;
						}
						else
						{
							nanoAvgA += nanoDiffA;
							if ( nanoMinA > nanoDiffA )
							{
								nanoMinA = nanoDiffA;
							}
							if ( nanoMaxA < nanoDiffA )
							{
								nanoMaxA = nanoDiffA;
							}
						}
						
						//update statistic response
						if (miliAvgR == 0)
						{
							miliAvgR = miliMinR = miliMaxR = miliDiffR;
						}
						else
						{
							miliAvgR += miliDiffR;
							if ( miliMinR > miliDiffR )
							{
								miliMinR = miliDiffR;
							}
							if ( miliMaxR < miliDiffR )
							{
								miliMaxR = miliDiffR;
							}
						}
						
						if (nanoAvgR == 0)
						{
							nanoAvgR = nanoMinR = nanoMaxR = nanoDiffR;
						}
						else
						{
							nanoAvgR += nanoDiffR;
							if ( nanoMinR > nanoDiffR )
							{
								nanoMinR = nanoDiffR;
							}
							if ( nanoMaxR < nanoDiffR )
							{
								nanoMaxR = nanoDiffR;
							}
						}
						
						timeOutput.write( miliDiffA + " " + miliDiffR );
						timeOutput.newLine();
						timeNanoOutput.write( nanoDiffA + " " + nanoDiffR );
						timeNanoOutput.newLine();
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
		
			timeOutput.write( "------------------------------------------" ); timeOutput.newLine();
			timeOutput.write( "Count: " + count ); timeOutput.newLine();
			timeOutput.write( "Avg: " + miliAvgA + " " + miliAvgR ); timeOutput.newLine();
			timeOutput.write( "Avg/count: " + miliAvgA/count + " " + miliAvgR/count ); timeOutput.newLine();
			timeOutput.write( "Min: " + miliMinA + " " +  miliMinR ); timeOutput.newLine();
			timeOutput.write( "Max:"  + miliMaxA + " " + miliMaxR ); timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine(); timeOutput.flush();
			
			timeNanoOutput.write( "------------------------------------------" ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Count:: " + count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg: " + nanoAvgA + " " + nanoAvgR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg/count: " + nanoAvgA/count + " " + nanoAvgR/count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Min: " + nanoMinA + " " + nanoMinR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Max:"  + nanoMaxA + " " + nanoMaxR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );				
			timeNanoOutput.newLine(); timeNanoOutput.flush();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			System.out.println( repeat + " 7" );
			writeFrame.clear();
			writeFrame.add( 0x10 ); //Command 'k', 1 Read measured value of one component (11)
			writeFrame.add( 0x01 );
			writeFrame.add( 0xAA );
			writeFrame.add( 0xE0 );
			writeFrame.add( 0x6B ); //k
			writeFrame.add( 0x01 );
			writeFrame.add( 0x10 );
			writeFrame.add( 0x03 );
			writeFrame.add( 0xC8 );
			writeFrame.add( 0x3E );
			
			nanoTimeS = 0; nanoTimeA = 0; nanoTimeR = 0; nanoDiffA = 0; nanoDiffR = 0; //start, acknowledge, response, acknowledge, response
			currentTimeMillisS = 0; currentTimeMillisA = 0; currentTimeMillisR = 0; miliDiffA = 0; miliDiffR = 0; //start, acknowledge, response, acknowledge, response

			nanoAvgA = 0; miliAvgA = 0; //statistic for acknowledge		
			nanoMinA = 0; miliMinA = 0;		
			nanoMaxA = 0; miliMaxA = 0;
			
			nanoAvgR = 0; miliAvgR = 0; //statistic for response		
			nanoMinR = 0; miliMinR = 0;		
			nanoMaxR = 0; miliMaxR = 0;
			
			count = 0; //common count of samples
			
			timeOutput.write( "Command 'k', 1 Read measured value of one component (11)" );
			timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine();
			timeNanoOutput.newLine();
			timeNanoOutput.write( "Command 'k', 1 Read measured value of one component (11)" );
			timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );
			timeNanoOutput.newLine();
			
			for ( int i = 0; i < 1000; i++ )
			{			
				for( Integer value2: writeFrame )
				{
					connection.write( value2 );
				}
				nanoTimeS = System.nanoTime();			
				currentTimeMillisS = System.currentTimeMillis();
							
		    	previousCharacter = -1;
		    	
		    	curentCharacter = connection.read(); //Read first character from new frame
				nanoTimeA = System.nanoTime();
				currentTimeMillisA = System.currentTimeMillis();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				nanoTimeR = System.nanoTime();
				currentTimeMillisR = System.currentTimeMillis();
		    	
				while(curentCharacter!=-1)
		    	{		    		
		    		do //loop reading until detect start of frame 16,1
		    		{   
		    			previousCharacter = curentCharacter;  
		    			curentCharacter = connection.read(); //Read next character from new frame	
		    		}
		    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
		    				    		
		    		data.add(previousCharacter); //Add start of frame detect in previous loop
		    		data.add(curentCharacter);
		    		
		    		do
		    		{   
		    			previousCharacter = curentCharacter;  		    			
		    			curentCharacter = connection.read(); //Read next character from new frame			    			
		    			data.add(curentCharacter); //Add current character to collected frame
		    		}
		    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
		    		
		    		if ( Main.isDebug() )
					{
		    			System.out.println( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );
		            	System.out.println( data );
					}    		        	
		        			    		
					CRCLow = connection.read(); //Add CRC16 to frame
					CRCHigh = connection.read();		
					CRC = ( CRCHigh << 8 ) + CRCLow;
					
					if( ELANCRC16.checkCRC16( data, CRC ) )
					{
						count++;
						miliDiffA = currentTimeMillisA - currentTimeMillisS;
						nanoDiffA = nanoTimeA - nanoTimeS;
						
						miliDiffR = currentTimeMillisR - currentTimeMillisS;
						nanoDiffR = nanoTimeR - nanoTimeS;
						
						//update statistic acknowledge
						if (miliAvgA == 0)
						{
							miliAvgA = miliMinA = miliMaxA = miliDiffA;
						}
						else
						{
							miliAvgA += miliDiffA;
							if ( miliMinA > miliDiffA )
							{
								miliMinA = miliDiffA;
							}
							if ( miliMaxA < miliDiffA )
							{
								miliMaxA = miliDiffA;
							}
						}
						
						if (nanoAvgA == 0)
						{
							nanoAvgA = nanoMinA = nanoMaxA = nanoDiffA;
						}
						else
						{
							nanoAvgA += nanoDiffA;
							if ( nanoMinA > nanoDiffA )
							{
								nanoMinA = nanoDiffA;
							}
							if ( nanoMaxA < nanoDiffA )
							{
								nanoMaxA = nanoDiffA;
							}
						}
						
						//update statistic response
						if (miliAvgR == 0)
						{
							miliAvgR = miliMinR = miliMaxR = miliDiffR;
						}
						else
						{
							miliAvgR += miliDiffR;
							if ( miliMinR > miliDiffR )
							{
								miliMinR = miliDiffR;
							}
							if ( miliMaxR < miliDiffR )
							{
								miliMaxR = miliDiffR;
							}
						}
						
						if (nanoAvgR == 0)
						{
							nanoAvgR = nanoMinR = nanoMaxR = nanoDiffR;
						}
						else
						{
							nanoAvgR += nanoDiffR;
							if ( nanoMinR > nanoDiffR )
							{
								nanoMinR = nanoDiffR;
							}
							if ( nanoMaxR < nanoDiffR )
							{
								nanoMaxR = nanoDiffR;
							}
						}
						
						timeOutput.write( miliDiffA + " " + miliDiffR );
						timeOutput.newLine();
						timeNanoOutput.write( nanoDiffA + " " + nanoDiffR );
						timeNanoOutput.newLine();
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
		
			timeOutput.write( "------------------------------------------" ); timeOutput.newLine();
			timeOutput.write( "Count: " + count ); timeOutput.newLine();
			timeOutput.write( "Avg: " + miliAvgA + " " + miliAvgR ); timeOutput.newLine();
			timeOutput.write( "Avg/count: " + miliAvgA/count + " " + miliAvgR/count ); timeOutput.newLine();
			timeOutput.write( "Min: " + miliMinA + " " +  miliMinR ); timeOutput.newLine();
			timeOutput.write( "Max:"  + miliMaxA + " " + miliMaxR ); timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine(); timeOutput.flush();
			
			timeNanoOutput.write( "------------------------------------------" ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Count:: " + count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg: " + nanoAvgA + " " + nanoAvgR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg/count: " + nanoAvgA/count + " " + nanoAvgR/count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Min: " + nanoMinA + " " + nanoMinR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Max:"  + nanoMaxA + " " + nanoMaxR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );				
			timeNanoOutput.newLine(); timeNanoOutput.flush();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			System.out.println( repeat + " 8" );
			writeFrame.clear();
			writeFrame.add( 0x10 ); //Command 'w', 20 LCD contrast
			writeFrame.add( 0x01 );
			writeFrame.add( 0xA0 );
			writeFrame.add( 0xE0 );
			writeFrame.add( 0x77 ); //'w'
			writeFrame.add( 0x14 ); //
			writeFrame.add( 0x10 );
			writeFrame.add( 0x03 );
			writeFrame.add( 0xDE );
			writeFrame.add( 0xC0 );
			
			nanoTimeS = 0; nanoTimeA = 0; nanoTimeR = 0; nanoDiffA = 0; nanoDiffR = 0; //start, acknowledge, response, acknowledge, response
			currentTimeMillisS = 0; currentTimeMillisA = 0; currentTimeMillisR = 0; miliDiffA = 0; miliDiffR = 0; //start, acknowledge, response, acknowledge, response

			nanoAvgA = 0; miliAvgA = 0; //statistic for acknowledge		
			nanoMinA = 0; miliMinA = 0;		
			nanoMaxA = 0; miliMaxA = 0;
			
			nanoAvgR = 0; miliAvgR = 0; //statistic for response		
			nanoMinR = 0; miliMinR = 0;		
			nanoMaxR = 0; miliMaxR = 0;
			
			count = 0; //common count of samples
			
			timeOutput.write( "Command 'w', 20 LCD contrast" );
			timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine();
			timeNanoOutput.newLine();
			timeNanoOutput.write( "Command 'w', 20 LCD contrast" );
			timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );
			timeNanoOutput.newLine();
			
			for ( int i = 0; i < 1000; i++ )
			{			
				for( Integer value2: writeFrame )
				{
					connection.write( value2 );
				}
				nanoTimeS = System.nanoTime();			
				currentTimeMillisS = System.currentTimeMillis();
							
		    	previousCharacter = -1;
		    	
		    	curentCharacter = connection.read(); //Read first character from new frame
				nanoTimeA = System.nanoTime();
				currentTimeMillisA = System.currentTimeMillis();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				
				previousCharacter = curentCharacter;  
				curentCharacter = connection.read();
				nanoTimeR = System.nanoTime();
				currentTimeMillisR = System.currentTimeMillis();
		    	
				while(curentCharacter!=-1)
		    	{		    		
		    		do //loop reading until detect start of frame 16,1
		    		{   
		    			previousCharacter = curentCharacter;  
		    			curentCharacter = connection.read(); //Read next character from new frame	
		    		}
		    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
		    				    		
		    		data.add(previousCharacter); //Add start of frame detect in previous loop
		    		data.add(curentCharacter);
		    		
		    		do
		    		{   
		    			previousCharacter = curentCharacter;  		    			
		    			curentCharacter = connection.read(); //Read next character from new frame			    			
		    			data.add(curentCharacter); //Add current character to collected frame
		    		}
		    		while ( !( (previousCharacter==16) & (curentCharacter==3) ) );
		    		
		    		if ( Main.isDebug() )
					{
		    			System.out.println( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );
		            	System.out.println( data );
					}    		        	
		        			    		
					CRCLow = connection.read(); //Add CRC16 to frame
					CRCHigh = connection.read();		
					CRC = ( CRCHigh << 8 ) + CRCLow;
					
					if( ELANCRC16.checkCRC16( data, CRC ) )
					{
						count++;
						miliDiffA = currentTimeMillisA - currentTimeMillisS;
						nanoDiffA = nanoTimeA - nanoTimeS;
						
						miliDiffR = currentTimeMillisR - currentTimeMillisS;
						nanoDiffR = nanoTimeR - nanoTimeS;
						
						//update statistic acknowledge
						if (miliAvgA == 0)
						{
							miliAvgA = miliMinA = miliMaxA = miliDiffA;
						}
						else
						{
							miliAvgA += miliDiffA;
							if ( miliMinA > miliDiffA )
							{
								miliMinA = miliDiffA;
							}
							if ( miliMaxA < miliDiffA )
							{
								miliMaxA = miliDiffA;
							}
						}
						
						if (nanoAvgA == 0)
						{
							nanoAvgA = nanoMinA = nanoMaxA = nanoDiffA;
						}
						else
						{
							nanoAvgA += nanoDiffA;
							if ( nanoMinA > nanoDiffA )
							{
								nanoMinA = nanoDiffA;
							}
							if ( nanoMaxA < nanoDiffA )
							{
								nanoMaxA = nanoDiffA;
							}
						}
						
						//update statistic response
						if (miliAvgR == 0)
						{
							miliAvgR = miliMinR = miliMaxR = miliDiffR;
						}
						else
						{
							miliAvgR += miliDiffR;
							if ( miliMinR > miliDiffR )
							{
								miliMinR = miliDiffR;
							}
							if ( miliMaxR < miliDiffR )
							{
								miliMaxR = miliDiffR;
							}
						}
						
						if (nanoAvgR == 0)
						{
							nanoAvgR = nanoMinR = nanoMaxR = nanoDiffR;
						}
						else
						{
							nanoAvgR += nanoDiffR;
							if ( nanoMinR > nanoDiffR )
							{
								nanoMinR = nanoDiffR;
							}
							if ( nanoMaxR < nanoDiffR )
							{
								nanoMaxR = nanoDiffR;
							}
						}
						
						timeOutput.write( miliDiffA + " " + miliDiffR );
						timeOutput.newLine();
						timeNanoOutput.write( nanoDiffA + " " + nanoDiffR );
						timeNanoOutput.newLine();
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
		
			timeOutput.write( "------------------------------------------" ); timeOutput.newLine();
			timeOutput.write( "Count: " + count ); timeOutput.newLine();
			timeOutput.write( "Avg: " + miliAvgA + " " + miliAvgR ); timeOutput.newLine();
			timeOutput.write( "Avg/count: " + miliAvgA/count + " " + miliAvgR/count ); timeOutput.newLine();
			timeOutput.write( "Min: " + miliMinA + " " +  miliMinR ); timeOutput.newLine();
			timeOutput.write( "Max:"  + miliMaxA + " " + miliMaxR ); timeOutput.newLine();
			timeOutput.write( "------------------------------------------" );
			timeOutput.newLine(); timeOutput.flush();
			
			timeNanoOutput.write( "------------------------------------------" ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Count:: " + count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg: " + nanoAvgA + " " + nanoAvgR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Avg/count: " + nanoAvgA/count + " " + nanoAvgR/count ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Min: " + nanoMinA + " " + nanoMinR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "Max:"  + nanoMaxA + " " + nanoMaxR ); timeNanoOutput.newLine();
			timeNanoOutput.write( "------------------------------------------" );				
			timeNanoOutput.newLine(); timeNanoOutput.flush();
		}
		
		timeNanoOutput.write( dateFormater.format( new Date() ) );
		timeNanoOutput.newLine(); timeNanoOutput.flush();
		timeOutput.write( dateFormater.format( new Date() ) );
		timeOutput.newLine(); timeOutput.flush();
		
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
