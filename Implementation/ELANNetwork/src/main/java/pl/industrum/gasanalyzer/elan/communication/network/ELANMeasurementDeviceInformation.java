package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;
import pl.industrum.gasanalyzer.elan.types.ELANVariableDimensionPair;

public class ELANMeasurementDeviceInformation implements Iterable<ELANVariableDimensionPair>
{
	private String name;
	private ELANDeviceType deviceType;
	private Integer deviceAddress;
	private Integer deviceIDInDatabase;
	private Queue<ELANVariableDimensionPair> measuredVariables;
	
	public ELANMeasurementDeviceInformation()
	{
		name = "";
		measuredVariables = new LinkedList<ELANVariableDimensionPair>();
	}
	
	public Iterator<ELANVariableDimensionPair> iterator() 
	{
        Iterator<ELANVariableDimensionPair> imeasuredVariables = measuredVariables.iterator();
        return imeasuredVariables; 
    }
	
	public void addMeasuredVariable( ELANVariableDimensionPair measuredVariable )
	{
		for( ELANVariableDimensionPair variable : this )
		{
			if( variable.getVariable() == measuredVariable.getVariable() )
			{
				return;
			}
		}
		measuredVariables.add( measuredVariable );
	}
	
	public void setMeasuredVariables( Queue<ELANVariableDimensionPair> measuredVariables )
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

	/**
	 * @return the deviceIDInDatabase
	 */
	public Integer getDeviceIDInDatabase()
	{
		return deviceIDInDatabase;
	}

	/**
	 * @param deviceIDInDatabase the deviceIDInDatabase to set
	 */
	public void setDeviceIDInDatabase( Integer deviceIDInDatabase )
	{
		this.deviceIDInDatabase = deviceIDInDatabase;
	}
}
