package pl.industrum.gasanalyzer.model;

// default package
// Generated 2013-05-07 14:42:04 by Hibernate Tools 4.0.0

/**
 * Measurement generated by hbm2java
 */
@SuppressWarnings( { "serial" } )
public class Measurement implements java.io.Serializable
{

	private int id;
	private MeasurementSet measurementSet;
	private MeasurementDimension measurementDimension;
	private MeasurementVariable measurementVariable;
	private double value;

	public Measurement()
	{
	}

	public Measurement( int id, MeasurementSet measurementSet,
			MeasurementDimension measurementDimension,
			MeasurementVariable measurementVariable, double value )
	{
		this.id = id;
		this.measurementSet = measurementSet;
		this.measurementDimension = measurementDimension;
		this.measurementVariable = measurementVariable;
		this.value = value;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public MeasurementSet getMeasurementSet()
	{
		return this.measurementSet;
	}

	public void setMeasurementSet( MeasurementSet measurementSet )
	{
		this.measurementSet = measurementSet;
	}

	public MeasurementDimension getMeasurementDimension()
	{
		return this.measurementDimension;
	}

	public void setMeasurementDimension(
			MeasurementDimension measurementDimension )
	{
		this.measurementDimension = measurementDimension;
	}

	public MeasurementVariable getMeasurementVariable()
	{
		return this.measurementVariable;
	}

	public void setMeasurementVariable( MeasurementVariable measurementVariable )
	{
		this.measurementVariable = measurementVariable;
	}

	public double getValue()
	{
		return this.value;
	}

	public void setValue( double value )
	{
		this.value = value;
	}

}
