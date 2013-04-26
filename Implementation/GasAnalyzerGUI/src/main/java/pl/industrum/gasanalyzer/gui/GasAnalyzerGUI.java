/**
 * 
 */
package pl.industrum.gasanalyzer.gui;

import java.io.PrintStream;

/**
 * @author duzydamian(Damian Karbowiak)
 * 
 */
public class GasAnalyzerGUI
{
	private static String AppVersion;
	private static String RXTXVersion;
	private static String NativeRXTXVersion;
	
	private static boolean toWindowForwarding = false;

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
					if ( forwarding.equalsIgnoreCase( "out" ) )
					{
						//nothing to do
					} 
					else if ( forwarding.equalsIgnoreCase( "panel" ) | forwarding.equalsIgnoreCase( "window" ) )
					{
						toWindowForwarding = true;
					}
					else if ( forwarding.equalsIgnoreCase( "file" ) )
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
			
			SplashScreen splashScreen = new SplashScreen();
			splashScreen.open();

			if ( splashScreen.isAllTestComplete() )
			{
				GasAnalyzerMainWindow window = new GasAnalyzerMainWindow(toWindowForwarding);
				window.open();
			}
		} catch ( Exception e )
		{
			e.printStackTrace();
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
}
