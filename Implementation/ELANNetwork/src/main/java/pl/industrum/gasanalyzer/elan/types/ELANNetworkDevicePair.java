package pl.industrum.gasanalyzer.elan.types;

public class ELANNetworkDevicePair
{
	private String networkPort;
	private Integer deviceAddress;
	
	public ELANNetworkDevicePair( String networkPort, Integer deviceAddress )
	{
		this.networkPort = networkPort;
		this.deviceAddress = deviceAddress;
	}
	
	public String getNetworkPort()
	{
		return networkPort;
	}
	
	public Integer getDeviceAddress()
	{
		return deviceAddress;
	}
}
