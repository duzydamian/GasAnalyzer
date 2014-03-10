package pl.industrum.gasanalyzer.gui;
 
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import pl.industrum.gasanalyzer.test.TestVector;
import pl.industrum.gasanalyzer.types.UsefulColor;
import pl.industrum.gasanalyzer.types.UsefulImage;

public class SplashScreen
{//FIXME TODO check on windows
	private TestVector testVector;
	private ProgressBar progressBar;
	private int testsCount;
	private FormData progressBarData;
	private Label label;
	private Label stateLabel;
	private FormData stateLabelData;
	private FormLayout layout;
	private Shell splash;
	private GC gc;
	private Image image;
	private Display display;
	private Rectangle splashRect;
	private Rectangle displayRect;
	private boolean allTestComplete;

	/**
	 * @wbp.parser.entryPoint
	 */
	public SplashScreen()
	{
		super();
		allTestComplete = true;
		display = new Display();		
		image = new Image(display, 400, 320);		
		gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		gc.fillRectangle(image.getBounds());
		gc.setFont(new Font(display, "Arial", 18, SWT.BOLD | SWT.ITALIC));
		gc.drawText("Gas Analyzer", 10, 10);
		gc.setFont(new Font(display, "Arial", 12, SWT.BOLD));
		gc.drawText("Autorzy: Damian Karbowiak & Grzegorz Powała", 10, 40);
		gc.drawImage( UsefulImage.INDUSTRUM_LOGO.getImage(), (image.getBounds().width/2 - UsefulImage.INDUSTRUM_LOGO.getImage().getBounds().width/2), 60);
		gc.drawImage( UsefulImage.POLSL_LOGO.getImage(), 10, UsefulImage.INDUSTRUM_LOGO.getImage().getBounds().height+60 );
		gc.drawImage( UsefulImage.IMIUE_LOGO.getImage(), (image.getBounds().width/2 - UsefulImage.IMIUE_LOGO.getImage().getBounds().width/2), UsefulImage.INDUSTRUM_LOGO.getImage().getBounds().height+60 );
		gc.drawImage( UsefulImage.ZKIWP_LOGO.getImage(), (image.getBounds().width - UsefulImage.ZKIWP_LOGO.getImage().getBounds().width)-10, UsefulImage.INDUSTRUM_LOGO.getImage().getBounds().height+60 );
		gc.dispose();
		
		splash = new Shell(SWT.ON_TOP);
		splash.setSize( 400, 400 );
		splash.setBackground( UsefulColor.WHITE.getColor() );
		
		layout = new FormLayout();
		splash.setLayout(layout);				
		
		label = new Label(splash, SWT.NONE);
		label.setImage(image);
		
		stateLabel = new Label(splash, SWT.NONE);
		stateLabel.setBackground( UsefulColor.WHITE.getColor() );
		stateLabel.setText("Test przed uruchomieniem...");
		stateLabelData = new FormData();
		stateLabelData.left = new FormAttachment (0, 5);
		stateLabelData.right = new FormAttachment (100, -5);
		stateLabelData.bottom = new FormAttachment (93, -5);
		stateLabel.setLayoutData(stateLabelData);
		
		progressBar = new ProgressBar(splash, SWT.NONE);
		progressBar.setMaximum(testsCount);
		progressBarData = new FormData ();
		progressBarData.left = new FormAttachment (0, 5);
		progressBarData.right = new FormAttachment (100, -5);
		progressBarData.bottom = new FormAttachment (100, -5);		
		progressBar.setLayoutData(progressBarData);
		
		splashRect = splash.getBounds();
		displayRect = display.getBounds();
		
		int x = (displayRect.width - splashRect.width) / 2;
		int y = (displayRect.height - splashRect.height) / 2;
		
		testVector = new TestVector(splash.getShell());
		testsCount = testVector.size();
		
		splash.setLocation(x, y);
		splash.layout();
		splash.open();
		
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				for (int i=0; i<testsCount; i++)
				{		
					if ( allTestComplete )
					{
						try
						{
							stateLabel.setText(testVector.get(i).getName());
							testVector.get(i).test();
							allTestComplete = testVector.get( i ).isPassed(); 
							
							Thread.sleep(100);
						}
						catch (Throwable e)
						{
							e.printStackTrace();
						}
					}
					progressBar.setSelection(i+1);
				}
				splash.close();
				image.dispose();
			}
		});
		
		while (!splash.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
	}
	
	/**
	 * @throws Exception 
	 * @wbp.parser.entryPoint
	 */
	public void open() throws Exception
	{
			//throw new Exception(); //Ta linia pozwala na przetestowanie okna SendExceptionCatched
	}

	/**
	 * @return the allTestComplete
	 * @wbp.parser.entryPoint
	 */
	public boolean isAllTestComplete()
	{
		return allTestComplete;
	}
}
