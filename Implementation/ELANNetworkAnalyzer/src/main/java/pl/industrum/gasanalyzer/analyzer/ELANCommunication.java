package pl.industrum.gasanalyzer.analyzer;

import java.io.IOException;
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
	//private static SimpleDateFormat dateFormater = new SimpleDateFormat( "HH:mm:ss:SS dd/MM/yyyy", Locale.getDefault() );
	
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
	try
	{
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
    		    			
//				CollectData.frameOutput.write( dateFormater.format( new Date() ) + " : ramka z ELAN Communication" );				
    			CollectData.frameOutput.write( data.toString() );   
    			CollectData.frameOutput.newLine();
        	
    		//Add CRC16 to frame
			int CRCLow = elanConnection.read();
			int CRCHigh = elanConnection.read();		
			int CRC = ( CRCHigh << 8 ) + CRCLow;
			
			if( ELANCRC16.checkCRC16( data, CRC ) )
			{
					CollectData.frameOutput.write( "Crc correct" + CRCLow + " " + CRCHigh + " = " + CRC );
					CollectData.frameOutput.newLine();
					return data;
			}
			else
			{
					CollectData.frameOutput.write( "Error in crc" + CRCLow + " " + CRCHigh + " = " + CRC );
					CollectData.frameOutput.newLine();
					return null;		
			}
    	}
		return null;
	}
	catch ( IOException e )
	{
		e.printStackTrace();
		return null;
	}
	}
	
	/**
	 * Write one frame to network
	 */
	public void writeFrame(Queue<Integer> frame)
	{
		for( Integer value: frame )
		{
			elanConnection.write( value );
		}
	}
}
