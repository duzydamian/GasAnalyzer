package pl.industrum.gasanalyzer.elan.exceptions;

public class NullDeviceException extends Exception
{
	private static final long serialVersionUID = 6304726962060714893L;

	public NullDeviceException() {}
	
	public NullDeviceException( String message )
	{
		super(message);
	}
}
