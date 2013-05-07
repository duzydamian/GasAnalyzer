package pl.industrum.gasanalyzer.gui.frames;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import pl.industrum.gasanalyzer.elan.communication.network.ELANMeasurementDevice;
import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DeviceCollection extends Composite
{
	GridData compositeData;
	private Composite currentBody;
	private Vector<Device> devices;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DeviceCollection( Composite parent, int style, String name )
	{
		super( parent, style );
//		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
//				true, false );
//		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );		
		//this.setLayoutData( compositeData );
		this.devices = new Vector<Device>();

		currentBody = new Composite( this, SWT.NONE );
		currentBody.setLayout( new GridLayout( 1, false ) );
	}

	@Override
	public void setEnabled( boolean arg0 )
	{
		super.setEnabled( arg0 );
	}
	
	public void addDevice( ELANMeasurementDevice device )
	{
		Device addedDevice = new Device( currentBody, SWT.NONE, device );
		addedDevice.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
		devices.add( addedDevice );
	}
	
	public void setVisibleDivice(String name)
	{
		for( Device device: devices )
		{
			if( device.getDeviceName().equalsIgnoreCase( name ))
			{
				GridData deviceLayoutData = new GridData( GridData.FILL, GridData.FILL,
						true, true );
				deviceLayoutData.verticalSpan = 3;
				device.setLayoutData( deviceLayoutData );
				device.setVisible( true );
			}
			else
			{
				device.setVisible( false );
			}
		}
		currentBody.layout();
	}
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public void updateMeasurmentFormDevice( Integer deviceAddress,
			ELANRxBroadcastFrame frame )
	{
		for( Device device: devices )
		{
			if( device.getDeviceAddress() == deviceAddress)
				device.updateMeasurment( frame );			
		}
	}
}
