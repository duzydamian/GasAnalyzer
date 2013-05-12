package pl.industrum.gasanalyzer.gui.frames;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;

import pl.industrum.gasanalyzer.gui.dialogs.NewSurvey;
import pl.industrum.gasanalyzer.gui.dialogs.OpenSurvey;
import pl.industrum.gasanalyzer.i18n.Messages;
import pl.industrum.gasanalyzer.model.Survey;
import pl.industrum.gasanalyzer.types.UsefulImage;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public abstract class ToolBar extends Composite
{
	GridData compositeData;
	private ToolItem btnPdf;
	private ToolItem btnXls;
	private org.eclipse.swt.widgets.ToolBar bar;
	private ToolItem btnRefresh;
	private ToolItem btnExit;
	private ToolItem btnNewSurvey;
	private ToolItem btnOpenSurvey;
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param btnExit 
	 */
	public ToolBar( final Composite parent, int style )
	{
		super( parent, style );
		compositeData = new GridData( GridData.FILL, GridData.GRAB_VERTICAL,
				true, false );
		compositeData.horizontalSpan = 6;
		this.setLayout( new FillLayout( SWT.HORIZONTAL ) );
		this.setLayoutData( compositeData );

		bar = new org.eclipse.swt.widgets.ToolBar( this, SWT.NONE );

		btnNewSurvey = new ToolItem( bar, SWT.PUSH );
		btnNewSurvey.setToolTipText(Messages.getString("ToolBar.btnNewSurvey.toolTipText")); //$NON-NLS-1$
		btnNewSurvey.setImage( UsefulImage.NEW_SURVEY.getImage() );
		btnNewSurvey.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				NewSurvey newSurvey = new NewSurvey( getShell(), SWT.NONE );
				Survey survey = newSurvey.open();
				if ( survey != null )
				{
					newSurveyCreated(survey);
					enableSurvey();
				}
			}
		} );
		
		btnOpenSurvey = new ToolItem( bar, SWT.PUSH );
		btnOpenSurvey.setToolTipText(Messages.getString("ToolBar.btnOpenSurvey.toolTipText")); //$NON-NLS-1$
		btnOpenSurvey.setImage( UsefulImage.OPEN_SURVEY.getImage() );
		btnOpenSurvey.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				OpenSurvey newSurvey = new OpenSurvey( getShell(), SWT.NONE );
				Survey survey = newSurvey.open();
				if ( survey != null )
				{
					newSurveyCreated(survey);
					enableSurvey();
				}
			}
		} );
		
		btnExit = new ToolItem( bar, SWT.PUSH );
		btnExit.setToolTipText(Messages.getString("ToolBar.btnExit.toolTipText")); //$NON-NLS-1$
		btnExit.setImage( UsefulImage.SHUTDOWN.getImage() );

		btnExit.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				closeApplication();
			}
		});
		
		new ToolItem( bar, SWT.SEPARATOR );
		
		btnRefresh = new ToolItem( bar, SWT.NONE );
		btnRefresh.setToolTipText(Messages.getString("ToolBar.btnRefresh.toolTipText")); //$NON-NLS-1$
		btnRefresh.setImage( UsefulImage.REFRESH.getImage() );

		btnRefresh.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{
				refreshDeviceTree();
			}
		});
		btnRefresh.setEnabled( false );

		new ToolItem( bar, SWT.SEPARATOR );
		
		btnPdf = new ToolItem( bar, SWT.PUSH );
		btnPdf.setImage( UsefulImage.PDF.getImage() );
		btnPdf.setToolTipText( Messages.getString("ToolBar.btnPdf.toolTipText") ); //$NON-NLS-1$

		btnPdf.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{				
				generatePDFReport();				
			}
		} );
		btnPdf.setEnabled( false );

		btnXls = new ToolItem( bar, SWT.PUSH );
		btnXls.setToolTipText(Messages.getString("ToolBar.btnXls.toolTipText")); //$NON-NLS-1$
		btnXls.setImage( UsefulImage.EXCEL.getImage() );

		btnXls.addSelectionListener( new SelectionAdapter()
		{
			@Override
			public void widgetSelected( SelectionEvent arg0 )
			{				
				generateXLSReport();
			}
		} );
		btnXls.setEnabled( false );

		bar.pack();
	}

	public void enableSurvey()
	{
		btnRefresh.setEnabled( true );
		btnXls.setEnabled( true );
		btnPdf.setEnabled( true );
	}
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public abstract void refreshDeviceTree();
	public abstract void closeApplication();
	public abstract void newSurveyCreated(Survey survey);
	public abstract Survey getSurveyToEdit();
	public abstract void generatePDFReport();
	public abstract void generateXLSReport();
}
