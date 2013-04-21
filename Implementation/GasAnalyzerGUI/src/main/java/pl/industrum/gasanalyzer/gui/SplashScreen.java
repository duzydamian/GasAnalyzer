package pl.industrum.gasanalyzer.gui;
 
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

import pl.industrum.gasanalyzer.test.TestVector;

public class SplashScreen
{
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

	public SplashScreen()
	{
		super();
	}
	
	public void open()
	{
		allTestComplete = true;
		display = new Display();
		testVector = new TestVector();
		testsCount = testVector.size();
		image = new Image(display, 300, 300);
		gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		gc.fillRectangle(image.getBounds());
		gc.drawText("Splash Screen", 10, 10);
		gc.dispose();
		splash = new Shell(SWT.ON_TOP);
		
		layout = new FormLayout();
		splash.setLayout(layout);
		
		stateLabel = new Label(splash, SWT.NONE);
		stateLabel.setText("Test przed uruchomieniem...");
		
		stateLabelData = new FormData();
		stateLabelData.left = new FormAttachment (0, 5);
		stateLabelData.right = new FormAttachment (100, -5);
		stateLabelData.bottom = new FormAttachment (93, -5);
		stateLabel.setLayoutData(stateLabelData);
		
		progressBar = new ProgressBar(splash, SWT.NONE);
		progressBar.setMaximum(testsCount);
		
		label = new Label(splash, SWT.NONE);
		label.setImage(image);
		
		labelData = new FormData ();
		labelData.right = new FormAttachment (100, 0);
		labelData.bottom = new FormAttachment (100, 0);
		label.setLayoutData(labelData);
		
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
						Thread.sleep(500);
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
	 * @return the allTestComplete
	 */
	public boolean isAllTestComplete()
	{
		return allTestComplete;
	}
}
