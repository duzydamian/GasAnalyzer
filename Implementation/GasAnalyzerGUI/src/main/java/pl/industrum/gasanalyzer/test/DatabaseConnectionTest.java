/**
 * 
 */
package pl.industrum.gasanalyzer.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class DatabaseConnectionTest extends Test
{
	private MessageBox messageDialog;
	
	/**
	 * @param parentShell 
	 * 
	 */
	public DatabaseConnectionTest(Shell parentShell)
	{
		super( "Testowanie połączenia do bazy danych" );
		messageDialog = new MessageBox( parentShell, SWT.ICON_ERROR );
		messageDialog.setText( "Błąd" );
		messageDialog
				.setMessage( "Problem z połączeniem do bazy danych." );
	}

	public void test()
	{
		try
		{
			Session session = Hibernate.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.getTransaction().commit();
			setPassed();
		}
		catch(Exception e)
		{
			setFailed();
			e.printStackTrace();
			messageDialog.open();
		}
	}
}
