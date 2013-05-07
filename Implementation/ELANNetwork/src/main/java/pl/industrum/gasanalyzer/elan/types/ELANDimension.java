package pl.industrum.gasanalyzer.elan.types;

public enum ELANDimension
{
	NOT_USED(""), //empty field due to table starts from "1" value
	NODIMENSION(""), PPM("ppm"), PPB("ppb"), VPM("vpm"), PPM_C1("ppm C_1"), PPM_C3("ppm C_3"), 
	PPM_C6("ppm C_6"), MG_CM3("mg C/m^3"), MGM3("mg/m^3"), PERCENT("%"), PERCENT_VOL("% Vol"), 
	PERCENT_OF_MEASURING_RANGE("% of Meas. range"), PERCENT_SATURATION("% Saturation"), 
	PERCENT_C("st. C"), PERCENT_K("% K"), PERCENT_WEIGHT("% weight"), MV_PH("mV/pH"), 
	MV_MBAR("mV/mbar"), NA_MBAR("nA/mbar"), S_M("S/m"), S_CM("S/cm"), MS_M("mS/m"), 
	MS_CM("mS/cm"), MICRO_S_M("uS/m"), MICRO_S_CM("uS/cm"), S("S"), MIN("mni"), H("H"), 
	PA("Pa"), MA("MA"), MICOR_V("uV"), MV("MV"), V("V"), MBAR("mbar"), HPA("hPa"), 
	ML_MIN("Ml/min"), K("k"), M("M"), S_("S"), DEG_C("st. C"), HZ("Hz"), PH("pH"),
	MICRO_G_L("ug/l"), MG_L("mg/l"), L_MIN("l/min"), MICRO_A("uA"), MG_DM3("mg/dm^3"), 
	KPA("kPa"), K_OHM_CM("k*Ohm*cm"), M_OHM_CM("M*Ohm*cm"), DEGREE("st."), _1_MIN("1/min"), 
	_1_M("1/m"), G_M3("g/m^3"), G_L("g/l"), PERCENT_VOL_C("% Vol C");  
	
	private String printable;
	
	private ELANDimension(String printable)
	{
		this.setPrintable( printable );
	}
	
	public static ELANDimension getValue(int index)
	{		
		return ELANDimension.values()[index];
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
