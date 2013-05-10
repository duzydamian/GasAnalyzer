/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.DeviceTypeDictionary;
import pl.industrum.gasanalyzer.hibernate.model.managers.DeviceManager;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DatabaseDeviceTypeCorrectTest extends Test
{	
	private MessageBox messageDialog;
	private Shell shell;
	
	/**
	 * 
	 */
	public DatabaseDeviceTypeCorrectTest()
	{
		super( "Testowanie słownika Device type z bazy danych" );
		shell = new Shell();
		messageDialog = new MessageBox( shell, SWT.ICON_WARNING );
		messageDialog.setText( "Ostrzeżenie" );
		messageDialog
				.setMessage( "Problem z naprawieniem słownika" );
	}

	public void test()
	{
		if ( DeviceTypeDictionary.getAll().size() != ELANDeviceType.values().length )
		{			
			try
			{
				System.out.println( "Run dictionary repair from test: " + this.getName() );
				
				DeviceManager.deleteAllDevices();
				DeviceTypeDictionary.deleteAll();
				
				for( ELANDeviceType deviceType: ELANDeviceType.values() )
				{
					DeviceTypeDictionary.add( deviceType.ordinal(), deviceType.name(), null );
				}
			}
			catch(Exception e)
			{
				messageDialog.open();
			}
		}
	}
}
