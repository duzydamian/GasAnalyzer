package pl.industrum.gasanalyzer.gui;
 
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class SplashScreen
{

	private static ProgressBar progressBar;
	private static int testsCount;
	private static FormData progressBarData;
	private static Label label;
	private static Label stateLabel;
	private static FormData stateLabelData;
	private static FormLayout layout;
	private static Shell splash;
	private static GC gc;
	private static Image image;
	private static Display display;
	private static Rectangle splashRect;
	private static Rectangle displayRect;
	private static FormData labelData;

	public static void main(String[] args)
	{
		display = new Display();
		testsCount = 10;
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
					progressBar.setSelection(i+1);
					try
					{
						Thread.sleep(100);
					}
					catch (Throwable e)
					{
						e.printStackTrace();
					}
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

}
