package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class Network extends Composite
{
	GridData compositeData;
	private Group grpOneNetwork;
	private Composite body;
	private Label lblState;
	private String networkName;
	private Label lblStateMessage;
	private Label lblDevicesCount;
	private Label lblDevicesCountValue;
	private Composite bodyForMeasure;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Network( Composite parent, int style, String name )
	{//FIXME add all device current measurement to this frame
		super( parent, style );
//		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
//				true, false );
//		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );		
		//this.setLayoutData( compositeData );		

		grpOneNetwork = new Group( this, SWT.NONE );
		grpOneNetwork.setText( name );
		grpOneNetwork.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		body = new Composite( grpOneNetwork, SWT.NONE );
		body.setLayout( new GridLayout( 2, false ) );		

		lblState = new Label( body, SWT.NONE );
		lblState.setText( "Stan sieci" );
		lblStateMessage = new Label( body, SWT.NONE );
		lblStateMessage.setText( "Niepołączona" );	
		
		lblDevicesCount = new Label( body, SWT.WRAP );
		GridData gd_lblDevicesCount = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblDevicesCount.widthHint = 100;
		lblDevicesCount.setLayoutData(gd_lblDevicesCount);
		lblDevicesCount.setText( "Liczba urządzeń w sieci" );
		lblDevicesCountValue = new Label( body, SWT.NONE );
		lblDevicesCountValue.setText( "-" );	
		
		bodyForMeasure = new Composite( body, SWT.NONE );
		bodyForMeasure.setLayoutData( new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1) );
		
		
		body.layout();
		layout();
		this.setNetworkName( name );
	}

	@Override
	public void setEnabled( boolean arg0 )
	{
		super.setEnabled( arg0 );
		grpOneNetwork.setEnabled( arg0 );
		body.layout();
		layout();
	}

	/**
	 * @return the deviceName
	 */
	public String getNetworkName()
	{
		return networkName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setNetworkName( String name )
	{
		this.networkName = name;
		this.grpOneNetwork.setText( name );
		body.layout();
		layout();
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public void setNetworkConnected( int networkSize )
	{
		lblStateMessage.setText( "Podłączona" );
		lblDevicesCountValue.setText( String.valueOf( networkSize ) );
		body.layout();
		layout();
	}
	
	public void addDeviceTableWithMeasure( Control[] devices )
	{
		bodyForMeasure.layout( devices );
	}
}
