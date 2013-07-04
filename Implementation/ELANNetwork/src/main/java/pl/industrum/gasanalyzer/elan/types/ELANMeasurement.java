package pl.industrum.gasanalyzer.elan.types;

import java.math.BigDecimal;
import java.text.NumberFormat;


public class ELANMeasurement
{
	private ELANDimension dimension;
	private ELANMeasuredVariable measuredVariable;
	private Double value;
	private Integer precision;
	
	private static NumberFormat nf = NumberFormat.getInstance();
	
	public ELANMeasurement( ELANDimension dimension, ELANMeasuredVariable measuredVariable, Double value )
	{
		this.dimension = dimension;
		this.measuredVariable = measuredVariable;
		this.value = value;
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
	
	public void setPrecision( Integer precision )
	{
		this.precision = precision;
	}
	
	public Integer getPrecision()
	{
		return precision;
	}
	
	@Override
	public String toString()
	{
		return value.toString() + " " + dimension.toString() + " " + measuredVariable.toString();
	}
	
	public double doubleRet()
    {
        return new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }
	
	public String doubleAsStringRet()
	{
		if(precision != null)
		{
			nf.setMaximumFractionDigits(precision);
		    nf.setMinimumFractionDigits(precision);
		    return nf.format(new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue()).replace(".", ",");
		}
		else
		{
			nf.setMaximumFractionDigits(2);
		    nf.setMinimumFractionDigits(2);
		    return nf.format(new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue()).replace(".", ",");
		}
	}
}
