package pl.industrum.gasanalyzer.elan.communication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class ELANRxBroadcastFrame extends ELANRxFrame implements Iterable<ELANMeasurement> {
	private ArrayList<ELANMeasurement> measurements;
	
	public ELANRxBroadcastFrame( Queue<Integer> frame ) {
		super(frame);
		// TODO Auto-generated constructor stub
	}
	
	public Iterator<ELANMeasurement> iterator() {        
        Iterator<ELANMeasurement> imeasurements = measurements.iterator();
        return imeasurements; 
    }

}
