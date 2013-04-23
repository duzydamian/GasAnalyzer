package pl.industrum.gasanalyzer.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Observer;

import pl.industrum.gasanalyzer.elan.communication.network.ELANNetwork;
import pl.industrum.gasanalyzer.elan.types.ELANConnectionState;

public class ELANConnectionWrapper implements Iterable<ELANNetwork>
{
	//Collection for all networks
	private HashMap<String, ELANNetwork> networks;
	
	public ELANConnectionWrapper()
	{
		//initialize network collection and add new network
		this.networks = new HashMap<String, ELANNetwork>();
	}
	
	public Iterator<ELANNetwork> iterator() 
	{
        Iterator<ELANNetwork> inetworks = ( networks.values() ).iterator();
        return inetworks; 
    }
	
	public ELANNetwork getNetwork( String port )
	{
		return networks.get( port );
	}
	
	public ELANConnectionState connectWithNetwork( String port, Observer observer )
	{
		try
		{	
			ELANNetwork network = new ELANNetwork( ELANNetwork.getNetworksCounter() );
			ELANNetwork.incNetworksCounter();
			ELANConnectionState state = network.getConnection().getInstance().connect( port, network );
			network.addObserver( observer );
			networks.put( port, network );
			
			return state;
		}
		catch( Exception e )
		{
			return ELANConnectionState.CONNECTION_EXCEPTION;
		}
	}
}
