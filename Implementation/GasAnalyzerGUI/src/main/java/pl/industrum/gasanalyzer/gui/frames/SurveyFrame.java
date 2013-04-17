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

import pl.industrum.gasanalyzer.gui.dialogs.NewSurveyUser;
import pl.industrum.gasanalyzer.gui.dialogs.DatePicker;
import pl.industrum.gasanalyzer.gui.dialogs.NewSurveyPlace;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class SurveyFrame extends Composite
{	

	GridData surveyFrameData;
	private Label lblSurveyName;
	private Text txtSurveyName;
	
	private Label lblSurveyDate;
	private DateTime txtSurveyDate;
		
	private Label lblSurveyUser;
	private Combo listSurveyUser;
	private Button btnNewSurveyUser;
	
	private Button hideShowButton;		
	
	private Label lblSurveyPlace;
	private Button btnNewSurveyPlace;
	private Combo listSurveyPlace;
	
	private Label lblSurveyLoad;
	private Text textSurveyLoad;
	
	private Label lblSurveySpecialConditions;
	private StyledText styledTextSurveySpecialConditions;
		
	private Label lblComment;
	private StyledText styledTextComment;
	
	private boolean showed;
	private Composite surveyForm;
	private Button btnSelectDate;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SurveyFrame(final Composite parent, int style)
	{
		super(parent, style);
		surveyFrameData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		surveyFrameData.horizontalSpan = 6;
		this.setLayoutData(surveyFrameData);
		setLayout(new GridLayout(1, false));
		
		surveyForm = new Composite(this, SWT.NONE);
		surveyForm.setLayout(new GridLayout(3, false));
		
		lblSurveyName = new Label(surveyForm, SWT.NONE);
		lblSurveyName.setSize(44, 17);
		lblSurveyName.setText("Nazwa");
		
		txtSurveyName = new Text(surveyForm, SWT.BORDER);
		txtSurveyName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		lblSurveyDate = new Label(surveyForm, SWT.NONE);
		lblSurveyDate.setText("Data");
		
		txtSurveyDate = new DateTime(surveyForm, SWT.DATE);
		txtSurveyDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		btnSelectDate = new Button(surveyForm, SWT.NONE);
		btnSelectDate.setText("Wybierz");	
		btnSelectDate.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected (SelectionEvent e)
			{
				DatePicker datePicker = new DatePicker(parent.getShell(), SWT.NONE);
				int[] date = datePicker.open();
				if (date != null)
					txtSurveyDate.setDate(date[0], date[1], date[2]);
				
			}
		});
		
		lblSurveyUser = new Label(surveyForm, SWT.NONE);
		lblSurveyUser.setText("Prowadzący pomiary");
		
		listSurveyUser = new Combo(surveyForm, SWT.NONE);
		listSurveyUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		listSurveyUser.add("Jan Wężyk");
		listSurveyUser.add("Kuba guzik");
		
		btnNewSurveyUser = new Button(surveyForm, SWT.NONE);
		btnNewSurveyUser.setText("Nowy");	
		btnNewSurveyUser.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected (SelectionEvent e)
			{
				NewSurveyUser addSurveyUser = new NewSurveyUser(parent.getShell(), SWT.NONE);
				addSurveyUser.open();
				
				refreshListSurveyUser();
			}
		});
		
		lblSurveyPlace = new Label(surveyForm, SWT.NONE);
		lblSurveyPlace.setText("Obiekt");
		
		listSurveyPlace = new Combo(surveyForm, SWT.NONE);
		listSurveyPlace.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		listSurveyPlace.add("Narnia");
		listSurveyPlace.add("Mordor");
		
		btnNewSurveyPlace = new Button(surveyForm, SWT.NONE);
		btnNewSurveyPlace.setText("Nowy");
		btnNewSurveyPlace.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected (SelectionEvent e)
			{
				NewSurveyPlace newSurveyPlace = new NewSurveyPlace(parent.getShell(), SWT.NONE);
				newSurveyPlace.open();
				
				refreshListSurveyPlace();
			}
		});
		
		lblSurveyLoad = new Label(surveyForm, SWT.NONE);
		lblSurveyLoad.setText("Obciążenie");
		
		textSurveyLoad = new Text(surveyForm, SWT.BORDER);
		textSurveyLoad.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		lblSurveySpecialConditions = new Label(surveyForm, SWT.NONE);
		lblSurveySpecialConditions.setText("Warunki szczególne");
		
		styledTextSurveySpecialConditions = new StyledText(surveyForm, SWT.BORDER);
		styledTextSurveySpecialConditions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		lblComment = new Label(surveyForm, SWT.NONE);
		lblComment.setText("Komentarz");
		
		styledTextComment = new StyledText(surveyForm, SWT.BORDER);
		styledTextComment.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
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
		surveyForm.setVisible(false);
		hideShowButton.reskin(SWT.ARROW | SWT.DOWN);
		showed = false;
	}
	
	private void show()
	{
		surveyForm.setVisible(true);
		hideShowButton.reskin(SWT.ARROW | SWT.UP);
		showed = true;
	}
	
	private void refreshListSurveyUser()
	{
		
	}
	
	private void refreshListSurveyPlace()
	{
		
	}
}
