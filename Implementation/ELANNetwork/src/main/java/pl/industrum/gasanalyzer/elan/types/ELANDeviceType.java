package pl.industrum.gasanalyzer.elan.types;

public enum ELANDeviceType
{
	TYPE_NOT_SET, //start type for all devices
	ULTRAMAT_6, OXYMAT_6, OXYMAT_61, CALOMAT_6, ULTRAMAT_23;
	
	public static ELANDeviceType getValue(int index)
	{		
		return ELANDeviceType.values()[index];
	}
	
	@Override
	public String toString()
	{
		return this.name();
	}
}
