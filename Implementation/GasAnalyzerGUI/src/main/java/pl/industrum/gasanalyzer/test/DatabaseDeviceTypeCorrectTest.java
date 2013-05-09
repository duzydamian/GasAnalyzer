/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.DeviceTypeDictionary;
import pl.industrum.gasanalyzer.hibernate.model.managers.DeviceManager;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DatabaseDeviceTypeCorrectTest extends Test
{	
	/**
	 * 
	 */
	public DatabaseDeviceTypeCorrectTest()
	{
		super( "Testowanie słownika Device type z bazy danych" );
	}

	public void test()
	{
		if ( DeviceTypeDictionary.getAll().size() != ELANDeviceType.values().length )
		{			
			DeviceManager.deleteAllDevices();
			DeviceTypeDictionary.deleteAll();
			
			for( ELANDeviceType deviceType: ELANDeviceType.values() )
			{
				DeviceTypeDictionary.add( deviceType.ordinal(), deviceType.name(), null );
			}
		}
	}
}
