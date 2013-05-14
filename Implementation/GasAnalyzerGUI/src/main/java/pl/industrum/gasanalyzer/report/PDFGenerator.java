/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.industrum.gasanalyzer.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.swt.program.Program;

import pl.industrum.gasanalyzer.hibernate.model.managers.MeasurementSnapshotManager;
import pl.industrum.gasanalyzer.model.Measurement;
import pl.industrum.gasanalyzer.model.MeasurementSet;
import pl.industrum.gasanalyzer.model.MeasurementSnapshot;
import pl.industrum.gasanalyzer.model.Survey;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author duzydamian
 */
@SuppressWarnings( "unused" )
public abstract class PDFGenerator
{

	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault() );
	private static SimpleDateFormat hourFormater = new SimpleDateFormat( "HH:mm:ss", Locale.getDefault() );
    
	static BaseFont font;
    
	private Font czcionka8;
	private Font czcionka10;
	private Font czcionka10b;
	private Font czcionka16;
	private Font czcionka16u;	
	private Font czcionka20;
	private Font czcionka16b;

    public PDFGenerator()
    {
        try
        {
            font = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
            czcionka8 = new Font(font,8,Font.NORMAL);
            czcionka10 = new Font(font,10,Font.NORMAL);
            czcionka10b = new Font(font,10,Font.BOLD);
            czcionka16 = new Font(font,12,Font.NORMAL);
            czcionka16u = new Font(font,12,Font.UNDERLINE);
            czcionka16b = new Font(font,12,Font.BOLD);
            czcionka20 = new Font(font,14,Font.BOLD);
        }
        catch (DocumentException ex)
        {
        	ex.printStackTrace();
        }
        catch (IOException ex)
        {
        	ex.printStackTrace();
        }
    }
    
    public void create( Survey survey, String path )
    {
        Document document = new Document();
        
        try
        {
            PdfWriter writer = PdfWriter.getInstance((com.itextpdf.text.Document) document,
                             new FileOutputStream(path));
            document.open();

            Paragraph headerLeftImgages = new Paragraph();
            headerLeftImgages.setAlignment(Paragraph.ALIGN_LEFT);
            
            headerLeftImgages.add(new Chunk("ORYGINAŁ \n", czcionka16));
            headerLeftImgages.add("\n");
			Image logo = Image.getInstance("src/main/resources/pl/industrum/gasanalyzer/gui/PolslLogo.png");
			logo.scalePercent(50);
			headerLeftImgages.add(logo);
            
			Paragraph headerRight = new Paragraph();            
            headerRight.setAlignment(Paragraph.ALIGN_LEFT);
			headerRight.add(new Chunk("POLITECHNIKA ŚLĄSKA \n", czcionka16b));          
            headerRight.add(new Chunk("\n", czcionka16b));
            
            Paragraph headerRightInstitute = new Paragraph();
            headerRightInstitute.add(new Chunk("WYDZIAŁ INŻYNIERII ŚRODOWISKA \nI ENERGETYKI \n", czcionka10b));
            headerRightInstitute.add("\n");
            headerRightInstitute.add(new Chunk("INSTYTUT MASZYN I URZĄDZEŃ \nENERGETYCZNYCH \n", czcionka10b));
            headerRightInstitute.add("\n");
            headerRightInstitute.add(new Chunk("ZAKŁAD KOTŁÓW I WYTORNIC PARY \nwww.kotly.polsl.pl \n", czcionka10b));
            Paragraph headerRightAdrress = new Paragraph();
            headerRightAdrress.add(new Chunk("UL. KONARSKIEGO 20 \n", czcionka8));
            headerRightAdrress.add("\n");
            headerRightAdrress.add(new Chunk("44-100  GLIWICE \n", czcionka8));
            headerRightAdrress.add("\n");
            headerRightAdrress.add(new Chunk("T: +48 32 237 12 73 \n", czcionka8));
            headerRightAdrress.add("\n");
            headerRightAdrress.add(new Chunk("F: +48 32 237 21 93 \n", czcionka8));
            headerRightAdrress.add("\n");
            headerRightAdrress.add(new Chunk("kotly@polsl.pl \n", czcionka8));
            
			PdfPTable headerRightTable = new PdfPTable(2);
			headerRightTable.setWidthPercentage(100);
			PdfPCell headerRL = new PdfPCell(headerRightInstitute);
			headerRL.setBorder(Rectangle.NO_BORDER);
			PdfPCell headerRR = new PdfPCell(headerRightAdrress);
			headerRR.setBorder(Rectangle.NO_BORDER);
			headerRightTable.addCell(headerRL);
			headerRightTable.addCell(headerRR);
			
			headerRight.add( headerRightTable );
			
			PdfPTable header = new PdfPTable(2);
			header.setWidthPercentage(100);
			PdfPCell headerL = new PdfPCell(headerLeftImgages);
			headerL.setBorder(Rectangle.NO_BORDER);
			PdfPCell headerR = new PdfPCell(headerRight);
			headerR.setBorder(Rectangle.NO_BORDER);
			headerR.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			header.addCell(headerL);
			header.addCell(headerR);
			
			Paragraph surveyData = new Paragraph();
			surveyData.setAlignment(Paragraph.ALIGN_LEFT);	
			surveyData.add(new Chunk("Nazwa pomiarów: ", czcionka16));
			surveyData.add(new Chunk(survey.getName(), czcionka16b));
			surveyData.add("\n");
			surveyData.add(new Chunk("Data pomiarów: ", czcionka16));
			surveyData.add(new Chunk(dateFormater.format( survey.getTimestamp() ), czcionka16b));
			surveyData.add("\n");
			surveyData.add(new Chunk("Obiekt: ", czcionka16));
			surveyData.add(new Chunk("", czcionka16b));
			surveyData.add("\n");
			surveyData.add(new Chunk("Obciążenie: ", czcionka16));
			surveyData.add(new Chunk(survey.getLoad(), czcionka16b));
			surveyData.add("\n");
			surveyData.add(new Chunk("Warunki szczególne: ", czcionka16));
			surveyData.add(new Chunk(survey.getSpecialConditions(), czcionka16b));
			surveyData.add("\n");
			surveyData.add(new Chunk("Prowadzący pomiary: ", czcionka16));
			surveyData.add(new Chunk(survey.getApplicationUser().toString(), czcionka16b));
		
			progressIncrement();
			
			float[] colsWidth = new float[MeasurementSnapshotManager.getMeasurementSnapshotMeasuredVariableCount( survey.getId() )+3];
			colsWidth[0] = 4f;
			colsWidth[1] = 20f;
			for( int i = 2; i < colsWidth.length-1; i++ )
			{
				colsWidth[i] = 10f;
			}
			colsWidth[colsWidth.length-1] = 20f;
			
			PdfPTable measurementSnapshotList = new PdfPTable(colsWidth);
			measurementSnapshotList.setWidthPercentage(100);
			measurementSnapshotList.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell emptyPdfPCell = new PdfPCell();
			emptyPdfPCell.setColspan( 2 );
			measurementSnapshotList.addCell(emptyPdfPCell);
			MeasurementSnapshot snapshotForHeader = MeasurementSnapshotManager.getLastMeasurementSnapshot( survey.getId() );
			for( Object set: snapshotForHeader.getMeasurementSetsSorted() )
			{
				MeasurementSet measurementSet = ( MeasurementSet )set;
				PdfPCell devicePdfPCell = new PdfPCell(new Paragraph( measurementSet.getDevice().getName(), czcionka10b ));
				devicePdfPCell.setColspan( measurementSet.getMeasurements().size()-1 );
				devicePdfPCell.setHorizontalAlignment( Paragraph.ALIGN_CENTER );
				measurementSnapshotList.addCell(devicePdfPCell);
				progressIncrement();
			}			
			
			emptyPdfPCell.setColspan( 1 );
			measurementSnapshotList.addCell(emptyPdfPCell);
			
			measurementSnapshotList.addCell(new PdfPCell(new Paragraph("Lp.",czcionka10b)));
			measurementSnapshotList.addCell(new PdfPCell(new Paragraph("Godzina",czcionka10b)));
			
			for( Object set: snapshotForHeader.getMeasurementSetsSorted() )
			{
				MeasurementSet measurementSet = ( MeasurementSet )set;
				for( Object measurement: measurementSet.getMeasurementsSorted() )
				{
					Measurement measurement2 = ( Measurement )measurement;
					if ( !measurement2.getMeasurementVariable().getName().equalsIgnoreCase( "Process preassure" ) )
					{
						measurementSnapshotList.addCell(new PdfPCell(new Paragraph(measurement2.getMeasurementVariable().getName()+"\n["+measurement2.getMeasurementDimension().getName()+"]" ,czcionka10b)));
						progressIncrement();
					}					
				}
			}
			measurementSnapshotList.addCell(new PdfPCell(new Paragraph("Uwagi",czcionka10b)));
			
			int i = 1;
			for( MeasurementSnapshot snapshot: MeasurementSnapshotManager.getAllMeasurementSnapshots( survey.getId() ) )
			{				
				measurementSnapshotList.addCell(new PdfPCell(new Paragraph(String.valueOf(i),czcionka10)));
				measurementSnapshotList.addCell(new PdfPCell(new Paragraph(hourFormater.format( snapshot.getTimestamp() ),czcionka10)));
				for( Object set: snapshot.getMeasurementSetsSorted() )
				{
					MeasurementSet measurementSet = ( MeasurementSet )set;
					for( Object measurement: measurementSet.getMeasurementsSorted() )
					{
						Measurement measurement2 = ( Measurement )measurement;
						if ( !measurement2.getMeasurementVariable().getName().equalsIgnoreCase( "Process preassure" ) )
						{
							measurementSnapshotList.addCell(new PdfPCell(new Paragraph(String.valueOf( measurement2.getValue() ) ,czcionka10)));
						}						
					}
					
				}
				measurementSnapshotList.addCell(new PdfPCell(new Paragraph(snapshot.getComment(),czcionka10)));
				i++;				
				progressIncrement();
			}

			document.add(logo);
            document.add(header);

            document.add(surveyData);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(measurementSnapshotList);

//            document.addAuthor("duzydamian");
//            document.addProducer();
//            document.addCreator(sp.getNazwa());
//            document.addSubject("Faktrura Vat");
            document.addTitle("Gas Analyzer " + survey.getName());
//            document.addCreationDate();
//            document.addKeywords("faktura, vat, firma, klient, produkt");
//            document.addHeader(numer, numer);

            }
        	catch (DocumentException de)
            {
                de.printStackTrace();
            }
        	catch (IOException ioe)
        	{
                ioe.printStackTrace();
            }
            document.close();
    }

    public void open(String path)
    {
            Program.launch( path );
    }
    
    public abstract void progressIncrement();
}
