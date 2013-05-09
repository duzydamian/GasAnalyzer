/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import pl.industrum.gasanalyzer.hibernate.model.managers.DeviceManager;

/**
 * Probably unused infuture
 * TODO remove or delete this info
 * 
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DatabaseDeviceCorrectTest extends Test
{	
	/**
	 * 
	 */
	public DatabaseDeviceCorrectTest()
	{
		super( "Testowanie słownika Device z bazy danych" );
	}

	public void test()
	{
		if ( DeviceManager.getAllDevices().size() != 12 )
		{			
			DeviceManager.deleteAllDevices();
			
			for( int i=1; i<13; i++ )
			{
				DeviceManager.addDevice( 1 , "Device "+i, i );
			}
		}
	}
}
