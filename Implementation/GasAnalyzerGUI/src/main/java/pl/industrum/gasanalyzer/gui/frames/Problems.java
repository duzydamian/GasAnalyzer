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
import org.eclipse.swt.widgets.Text;

import pl.industrum.gasanalyzer.types.UsefulImage;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public abstract class Problems extends Composite
{
	private GridData compositeData;
	private Composite body;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Problems( Composite parent, int style )
	{
		super( parent, style );
		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
				true, false );
		compositeData.horizontalSpan = 6;
		this.setLayoutData( compositeData );
		setLayout(new FillLayout(SWT.HORIZONTAL));

		body = new Composite( this, SWT.NONE );
		body.setLayout( new GridLayout( 2, false ) );
		
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
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	@SuppressWarnings( "unused" )
	private void hide()
	{
		this.setVisible( false );
	}

	@SuppressWarnings( "unused" )
	private void show()
	{
		this.setVisible( true );
	}

	public abstract void addWarning();
	public abstract void addError();
}
