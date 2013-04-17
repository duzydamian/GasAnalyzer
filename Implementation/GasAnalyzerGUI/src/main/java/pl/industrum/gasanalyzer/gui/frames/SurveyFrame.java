package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
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
	private Label lblSurveyName;
	private Text surveyName;
	
	private Label lblSurveyDate;
	private DateTime surveyDate;
		
	private Label lblSurveyUserList;
	private Combo surveyUserList;
	private Button btnNewSurveyUser;
	
	private Button hideShowButton;		
	
	private Label lblPlace;
	private Button btnNewPlace;
	private Combo combo;
	
	private Label lblLoad;
	private Text text;
	
	private Label lblWarunkiSzczeglne;
	private StyledText styledText;
		
	private Label lblKomentarz;
	private StyledText styledText_1;
	
	private boolean showed;
	private Composite form;

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
		setLayout(new GridLayout(1, false));
		
		form = new Composite(this, SWT.NONE);
		GridData gd_form = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_form.widthHint = 666;
		form.setLayoutData(gd_form);
		form.setLayout(new GridLayout(4, false));
		
		lblSurveyName = new Label(form, SWT.NONE);
		lblSurveyName.setSize(44, 17);
		lblSurveyName.setText("Nazwa");
		
		surveyName = new Text(form, SWT.BORDER);
		GridData gd_surveyName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_surveyName.widthHint = 190;
		surveyName.setLayoutData(gd_surveyName);
		
		lblSurveyDate = new Label(form, SWT.NONE);
		lblSurveyDate.setText("Data");
		
		surveyDate = new DateTime(form, SWT.BORDER);
		
		lblSurveyUserList = new Label(form, SWT.NONE);
		lblSurveyUserList.setText("Prowadzący pomiary");
		
		surveyUserList = new Combo(form, SWT.NONE);
		surveyUserList.add("Jan Wężyk");
		surveyUserList.add("Kuba guzik");
		
		btnNewSurveyUser = new Button(form, SWT.NONE);
		btnNewSurveyUser.setText("Nowy");
		new Label(form, SWT.NONE);
		
		lblPlace = new Label(form, SWT.NONE);
		lblPlace.setText("Obiekt");
		
		combo = new Combo(form, SWT.NONE);
		combo.add("Narnia");
		combo.add("Mordor");
		
		btnNewPlace = new Button(form, SWT.NONE);
		btnNewPlace.setText("Nowy");
		new Label(form, SWT.NONE);
		
		lblLoad = new Label(form, SWT.NONE);
		lblLoad.setText("Obciążenie");
		
		text = new Text(form, SWT.BORDER);
		new Label(form, SWT.NONE);
		new Label(form, SWT.NONE);
		
		lblWarunkiSzczeglne = new Label(form, SWT.NONE);
		lblWarunkiSzczeglne.setText("Warunki szczególne");
		
		styledText = new StyledText(form, SWT.BORDER);
		
		lblKomentarz = new Label(form, SWT.NONE);
		lblKomentarz.setText("Komentarz");
		
		styledText_1 = new StyledText(form, SWT.BORDER);
		
		hideShowButton = new Button(this, SWT.ARROW | SWT.UP);
		hideShowButton.setLayoutData(new GridData(GridData.FILL, GridData.GRAB_VERTICAL, true, false));
		hideShowButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (showed)
					hide();				
				else
					show();				
			}
		});
		
		showed = true;
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	private void hide()
	{
		form.setVisible(false);
		hideShowButton.reskin(SWT.ARROW | SWT.DOWN);
		showed = false;
	}
	
	private void show()
	{
		form.setVisible(true);
		hideShowButton.reskin(SWT.ARROW | SWT.UP);
		showed = true;
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
