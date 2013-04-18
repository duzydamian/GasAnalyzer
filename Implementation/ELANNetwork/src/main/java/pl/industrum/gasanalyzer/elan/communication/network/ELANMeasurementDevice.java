package pl.industrum.gasanalyzer.elan.communication.network;

import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;

public class ELANMeasurementDevice
{
	private String name; //optional
	private ELANDeviceType deviceType;
	private Integer deviceAddress;
	
	public ELANMeasurementDevice( String name, Integer deviceAddress )
	{
		this.setName( name );
		this.setDeviceAddress( deviceAddress );
	}
	
	public ELANMeasurementDevice( Integer deviceAddress )
	{
		this.setDeviceAddress( deviceAddress );
	}
	
	public ELANMeasurementDevice()
	{
		//no params constructor
	}
	
	public void setDeviceType()
	{
		//!!!empty for now, it is needed to ask the device for its type
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public ELANDeviceType getDeviceType()
	{
		return deviceType;
	}

	public void setDeviceType( ELANDeviceType deviceType )
	{
		this.deviceType = deviceType;
	}

	public Integer getDeviceAddress()
	{
		return deviceAddress;
	}

	public void setDeviceAddress( Integer deviceAddress )
	{
		this.deviceAddress = deviceAddress;
	}
	
	
}
