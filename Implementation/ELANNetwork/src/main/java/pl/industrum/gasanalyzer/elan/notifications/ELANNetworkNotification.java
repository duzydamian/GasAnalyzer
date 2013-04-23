package pl.industrum.gasanalyzer.elan.notifications;

public class ELANNetworkNotification implements ELANNotification
{
	private String networkPort;
	
	public ELANNetworkNotification( String networkPort )
	{
		this.networkPort = networkPort;
	}
	
	public String getData()
	{
		return networkPort;
	}
}
