package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class SurveyFrame extends Composite
{
	GridData surveyFrameData;
	private Text text;
	private Button button;
	private DateTime dateTime;
	private Label lblData;
	private Combo combo;
	private Label lblUytkownik;
	private GridData gd_button;
	private Label lblNazwa;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SurveyFrame(Composite parent, int style)
	{
		super(parent, style);
		surveyFrameData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		surveyFrameData.horizontalSpan = 6;
		this.setLayoutData(surveyFrameData);
		setLayout(new GridLayout(2, false));
		
		lblNazwa = new Label(this, SWT.NONE);
		lblNazwa.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNazwa.setText("Nazwa");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblData = new Label(this, SWT.NONE);
		lblData.setText("Data");
		
		dateTime = new DateTime(this, SWT.BORDER);
		
		lblUytkownik = new Label(this, SWT.NONE);
		lblUytkownik.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUytkownik.setText("Użytkownik");
		
		combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		button = new Button(this, SWT.UP);
		button.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				hide();
			}
		});
		gd_button = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 81;
		button.setLayoutData(gd_button);

	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	private void hide()
	{
		button.setVisible(false);
		this.setVisible(false);
	}
	
	@SuppressWarnings("unused")
	private void show()
	{
		this.setVisible(true);
	}
	
	public Text getText()
	{
		return text;
	}

	public DateTime getDateTime()
	{
		return dateTime;
	}

	public Combo getCombo()
	{
		return combo;
	}

	public Label getLblUytkownik()
	{
		return lblUytkownik;
	}
}
