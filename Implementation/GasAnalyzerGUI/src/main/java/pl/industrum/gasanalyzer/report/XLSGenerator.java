package pl.industrum.gasanalyzer.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.eclipse.swt.program.Program;

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
		HSSFSheet informationSheet =  workbook.createSheet("karta informacyjna");
        HSSFSheet measurementSheet =  workbook.createSheet("karta pomiarowa");
        
        //Create information sheet
        ////Add institution
        HSSFRow rowInstitution = informationSheet.createRow( 2 );
        CellRangeAddress mergedRegionInstitution = new CellRangeAddress( 2, 14, 2, 8 );
        informationSheet.addMergedRegion( mergedRegionInstitution );
        String informationString = "";
        informationString += "POLITECHNIKA ŚLĄSKA\n";
        informationString += "WYDZIAŁ INŻYNIERII ŚRODOWISKA I ENERGETYKI\n";
        informationString += "INSTYTUT MASZYN I URZĄDZEŃ ENERGETYCZNYCH\n";
        informationString += "ZAKŁAD KOTŁÓW I WYTORNIC PARY\n";
        informationString += "\n";
        informationString += "UL. KONARSKIEGO 20\n";
        informationString += "44-100  GLIWICE\n";
        informationString += "\n";
        informationString += "T: +48 32 237 12 73\n";
        informationString += "F: +48 32 237 21 93\n";
        informationString += "\n";
        informationString += "www.kotly.polsl.pl\n";
        informationString += "kotly@polsl.pl\n";
        HSSFCell cell = rowInstitution.createCell( 2 );
        cell.setCellValue(  informationString );
        
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment( CellStyle.ALIGN_CENTER );
		cell.setCellStyle( style );
        ////Add survey data
        rowInstitution = informationSheet.createRow( 16 );
        mergedRegionInstitution = new CellRangeAddress( 16, 24, 2, 8 );
        informationSheet.addMergedRegion( mergedRegionInstitution );
        informationString = "DANE POMIARU:\n";
        informationString += "Nazwa pomiarów: " + survey.getName() + "\n";
        informationString += "Data pomiarów: " + dateFormater.format( survey.getTimestamp() ) + "\n";
        informationString += "Miejsce: " + survey.getObject().getPlace().toString() + "\n";
        informationString += "Obiekt: " + survey.getObject().toString() + "\n";
        informationString += "Obciążenie: " + survey.getLoad() + "\n";
        informationString += "Warunki szczególne: " + survey.getSpecialConditions() + "\n";
        informationString += "Prowadzący pomiary: " + survey.getApplicationUser().toString() + "\n";
        informationString += "";
        rowInstitution.createCell( 2 ).setCellValue(  informationString );
        
        //Add free cells above L.p. and Timestamp
        HSSFRow rowhead = measurementSheet.createRow( 0 );
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
	        measurementSheet.addMergedRegion( mergedRegion );
	        rowhead.createCell( currColumn + 1 ).setCellValue( measurementSet.getDevice().getName() );
	        currColumn += ( measurementSet.getMeasurements().size() - 1 );
			
			progressIncrement();
		}
		
		//Add free cells above L.p. and Timestamp
        HSSFRow rowVariable = measurementSheet.createRow( 1 );
        HSSFRow rowDimension = measurementSheet.createRow( 2 );
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
        
        int i = 1;
        HSSFRow dataRow;
		for( MeasurementSnapshot snapshot: MeasurementSnapshotManager.getAllMeasurementSnapshots( survey.getId() ) )
		{	
			currColumn = 1;
			dataRow = measurementSheet.createRow( currRow ); currRow++;
			
			dataRow.createCell( currColumn ).setCellValue( String.valueOf( i ) ); currColumn++;
			dataRow.createCell( currColumn ).setCellValue( hourFormater.format( snapshot.getTimestamp() ) ); currColumn++;
			
			for( Object set: snapshot.getMeasurementSetsSorted() )
			{
				MeasurementSet measurementSet = ( MeasurementSet )set;
				for( Object measurement: measurementSet.getMeasurementsSorted() )
				{
					if ( !( ( Measurement )measurement  ).getMeasurementVariable().getName().equalsIgnoreCase( "Process preassure" ) )
					{
						dataRow.createCell( currColumn ).setCellValue( ( ( Measurement )measurement  ).getValue() ); currColumn++;
					}						
				}
				
			}
			
			dataRow.createCell( currColumn ).setCellValue( snapshot.getComment() );
			
			i++;				
			progressIncrement();
		}
        
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
