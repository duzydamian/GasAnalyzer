/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import java.util.Vector;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class TestVector extends Vector<Test>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -554518877456530904L;

	public TestVector()
	{
		super();
		add(new LibRxTxTest());
		add(new ComPortAccessibilityTest());
	}

}
