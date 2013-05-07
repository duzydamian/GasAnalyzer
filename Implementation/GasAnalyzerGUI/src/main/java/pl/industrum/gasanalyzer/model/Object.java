package pl.industrum.gasanalyzer.model;

// default package
// Generated 2013-05-07 14:42:04 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Object generated by hbm2java
 */
public class Object implements java.io.Serializable
{

	private int id;
	private Place place;
	private String name;
	private String description;
	private Set surveys = new HashSet( 0 );

	public Object()
	{
	}

	public Object( int id, Place place, String name )
	{
		this.id = id;
		this.place = place;
		this.name = name;
	}

	public Object( int id, Place place, String name, String description,
			Set surveys )
	{
		this.id = id;
		this.place = place;
		this.name = name;
		this.description = description;
		this.surveys = surveys;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public Place getPlace()
	{
		return this.place;
	}

	public void setPlace( Place place )
	{
		this.place = place;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public Set getSurveys()
	{
		return this.surveys;
	}

	public void setSurveys( Set surveys )
	{
		this.surveys = surveys;
	}

}
