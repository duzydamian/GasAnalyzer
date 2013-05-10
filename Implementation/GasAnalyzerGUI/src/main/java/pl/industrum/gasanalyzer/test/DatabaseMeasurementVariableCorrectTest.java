/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.types.ELANMeasuredVariable;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.MeasurementVariableDictionary;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DatabaseMeasurementVariableCorrectTest extends Test
{
	private MessageBox messageDialog;
	private Shell shell;
	
	/**
	 * 
	 */
	public DatabaseMeasurementVariableCorrectTest()
	{
		super( "Testowanie słownika Measurement variable z bazy danych" );
		shell = new Shell();
		messageDialog = new MessageBox( shell, SWT.ICON_WARNING );
		messageDialog.setText( "Ostrzeżenie" );
		messageDialog
				.setMessage( "Problem z naprawieniem słownika Measurement variable." );
	}

	public void test()
	{
		if ( MeasurementVariableDictionary.getAll().size() != ELANMeasuredVariable.values().length )
		{
			try
			{
				System.out.println( "Run Measurement variable dictionary repair." );
				
				for( ELANMeasuredVariable measuredVariable: ELANMeasuredVariable.values() )
				{
					MeasurementVariableDictionary.update( measuredVariable.ordinal(), measuredVariable.getPrintable() );
				}
				
				System.out.println( "Successful repair Measurement variable dictionary: " );
			}
			catch(Exception e)
			{
				messageDialog.open();
			}
		}
	}
}
