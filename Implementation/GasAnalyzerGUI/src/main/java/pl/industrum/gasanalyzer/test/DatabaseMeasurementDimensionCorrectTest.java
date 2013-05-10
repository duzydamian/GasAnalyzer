/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.elan.types.ELANDimension;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.MeasurementDimensionDictionary;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DatabaseMeasurementDimensionCorrectTest extends Test
{	
	private MessageBox messageDialog;
	private Shell shell;
	
	/**
	 * 
	 */
	public DatabaseMeasurementDimensionCorrectTest()
	{
		super( "Testowanie słownika Measurement dimension z bazy danych" );
		shell = new Shell();
		messageDialog = new MessageBox( shell, SWT.ICON_WARNING );
		messageDialog.setText( "Ostrzeżenie" );
		messageDialog
				.setMessage( "Problem z naprawieniem słownika" );
	}

	public void test()
	{
		if ( MeasurementDimensionDictionary.getAll().size() != ELANDimension.values().length )
		{
			try
			{
				System.out.println( "Run dictionary repair from test: " + this.getName() );
				
				MeasurementDimensionDictionary.deleteAll();
				
				for( ELANDimension dimension: ELANDimension.values() )
				{
					MeasurementDimensionDictionary.add( dimension.ordinal(), dimension.getPrintable() );
				}
			}
			catch(Exception e)
			{
				messageDialog.open();
			}
		}
	}
}
