/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import java.util.Vector;

import org.eclipse.swt.widgets.Shell;

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

	public TestVector(Shell parentShell)
	{
		super();
		add( new LibRxTxTest(parentShell) );
		add( new ComPortAccessibilityTest(parentShell) );
		add( new DatabaseConnectionTest(parentShell) );
		add( new DatabaseMeasurementDimensionCorrectTest(parentShell) );
		add( new DatabaseMeasurementVariableCorrectTest(parentShell) );
		add( new DatabaseDeviceTypeCorrectTest(parentShell) );
		add( new DatabaseDeviceCorrectTest(parentShell) );
	}

}
