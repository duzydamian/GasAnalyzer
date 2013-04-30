package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
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
	private Composite currentBody;
	private Label lblState;
	private String networkName;
	private Label lblStateMessage;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Network( Composite parent, int style, String name )
	{
		super( parent, style );
//		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
//				true, false );
//		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );		
		//this.setLayoutData( compositeData );		

		grpOneNetwork = new Group( this, SWT.NONE );
		grpOneNetwork.setText( name );
		grpOneNetwork.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		currentBody = new Composite( grpOneNetwork, SWT.NONE );
		currentBody.setLayout( new GridLayout( 2, false ) );

		lblState = new Label( currentBody, SWT.NONE );
		lblState.setText( "Stan sieci" );
		lblStateMessage = new Label( currentBody, SWT.NONE );
		lblStateMessage.setText( "Niepołączona" );	
		
		this.setNetworkName( name );
	}

	@Override
	public void setEnabled( boolean arg0 )
	{
		super.setEnabled( arg0 );
		grpOneNetwork.setEnabled( arg0 );
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
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
