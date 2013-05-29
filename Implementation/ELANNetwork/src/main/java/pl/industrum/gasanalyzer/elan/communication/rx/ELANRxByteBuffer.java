package pl.industrum.gasanalyzer.elan.communication.rx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.communication.ELANCommunication;
import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.elan.notifications.ELANRxByteBufferNotification;
import pl.industrum.gasanalyzer.elan.using.Main;

public class ELANRxByteBuffer extends Observable implements Runnable
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "HH:mm:ss:SS dd/MM/yyyy", Locale.getDefault() );
	
	private ELANCommunication communication;
	
	public ELANRxByteBuffer( ELANConnection connection )
	{
		//Open port and start communication.
		communication = new ELANCommunication( connection );
	}
	
	public void run()
	{
		while( !Thread.currentThread().isInterrupted() )
		{			
			try
			{				
				//Read all frames and send every correct frame to ELANRxBufferObserver.
	        	Queue<Integer> frame = communication.readFrame();
	        		      
	        	if ( Main.isDebug() )
				{
	        		System.out.println( dateFormater.format( new Date() ) + " : Ramka z ELANRxByte Buffer" );
		        	System.out.println( frame );
		        	System.out.println( );
				}	        	
	        	
	        	if(frame != null)
	        	{	        		
		        	setChanged();
		        	notifyObservers( new ELANRxByteBufferNotification( frame ) );
	        	}	        	
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
        }
	}
}
