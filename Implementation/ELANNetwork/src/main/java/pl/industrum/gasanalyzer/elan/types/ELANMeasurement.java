package pl.industrum.gasanalyzer.elan.types;


public class ELANMeasurement
{
	private ELANDimension dimension;
	private ELANMeasuredVariable measuredVariable;
	private Double value;
	
	public ELANMeasurement( ELANDimension dimension, ELANMeasuredVariable measuredVariable ) {
		this.dimension = dimension;
		this.measuredVariable = measuredVariable;
	}
	
	public ELANDimension getDimension()
	{
		return dimension;
	}
	
	public ELANMeasuredVariable getMeasuredVariable()
	{
		return measuredVariable;
	}
	
	public Double getValue()
	{
		return value;
	}
}
