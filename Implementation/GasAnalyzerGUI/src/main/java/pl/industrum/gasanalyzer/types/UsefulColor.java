/**
 * 
 */
package pl.industrum.gasanalyzer.types;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public enum UsefulColor
{
	RED_ERROR( 255, 100, 100 ), 
	YELLOW_WARNING( 255, 255, 100 ), 
	WHITE( 255, 255, 255 ),
	BLACK( 0, 0, 0 ),
	GRAY_DISCONNECT( 190, 190, 190 );	

	int red;
	int green;
	int blue;

	private UsefulColor( int redNew, int greenNew, int blueNew )
	{
		red = redNew;
		green = greenNew;
		blue = blueNew;
	}

	public Color getColor()
	{
		return new Color( Display.getCurrent(), red, green, blue );
	}
}
