package pl.industrum.gasanalyzer.model;

// default package
// Generated 2013-05-07 14:42:04 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * MeasurementSet generated by hbm2java
 */
@SuppressWarnings( { "rawtypes", "serial" } )
public class MeasurementSet implements java.io.Serializable
{

	private int id;
	private Date timestamp;
	private Device device;
	private MeasurementSnapshot measurementSnapshot;
	private Set measurements = new HashSet( 0 );

	public MeasurementSet()
	{
	}

	public MeasurementSet( int id, Device device )
	{
		this.id = id;
		this.device = device;
	}

	public MeasurementSet( int id, Device device,
			MeasurementSnapshot measurementSnapshot, Set measurements )
	{
		this.id = id;
		this.device = device;
		this.measurementSnapshot = measurementSnapshot;
		this.measurements = measurements;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public Date getTimestamp()
	{
		return this.timestamp;
	}

	public void setTimestamp( Date timestamp )
	{
		this.timestamp = timestamp;
	}

	public Device getDevice()
	{
		return this.device;
	}

	public void setDevice( Device device )
	{
		this.device = device;
	}

	public MeasurementSnapshot getMeasurementSnapshot()
	{
		return this.measurementSnapshot;
	}

	public void setMeasurementSnapshot( MeasurementSnapshot measurementSnapshot )
	{
		this.measurementSnapshot = measurementSnapshot;
	}

	public Set getMeasurements()
	{
		return this.measurements;
	}

	public void setMeasurements( Set measurements )
	{
		this.measurements = measurements;
	}

}