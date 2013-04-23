package pl.industrum.gasanalyzer.i18n;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages
{
	// //////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	// //////////////////////////////////////////////////////////////////////////
	private Messages()
	{
		// do not instantiate
	}

	// //////////////////////////////////////////////////////////////////////////
	//
	// Bundle access
	//
	// //////////////////////////////////////////////////////////////////////////
	private static final String BUNDLE_NAME = "pl.industrum.gasanalyzer.i18n.messages"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = loadBundle();

	private static ResourceBundle loadBundle()
	{
		return ResourceBundle.getBundle( BUNDLE_NAME );
	}

	// //////////////////////////////////////////////////////////////////////////
	//
	// Strings access
	//
	// //////////////////////////////////////////////////////////////////////////
	public static String getString( String key )
	{
		try
		{
			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle()
					: RESOURCE_BUNDLE;
			String string = bundle.getString( key );
			return string;
		} catch ( MissingResourceException e )
		{
			return "!" + key + "!";
		}
	}
}
