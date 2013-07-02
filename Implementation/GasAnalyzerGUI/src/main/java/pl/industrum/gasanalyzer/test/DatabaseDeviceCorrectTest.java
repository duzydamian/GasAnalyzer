/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.hibernate.model.managers.DeviceManager;

/**
 * 
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DatabaseDeviceCorrectTest extends Test
{	
	private MessageBox messageDialog;
	
	/**
	 * @param parentShell 
	 * 
	 */
	public DatabaseDeviceCorrectTest(Shell parentShell)
	{
		super( "Testowanie słownika Device z bazy danych" );
		messageDialog = new MessageBox( parentShell, SWT.ICON_WARNING );
		messageDialog.setText( "Ostrzeżenie" );
		messageDialog
				.setMessage( "Problem z naprawieniem słownika" );
	}

	public void test()
	{		
		if ( DeviceManager.getAllDevices().size() != 12 )
		{	
			try
			{
				System.out.println( "Run dictionary repair from test: " + this.getName() );
				
				DeviceManager.deleteAllDevices();
				
				for( int i=1; i<13; i++ )
				{
					DeviceManager.addDevice( 1 , "Device "+i, i );
				}
				
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
