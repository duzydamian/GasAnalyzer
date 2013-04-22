package pl.industrum.gasanalyzer.elan.notifications;

public class ELANMeasurementDeviceNotification implements ELANNotification
{
	private Integer deviceAddress;
	
	public ELANMeasurementDeviceNotification( Integer deviceAddress )
	{
		this.deviceAddress = deviceAddress;
	}
	
	public Integer getData()
	{
		return deviceAddress;
	}
}
