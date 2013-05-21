package pl.industrum.gasanalyzer.elan.types;

public class ELANNetworkDeviceInfoObject
{
	private String networkPort;
	private Integer deviceAddress;
	private ELANBufferType bufferType;
	
	public ELANNetworkDeviceInfoObject( String networkPort, Integer deviceAddress, ELANBufferType bufferType )
	{
		this.networkPort = networkPort;
		this.deviceAddress = deviceAddress;
		this.bufferType = bufferType;
	}
	
	public String getNetworkPort()
	{
		return networkPort;
	}
	
	public Integer getDeviceAddress()
	{
		return deviceAddress;
	}
	
	public ELANBufferType getBufferType()
	{
		return bufferType;
	}
}
