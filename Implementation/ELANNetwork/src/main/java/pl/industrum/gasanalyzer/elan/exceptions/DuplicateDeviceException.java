package pl.industrum.gasanalyzer.elan.exceptions;

public class DuplicateDeviceException extends Exception
{
	private static final long serialVersionUID = 469510111877243963L;

	public DuplicateDeviceException() {}
	
	public DuplicateDeviceException( String message )
	{
		super(message);
	}
}
