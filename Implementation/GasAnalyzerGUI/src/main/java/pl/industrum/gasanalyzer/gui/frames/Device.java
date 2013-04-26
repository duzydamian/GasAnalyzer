package pl.industrum.gasanalyzer.gui.frames;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;
import pl.industrum.gasanalyzer.i18n.Messages;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

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
	private Label lblStan;
	private ArrayList<ELANMeasurement> measurements;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Device( Composite parent, int style, String name )
	{
		super( parent, style );
//		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
//				true, false );
//		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );		
		//this.setLayoutData( compositeData );
		this.measurements = new ArrayList<ELANMeasurement>();

		grpOneDIvice = new Group( this, SWT.NONE );
		grpOneDIvice.setText( name );
		grpOneDIvice.setLayout( new FillLayout( SWT.HORIZONTAL ) );

		tabFolder = new TabFolder( grpOneDIvice, SWT.NONE );

		TabItem tbitmCurrent = new TabItem( tabFolder, SWT.NONE );
		tbitmCurrent.setText( Messages.getString( "Device.tbtmBiecy.text" ) ); //$NON-NLS-1$

		currentBody = new Composite( tabFolder, SWT.NONE );
		tbitmCurrent.setControl( currentBody );
		currentBody.setLayout( new GridLayout( 2, false ) );

		lblStan = new Label( currentBody, SWT.NONE );
		lblStan.setText( Messages.getString( "Device.lblStan.text" ) );
		Label lblOk = new Label( currentBody, SWT.NONE );
		lblOk.setText( Messages.getString( "Device.lblOk.text" ) );		

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
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
