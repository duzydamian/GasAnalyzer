package pl.industrum.gasanalyzer.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.swt.program.Program;

import pl.industrum.gasanalyzer.hibernate.model.managers.MeasurementSnapshotManager;
import pl.industrum.gasanalyzer.model.Measurement;
import pl.industrum.gasanalyzer.model.MeasurementSet;
import pl.industrum.gasanalyzer.model.MeasurementSnapshot;
import pl.industrum.gasanalyzer.model.Survey;
import pl.industrum.gasanalyzer.types.Formater;
import pl.industrum.gasanalyzer.types.UsefulImage;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author duzydamian
 */
@SuppressWarnings( "unused" )
public abstract class PDFGenerator
{
	static BaseFont font;
    
	private Font czcionka2;
	private Font czcionka6;
	private Font czcionka8;
	private Font czcionka10;
	private Font czcionka10b;
	private Font czcionka16;
	private Font czcionka16u;	
	private Font czcionka20;
	private Font czcionka16b;
	private Image logoPolsl;
	private Image logoZkiwp;
	private Image logoImiue;

    public PDFGenerator()
    {
        try
        {
            font = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
            czcionka2 = new Font(font,2,Font.NORMAL);
            czcionka6 = new Font(font,6,Font.NORMAL);
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
            
            TableHeader tableHeader = new TableHeader();
            tableHeader.setHeader( survey.getName() );
            writer.setPageEvent( tableHeader );
            
            document.open();

            try
            {
				logoPolsl = Image.getInstance("src/main/resources/pl/industrum/gasanalyzer/gui/PolslLogo.png");			
				logoZkiwp = Image.getInstance("src/main/resources/pl/industrum/gasanalyzer/gui/ZKiWPLogo.png");
				logoImiue = Image.getInstance("src/main/resources/pl/industrum/gasanalyzer/gui/IMIUELogo.png");
            }
            catch (FileNotFoundException ex)
            {
            	ex.printStackTrace();            	
            	logoPolsl = Image.getInstance( getClass().getResource( UsefulImage.POLSL_LOGO.getImagePath() ) );			
				logoZkiwp = Image.getInstance( getClass().getResource( UsefulImage.ZKIWP_LOGO.getImagePath() ) );
				logoImiue = Image.getInstance( getClass().getResource( UsefulImage.IMIUE_LOGO.getImagePath() ) );
            }
            
			PdfPTable imagesTable = new PdfPTable(3);
			imagesTable.setWidthPercentage(30);
			imagesTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			//imagesTable.
			
			PdfPCell logoPolslPdfPCell = new PdfPCell( logoPolsl );
			logoPolslPdfPCell.setBorder( Rectangle.NO_BORDER );
			PdfPCell logoImiuePdfPCell = new PdfPCell( logoImiue );
			logoImiuePdfPCell.setBorder( Rectangle.NO_BORDER );
			PdfPCell logoZkiwpPdfPCell = new PdfPCell( logoZkiwp );
			logoZkiwpPdfPCell.setBorder( Rectangle.NO_BORDER );
			imagesTable.addCell( logoPolslPdfPCell );
			imagesTable.addCell( logoImiuePdfPCell );
			imagesTable.addCell( logoZkiwpPdfPCell );	
            
			PdfPTable headerRight = new PdfPTable(1);  
			headerRight.setWidthPercentage( 50 );
			PdfPCell headerRightCell = new PdfPCell(new Paragraph("POLITECHNIKA ŚLĄSKA \n", czcionka16b));
			headerRightCell.setBorder(Rectangle.NO_BORDER);
			headerRight.addCell( headerRightCell );
            
            Paragraph headerRightInstitute = new Paragraph();
            headerRightInstitute.add(new Chunk("WYDZIAŁ INŻYNIERII ŚRODOWISKA \nI ENERGETYKI \n", czcionka8));
            headerRightInstitute.add(new Chunk("\n", czcionka6));
            headerRightInstitute.add(new Chunk("INSTYTUT MASZYN I URZĄDZEŃ \nENERGETYCZNYCH \n", czcionka8));
            headerRightInstitute.add(new Chunk("\n", czcionka6));
            headerRightInstitute.add(new Chunk("ZAKŁAD KOTŁÓW I WYTORNIC PARY \nwww.kotly.polsl.pl \n", czcionka8));
            
            Paragraph headerRightAdrress = new Paragraph();
            headerRightAdrress.setAlignment( Element.ALIGN_BOTTOM );
            headerRightAdrress.add(new Chunk("UL. KONARSKIEGO 20 \n", czcionka6));
            headerRightAdrress.add(new Chunk("\n", czcionka2));
            headerRightAdrress.add(new Chunk("44-100  GLIWICE \n", czcionka6));
            headerRightAdrress.add(new Chunk("\n", czcionka2));
            headerRightAdrress.add(new Chunk("T: +48 32 237 12 73 \n", czcionka6));
            headerRightAdrress.add(new Chunk("\n", czcionka2));
            headerRightAdrress.add(new Chunk("F: +48 32 237 21 93 \n", czcionka6));
            headerRightAdrress.add(new Chunk("\n", czcionka2));
            headerRightAdrress.add(new Chunk("kotly@polsl.pl \n", czcionka6));
            
			PdfPTable headerRightTable = new PdfPTable(new float[]{ 70f, 30f});
			headerRightTable.setWidthPercentage(30);			
			PdfPCell headerRL = new PdfPCell(headerRightInstitute);
			headerRL.setBorder(Rectangle.NO_BORDER);
			PdfPCell headerRR = new PdfPCell(headerRightAdrress);
			headerRR.setBorder(Rectangle.NO_BORDER);
			headerRightTable.addCell(headerRL);
			headerRightTable.addCell(headerRR);
			
			PdfPCell headerRightTableCell = new PdfPCell( headerRightTable );
			headerRightTableCell.setBorder(Rectangle.NO_BORDER);
			headerRight.addCell( headerRightTableCell );
			
			PdfPTable header = new PdfPTable(2);
			header.setWidthPercentage(100);
			PdfPCell headerL = new PdfPCell(imagesTable);
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
			surveyData.add(new Chunk(Formater.getDateFormater().format( survey.getTimestamp() ), czcionka16b));
			surveyData.add("\n");
			surveyData.add(new Chunk("Miejsce: ", czcionka16));
			surveyData.add(new Chunk(survey.getObject().getPlace().toString(), czcionka16b));
			surveyData.add("\n");
			surveyData.add(new Chunk("Obiekt: ", czcionka16));
			surveyData.add(new Chunk(survey.getObject().toString(), czcionka16b));
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
			colsWidth[0] = 10f;
			colsWidth[1] = 20f;
			for( int i = 2; i < colsWidth.length-1; i++ )
			{
				colsWidth[i] = 10f;
			}
			colsWidth[colsWidth.length-1] = 20f;
			
			PdfPTable measurementSnapshotList = new PdfPTable(colsWidth);
			measurementSnapshotList.setWidthPercentage(100);
			measurementSnapshotList.setHorizontalAlignment(Element.ALIGN_CENTER);
			measurementSnapshotList.setHeaderRows( 2 );
			
			PdfPCell pdfPCellLP = new PdfPCell(new Paragraph("Lp.",czcionka10b));
			pdfPCellLP.setRowspan( 2 );
			pdfPCellLP.setVerticalAlignment( PdfPCell.ALIGN_MIDDLE );
			PdfPCell pdfPCellHour = new PdfPCell(new Paragraph("Godzina",czcionka10b));
			pdfPCellHour.setRowspan( 2 );
			pdfPCellHour.setVerticalAlignment( PdfPCell.ALIGN_MIDDLE );
			measurementSnapshotList.addCell(pdfPCellLP);
			measurementSnapshotList.addCell(pdfPCellHour);
			
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
			
			PdfPCell pdfPCellcomment = new PdfPCell(new Paragraph("Uwagi",czcionka10b));
			pdfPCellcomment.setRowspan( 2 );
			pdfPCellcomment.setVerticalAlignment( PdfPCell.ALIGN_MIDDLE );
			pdfPCellcomment.setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
			measurementSnapshotList.addCell(pdfPCellcomment);
			
			for( Object set: snapshotForHeader.getMeasurementSetsSorted() )
			{
				MeasurementSet measurementSet = ( MeasurementSet )set;
				for( Object measurement: measurementSet.getMeasurementsSorted() )
				{
					Measurement measurement2 = ( Measurement )measurement;
					if ( !measurement2.getMeasurementVariable().getName().equalsIgnoreCase( "Process preassure" ) )
					{
						PdfPCell pdfPCell = new PdfPCell(new Paragraph(measurement2.getMeasurementVariable().getName()+"\n["+measurement2.getMeasurementDimension().getName()+"]" ,czcionka10b));
						pdfPCell.setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
						measurementSnapshotList.addCell(pdfPCell);
						progressIncrement();
					}					
				}
			}			
			
			int i = 1;
			for( MeasurementSnapshot snapshot: MeasurementSnapshotManager.getAllMeasurementSnapshots( survey.getId() ) )
			{				
				measurementSnapshotList.addCell(new PdfPCell(new Paragraph(String.valueOf(i),czcionka10)));
				measurementSnapshotList.addCell(new PdfPCell(new Paragraph(Formater.getHourFormater().format( snapshot.getTimestamp() ),czcionka10)));
				for( Object set: snapshot.getMeasurementSetsSorted() )
				{
					MeasurementSet measurementSet = ( MeasurementSet )set;
					for( Object measurement: measurementSet.getMeasurementsSorted() )
					{
						Measurement measurement2 = ( Measurement )measurement;
						if ( !measurement2.getMeasurementVariable().getName().equalsIgnoreCase( "Process preassure" ) )
						{
							String valueAsString = "";
							if ( measurement2.getMeasurementDimension().getId() < 8 )
							{
								valueAsString = Formater.doubleWithPrecisionAsString( measurement2.getValue(), 0 );
							}
							else if ( measurement2.getMeasurementVariable().getId() == 4 )
							{
								valueAsString = Formater.doubleWithPrecisionAsString( measurement2.getValue(), 3 );
							} 
							else
							{
								valueAsString = Formater.doubleWithPrecisionAsString( measurement2.getValue(), 2 );
							}
							PdfPCell pdfPCell = new PdfPCell( new Paragraph( valueAsString, czcionka10 ) );
							pdfPCell.setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
							measurementSnapshotList.addCell( pdfPCell );
						}						
					}
					
				}
				measurementSnapshotList.addCell(new PdfPCell(new Paragraph(snapshot.getComment(),czcionka10)));
				i++;				
				progressIncrement();
			}
						
            document.add(header);
            document.add(new Paragraph("\n"));
            
            document.add(surveyData);            
            document.add(new Paragraph("\n"));
            
            document.add(measurementSnapshotList);

            document.addAuthor("Damian Karbowiak & Grzegorz Powała");
            document.addCreator(survey.getApplicationUser().toString());
            document.addSubject(survey.getName());
            document.addTitle("Gas Analyzer " + survey.getName());
            document.addCreationDate();

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
    
    /**
     * Inner class to add a table as header.
     */
    class TableHeader extends PdfPageEventHelper
    {
        /** The header text. */
        String header;
        /** The template with the total number of pages. */
        PdfTemplate total;
 
        /**
         * Allows us to change the content of the header.
         * @param header The new header String
         */
        public void setHeader(String header)
        {
            this.header = header;
        }
 
        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document)
        {
            total = writer.getDirectContent().createTemplate(40, 20);
        }
 
        /**
         * Adds a header to every page
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document)
        {
            PdfPTable table = new PdfPTable(3);
            try
            {
                table.setWidths(new int[]{60, 30, 10});
                table.setTotalWidth(writer.getPageSize().getWidth()-60);       
                table.getDefaultCell().setFixedHeight(30);
                table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                PdfPCell pdfPCell = new PdfPCell(new Paragraph(" Wygenerowano przy pomocy programu Gas Analyzer \n Autorzy: Damian Karbowiak & Grzegorz Powała", czcionka8));
                pdfPCell.setBorder( Rectangle.NO_BORDER );
                table.addCell(pdfPCell);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);                
                table.addCell(String.format("Strona %d z", writer.getPageNumber()));
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
                cell.setPaddingLeft( 2f );
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
                table.writeSelectedRows(0, -1, 30f, 30f, writer.getDirectContent());
            }
            catch(DocumentException de)
            {
                throw new ExceptionConverter(de);
            }
        }
 
        /**
         * Fills out the total number of pages before the document is closed.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document)
        {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                    0f, 6f, 0);
        }
    }    
}
