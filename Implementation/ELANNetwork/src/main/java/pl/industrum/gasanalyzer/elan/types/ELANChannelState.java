package pl.industrum.gasanalyzer.elan.types;

public enum ELANChannelState
{
	NOT_USED, //empty field due to table starts from "1" value
	WARM_UP, PAUSE, STANDBY, MEASURE, ZERO_CALIBRATION, ADJUST_COMPONENT_SLOPE,
	NOT_YET_DEFINED_1, ADJUST_CURVE_DIP, ADJUST_LINEARIZATION_SENSITIVITY,
	ADJUST_TEMPERATURE_COMPENSATION, ADJUST_PRESSURE_COMPENSATION, 
	ADJUST_LINEARIZATION_ZERO, NOT_YET_DEFINED_2, AUTOCAL, ADJUST_PHASE,
	ZERO_CALIBRATION_OF_O2_SENSOR, SYNCHRONOUS_ZERO_CALIBRATION, 
	PURGING_FOR_SYNCHRONOUS_ZERO_CALIBRATION, ADJUST_ANALOG_OUTPUT, 
	ADJUST_ANALOG_INPUT, AUTOCAL_CHECK;
	
	public static ELANChannelState getValue(int index)
	{		
		return ELANChannelState.values()[index];
	}
}
