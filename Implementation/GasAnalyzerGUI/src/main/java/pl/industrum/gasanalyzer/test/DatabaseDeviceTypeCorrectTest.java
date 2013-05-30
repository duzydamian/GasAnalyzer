/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.DeviceTypeDictionary;

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
				.setMessage( "Problem z naprawieniem słownika Device type" );
	}

	public void test()
	{
		if ( DeviceTypeDictionary.getAll().size() != ELANDeviceType.values().length )
		{			
			try
			{
				System.out.println( "Run Device type dictionary repair." );				
				
				for( ELANDeviceType deviceType: ELANDeviceType.values() )
				{
					DeviceTypeDictionary.update( deviceType.ordinal(), deviceType.name(), null );
				}
				
				System.out.println( "Successful repair Device type dictionary: " );
			}
			catch(Exception e)
			{
				messageDialog.open();
			}
		}
		setPassed();
	}
}
