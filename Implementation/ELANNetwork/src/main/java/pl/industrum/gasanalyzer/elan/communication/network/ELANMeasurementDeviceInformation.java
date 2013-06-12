package pl.industrum.gasanalyzer.elan.communication.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.types.ELANDeviceType;
import pl.industrum.gasanalyzer.elan.types.ELANMeasuredVariable;
import pl.industrum.gasanalyzer.elan.types.ELANVariableDimensionPrecisionTrio;

public class ELANMeasurementDeviceInformation implements Iterable<ELANVariableDimensionPrecisionTrio>
{
	private String name;
	private ELANDeviceType deviceType;
	private Integer deviceAddress;
	private Integer deviceIDInDatabase;
	private Queue<ELANVariableDimensionPrecisionTrio> measuredVariables;
	
	public ELANMeasurementDeviceInformation()
	{
		name = "";
		measuredVariables = new LinkedList<ELANVariableDimensionPrecisionTrio>();
	}
	
	public Integer getVariablePrecision( ELANMeasuredVariable measuredVariable ) throws Exception
	{
		for( ELANVariableDimensionPrecisionTrio trio : measuredVariables )
		{
			if( trio.getVariable() == measuredVariable )
			{
				return trio.getPrecision();
			}
		}
		
		throw new Exception( "No such measured variable." );
	}
	
	public Iterator<ELANVariableDimensionPrecisionTrio> iterator() 
	{
        Iterator<ELANVariableDimensionPrecisionTrio> imeasuredVariables = measuredVariables.iterator();
        return imeasuredVariables; 
    }
	
	public void addMeasuredVariable( ELANVariableDimensionPrecisionTrio measuredVariable )
	{
		for( ELANVariableDimensionPrecisionTrio variable : this )
		{
			if( variable.getVariable() == measuredVariable.getVariable() )
			{
				return;
			}
		}
		measuredVariables.add( measuredVariable );
	}
	
	public void setMeasuredVariables( Queue<ELANVariableDimensionPrecisionTrio> measuredVariables )
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

	public void setPrecisions( HashMap<String, Integer> measurementPrecisionMap )
	{
		// TODO Auto-generated method stub
		
	}
}
