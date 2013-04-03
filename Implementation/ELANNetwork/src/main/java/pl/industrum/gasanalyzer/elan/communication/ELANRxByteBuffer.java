package pl.industrum.gasanalyzer.elan.communication;

import java.io.BufferedReader;
import java.util.Observable;
import java.util.Queue;
import java.util.LinkedList;

public class ELANRxByteBuffer extends Observable implements Runnable
{
	private BufferedReader is;
	private Queue<Integer> dataBuffer;
	
	public ELANRxByteBuffer( BufferedReader is )
	{
		this.is = is;
		dataBuffer = new LinkedList<Integer>();
	}
	
	public void run()
	{
		while( !Thread.currentThread().isInterrupted() )
		{
			try
			{
				int previousCharacter = -1;
				//Read first character from new frame
				int currentCharacter = is.read();   
				
	        	while( currentCharacter != -1 )
	        	{
	        		dataBuffer.add( currentCharacter );
	        		String frame = "";
	        		//Add current character to collected frame
	        		frame += Integer.toHexString(currentCharacter)+"H ";
	        		do{   
	        			previousCharacter = currentCharacter;  
	        			//Read next character from new frame
	        			currentCharacter = is.read();	
	        			//Add current character to collected frame
	        			frame += Integer.toHexString(currentCharacter)+"H "; 
	        		} while (!((previousCharacter==16) & (currentCharacter==3)));
	        		//Add CRC16 to frame
	    			frame += Integer.toHexString(is.read())+"H "+Integer.toHexString(is.read())+"H ";
	        		System.out.println(frame);
	        		//Read first character from next frame 
	        		currentCharacter = is.read();
	        		
	        		//wait for the end of frame... some kind of check here
	        		//...
	        		//...
	        		//when frame is complete
	        		//notifyObservers( dataBuffer );
	        	}
			}
			catch( Exception e )
			{
				
			}
        }
	}
}
