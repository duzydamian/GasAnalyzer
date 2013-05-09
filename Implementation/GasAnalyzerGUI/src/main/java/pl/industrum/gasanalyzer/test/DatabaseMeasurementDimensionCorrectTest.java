/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import pl.industrum.gasanalyzer.elan.types.ELANDimension;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.MeasurementDimensionDictionary;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DatabaseMeasurementDimensionCorrectTest extends Test
{	
	/**
	 * 
	 */
	public DatabaseMeasurementDimensionCorrectTest()
	{
		super( "Testowanie słownika Measurement dimension z bazy danych" );
	}

	public void test()
	{
		if ( MeasurementDimensionDictionary.getAll().size() != ELANDimension.values().length )
		{
			MeasurementDimensionDictionary.deleteAll();
			
			for( ELANDimension dimension: ELANDimension.values() )
			{
				MeasurementDimensionDictionary.add( dimension.ordinal(), dimension.getPrintable() );
			}
		}
	}
}
