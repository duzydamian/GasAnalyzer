package pl.industrum.gasanalyzer.elan.communication;

public class ELANMeasurement {
	private String unit;
	private Double value;
	
	public ELANMeasurement( String unit, Double value ) {
		this.unit = unit;
		this.value = value;
	}
	
	public String getUnit() {
		return this.unit;
	}
	
	public Double getValue() {
		return this.value;
	}
}
