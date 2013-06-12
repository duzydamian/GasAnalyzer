package pl.industrum.gasanalyzer.elan.types;

public class ELANVariableDimensionPrecisionTrio
{
	private ELANMeasuredVariable variable;
	private ELANDimension dimension;
	private Integer precision;
	
	public ELANVariableDimensionPrecisionTrio( ELANMeasuredVariable variable, ELANDimension dimension, Integer precision )
	{
		this.variable = variable;
		this.dimension = dimension;
		this.precision = precision;
	}
	
	public ELANMeasuredVariable getVariable()
	{
		return variable;
	}
	
	public ELANDimension getDimension()
	{
		return dimension;
	}
	
	public Integer getPrecision()
	{
		return precision;
	}
}
