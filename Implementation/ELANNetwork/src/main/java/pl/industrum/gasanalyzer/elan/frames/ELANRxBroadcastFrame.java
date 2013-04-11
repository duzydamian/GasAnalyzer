package pl.industrum.gasanalyzer.elan.frames;

import java.util.ArrayList;
import java.util.Iterator;

import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;

public class ELANRxBroadcastFrame extends ELANRxFrame implements Iterable<ELANMeasurement> {
	private ArrayList<ELANMeasurement> measurements;
	
	public ELANRxBroadcastFrame( Integer sourceAdress, Integer targetAdress )
	{
		super( sourceAdress, targetAdress, true );
		this.measurements = new ArrayList<ELANMeasurement>();
	}
	
	public Iterator<ELANMeasurement> iterator() {        
        Iterator<ELANMeasurement> imeasurements = measurements.iterator();
        return imeasurements; 
    }
	
	public void addMeasurement( ELANMeasurement measurement )
	{
		measurements.add( measurement );
	}

}
