package pl.industrum.gasanalyzer.elan.communication;

import java.util.LinkedList;
import java.util.Queue;

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
	public ELANCommunication()
	{
		super();
		elanConnection = ELANConnection.getInstance();
	}
	
	/**
	 * Read one frame from network
	 */
	public Queue<Integer> readFrame()
	{
		Queue<Integer> data = new LinkedList<Integer>();
    	int  previousCharacter = -1;
    	//Read first character from new frame
    	int  courentCharacter = elanConnection.read();
    	while(courentCharacter!=-1)
    	{
    		//Add current character to collected frame
    		data.add(courentCharacter);
    		do
    		{   
    			previousCharacter = courentCharacter;  
    			//Read next character from new frame
    			courentCharacter = elanConnection.read();	
    			//Add current character to collected frame
    			data.add(courentCharacter);
    		}
    		while ( !( (previousCharacter==16) & (courentCharacter==3) ) );
    		
    		//Add CRC16 to frame
			int CRCLow = elanConnection.read();
			int CRCHigh = elanConnection.read();		
			int CRC = ( CRCHigh << 8 ) + CRCLow;
			
			if( ELANCRC16.checkCRC16( data, CRC ) )
			{
				return data;
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
