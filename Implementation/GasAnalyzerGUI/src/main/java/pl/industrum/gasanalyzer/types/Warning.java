/**
 * 
 */
package pl.industrum.gasanalyzer.types;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public enum Warning
{
	NONE("E0000","","");
	
	String code;
	String message;
	String description;
	
	private Warning(String code, String message, String description)
	{
		this.code = code;
		this.message = message;
		this.description = description;
	}
}
