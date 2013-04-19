/**
 * 
 */
package pl.industrum.gasanalyzer.gui;

/**
 * @author duzydamian(Damian Karbowiak)
 *
 */
public class GasAnalyzerGUI
{	
	
	public GasAnalyzerGUI()
	{
		super();		
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{		
			GasAnalyzerMainWindow window = new GasAnalyzerMainWindow();
			window.open();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		       
	}       
}
