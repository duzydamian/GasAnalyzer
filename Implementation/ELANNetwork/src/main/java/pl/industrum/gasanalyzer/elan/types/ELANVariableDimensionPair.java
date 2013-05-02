package pl.industrum.gasanalyzer.elan.types;

public class ELANVariableDimensionPair
{
	private ELANMeasuredVariable variable;
	private ELANDimension dimension;
	
	public ELANVariableDimensionPair( ELANMeasuredVariable variable, ELANDimension dimension )
	{
		this.variable = variable;
		this.dimension = dimension;
	}
	
	public ELANMeasuredVariable getVariable()
	{
		return variable;
	}
	
	public ELANDimension getDimension()
	{
		return dimension;
	}
}
