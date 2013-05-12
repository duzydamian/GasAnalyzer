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

import pl.industrum.gasanalyzer.model.Survey;
import pl.industrum.gasanalyzer.types.UsefulImage;

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
public class PDFGenerator
{

	private static SimpleDateFormat dateFormater = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault() );
	private static SimpleDateFormat hourFormater = new SimpleDateFormat( "HH:mm", Locale.getDefault() );
    
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

            Paragraph naglowekorglewy = new Paragraph();
            Paragraph naglowekorgprawy = new Paragraph();
            naglowekorglewy.setAlignment(Paragraph.ALIGN_LEFT);
            naglowekorgprawy.setAlignment(Paragraph.ALIGN_LEFT);
            naglowekorglewy.add(new Chunk("Faktura VAT\n", czcionka20));          
            naglowekorglewy.add("\n");
            naglowekorglewy.add(new Chunk("ORYGINAŁ \n", czcionka16));
            naglowekorglewy.add("\n");
//			Image logo = Image.getInstance(UsefulImage.POLSL_LOGO.getImagePath());
//			logo.scalePercent(50);
//			naglowekorglewy.add(new Image(logo) {});
            naglowekorgprawy.add(new Chunk("POLITECHNIKA ŚLĄSKA \n", czcionka16b));
            naglowekorgprawy.add(new Chunk("WYDZIAŁ INŻYNIERII ŚRODOWISKA \nI ENERGETYKI \n", czcionka10b));
            naglowekorgprawy.add(new Chunk("WYDZIAŁ INŻYNIERII ŚRODOWISKA \nI ENERGETYKI \n", czcionka10b));
			
			
			PdfPTable oryg = new PdfPTable(2);
			oryg.setWidthPercentage(100);
			PdfPCell orgl = new PdfPCell(naglowekorglewy);
			orgl.setBorder(Rectangle.NO_BORDER);
			PdfPCell orgp = new PdfPCell(naglowekorgprawy);
			orgp.setBorder(Rectangle.NO_BORDER);
			orgp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			oryg.addCell(orgl);
			oryg.addCell(orgp);
			
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
			
			PdfPTable sd = new PdfPTable(1);
			sd.setWidthPercentage(100);
			//sn.getDefaultCell().setBorder(0);
			sd.addCell(new PdfPCell(surveyData));
			
			//float[] colsWidth = {4f, 38f, 10f, 5f, 5f, 7f, 8f, 7f, 8f, 8f};
			float[] colsWidth = {4f, 38f};
			PdfPTable measurementSnapshotList = new PdfPTable(colsWidth);
			measurementSnapshotList.setWidthPercentage(100);
			measurementSnapshotList.setHorizontalAlignment(Element.ALIGN_CENTER);
			measurementSnapshotList.addCell(new PdfPCell(new Paragraph("Lp.",czcionka10b)));
			measurementSnapshotList.addCell(new PdfPCell(new Paragraph("Godzina",czcionka10b)));
			
//			int i = 1;
//			for( MeasurementSnapshot snapshot: MeasurementSnapshotManager.getAllMeasurementSnapshots( survey.getId() ) )
//			{
//				
//				measurementSnapshotList.addCell(new PdfPCell(new Paragraph(String.valueOf(i),czcionka10)));
//				measurementSnapshotList.addCell(new PdfPCell(new Paragraph(hourFormater.format( snapshot.getTimestamp() ),czcionka10)));
//				i++;
//			}

            document.add(oryg);
//            PdfContentByte cb = writer.getDirectContent();
//            cb.setLineWidth(2.0f);	 // Make a bit thicker than 1.0 default
//            cb.moveTo(30, writer.getVerticalPosition(true));
//            cb.lineTo(PageSize.A4.getWidth()-30, writer.getVerticalPosition(true));
//            cb.stroke();
            document.add(sd);
//            cb.moveTo(30, writer.getVerticalPosition(true));
//            cb.lineTo(PageSize.A4.getWidth()-30, writer.getVerticalPosition(true));
//            cb.stroke();
            document.add(new Paragraph("\n"));
            document.add(measurementSnapshotList);
            document.add(new Paragraph("\n"));

            //document.add(list);
            //document.add(new Paragraph("\n"));
            //document.add(overview);

//            document.addAuthor("duzydamian");
//            document.addProducer();
//            document.addCreator(sp.getNazwa());
//            document.addSubject("Faktrura Vat");
//            document.addTitle("Faktura nr " + numer);
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
}
