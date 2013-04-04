package pl.industrum.gasanalyzer.elan.communication;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * 
 * @author duzydamian (Damian Karbowiak)
 * @see ELANConnection
 */
public class ELANCommunication {

	/**
	 * Object stores existing connection.  
	 */
	ELANConnection elanConnection;
	
	/**
	 * Crates new object to comunnicate with some device
	 */
	public ELANCommunication() {
		super();
		elanConnection = ELANConnection.getInstance();
	}
	
	/**
	 * Read one frame from network
	 */
	public String readFrame(){
		Queue<Integer> data = new LinkedList<Integer>();
//    	System.out.println("Read frame from port");
    	int  previousCharacter = -1;
    	//Read first character from new frame
    	int  courentCharacter = elanConnection.read();
    	while(courentCharacter!=-1){
    		String frame = "";
    		//Add current character to collected frame
    		frame += Integer.toHexString(courentCharacter)+" ";
    		data.add(courentCharacter);
    		do{   
    			previousCharacter = courentCharacter;  
    			//Read next character from new frame
    			courentCharacter = elanConnection.read();	
    			//Add current character to collected frame
    			frame += Integer.toHexString(courentCharacter)+" "; 
    			data.add(courentCharacter);
    		} while (!((previousCharacter==16) & (courentCharacter==3)));
    		
    		@SuppressWarnings("unused")
			int crc = ELANCRC16.countCRC16(data);
    		//Add CRC16 to frame
			frame += "  "+Integer.toHexString(elanConnection.read())+" "+Integer.toHexString(elanConnection.read())+" ";
//    		System.out.println(frame);
    		//Read first character from next frame 
    		//courentCharacter = elanConnection.read();
			return frame;
    	}
		return "";
	}
	
	/**
	 * Write one frame to network
	 */
	public void writeFrame(){
		
	}
}
