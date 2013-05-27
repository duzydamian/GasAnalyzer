package pl.industrum.gasanalyzer.elan.communication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.helpers.ELANCRC16;
import pl.industrum.gasanalyzer.elan.using.Main;

/**
 * 
 * 
 * @author duzydamian (Damian Karbowiak)
 * @see ELANConnection
 */
public class ELANCommunication
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "HH:mm:ss:SS dd/MM/yyyy", Locale.getDefault() );
	
	/**
	 * Object stores existing connection.  
	 */
	ELANConnection elanConnection;
	
	/**
	 * Crates new object to communicate with some device
	 */
	public ELANCommunication( ELANConnection elanConnection )
	{
		super();
		this.elanConnection = elanConnection;
	}
	
	/**
	 * Read one frame from network
	 */
	public Queue<Integer> readFrame()
	{//FIXME analyze read data with interrupt not in loop
		Queue<Integer> data = new LinkedList<Integer>();
    	int  previousCharacter = -1;
    	//Read first character from new frame
    	int  curentCharacter = elanConnection.read();
    	while(curentCharacter!=-1)
    	{
    		//loop reading until detect start of frame 16,1
    		do
    		{   
    			previousCharacter = curentCharacter;  
    			//Read next character from new frame
    			curentCharacter = elanConnection.read();	
    		}
    		while ( !( (previousCharacter == 16) & (curentCharacter == 1) ) );
    		
    		//Add start of frame detect in previous loop
    		data.add(previousCharacter);
    		data.add(curentCharacter);
    		
    		do
    		{   
    			previousCharacter = curentCharacter;  
    			//Read next character from new frame
    			curentCharacter = elanConnection.read();	
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
			int CRCLow = elanConnection.read();
			int CRCHigh = elanConnection.read();		
			int CRC = ( CRCHigh << 8 ) + CRCLow;
			
			if( ELANCRC16.checkCRC16( data, CRC ) )
			{
				if ( Main.isDebug() )
				{
					System.out.println( "Crc correct" );
					System.out.println( );
				}
				return data;
			}
			else
			{
				if ( Main.isDebug() )
				{
					System.out.println( "Error in crc" );
					System.out.println( );
				}
				return null;		
			}
    	}
		return null;
	}
	
	/**
	 * Write one frame to network
	 */
	public void writeFrame()
	{
		
	}
}
