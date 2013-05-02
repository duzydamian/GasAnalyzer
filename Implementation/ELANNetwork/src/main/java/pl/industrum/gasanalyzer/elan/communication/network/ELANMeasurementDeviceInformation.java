package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;
import pl.industrum.gasanalyzer.elan.types.ELANMeasuredVariable;

public class ELANMeasurementDeviceInformation implements Iterable<ELANMeasuredVariable>
{
	private String name;
	private ELANDeviceType deviceType;
	private Integer deviceAddress;
	private Queue<ELANMeasuredVariable> measuredVariables;
	
	public ELANMeasurementDeviceInformation()
	{
		name = "";
		measuredVariables = new LinkedList<ELANMeasuredVariable>();
	}
	
	public Iterator<ELANMeasuredVariable> iterator() 
	{
        Iterator<ELANMeasuredVariable> imeasuredVariables = measuredVariables.iterator();
        return imeasuredVariables; 
    }
	
	public void addMeasuredVariable( ELANMeasuredVariable measuredVariable )
	{
		for( ELANMeasuredVariable variable : this )
		{
			if( variable == measuredVariable )
			{
				return;
			}
		}
		measuredVariables.add( measuredVariable );
	}
	
	public void setMeasuredVariables( Queue<ELANMeasuredVariable> measuredVariables )
	{
		this.measuredVariables = measuredVariables;
	}
	
	public String getName()
	{
		if( name == "" )
		{
			return "Device " + deviceAddress.toString();
		}
		else
		{
			return name;
		}
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public ELANDeviceType getDeviceType()
	{
		return deviceType;
	}

	public void setDeviceType( ELANDeviceType deviceType )
	{
		//!!!empty for now, it is needed to ask the device for its type
	}

	public Integer getDeviceAddress()
	{
		return deviceAddress;
	}

	public void setDeviceAddress( Integer deviceAddress )
	{
		this.deviceAddress = deviceAddress;
	}
}
