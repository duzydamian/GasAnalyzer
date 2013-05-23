package pl.industrum.gasanalyzer.gui.frames;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
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
public abstract class Problems extends Composite
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "dd/MM/yyyy HH:mm", Locale.getDefault() );
	
	private GridData compositeData;
	private Composite body;
	private GridData globaGridData;
	private CTabFolder folder;
	private CTabItem itemWarning;
	private CTabItem itemError;
	private Table tableError;
	private Table tableWarning;
	private String[] columns;
	private MenuItem menuItemWarning;
	private MenuItem menuitemError;
	private MenuItem menuItemWarning2;
	private MenuItem menuitemError2;

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
		folder.setMaximized(true);
		folder.setUnselectedCloseVisible(false);
		
		columns = new String[] {"Czas wystąpienia", "Kod", "Nazwa", "Opis", "Źródło"};
		
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
		
		Menu menuWarning = new Menu (body.getShell(), SWT.POP_UP);
		tableWarning.setMenu (menuWarning);
		menuItemWarning = new MenuItem (menuWarning, SWT.PUSH);
		menuItemWarning.setText ("Usuń wybrany");
		menuItemWarning.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				tableWarning.remove (tableWarning.getSelectionIndices ());
				hideIfEmpty();
			}
		});
		new MenuItem( menuWarning, SWT.SEPARATOR );
		menuItemWarning2 = new MenuItem (menuWarning, SWT.PUSH);
		menuItemWarning2.setText ("Usuń wszystkie");
		menuItemWarning2.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				tableWarning.removeAll();
				hideIfEmpty();
			}
		});
		

		Menu menuError = new Menu (body.getShell(), SWT.POP_UP);
		tableError.setMenu (menuError);
		menuitemError = new MenuItem (menuError, SWT.PUSH);
		menuitemError.setText ("Usuń wybrany");
		menuitemError.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				tableError.remove (tableError.getSelectionIndices ());
				hideIfEmpty();
			}			
		});		
		new MenuItem( menuError, SWT.SEPARATOR );
		menuitemError2 = new MenuItem (menuError, SWT.PUSH);
		menuitemError2.setText ("Usuń szystkie");
		menuitemError2.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				tableError.removeAll();
				hideIfEmpty();
			}			
		});
		
		body.layout();
		layout();		
		
		hideIfEmpty();
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	private void hideIfEmpty()
	{
		if ( tableWarning.getItems().length == 0 & tableError.getItems().length == 0 )
		{
			setVisible( false );
		}
		else if ( tableWarning.getItems().length == 0 & tableError.getItems().length != 0 )
		{
			showError();
		}
		else if ( tableWarning.getItems().length != 0 & tableError.getItems().length == 0 )
		{
			showWarning();
		}		
		
		layoutMainWindow();
	}

	private void show()
	{
		setVisible( true );
		layoutMainWindow();
	}

	public void addWarning(Warning warning, String source)
	{
		//check if warning with code per source existing in table
		for( TableItem testedItem: tableWarning.getItems() )
		{
			if ( testedItem.getText(1).equalsIgnoreCase( warning.getCode() ) & testedItem.getText(4).equalsIgnoreCase( source ) )
			{
				return;
			}
		}
		
		TableItem item = new TableItem (tableWarning, SWT.NONE);
		item.setText (0, dateFormater.format( new Date() ));
		item.setText (1, warning.getCode());
		item.setText (2, warning.getMessage());
		item.setText (3, warning.getDescription());
		item.setText (4, source);				
		for (int i=0; i<columns.length; i++)
		{
			tableWarning.getColumn (i).pack ();
			tableWarning.getColumn (i).setMoveable(true);
		}
		
		if ( !isVisible() )
		{
			show();
		}
		
		showWarning();
	}
	
	public void showWarning()
	{
		folder.showItem( itemWarning );
		folder.forceFocus();
	}
	
	public void addError(Error error, String source)
	{
		//check if error with code per source existing in table
		for( TableItem testedItem: tableError.getItems() )
		{
			if ( testedItem.getText(1).equalsIgnoreCase( error.getCode() ) & testedItem.getText(4).equalsIgnoreCase( source ) )
			{
				return;
			}
		}		
		
		TableItem item = new TableItem (tableError, SWT.NONE);
		item.setText (0, dateFormater.format( new Date() ));
		item.setText (1, error.getCode());
		item.setText (2, error.getMessage());
		item.setText (3, error.getDescription());
		item.setText (4, source);		
		for (int i=0; i<columns.length; i++)
		{
			tableError.getColumn (i).pack ();
			tableError.getColumn (i).setMoveable(true);
		}
		
		if ( !isVisible() )
		{
			show();
		}
		
		showError();
	}
	
	public void showError()
	{
		folder.showItem( itemError );
		folder.forceFocus();
	}
	
	public abstract void layoutMainWindow();
}
