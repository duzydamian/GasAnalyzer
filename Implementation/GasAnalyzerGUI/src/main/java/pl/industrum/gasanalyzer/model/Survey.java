package pl.industrum.gasanalyzer.model;

// default package
// Generated 2013-05-07 14:42:04 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Survey generated by hbm2java
 */
@SuppressWarnings( { "rawtypes", "serial" } )
public class Survey implements java.io.Serializable
{

	private int id;
	private Date timestamp;
	private MeasuredObject object;
	private ApplicationUser applicationUser;
	private String name;
	private String load;
	private String specialConditions;
	private String comment;
	private Set measurementSnapshots = new HashSet( 0 );

	public Survey()
	{
	}

	public Survey( int id, MeasuredObject object, ApplicationUser applicationUser,
			String name )
	{
		this.id = id;
		this.object = object;
		this.applicationUser = applicationUser;
		this.name = name;
	}

	public Survey( int id, MeasuredObject object, ApplicationUser applicationUser,
			String name, String load, String specialConditions, String comment,
			Set measurementSnapshots )
	{
		this.id = id;
		this.object = object;
		this.applicationUser = applicationUser;
		this.name = name;
		this.load = load;
		this.specialConditions = specialConditions;
		this.comment = comment;
		this.measurementSnapshots = measurementSnapshots;
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

	public MeasuredObject getObject()
	{
		return this.object;
	}

	public void setObject( MeasuredObject object )
	{
		this.object = object;
	}

	public ApplicationUser getApplicationUser()
	{
		return this.applicationUser;
	}

	public void setApplicationUser( ApplicationUser applicationUser )
	{
		this.applicationUser = applicationUser;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getLoad()
	{
		return this.load;
	}

	public void setLoad( String load )
	{
		this.load = load;
	}

	public String getSpecialConditions()
	{
		return this.specialConditions;
	}

	public void setSpecialConditions( String specialConditions )
	{
		this.specialConditions = specialConditions;
	}

	public String getComment()
	{
		return this.comment;
	}

	public void setComment( String comment )
	{
		this.comment = comment;
	}

	public Set getMeasurementSnapshots()
	{
		return this.measurementSnapshots;
	}

	public void setMeasurementSnapshots( Set measurementSnapshots )
	{
		this.measurementSnapshots = measurementSnapshots;
	}

}
