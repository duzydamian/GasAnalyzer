package pl.industrum.gasanalyzer.elan.types;

public enum ELANBufferType
{
	INVALID_FRAME, BROADCAST_FRAME;
	
	public static ELANBufferType getValue(int index)
	{		
		return ELANBufferType.values()[index];
	}
}
