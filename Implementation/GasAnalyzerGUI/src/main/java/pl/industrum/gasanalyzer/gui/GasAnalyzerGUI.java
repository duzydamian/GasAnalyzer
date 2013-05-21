/**
 * 
 */
package pl.industrum.gasanalyzer.gui;

import java.io.PrintStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.gui.dialogs.SendExceptionCatched;

/**
 * @author duzydamian(Damian Karbowiak)
 * 
 */
public class GasAnalyzerGUI
{
	private static boolean debug = false;
	private static boolean develop = true;
	
	private static String AppVersion;
	private static String RXTXVersion;
	private static String NativeRXTXVersion;

	public GasAnalyzerGUI()
	{
		super();
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main( String[] args )
	{
		try
		{
			try
			{
				if ( args.length > 0 )
				{
					String forwarding = args[0];
					if ( forwarding.equalsIgnoreCase( "file" ) )
					{
						System.setErr( new PrintStream( "err.log" ) );
						System.setOut( new PrintStream( "out.log" ) );
					}
				}
				else
				{
					if ( !develop & !debug )
					{
						System.setErr( new PrintStream( "err.log" ) );
						System.setOut( new PrintStream( "out.log" ) );
					}					
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}									
						
			if ( !develop )
			{	
				SplashScreen splashScreen = new SplashScreen();
				splashScreen.open();
				if ( splashScreen.isAllTestComplete() )
				{				
					GasAnalyzerMainWindow window = new GasAnalyzerMainWindow();
					window.open();
				}
			}
			else
			{
				GasAnalyzerMainWindow window = new GasAnalyzerMainWindow();
				window.open();
			}
			
		} catch ( Exception e )
		{
			e.printStackTrace();
			SendExceptionCatched sendExceptionCatched = new SendExceptionCatched(new Shell(), SWT.NONE, e );
			sendExceptionCatched.open();
		}
	}

	/**
	 * @return the appVersion
	 */
	public static String getAppVersion()
	{
		return AppVersion;
	}

	/**
	 * @param appVersion the appVersion to set
	 */
	public static void setAppVersion( String appVersion )
	{
		AppVersion = appVersion;
	}

	/**
	 * @return the rXTXVersion
	 */
	public static String getRXTXVersion()
	{
		return RXTXVersion;
	}

	/**
	 * @param rXTXVersion
	 *            the rXTXVersion to set
	 */
	public static void setRXTXVersion( String rXTXVersion )
	{
		RXTXVersion = rXTXVersion;
	}

	/**
	 * @return the nativeRXTXVersion
	 */
	public static String getNativeRXTXVersion()
	{
		return NativeRXTXVersion;
	}

	/**
	 * @param nativeRXTXVersion
	 *            the nativeRXTXVersion to set
	 */
	public static void setNativeRXTXVersion( String nativeRXTXVersion )
	{
		NativeRXTXVersion = nativeRXTXVersion;
	}
	
	/**
	 * @return the debug
	 */
	public static boolean isDebug()
	{
		return debug;
	}

	/**
	 * @return the develop
	 */
	public static boolean isDevelop()
	{
		return develop;
	}
}
