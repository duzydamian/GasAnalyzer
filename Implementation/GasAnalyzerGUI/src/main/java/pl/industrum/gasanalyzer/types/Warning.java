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
	NONE("W0000","",""),
	NO_DEVICE("W0001", "Brak urządzeń w sieci", "Niestety w sieci nie wtkryto żadnych urządzeń. Sprawdź połączenia i spróbój ponownie.");
	
	String code;
	String message;
	String description;
	
	private Warning(String code, String message, String description)
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
}
