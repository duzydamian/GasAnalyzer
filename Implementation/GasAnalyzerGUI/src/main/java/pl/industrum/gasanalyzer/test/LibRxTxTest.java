/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.gui.GasAnalyzerGUI;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class LibRxTxTest extends Test
{
	private MessageBox messageDialog;
	
	/**
	 * @param parentShell 
	 * 
	 */
	public LibRxTxTest(Shell parentShell)
	{
		super( "Testowanie biblioteki RxTx" );
		messageDialog = new MessageBox( parentShell, SWT.ICON_ERROR );
		messageDialog.setText( "Błąd" );
		messageDialog
				.setMessage( "W systemie nie zainstalowano biblioteki RXTX! Odwiedź stronę projektu, aby znaleźć rozwiązanie problemu" );
	}

	public void test()
	{
		try
		{
			GasAnalyzerGUI.setRXTXVersion( ELANConnection.getRXTXVersion() );
			GasAnalyzerGUI.setNativeRXTXVersion( ELANConnection
				.getNativeRXTXVersion() );
		}
		catch ( Throwable throwable )
		{
			throwable.printStackTrace();
			messageDialog.open();
		}
		setPassed();		
	}
}
