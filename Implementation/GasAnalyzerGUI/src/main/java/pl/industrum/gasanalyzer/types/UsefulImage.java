/**
 * 
 */
package pl.industrum.gasanalyzer.types;

import org.eclipse.swt.graphics.Image;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public enum UsefulImage
{
	ABOUT_STAR( "/pl/industrum/gasanalyzer/gui/aboutmini.png" ),
	SHUTDOWN( "/pl/industrum/gasanalyzer/gui/shutdown.png" ),
	REFRESH( "/pl/industrum/gasanalyzer/gui/odswiez.png" ),
	PDF( "/pl/industrum/gasanalyzer/gui/pdf.png" ),
	EXCEL( "/pl/industrum/gasanalyzer/gui/excel.png" ),
	NEW_SURVEY( "/pl/industrum/gasanalyzer/gui/add.png" ),
	OPEN_SURVEY( "/pl/industrum/gasanalyzer/gui/dir.png" ),
	PREFERENCES( "/pl/industrum/gasanalyzer/gui/ustawienia.png" ),
	CALENDAR( "/pl/industrum/gasanalyzer/gui/calendar.png" ),
	CONNECT( "/pl/industrum/gasanalyzer/gui/connect.png" ),
	DISCONNECT( "/pl/industrum/gasanalyzer/gui/disconnect.png" ),
	ADD( "/pl/industrum/gasanalyzer/gui/add.png" ),
	INDUSTRUM_LOGO( "/pl/industrum/gasanalyzer/gui/SKNIndustrumLogo.png" ),
	IMIUE_LOGO( "/pl/industrum/gasanalyzer/gui/IMIUELogo.png" ),
	ZKIWP_LOGO( "/pl/industrum/gasanalyzer/gui/ZKiWPLogo.png" ),
	POLSL_LOGO( "/pl/industrum/gasanalyzer/gui/PolslLogo.png" ),
	OK( "/pl/industrum/gasanalyzer/gui/ok.png" ),
	WARNING( "/pl/industrum/gasanalyzer/gui/warning.png" ),
	ERROR( "/pl/industrum/gasanalyzer/gui/remove.png" ),
	GRAY_DISCONNECT( "");	

	String imagePath;

	private UsefulImage( String newImagePath )
	{
		imagePath = newImagePath;
	}

	public Image getImage()
	{
		return SWTResourceManager.getImage( UsefulImage.class, imagePath );
	}
}
