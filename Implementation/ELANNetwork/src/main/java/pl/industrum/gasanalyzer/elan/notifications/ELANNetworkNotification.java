package pl.industrum.gasanalyzer.elan.notifications;

public class ELANNetworkNotification implements ELANNotification
{
	private Integer networkID;
	
	public ELANNetworkNotification( Integer networkID )
	{
		this.networkID = networkID;
	}
	
	public Integer getData()
	{
		return networkID;
	}
}
