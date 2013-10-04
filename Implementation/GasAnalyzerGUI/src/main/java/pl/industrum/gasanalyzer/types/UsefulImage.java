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
	APPLICATION( "/pl/industrum/gasanalyzer/gui/application.png" ),
	ABOUT_STAR( "/pl/industrum/gasanalyzer/gui/about.png" ),
	HELP( "/pl/industrum/gasanalyzer/gui/help.png" ),
	NEW_MAIL( "/pl/industrum/gasanalyzer/gui/mail.png" ),
	SEND_MAIL( "/pl/industrum/gasanalyzer/gui/mail_send.png" ),
	SHUTDOWN( "/pl/industrum/gasanalyzer/gui/shutdown.png" ),
	REFRESH( "/pl/industrum/gasanalyzer/gui/odswiez.png" ),
	PDF( "/pl/industrum/gasanalyzer/gui/pdf.png" ),
	EXCEL( "/pl/industrum/gasanalyzer/gui/excel.png" ),
	NEW_SURVEY( "/pl/industrum/gasanalyzer/gui/newSurvey.png" ),
	OPEN_SURVEY( "/pl/industrum/gasanalyzer/gui/dir.png" ),
	PREFERENCES( "/pl/industrum/gasanalyzer/gui/preferences.png" ),
	CALENDAR( "/pl/industrum/gasanalyzer/gui/calendar.png" ),
	CONNECT( "/pl/industrum/gasanalyzer/gui/connect.png" ),
	DISCONNECT( "/pl/industrum/gasanalyzer/gui/disconnect.png" ),
	START( "/pl/industrum/gasanalyzer/gui/run.png" ),
	STOP( "/pl/industrum/gasanalyzer/gui/stop.png" ),
	COMMENT( "/pl/industrum/gasanalyzer/gui/comment.png" ),
	ADD( "/pl/industrum/gasanalyzer/gui/add.png" ),
	EDIT( "/pl/industrum/gasanalyzer/gui/edit.png" ),
	INDUSTRUM_LOGO( "/pl/industrum/gasanalyzer/gui/SKNIndustrumLogo.png" ),
	IMIUE_LOGO( "/pl/industrum/gasanalyzer/gui/IMIUELogo.png" ),
	ZKIWP_LOGO( "/pl/industrum/gasanalyzer/gui/ZKiWPLogo.png" ),
	POLSL_LOGO( "/pl/industrum/gasanalyzer/gui/PolslLogo.png" ),
	OK( "/pl/industrum/gasanalyzer/gui/ok.png" ),
	CANCEL( "/pl/industrum/gasanalyzer/gui/cancel.png" ),
	FIND( "/pl/industrum/gasanalyzer/gui/find.png" ),
	WARNING( "/pl/industrum/gasanalyzer/gui/warning.png" ),
	ERROR( "/pl/industrum/gasanalyzer/gui/remove.png" ),
	UNKNOWN( "/pl/industrum/gasanalyzer/gui/unknown.png" ),
	GRAY_DISCONNECT( "");	

	String imagePath;

	private UsefulImage( String newImagePath )
	{
		imagePath = newImagePath;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath()
	{
		return imagePath;
	}

	public Image getImage()
	{
		return SWTResourceManager.getImage( UsefulImage.class, imagePath );
	}
}
