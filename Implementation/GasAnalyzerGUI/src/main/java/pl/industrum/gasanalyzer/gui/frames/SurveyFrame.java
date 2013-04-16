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
import org.eclipse.swt.custom.StyledText;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class SurveyFrame extends Composite
{	

	GridData surveyFrameData;
	private Label lblSurveyName;
	private Text surveyName;
	
	private Label lblSurveyDate;
	private DateTime surveyDate;
		
	private Label lblSurveyUserList;
	private Combo surveyUserList;
	private Button btnNewSurveyUser;
	
	private Button hideShowButton;		
	private GridData hideShowButtonGridData;
	
	private Label lblPlace;
	private Button btnNewPlace;
	private Combo combo;
	
	private Label lblLoad;
	private Text text;
	
	private Label lblWarunkiSzczeglne;
	private StyledText styledText;
		
	private Label lblKomentarz;
	private StyledText styledText_1;

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
		setLayout(new GridLayout(3, false));
		
		lblSurveyName = new Label(this, SWT.NONE);
		lblSurveyName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSurveyName.setText("Nazwa");
		
		surveyName = new Text(this, SWT.BORDER);
		surveyName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		lblSurveyDate = new Label(this, SWT.NONE);
		lblSurveyDate.setText("Data");
		
		surveyDate = new DateTime(this, SWT.BORDER);
		new Label(this, SWT.NONE);
		
		lblSurveyUserList = new Label(this, SWT.NONE);
		lblSurveyUserList.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSurveyUserList.setText("Prowadzący pomiary");		
		
		surveyUserList = new Combo(this, SWT.NONE);
		surveyUserList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		surveyUserList.add("Jan Wężyk");
		surveyUserList.add("Kuba guzik");
		
		btnNewSurveyUser = new Button(this, SWT.NONE);
		btnNewSurveyUser.setText("Nowy");
		
		lblPlace = new Label(this, SWT.NONE);
		lblPlace.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPlace.setText("Obiekt");
		
		combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo.add("Narnia");
		combo.add("Mordor");
		
		btnNewPlace = new Button(this, SWT.NONE);
		btnNewPlace.setText("Nowy");
		
		lblLoad = new Label(this, SWT.NONE);
		lblLoad.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLoad.setText("Obciążenie");
		
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		lblWarunkiSzczeglne = new Label(this, SWT.NONE);
		lblWarunkiSzczeglne.setText("Warunki szczególne");
		
		styledText = new StyledText(this, SWT.BORDER);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		new Label(this, SWT.NONE);
		
		lblKomentarz = new Label(this, SWT.NONE);
		lblKomentarz.setText("Komentarz");
		
		styledText_1 = new StyledText(this, SWT.BORDER);
		styledText_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		new Label(this, SWT.NONE);
		
		hideShowButtonGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		hideShowButtonGridData.horizontalSpan = 3;
		
		hideShowButton = new Button(this, SWT.UP);
		hideShowButton.setLayoutData(hideShowButtonGridData);
		hideShowButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				hide();
			}
		});
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	private void hide()
	{
		hideShowButton.setVisible(false);
		this.setVisible(false);
	}
	
	@SuppressWarnings("unused")
	private void show()
	{
		this.setVisible(true);
	}
	
	public Text getText()
	{
		return surveyName;
	}

	public DateTime getDateTime()
	{
		return surveyDate;
	}

	public Combo getCombo()
	{
		return surveyUserList;
	}

	public Label getLblUytkownik()
	{
		return lblSurveyUserList;
	}
}
