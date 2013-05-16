package pl.industrum.gasanalyzer.elan.communication;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.helpers.ELANCRC16;

/**
 * 
 * 
 * @author duzydamian (Damian Karbowiak)
 * @see ELANConnection
 */
public class ELANCommunication
{

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
    		//Add current character to collected frame
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
    		
    		System.out.println( new Date() + " : ramka z ELAN Communication" );
        	System.out.println( data );
        	
        	
    		//Add CRC16 to frame
			int CRCLow = elanConnection.read();
			int CRCHigh = elanConnection.read();		
			int CRC = ( CRCHigh << 8 ) + CRCLow;
			
			if( ELANCRC16.checkCRC16( data, CRC ) )
			{
				System.out.println( "Crc correct" );
				System.out.println( );
				return data;
			}
			else
			{
				System.out.println( "Error in crc" );
				System.out.println( );
				break;
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
