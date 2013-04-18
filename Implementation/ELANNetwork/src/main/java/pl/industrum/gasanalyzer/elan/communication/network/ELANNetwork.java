package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.ArrayList;
import java.util.Iterator;

public class ELANNetwork implements Iterable<ELANMeasurementDevice>
{
	private String name;
	private ArrayList<ELANMeasurementDevice> measurementDevices;
	
	public ELANNetwork( String name )
	{
		this.name = name;
		this.measurementDevices = new ArrayList<ELANMeasurementDevice>();
	}
	
	public Iterator<ELANMeasurementDevice> iterator() 
	{        
        Iterator<ELANMeasurementDevice> imeasurementDevices = measurementDevices.iterator();
        return imeasurementDevices; 
    }
	
	public String getName()
	{
		return this.name;
	}
	
	public ELANMeasurementDevice getDevice( int i )
	{
		return measurementDevices.get( i );
	}

}
