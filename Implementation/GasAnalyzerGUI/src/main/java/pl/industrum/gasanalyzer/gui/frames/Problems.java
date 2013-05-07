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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import pl.industrum.gasanalyzer.types.Error;
import pl.industrum.gasanalyzer.types.UsefulImage;
import pl.industrum.gasanalyzer.types.Warning;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class Problems extends Composite
{
	private GridData compositeData;
	private Composite body;
	private GridData globaGridData;
	private CTabFolder folder;
	private CTabItem itemWarning;
	private CTabItem itemError;
	private Table tableError;
	private Table tableWarning;
	private String[] columns;

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
		
		globaGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		globaGridData.horizontalSpan = 2;
		folder = new CTabFolder(body, SWT.NONE);
		folder.setLayoutData( globaGridData );
		folder.setSimple(false);
		folder.setUnselectedCloseVisible(false);
		folder.setMinimizeVisible(true);
		
		columns = new String[] {"Kod", "Nazwa", "Opis", "Lokalizacja"};
		
		itemWarning = new CTabItem(folder, SWT.CLOSE);
		itemWarning.setText("Warning");
		itemWarning.setImage(UsefulImage.WARNING.getImage());
		tableWarning = new Table (folder, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tableWarning.setLinesVisible (true);
		tableWarning.setHeaderVisible (true);
		for (int i=0; i<columns.length; i++)
		{
			TableColumn column = new TableColumn (tableWarning, SWT.NONE);
			column.setText (columns [i]);
		}
		for (int i=0; i<columns.length; i++)
		{
			tableWarning.getColumn (i).pack ();
			tableWarning.getColumn (i).setMoveable(true);
		}
		itemWarning.setControl(tableWarning);
		
		itemError = new CTabItem(folder, SWT.CLOSE);
		itemError.setText("Error");
		itemError.setImage(UsefulImage.ERROR.getImage());
		tableError = new Table (folder, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tableError.setLinesVisible (true);
		tableError.setHeaderVisible (true);
		for (int i=0; i<columns.length; i++) {
			TableColumn column = new TableColumn (tableError, SWT.NONE);
			column.setText (columns [i]);
		}
		for (int i=0; i<columns.length; i++) {
			tableError.getColumn (i).pack ();
			tableError.getColumn (i).setMoveable(true);
		}
		itemError.setControl(tableError);
		
		folder.forceFocus();
		folder.showItem( itemError );
		folder.forceFocus();
		
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
		
		Menu menuWarning = new Menu (body.getShell(), SWT.POP_UP);
		tableWarning.setMenu (menuWarning);
		MenuItem itemWarning = new MenuItem (menuWarning, SWT.PUSH);
		itemWarning.setText ("Delete Selection");
		itemWarning.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				tableWarning.remove (tableWarning.getSelectionIndices ());
			}
		});

		Menu menuError = new Menu (body.getShell(), SWT.POP_UP);
		tableError.setMenu (menuError);
		MenuItem itemError = new MenuItem (menuError, SWT.PUSH);
		itemError.setText ("Delete Selection");
		itemError.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				tableError.remove (tableError.getSelectionIndices ());
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

	public void addWarning(Warning warning, String source)
	{
		TableItem item = new TableItem (tableWarning, SWT.NONE);
		item.setText (0, warning.getCode());
		item.setText (1, warning.getMessage());
		item.setText (2, warning.getDescription());
		item.setText (3, source);				
		for (int i=0; i<columns.length; i++)
		{
			tableWarning.getColumn (i).pack ();
			tableWarning.getColumn (i).setMoveable(true);
		}
		folder.showItem( itemWarning );
		folder.forceFocus();
	}
	
	public void addError(Error error, String source)
	{
		TableItem item = new TableItem (tableError, SWT.NONE);
		item.setText (0, error.getCode());
		item.setText (1, error.getMessage());
		item.setText (2, error.getDescription());
		item.setText (3, source);		
		for (int i=0; i<columns.length; i++)
		{
			tableError.getColumn (i).pack ();
			tableError.getColumn (i).setMoveable(true);
		}
		folder.showItem( itemError );
		folder.forceFocus();
	}
}
