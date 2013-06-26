package pl.industrum.gasanalyzer.gui.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import pl.industrum.gasanalyzer.gui.GasAnalyzerGUI;

public class NetworkScan extends Dialog
{

	protected Object result;
	protected Shell shell;
	private ProgressBar progressBar;
	private Label lblWait;
	private Display display;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NetworkScan( Shell parent, int style )
	{
		super( parent, style );
		setText( "Skanowanie sieci" );
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open()
	{
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
		while ( !shell.isDisposed() )
		{
			if ( !display.readAndDispatch() )
			{
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents()
	{
		shell = new Shell( getParent(), getStyle() );
		shell.setSize( 400, 80 );
		shell.setText( getText() );
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		
		lblWait = new Label(shell, SWT.NONE);
		lblWait.setText("Proszę czekać...");
		
		progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setMinimum( 0 );
		progressBar.setMaximum( 100 );

		shell.getDisplay().asyncExec(new Runnable()
		{
			public void run()
			{
				for (int i=0; i<60; i++)
				{				
					try
					{						
						switch ( i%4 )
						{
						case 0:
							lblWait.setText("Proszę czekać");
							break;
						case 1:
							lblWait.setText("Proszę czekać.");
							break;
						case 2:
							lblWait.setText("Proszę czekać..");
							break;
						case 3:
							lblWait.setText("Proszę czekać...");
							break;
						default:
							lblWait.setText("Proszę czekać");
							break;
						}
						progressBar.setSelection(i);
						if ( GasAnalyzerGUI.isDevelop() )
						{
							Thread.sleep(10);
						}
						else
						{
							Thread.sleep(100);
						}						
					}
					catch (Throwable e)
					{
						e.printStackTrace();
					}					
				}
				shell.close();
			}
		});
	}

}
