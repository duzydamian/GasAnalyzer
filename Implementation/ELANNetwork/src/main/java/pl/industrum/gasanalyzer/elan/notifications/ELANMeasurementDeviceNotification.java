package pl.industrum.gasanalyzer.elan.notifications;

import pl.industrum.gasanalyzer.elan.types.ELANNetworkDevicePair;

public class ELANMeasurementDeviceNotification implements ELANNotification
{
	private ELANNetworkDevicePair data;
	
	public ELANMeasurementDeviceNotification( String networkPort, Integer deviceAddress )
	{
		data = new ELANNetworkDevicePair( networkPort, deviceAddress );
	}
	
	public ELANNetworkDevicePair getData()
	{
		return data;
	}
}
