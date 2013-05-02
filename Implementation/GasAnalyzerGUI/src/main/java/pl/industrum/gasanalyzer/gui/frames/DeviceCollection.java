package pl.industrum.gasanalyzer.gui.frames;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import pl.industrum.gasanalyzer.elan.communication.network.ELANMeasurementDevice;

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
		currentBody.setLayout( new FillLayout( SWT.VERTICAL ) );
	}

	@Override
	public void setEnabled( boolean arg0 )
	{
		super.setEnabled( arg0 );
	}
	
	public void addDevice( ELANMeasurementDevice device )
	{
		devices.add( new Device( currentBody, SWT.NONE, device ) );
	}
	
	public void setVisibleDivice(String name)
	{
		for( Device device: devices )
		{
			if( device.getDeviceName().equalsIgnoreCase( name ))
				device.setVisible( true );
			else
				device.setVisible( false );
		}
		currentBody.layout();
	}
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
