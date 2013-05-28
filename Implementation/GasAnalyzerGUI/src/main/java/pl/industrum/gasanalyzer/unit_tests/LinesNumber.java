package pl.industrum.gasanalyzer.unit_tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

public class LinesNumber
{

	@Test
	public void test()
	{
		String s = null;

        try 
        {
            Process p = Runtime.getRuntime().exec( "./../project_analize.sh" );
            
            BufferedReader stdInput = new BufferedReader( new InputStreamReader( p.getInputStream() ) );

            BufferedReader stdError = new BufferedReader( new InputStreamReader( p.getErrorStream() ) );

            while ( ( s = stdInput.readLine() ) != null )
            {
                System.out.println(s);
            }
            
            while ((s = stdError.readLine()) != null)
            {
                System.out.println(s);
            }
            
            System.exit(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
	}

}
