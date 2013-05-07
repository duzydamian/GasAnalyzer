package pl.industrum.gasanalyzer.elan.types;

public enum ELANMeasuredVariable
{
	NOT_USED(""), //empty field due to table starts from "1" value
	NOCOMPONENT("No component"), CO("CO"), CO2("CO_2"), CH4("CH_4"), C6H14("C_6H_14"),
	SO2("SO_2"), NO("NO"), NO2("NO_2"), CHCIF2("CHCIF_2"), C3H8("C_3H_8"), C4H10("C_4H_(10)"),
	O2("O_2"), C5H12("C_5H_(12)"), CNHM("CnHm"), P("P"), PH("pH"), T("T"), C2H4("C_2H_4"),
	C2H2("C_2H_2"), C3H6("C_3H_6"), C4H6("C_4H_6"), C4H8("C_4_8"), C2H6("C_2H_6"), NH3("NH_3"),
	N2O("N_2O"), C6H6("C_6H_6"), SF6("SF_6"), CH3OH("CH_3OH"), C2H5OH("C_2H_5OH"), 
	CH2CL2("CH_2Cl_2"), C2H4CL2("C_2H_4Cl_2"), CH3CL("CH_3Cl"), C2H4O("C_2H_4O"), H2O("H_2O"),
	GL("G/l"), C("C"), S("S"), N("N"), CF4("CF_4"), COCL2("COCl_2"), CHF3("CHF_3"),
	C2F6("C_2F_6"), SELF_DEFINED_COMPONENT("Self defined"), C2H3CL("C_2H_3Cl"), H2("H_2"),
	AR("Ar"), HE("He"), CL2("Cl_2"), N2("N_2"),
	HELP_VARIABLE_PROCESS_PRESSURE("Process preassure");
	
	private String printable;
	
	private ELANMeasuredVariable(String printable)
	{
		this.setPrintable( printable );
	}
	
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
	
	/**
	 * @return the printable
	 */
	public String getPrintable()
	{
		return printable;
	}
	
	/**
	 * @param printable the printable to set
	 */
	public void setPrintable( String printable )
	{
		this.printable = printable;
	}
}
