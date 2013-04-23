/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;
import pl.industrum.gasanalyzer.gui.GasAnalyzerGUI;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class LibRxTxTest extends Test
{

	/**
	 * 
	 */
	public LibRxTxTest()
	{
		super( "Testowanie biblioteki RxTx" );
	}

	public void test()
	{
		GasAnalyzerGUI.setRXTXVersion( ELANConnection.getRXTXVersion() );
		GasAnalyzerGUI.setNativeRXTXVersion( ELANConnection
				.getNativeRXTXVersion() );
	}
}
