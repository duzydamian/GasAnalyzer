/**
 * 
 */
package pl.industrum.gasanalyzer.gui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Vector;

import org.eclipse.swt.program.Program;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class EmailSystem
{

	private String mailto;
	private Vector<File> attachments;
	
	/**
	 * 
	 */
	
	public EmailSystem()
	{
		mailto="";
		attachments = new Vector<File>();
		attachments.add( new File( "out.log" ) );
		attachments.add( new File( "err.log" ) );
	}

	public void sendExceptionNotification(String stackTrace){
		openMailClient( new String[]{ "duzydamian@gmail.com", "bananowy.grzesiu@gmail.com" }, "[GasAnalyzer] Exception", stackTrace );
	}
	
	public void sendSuggestionNotification(){
		openMailClient( new String[]{ "duzydamian@gmail.com", "bananowy.grzesiu@gmail.com" }, "[GasAnalyzer] Propozycja", "Twoja propozycja" );
	}
	
	public void sendBugNotification(){
		openMailClient( new String[]{ "duzydamian@gmail.com", "bananowy.grzesiu@gmail.com" }, "[GasAnalyzer] Błąd", "Opis znalezionego przez Ciebie błędu" );
	}
	
	public void openMailClient(String to, String subject,String body)
	{
		mailto = "mailto:" + enc(to) + "?subject=" + enc(subject) + "&body=" + enc(body);
		
		if ( attachments != null )
		{
        	for (File f : attachments)
            {
                mailto += "&attach=" + f.getAbsolutePath();
            }
		}  
		
		System.out.println(mailto);
        Program.launch(mailto);        
	}
	
	public void openMailClient(String[] to, String subject,String body)
	{               
        if ( to.length ==1)
        {
        	mailto = "mailto:" + enc(to[0]) + "?subject=" + enc(subject) + "&body=" + enc(body);
        }
        else
        {
        	mailto = "mailto:";
        	for( String adr: to )
			{
        		mailto += enc(adr)+"?to=";
			}
        	mailto = mailto.substring( 0, mailto.length()-4 );
        	mailto += "&subject=" + enc(subject) + "&body=" + enc(body);
        }
        	
        if ( attachments != null )
		{
        	for (File f : attachments)
            {
                mailto += "&attach=" + f.getAbsolutePath();
            }
		}   
        
        System.out.println(mailto);
        Program.launch(mailto);
    }
 
    private String enc(String p)
    {
        if (p == null)
            p = "";
        try
        {
            return URLEncoder.encode(p, "UTF-8").replace("+", "%20");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException();
        }
    }
}
