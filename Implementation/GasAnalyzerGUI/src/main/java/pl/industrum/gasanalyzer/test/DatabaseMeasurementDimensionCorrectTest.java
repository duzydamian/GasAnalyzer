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
	
	/**
	 * @param parentShell 
	 * 
	 */
	public DatabaseMeasurementDimensionCorrectTest(Shell parentShell)
	{
		super( "Testowanie słownika Measurement dimension z bazy danych" );
		messageDialog = new MessageBox( parentShell, SWT.ICON_WARNING );
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
					if (MeasurementDimensionDictionary.update( dimension.ordinal(), dimension.getPrintable() ) == null)
					{
						MeasurementDimensionDictionary.add( dimension.ordinal(), dimension.getPrintable() );
					}
				}
				
				System.out.println( "Successful repair Measurement dimension dictionary: " );
				setPassed();
			}
			catch(Exception e)
			{
				setFailed();
				e.printStackTrace();				
				messageDialog.open();
			}
		}
		
	}
}
