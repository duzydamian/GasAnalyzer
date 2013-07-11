/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class ComPortAccessibilityTest extends Test
{
	private MessageBox messageDialog;

	/**
	 * @param parentShell 
	 * 
	 */
	public ComPortAccessibilityTest(Shell parentShell)
	{
		super( "Sprawdzanie dostępności portów COM" );
		messageDialog = new MessageBox( parentShell, SWT.ICON_WARNING );
		messageDialog.setText( "Ostrzeżenie" );
		messageDialog
				.setMessage( "W systemie nie wykryto żadnych portów szeregowych!" );
	}

	public void test()
	{
		try
		{
			if ( ELANConnection.vectorPorts().size() > 0 )
			{
				setPassed();
				return;
			}
			else
			{
				setPassed();
				messageDialog.open();
			}
		}
		catch ( Throwable throwable )
		{
			throwable.printStackTrace();
		}
	}
}
