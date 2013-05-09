/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import pl.industrum.gasanalyzer.elan.types.ELANMeasuredVariable;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.MeasurementVariableDictionary;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DatabaseMeasurementVariableCorrectTest extends Test
{
	/**
	 * 
	 */
	public DatabaseMeasurementVariableCorrectTest()
	{
		super( "Testowanie słownika Measurement variable z bazy danych" );
	}

	public void test()
	{
		if ( MeasurementVariableDictionary.getAll().size() != ELANMeasuredVariable.values().length )
		{
			MeasurementVariableDictionary.deleteAll();
			
			for( ELANMeasuredVariable measuredVariable: ELANMeasuredVariable.values() )
			{
				MeasurementVariableDictionary.add( measuredVariable.ordinal(), measuredVariable.getPrintable() );
			}						
		}
	}
}
