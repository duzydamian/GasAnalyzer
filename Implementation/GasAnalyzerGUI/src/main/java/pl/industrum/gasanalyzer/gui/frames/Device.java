package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import pl.industrum.gasanalyzer.elan.communication.network.ELANMeasurementDevice;
import pl.industrum.gasanalyzer.elan.types.ELANMeasuredVariable;
import pl.industrum.gasanalyzer.i18n.Messages;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class Device extends Composite
{
	GridData compositeData;
	private Group grpOneDIvice;
	private TabFolder tabFolder;
	private Composite currentBody;
	private Label lblState;
	private String deviceName;
	private Integer deviceAddress;
	private Label lblStateMessage;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Device( Composite parent, int style, ELANMeasurementDevice device )
	{
		super( parent, style );
//		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
//				true, false );
//		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );		
		//this.setLayoutData( compositeData );
		this.deviceName = device.getName() ;
		this.deviceAddress = device.getDeviceAddress();

		grpOneDIvice = new Group( this, SWT.NONE );
		grpOneDIvice.setText( deviceName );
		grpOneDIvice.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		tabFolder = new TabFolder( grpOneDIvice, SWT.NONE );

		TabItem tbitmCurrent = new TabItem( tabFolder, SWT.NONE );
		tbitmCurrent.setText( Messages.getString( "Device.tbtmBiecy.text" ) ); //$NON-NLS-1$

		currentBody = new Composite( tabFolder, SWT.NONE );
		tbitmCurrent.setControl( currentBody );
		currentBody.setLayout( new GridLayout( 3, false ) );

		for( ELANMeasuredVariable measurement: device.getDeviceInformation() )
		{
			Label lblMeasuredVariable = new Label( currentBody, SWT.NONE );
			lblMeasuredVariable.setText( measurement.name() );
			Label lblMeasuredValue = new Label( currentBody, SWT.NONE );
			lblMeasuredValue.setText( "0" );
			Label lblMeasuredDimension = new Label( currentBody, SWT.NONE );
			lblMeasuredDimension.setText( measurement.name() );
		}
		lblState = new Label( currentBody, SWT.NONE );
		lblState.setText( Messages.getString( "Device.lblStan.text" ) );
		lblStateMessage = new Label( currentBody, SWT.NONE );
		lblStateMessage.setText( Messages.getString( "Device.lblOk.text" ) );		

		TabItem tbitmHistory = new TabItem( tabFolder, SWT.NONE );
		tbitmHistory.setText( Messages.getString( "Device.tbtmNewItem.text" ) ); //$NON-NLS-1$

		Composite historyBody = new Composite( tabFolder, SWT.NONE );
		tbitmHistory.setControl( historyBody );
	}

	@Override
	public void setEnabled( boolean arg0 )
	{
		super.setEnabled( arg0 );
		grpOneDIvice.setEnabled( arg0 );
		tabFolder.setEnabled( arg0 );
	}
	
	public void addELANMeasuredVariable()
	{
		//for( iterable_type iterable_element: iterable )
		{
			
		}
	}
	
	public void updateMeasurment()
	{
		
	}
	
	/**
	 * @return the deviceName
	 */
	public String getDeviceName()
	{
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName( String deviceName )
	{
		this.deviceName = deviceName;
	}

	public Integer getDeviceAddress()
	{
		return deviceAddress;
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
