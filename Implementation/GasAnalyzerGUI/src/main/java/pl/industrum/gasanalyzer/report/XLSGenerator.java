package pl.industrum.gasanalyzer.report;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.swt.program.Program;

import pl.industrum.gasanalyzer.model.Survey;

/**
 *
 * @author duzydamian
 */
public abstract class XLSGenerator
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault() );
	private static SimpleDateFormat hourFormater = new SimpleDateFormat( "HH:mm:ss", Locale.getDefault() );
	
	public XLSGenerator()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void create( Survey survey, String path )
    {
    }
	
	public void open(String path)
    {
            Program.launch( path );
    }
	
	public abstract void progressIncrement();
}
