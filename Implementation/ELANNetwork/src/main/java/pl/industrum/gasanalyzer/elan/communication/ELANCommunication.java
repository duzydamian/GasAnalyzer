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
	{
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
    		
    		System.out.println( new Date() );
        	System.out.println( data );
        	System.out.println( );
        	
    		//Add CRC16 to frame
			int CRCLow = elanConnection.read();
			int CRCHigh = elanConnection.read();		
			int CRC = ( CRCHigh << 8 ) + CRCLow;
			
			if( ELANCRC16.checkCRC16( data, CRC ) )
			{
				return data;
			}
			else
			{
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
