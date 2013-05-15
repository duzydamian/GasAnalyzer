package pl.industrum.gasanalyzer.gui.frames;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
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
public abstract class DeviceCollection extends Composite
{
	GridData compositeData;
	private SashForm currentBody;
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

		currentBody = new SashForm( this, SWT.NONE );
		currentBody.setLayout( new GridLayout( 1, false ) );
	}

	@Override
	public void setEnabled( boolean arg0 )
	{
		super.setEnabled( arg0 );
	}
	
	public void addDevice( ELANMeasurementDevice device )
	{
		Device addedDevice = new Device( currentBody, SWT.NONE, device )
		{

			@Override
			public Integer getSurveyID()
			{
				return getSurveyIDFromGUI();
			}

			@Override
			public Integer getStep()
			{
				return getStepFromGUI();
			}
			
		};
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
				device.showCurrent();
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
	public void setVisible( boolean arg0 )
	{
		for( Device device: devices )
		{
			device.setVisible( arg0 );
		}
		currentBody.layout();
		super.setVisible( arg0 );
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
	
	public abstract Integer getSurveyIDFromGUI();
	public abstract Integer getStepFromGUI();
}
