/**
 * 
 */
package pl.industrum.gasanalyzer.types;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public enum Error
{
	NONE("E0000","",""),
	CONNECTION_PROBLEM("E0001", "Problem z połączeniem z wybraną siecią.", ""),
	DEVICE_ERROR("E2000", "Błąd w podanym urządzeniu", "");
	
	String code;
	String message;
	String description;

	private Error(String code, String message, String description)
	{
		this.code = code;
		this.message = message;
		this.description = description;
	}
	
	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage( String message )
	{
		this.message = message;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription( String description )
	{
		this.description = description;
	}
}
