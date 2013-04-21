/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;

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
		super("Testowanie biblioteki RxTx");
	}

	public void test()
	{		
		ELANConnection.listPorts();
	}
}
