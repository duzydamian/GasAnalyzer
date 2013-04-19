package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import pl.industrum.gasanalyzer.i18n.Messages;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class MainMenu extends Menu
{

	/**
	 * @param arg0
	 * @param arg1
	 * @wbp.parser.entryPoint
	 */
	public MainMenu(Decorations arg0, int arg1)
	{
		super(arg0, arg1);
		MenuItem mntmPlik = new MenuItem(this, SWT.CASCADE);
		mntmPlik.setText(Messages.getString("MainMenu.0")); //$NON-NLS-1$
		
		Menu menu_1 = new Menu(mntmPlik);
		mntmPlik.setMenu(menu_1);
		
		MenuItem mntmWyjcie = new MenuItem(menu_1, SWT.NONE);
		mntmWyjcie.setText(Messages.getString("MainMenu.1")); //$NON-NLS-1$
		
		MenuItem mntmEdycja = new MenuItem(this, SWT.CASCADE);
		mntmEdycja.setText(Messages.getString("MainMenu.2")); //$NON-NLS-1$
		
		Menu menu_2 = new Menu(mntmEdycja);
		mntmEdycja.setMenu(menu_2);
		
		MenuItem mntmPomoc = new MenuItem(this, SWT.CASCADE);
		mntmPomoc.setText(Messages.getString("MainMenu.3")); //$NON-NLS-1$
		
		Menu menu_3 = new Menu(mntmPomoc);
		mntmPomoc.setMenu(menu_3);
		
		MenuItem mntmO = new MenuItem(menu_3, SWT.NONE);
		mntmO.setText(Messages.getString("MainMenu.4")); //$NON-NLS-1$
	}

	protected void checkSubclass()
	{
	}	

}
