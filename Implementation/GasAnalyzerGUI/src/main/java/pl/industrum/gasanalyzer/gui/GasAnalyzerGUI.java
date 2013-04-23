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
			SplashScreen splashScreen = new SplashScreen();
			splashScreen.open();

			if ( splashScreen.isAllTestComplete() )
			{
				GasAnalyzerMainWindow window = new GasAnalyzerMainWindow();
				window.open();
			}
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
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
