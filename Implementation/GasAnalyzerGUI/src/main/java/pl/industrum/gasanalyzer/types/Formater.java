/**
 * 
 */
package pl.industrum.gasanalyzer.types;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class Formater
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault() );
	private static SimpleDateFormat hourFormater = new SimpleDateFormat( "HH:mm:ss", Locale.getDefault() );
	
	private static NumberFormat nf = NumberFormat.getInstance();

	/**
	 * @return the dateFormater
	 */
	public static SimpleDateFormat getDateFormater()
	{
		return dateFormater;
	}

	/**
	 * @return the hourFormater
	 */
	public static SimpleDateFormat getHourFormater()
	{
		return hourFormater;
	}
	
	public static double doubleWithPrecisionAsdouble(double value, int precision)
    {
        return new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static double doubleWithPrecisionAsdouble(String value, int precision)
    {
        return new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static Double doubleWithPrecisionAsDouble(double value, int precision)
    {
        return new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static Double doubleWithPrecisionAsDouble(String value, int precision)
    {
        return new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static String doubleWithPrecisionAsString(double value, int precision)
    {
        nf.setMaximumFractionDigits(precision);
        nf.setMinimumFractionDigits(precision);
        return nf.format(new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue()).replace(".", ",");
    }

    public static String doubleWithPrecisionAsString(String value, int precision)
    {
        nf.setMaximumFractionDigits(precision);
        nf.setMinimumFractionDigits(precision);
        return nf.format(new BigDecimal(value.replace(',', '.')).setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue()).replace(".", ",");
    }
}
