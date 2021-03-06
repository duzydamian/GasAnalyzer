package pl.industrum.gasanalyzer.model;

// default package
// Generated 2013-05-07 14:42:04 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * SurveySection generated by hbm2java
 */
@SuppressWarnings( { "rawtypes", "serial" } )
public class SurveySection implements java.io.Serializable
{

	private int id;
	private String name;
	private Set measurementSnapshots = new HashSet( 0 );

	public SurveySection()
	{
	}

	public SurveySection( int id, String name )
	{
		this.id = id;
		this.name = name;
	}

	public SurveySection( int id, String name, Set measurementSnapshots )
	{
		this.id = id;
		this.name = name;
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

	public String getName()
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
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
