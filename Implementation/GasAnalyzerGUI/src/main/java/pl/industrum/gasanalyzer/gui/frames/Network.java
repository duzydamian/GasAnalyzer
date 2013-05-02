package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.types.UsefulImage;

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

		body = new Composite( grpOneNetwork, SWT.NONE );
		body.setLayout( new GridLayout( 2, false ) );

		lblState = new Label( body, SWT.NONE );
		lblState.setText( "Stan sieci" );
		lblStateMessage = new Label( body, SWT.NONE );
		lblStateMessage.setText( "Niepołączona" );	
		
		lblDevicesCount = new Label( body, SWT.WRAP );
		lblDevicesCount.setText( "Liczba urządzeń w sieci" );
		lblDevicesCountValue = new Label( body, SWT.NONE );
		lblDevicesCountValue.setText( "" );	
		
		GridData globaGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		globaGridData.horizontalSpan = 2;
		final CTabFolder folder = new CTabFolder(body, SWT.BORDER);
		folder.setLayoutData( globaGridData );
		folder.setSimple(false);
		//folder.setUnselectedImageVisible(false);
		folder.setUnselectedCloseVisible(false);

		CTabItem itemWarning = new CTabItem(folder, SWT.CLOSE);
		itemWarning.setText("Warning");
		itemWarning.setImage(UsefulImage.WARNING.getImage());
		Text textitemWarning = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		textitemWarning.setText("Text for item "+"\n\none, two, three\n\nabcdefghijklmnop");
		itemWarning.setControl(textitemWarning);

		CTabItem itemError = new CTabItem(folder, SWT.CLOSE);
		itemError.setText("Error");
		itemError.setImage(UsefulImage.ERROR.getImage());
		Text textError = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		textError.setText("Text for item "+"\n\none, two, three\n\nabcdefghijklmnop");
		itemError.setControl(textError);
			
		folder.setMinimizeVisible(true);
		folder.setMaximizeVisible(true);
		folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			public void minimize(CTabFolderEvent event) {
				folder.setMinimized(true);
				GridData localGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
				localGridData.horizontalSpan = 2;
				folder.setLayoutData( localGridData) ;
				body.layout(true);
			}
			public void maximize(CTabFolderEvent event) {
				folder.setMaximized(true);
				GridData localGridData = new GridData(SWT.FILL, SWT.FILL, true, true); 
				localGridData.horizontalSpan = 2;
				folder.setLayoutData( localGridData );
				body.layout(true);
			}
			public void restore(CTabFolderEvent event) {
				folder.setMinimized(false);
				folder.setMaximized(false);
				GridData localGridData = new GridData(SWT.FILL, SWT.FILL, true, false); 
				localGridData.horizontalSpan = 2;				
				folder.setLayoutData( localGridData );
				body.layout(true);
			}
		});
		
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
}
