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
	RED_ERROR(251,51,51),
	YELLOW_WARNING(255,255,51),
	WHITE(255,255,255);
	
	int red;
	int green;
	int blue;
	
	private UsefulColor(int redNew, int greenNew, int blueNew)
	{
		red = redNew;
		green = greenNew;
		blue = blueNew;
	}
	
	public Color getColor()
	{
		return new Color(Display.getCurrent(), red, green, blue);		
	}
}
