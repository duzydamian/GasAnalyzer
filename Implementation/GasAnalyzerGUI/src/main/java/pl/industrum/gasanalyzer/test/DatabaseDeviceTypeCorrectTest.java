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
	
	/**
	 * @param parentShell 
	 * 
	 */
	public DatabaseDeviceTypeCorrectTest(Shell parentShell)
	{
		super( "Testowanie słownika Device type z bazy danych" );
		messageDialog = new MessageBox( parentShell, SWT.ICON_WARNING );
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
					if( DeviceTypeDictionary.update( deviceType.ordinal(), deviceType.name(), null ) == null)
					{
						DeviceTypeDictionary.add( deviceType.ordinal(), deviceType.name(), null );
					}
				}
				
				System.out.println( "Successful repair Device type dictionary: " );
				setPassed();
			}
			catch(Exception e)
			{
				setFailed();
				e.printStackTrace();
				messageDialog.open();
			}
		}
		else
		{
			setPassed();
		}
	}
}
