/**
 * 
 */
package pl.industrum.gasanalyzer.elan.types;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public enum ELANConnectionState
{
	CONNECTED(true, "Połączono"), CURRENTLY_IN_USE(false, "Port już zajęty"),
	WRITE_ONLY(false, "Port tylko do zapisu"), NO_SERIAL_PORT(false, "Błędny typ portu"),
	CONNECTION_EXCEPTION(false, "Connection threw exception");
	
	boolean connected;
	String message;
	
	private ELANConnectionState(boolean connected, String message)
	{
		this.connected = connected;
		this.message = message;
	}
	
	public boolean isConnected()
	{
		return connected;
	}

	public String getMessage()
	{
		return message;
	}
}
