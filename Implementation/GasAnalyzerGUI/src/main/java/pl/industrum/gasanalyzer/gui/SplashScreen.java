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
	private FormData labelData;
	private boolean allTestComplete;
	private Label industrumLogo;
	private FormData industrumLogoData;
	private Label imiueLogo;
	private Label zkiwpLogo;
	private Label polslLogo;
	private FormData imiueLogoData;
	private FormData zkiwpLogoData;
	private FormData polslLogoData;

	/**
	 * @wbp.parser.entryPoint
	 */
	public SplashScreen()
	{
		super();
		allTestComplete = true;
		display = new Display();
		testVector = new TestVector();
		testsCount = testVector.size();
		image = new Image(display, 400, 400);
		gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		gc.fillRectangle(image.getBounds());
		gc.setFont(new Font(display, "Arial", 14, SWT.BOLD | SWT.ITALIC));
		gc.drawText("Gas Analyzer", 10, 10);
		gc.dispose();
		splash = new Shell(SWT.ON_TOP);
		
		layout = new FormLayout();
		splash.setLayout(layout);				
		
		label = new Label(splash, SWT.NONE);
		label.setImage(image);
		labelData = new FormData ();
		labelData.right = new FormAttachment (100, 0);
		labelData.bottom = new FormAttachment (100, 0);
		label.setLayoutData(labelData);		
		
		industrumLogo = new Label(splash, SWT.NONE);
		industrumLogo.setImage( UsefulImage.INDUSTRUM_LOGO.getImage() );
		industrumLogoData = new FormData();
		industrumLogoData.bottom = new FormAttachment (50, -5);
		industrumLogo.setLayoutData(industrumLogoData);
	
		imiueLogo = new Label(splash, SWT.NONE);
		imiueLogo.setImage( UsefulImage.IMIUE_LOGO.getImage() );
		imiueLogoData = new FormData();
		imiueLogoData.left = new FormAttachment (2, 0);	
		imiueLogoData.bottom = new FormAttachment (80, -5);
		imiueLogo.setLayoutData(imiueLogoData);
		
		zkiwpLogo = new Label(splash, SWT.NONE);
		zkiwpLogo.setImage( UsefulImage.ZKIWP_LOGO.getImage() );
		zkiwpLogoData = new FormData();
		zkiwpLogoData.left = new FormAttachment (28, 0);
		zkiwpLogoData.bottom = new FormAttachment (80, -5);
		zkiwpLogo.setLayoutData(zkiwpLogoData);
		
		polslLogo = new Label(splash, SWT.NONE);
		polslLogo.setImage( UsefulImage.POLSL_LOGO.getImage() );
		polslLogoData = new FormData();
		polslLogoData.left = new FormAttachment (72, 0);		
		polslLogoData.bottom = new FormAttachment (80, -5);
		polslLogo.setLayoutData(polslLogoData);
		
		stateLabel = new Label(splash, SWT.NONE);
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
		
		splash.pack();
		
		splashRect = splash.getBounds();
		displayRect = display.getBounds();
		
		int x = (displayRect.width - splashRect.width) / 2;
		int y = (displayRect.height - splashRect.height) / 2;
		
		splash.setLocation(x, y);
		splash.open();
		
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				for (int i=0; i<testsCount; i++)
				{													
					try
					{
						stateLabel.setText(testVector.get(i).getName());
						testVector.get(i).test();
						Thread.sleep(100);
					}
					catch (Throwable e)
					{
						e.printStackTrace();
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
