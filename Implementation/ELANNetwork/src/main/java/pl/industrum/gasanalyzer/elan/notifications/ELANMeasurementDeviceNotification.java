package pl.industrum.gasanalyzer.elan.notifications;

import pl.industrum.gasanalyzer.elan.types.ELANBufferType;
import pl.industrum.gasanalyzer.elan.types.ELANNetworkDeviceInfoObject;

public class ELANMeasurementDeviceNotification implements ELANNotification
{
	private ELANNetworkDeviceInfoObject data;
	
	public ELANMeasurementDeviceNotification( String networkPort, Integer deviceAddress, ELANBufferType bufferType )
	{
		data = new ELANNetworkDeviceInfoObject( networkPort, deviceAddress, bufferType );
	}
	
	public ELANNetworkDeviceInfoObject getData()
	{
		return data;
	}
}
