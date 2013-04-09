package pl.industrum.gasanalyzer.elan.types;

public enum ELANDimension
{
	NOT_USED, //empty field due to table starts from "1" value
	NODIMENSION, PPM, PPB, VPM, PPM_C1, PPM_C3, PPM_C6, MG_CM3, MGM3, PERCENT,
	PERCENT_VOL, PERCENT_OF_MEASURING_RANGE, PERCENT_SATURATION, PERCENT_C,
	PERCENT_K, PERCENT_WEIGHT, MV_PH, MV_MBAR, NA_MBAR, S_M, S_CM, MS_M, MS_CM,
	MICRO_S_M, MICRO_S_CM, S, MIN, H, PA, MA, MICOR_V, MV, V, MBAR, HPA, ML_MIN,
	K, M, S_, DEG_C, HZ, PH, MICRO_G_L, MG_L, L_MIN, MICRO_A, MG_DM3, KPA,
	K_OHM_CM, M_OHM_CM, DEGREE, _1_MIN, _1_M, G_M3, G_L, PERCENT_VOL_C;  
	
	public static ELANDimension getValue(int index)
	{		
		return ELANDimension.values()[index];
	}
}
