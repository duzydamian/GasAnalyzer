package pl.industrum.gasanalyzer.elan.notifications;

import java.util.Queue;

public class ELANRxByteBufferNotification implements ELANNotification
{
	private Queue<Integer> frame;
	
	public ELANRxByteBufferNotification( Queue<Integer> frame )
	{
		this.frame = frame;
	}
	
	public Queue<Integer> getData()
	{
		return this.frame;
	}
}
