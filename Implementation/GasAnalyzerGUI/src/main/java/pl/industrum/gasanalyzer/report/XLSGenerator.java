package pl.industrum.gasanalyzer.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.eclipse.swt.program.Program;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;

import pl.industrum.gasanalyzer.hibernate.model.managers.MeasurementSnapshotManager;
import pl.industrum.gasanalyzer.model.Measurement;
import pl.industrum.gasanalyzer.model.MeasurementSet;
import pl.industrum.gasanalyzer.model.MeasurementSnapshot;
import pl.industrum.gasanalyzer.model.Survey;

/**
 *
 * @author duzydamian
 */
public abstract class XLSGenerator
{
	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault() );
	private static SimpleDateFormat hourFormater = new SimpleDateFormat( "HH:mm:ss", Locale.getDefault() );
	
	public XLSGenerator()
	{
	}
	
	public void create( Survey survey, String path )
    {
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet =  workbook.createSheet("FirstSheet");  

        //Add free cells above L.p. and Timestamp
        HSSFRow rowhead = sheet.createRow( 0 );
        rowhead.createCell( 1 ).setCellValue("");
        rowhead.createCell( 2 ).setCellValue("");
        
        Integer currRow = 0;
        Integer currColumn = 2;
        
        //Add devices names
        MeasurementSnapshot snapshotForHeader = MeasurementSnapshotManager.getLastMeasurementSnapshot( survey.getId() );
		for( Object set: snapshotForHeader.getMeasurementSetsSorted() )
		{
			MeasurementSet measurementSet = ( MeasurementSet )set;
			
			CellRangeAddress mergedRegion = new CellRangeAddress( currRow, currRow, currColumn + 1, currColumn + measurementSet.getMeasurements().size() - 1 );
	        sheet.addMergedRegion( mergedRegion );
	        rowhead.createCell( currColumn + 1 ).setCellValue( measurementSet.getDevice().getName() );
	        currColumn += 4;
			
			progressIncrement();
		}
		
		//Add free cells above L.p. and Timestamp
        HSSFRow rowVariable = sheet.createRow( 1 );
        HSSFRow rowDimension = sheet.createRow( 2 );
        rowVariable.createCell( 1 ).setCellValue("L.p.");
        rowVariable.createCell( 2 ).setCellValue("Godzina");
        
        currRow = 1;
        currColumn = 2;
		
        //Add measurement variables and dimension names
        for( Object set: snapshotForHeader.getMeasurementSetsSorted() )
		{
			MeasurementSet measurementSet = ( MeasurementSet )set;
			for( Object measurement: measurementSet.getMeasurementsSorted() )
			{
				if ( !( ( Measurement )measurement  ).getMeasurementVariable().getName().equalsIgnoreCase( "Process preassure" ) )
				{
					currColumn++;
					rowVariable.createCell( currColumn ).setCellValue( ( ( Measurement )measurement  ).getMeasurementVariable().getName() );
					rowDimension.createCell( currColumn ).setCellValue( ( ( Measurement )measurement  ).getMeasurementDimension().getName() );
					progressIncrement();
				}					
			}
		}
        currColumn++;
        rowVariable.createCell( currColumn ).setCellValue("Uwagi");
        
        currRow = 3;
        currColumn = 1;
        
		try
		{
			FileOutputStream outputStream = new FileOutputStream( path );
			workbook.write( outputStream );
			outputStream.close();
		} 
		catch ( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
    }
	
	public void open(String path)
    {
            Program.launch( path );
    }
	
	public abstract void progressIncrement();
}
