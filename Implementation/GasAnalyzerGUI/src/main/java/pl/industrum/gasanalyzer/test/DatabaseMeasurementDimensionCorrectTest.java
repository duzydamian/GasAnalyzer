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
				.setMessage( "Problem z naprawieniem słownika Measurement dimension" );
	}

	public void test()
	{
		if ( MeasurementDimensionDictionary.getAll().size() != ELANDimension.values().length )
		{
			try
			{
				System.out.println( "Run Measurement dimension dictionary repair." );
				
				for( ELANDimension dimension: ELANDimension.values() )
				{
					MeasurementDimensionDictionary.update( dimension.ordinal(), dimension.getPrintable() );
				}
				
				System.out.println( "Successful repair Measurement dimension dictionary: " );
			}
			catch(Exception e)
			{
				e.printStackTrace();
				messageDialog.open();
			}
		}
		setPassed();
	}
}
