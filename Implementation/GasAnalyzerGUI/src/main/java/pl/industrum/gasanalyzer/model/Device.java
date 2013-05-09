package pl.industrum.gasanalyzer.model;

// default package
// Generated 2013-05-07 14:42:04 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Device generated by hbm2java
 */
@SuppressWarnings( { "rawtypes", "serial" } )
public class Device implements java.io.Serializable
{

	private int id;
	private DeviceType deviceType;
	private String name;
	private int address;
	private Set measurementSets = new HashSet( 0 );

	public Device()
	{
	}

	public Device( int id, DeviceType deviceType, String name, int address )
	{
		this.id = id;
		this.deviceType = deviceType;
		this.name = name;
		this.address = address;
	}

	public Device( int id, DeviceType deviceType, String name, int address,
			Set measurementSets )
	{
		this.id = id;
		this.deviceType = deviceType;
		this.name = name;
		this.address = address;
		this.measurementSets = measurementSets;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public DeviceType getDeviceType()
	{
		return this.deviceType;
	}

	public void setDeviceType( DeviceType deviceType )
	{
		this.deviceType = deviceType;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public int getAddress()
	{
		return this.address;
	}

	public void setAddress( int address )
	{
		this.address = address;
	}

	public Set getMeasurementSets()
	{
		return this.measurementSets;
	}

	public void setMeasurementSets( Set measurementSets )
	{
		this.measurementSets = measurementSets;
	}

}
