package pl.industrum.gasanalyzer.gui.frames;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import pl.industrum.gasanalyzer.elan.communication.network.ELANNetwork;
import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.frames.ELANRxInvalidFrame;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class NetworkCollection extends Composite
{
	GridData compositeData;
	private SashForm currentBody;
	private Vector<Network> networks;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public NetworkCollection( Composite parent, int style, String name )
	{
		super( parent, style );
//		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
//				true, false );
//		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );		
		//this.setLayoutData( compositeData );
		this.networks = new Vector<Network>();

		currentBody = new SashForm( this, SWT.NONE );
		currentBody.setLayout( new FillLayout( SWT.VERTICAL ) );
	}

	@Override
	public void setEnabled( boolean arg0 )
	{
		super.setEnabled( arg0 );
	}
	
	public void addNetwork(String networkName)
	{
		networks.add( new Network( currentBody, SWT.NONE, networkName ) );
	}
	
	public void setVisibleNetwork(String name)
	{
		for( Network network: networks )
		{
			if( network.getNetworkName().equalsIgnoreCase( name ))
				network.setVisible( true );
			else
				network.setVisible( false );
		}
		currentBody.layout();
	}
	
	public void renameNetwork(String oldName, String newName)
	{
		for( Network network: networks )
		{
			if( network.getNetworkName().equalsIgnoreCase( oldName ))
				network.setNetworkName( newName );
		}
	}
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public void setNetworkConnected( int networkSize, String name, ELANNetwork elanNetwork )
	{
		for( Network network: networks )
		{
			if( network.getNetworkName().equalsIgnoreCase( name ))
			{
				network.setNetworkConnected( networkSize );
				network.fillDeviceTableWithMeasure( elanNetwork );
			}
		}
	}
	
	public void setNetworkDisconnected( String name )
	{
		for( Network network: networks )
		{
			if( network.getNetworkName().equalsIgnoreCase( name ))
			{
				network.setNetworkDisconnected( );
				network.clearDeviceTableWithMeasure( );
			}
		}
	}

	public void updateStateForDevice( String networkPort, String name, ELANRxInvalidFrame frame )
	{
		for( Network network: networks )
		{
			if( network.getNetworkName().contains( networkPort ))
			{
				network.updateState( frame, name );
			}
		}
	}

	public void updateMeasurmentForDevice( String networkPort, String name, ELANRxBroadcastFrame frame )
	{
		for( Network network: networks )
		{
			if( network.getNetworkName().contains( networkPort ))
			{
				network.updateMeasurment( frame, name );
			}
		}
	}
}
