package pl.industrum.gasanalyzer.elan.notifications;

public class ELANNetworkNotification implements ELANNotification
{
	private String networkName;
	
	public ELANNetworkNotification( String networkName )
	{
		this.networkName = networkName;
	}
	
	public String getData()
	{
		return networkName;
	}
}
