package pl.industrum.gasanalyzer.elan.types;

public enum ELANMeasuredVariable
{
	NOT_USED, //empty field due to table starts from "1" value
	NOCOMPONENT, CO, CO2, CH4, C6H14, SO2, NO, NO2, CHCIF2, C3H8, 
	C4H10, O2, C5H12, CNHM, P, PH, T, C2H4, C2H2, C3H6,
	C4H6, C4H8, C2H6, NH3, N2O, C6H6, SF6, CH3OH, C2H5OH, CH2CL2,
	C2H4CL2, CH3CL, C2H4O, H2O, GL, C, S, N, CF4, COCL2,
	CHF3, C2F6, SELF_DEFINED_COMPONENT, C2H3CL, H2, AR, HE, CL2, N2,
	HELP_VARIABLE_PROCESS_PRESSURE;
	
	public static ELANMeasuredVariable getValue(int index)
	{
		if ( index < 49)			
			return ELANMeasuredVariable.values()[index];
		else
			return ELANMeasuredVariable.values()[50];
	}
	
	@Override
	public String toString()
	{
		return this.name();
	}
}
