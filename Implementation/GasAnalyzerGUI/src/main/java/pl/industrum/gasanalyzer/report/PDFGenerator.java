/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.industrum.gasanalyzer.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
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
public class PDFGenerator {

    static BaseFont font;

    public PDFGenerator(){
        try {
            font = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException ex) {
//            Logger.getLogger(DdFakturyView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(DdFakturyView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    void create(Sprzedawca sp, Klienci kl, Object dataWystawieniat, Object dataSprzedazyt, JTable listaProduktowTable, String numer, String dopisek, String platnosc){
//        Double netto23 = 0.0, brutto23 = 0.0;
//        Double netto8 = 0.0, brutto8 = 0.0;
//        Double netto5 = 0.0, brutto5 = 0.0;
//        Double netto0 = 0.0, brutto0 = 0.0;
//        Double nettozw = 0.0, bruttozw = 0.0;
//        Document document = new Document();
//        Font czcionka8 = new Font(font,8,Font.NORMAL);
//        Font czcionka10 = new Font(font,10,Font.NORMAL);
//        Font czcionka10b = new Font(font,10,Font.BOLD);
//        Font czcionka16 = new Font(font,12,Font.NORMAL);
//        Font czcionka16u = new Font(font,12,Font.UNDERLINE);
//        Font czcionka16b = new Font(font,12,Font.BOLD);
//        Font czcionka20 = new Font(font,14,Font.BOLD);
//        Date data = new Date();
//        Date dataWystawienia = (Date) dataWystawieniat;
//        Date dataSprzedazy = (Date) dataSprzedazyt;
//        SimpleDateFormat rok = new SimpleDateFormat("yyyy");
//        SimpleDateFormat dataFormat = new SimpleDateFormat("dd / MM / yyyy");
//        try {
//            PdfWriter writer;
//            //if(ostatni==null)
//            writer = PdfWriter.getInstance((com.itextpdf.text.Document) document,
//                             new FileOutputStream("fakturyPDF"+System.getProperty("file.separator")+"Faktura_"+numer.replaceAll("/", "_")+".pdf"));
//            document.open();
//
//Paragraph naglowekorglewy = new Paragraph();
//Paragraph naglowekorgprawy = new Paragraph();
//naglowekorglewy.setAlignment(Paragraph.ALIGN_LEFT);
//naglowekorgprawy.setAlignment(Paragraph.ALIGN_RIGHT);
//naglowekorglewy.add(new Chunk("Faktura VAT\n", czcionka20));
//naglowekorglewy.add(new Chunk("Nr: " + numer + "\n", czcionka20));
//naglowekorgprawy.add(new Chunk("Data wystawienia: "+dataFormat.format(dataWystawienia)+"\n", czcionka16b));
//naglowekorgprawy.add(new Chunk("Data sprzedaży: "+dataFormat.format(dataSprzedazy)+"\n", czcionka16b));
//naglowekorglewy.add("\n");
//naglowekorglewy.add(new Chunk("ORYGINAŁ \n", czcionka16));
//naglowekorglewy.add("\n");
//
//Paragraph naglowekkoplewy = new Paragraph();
//Paragraph naglowekkopprawy = new Paragraph();
//naglowekkoplewy.setAlignment(Paragraph.ALIGN_LEFT);
//naglowekkopprawy.setAlignment(Paragraph.ALIGN_RIGHT);
//naglowekkoplewy.add(new Chunk("Faktura VAT\n", czcionka20));
//naglowekkoplewy.add(new Chunk("Nr: " + numer + "\n", czcionka20));
//naglowekkopprawy.add(new Chunk("Data wystawienia: "+dataFormat.format(dataWystawienia)+"\n", czcionka16b));
//naglowekkopprawy.add(new Chunk("Data sprzedaży: "+dataFormat.format(dataSprzedazy)+"\n", czcionka16b));
//naglowekkoplewy.add("\n");
//naglowekkoplewy.add(new Chunk("KOPIA \n", czcionka16));
//naglowekkoplewy.add("\n");
//
//PdfPTable oryg = new PdfPTable(2);
//oryg.setWidthPercentage(100);
//PdfPCell orgl = new PdfPCell(naglowekorglewy);
//orgl.setBorder(Rectangle.NO_BORDER);
//PdfPCell orgp = new PdfPCell(naglowekorgprawy);
//orgp.setBorder(Rectangle.NO_BORDER);
//orgp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
//oryg.addCell(orgl);
//oryg.addCell(orgp);
//
//PdfPTable kop = new PdfPTable(2);
//kop.setWidthPercentage(100);
//PdfPCell kopl = new PdfPCell(naglowekkoplewy);
//kopl.setBorder(Rectangle.NO_BORDER);
//PdfPCell kopp = new PdfPCell(naglowekkopprawy);
//kopp.setBorder(Rectangle.NO_BORDER);
//kopp.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
//kop.addCell(kopl);
//kop.addCell(kopp);
//
//Paragraph sprzedawca = new Paragraph();
//sprzedawca.setAlignment(Paragraph.ALIGN_LEFT);
//sprzedawca.add(new Chunk("Sprzedawca: \n\n", czcionka16u));
//sprzedawca.add(new Chunk(sp.getNazwa() + "\n", czcionka16));
//sprzedawca.add(new Chunk(sp.getAdres() + "\n", czcionka16));
//sprzedawca.add(new Chunk(sp.getKod() + " " + sp.getMiejscowosc() + "\n", czcionka16));
//sprzedawca.add(new Chunk("NIP: " + sp.getNip(), czcionka16));
//sprzedawca.add(new Chunk("\n"));
//
//Paragraph nabywca = new Paragraph();
//nabywca.setAlignment(Paragraph.ALIGN_RIGHT);
//nabywca.add(new Chunk("Nabywca: \n\n", czcionka16u));
//nabywca.add(new Chunk(kl.getNazwa() + "\n", czcionka16));
//nabywca.add(new Chunk(kl.getAdres() + "\n", czcionka16));
//nabywca.add(new Chunk(kl.getKod() + " " + kl.getMiejscowosc() + "\n", czcionka16));
//nabywca.add(new Chunk("NIP: " + kl.getNip(), czcionka16));
//nabywca.add(new Chunk("\n"));
//
//PdfPTable sn = new PdfPTable(2);
//sn.setWidthPercentage(100);
////sn.getDefaultCell().setBorder(0);
//sn.addCell(new PdfPCell(sprzedawca));
//sn.addCell(new PdfPCell(nabywca));
//
//float[] colsWidth = {4f, 38f, 10f, 5f, 5f, 7f, 8f, 7f, 8f, 8f}; // Code 1
//PdfPTable listaProduktow = new PdfPTable(colsWidth);
//listaProduktow.setWidthPercentage(100); // Code 2
//listaProduktow.setHorizontalAlignment(Element.ALIGN_CENTER);//Code 3
//listaProduktow.addCell(new PdfPCell(new Paragraph("Lp.",czcionka10b)));
//listaProduktow.addCell(new PdfPCell(new Paragraph("Nazwa",czcionka10b)));
//listaProduktow.addCell(new PdfPCell(new Paragraph("PKWiU",czcionka10b)));
//listaProduktow.addCell(new PdfPCell(new Paragraph("Ilość",czcionka10b)));
//listaProduktow.addCell(new PdfPCell(new Paragraph("jm.",czcionka10b)));
//listaProduktow.addCell(new PdfPCell(new Paragraph("Cena jdn.",czcionka10b)));
//listaProduktow.addCell(new PdfPCell(new Paragraph("Wartość Netto",czcionka10b)));
//listaProduktow.addCell(new PdfPCell(new Paragraph("Stawka VAT",czcionka10b)));
//listaProduktow.addCell(new PdfPCell(new Paragraph("Kwota Vat",czcionka10b)));
//listaProduktow.addCell(new PdfPCell(new Paragraph("Wartość Brutto",czcionka10b)));
//for(int i=0;i<listaProduktowTable.getRowCount();i++){
//listaProduktow.addCell(new PdfPCell(new Paragraph(String.valueOf(i+1),czcionka10)));
//listaProduktow.addCell(new PdfPCell(new Paragraph(listaProduktowTable.getValueAt(i, 0).toString(),czcionka10)));
//listaProduktow.addCell(new PdfPCell(new Paragraph(Produkty.getPKWiUByName(listaProduktowTable.getValueAt(i, 0).toString()),czcionka10)));
//listaProduktow.addCell(new PdfPCell(new Paragraph(listaProduktowTable.getValueAt(i, 1).toString(),czcionka10)));
//listaProduktow.addCell(new PdfPCell(new Paragraph(Produkty.getUnitByName(listaProduktowTable.getValueAt(i, 0).toString()),czcionka10)));
//listaProduktow.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(listaProduktowTable.getValueAt(i, 3).toString()),czcionka10)));
//listaProduktow.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(Double.valueOf(listaProduktowTable.getValueAt(i, 3).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString())),czcionka10)));
//listaProduktow.addCell(new PdfPCell(new Paragraph(listaProduktowTable.getValueAt(i, 2).toString(),czcionka10)));
//listaProduktow.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(Double.valueOf(listaProduktowTable.getValueAt(i, 4).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString())-Double.valueOf(listaProduktowTable.getValueAt(i, 3).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString())),czcionka10)));
//listaProduktow.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(Double.valueOf(listaProduktowTable.getValueAt(i, 4).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString())),czcionka10)));
//if(listaProduktowTable.getValueAt(i, 2).toString().equals("23%")){
//netto23 += Double.valueOf(listaProduktowTable.getValueAt(i, 3).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//brutto23 += Double.valueOf(listaProduktowTable.getValueAt(i, 4).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//}else if(listaProduktowTable.getValueAt(i, 2).toString().equals("8%")){
//netto8 += Double.valueOf(listaProduktowTable.getValueAt(i, 3).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//brutto8 += Double.valueOf(listaProduktowTable.getValueAt(i, 4).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//}else if(listaProduktowTable.getValueAt(i, 2).toString().equals("5%")){
//netto5 += Double.valueOf(listaProduktowTable.getValueAt(i, 3).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//brutto5 += Double.valueOf(listaProduktowTable.getValueAt(i, 4).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//}else if(listaProduktowTable.getValueAt(i, 2).toString().equals("0%")){
//netto0 += Double.valueOf(listaProduktowTable.getValueAt(i, 3).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//brutto0 += Double.valueOf(listaProduktowTable.getValueAt(i, 4).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//}else{
//nettozw += Double.valueOf(listaProduktowTable.getValueAt(i, 3).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//bruttozw += Double.valueOf(listaProduktowTable.getValueAt(i, 4).toString().replace(',', '.'))*Integer.valueOf(listaProduktowTable.getValueAt(i, 1).toString());
//}
//}
//
//float[] colsWidth2 = {2f, 2f, 2f, 2f}; // Code 1
//PdfPTable listaVat = new PdfPTable(colsWidth2);
//listaVat.setWidthPercentage(60); // Code 2
//listaVat.setHorizontalAlignment(Element.ALIGN_RIGHT);//Code 3
//listaVat.addCell(new PdfPCell(new Paragraph("Stawka VAT",czcionka10b)));
//listaVat.addCell(new PdfPCell(new Paragraph("Wartość Netto",czcionka10b)));
//listaVat.addCell(new PdfPCell(new Paragraph("Kwota Vat",czcionka10b)));
//listaVat.addCell(new PdfPCell(new Paragraph("Wartość Brutto",czcionka10b)));
//
//if(netto23!=0.0 & brutto23!=0.0){
//    listaVat.addCell(new PdfPCell(new Paragraph("23%",czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(netto23),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(brutto23-netto23),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(brutto23),czcionka10b)));
//}
//if(netto8!=0.0 & brutto8!=0.0){
//    listaVat.addCell(new PdfPCell(new Paragraph("8%",czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(netto8),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(brutto8-netto8),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(brutto8),czcionka10b)));
//}
//if(netto5!=0.0 & brutto5!=0.0){
//    listaVat.addCell(new PdfPCell(new Paragraph("5%",czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(netto5),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(brutto5-netto5),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(brutto5),czcionka10b)));
//}
//if(netto0!=0.0 & brutto0!=0.0){
//    listaVat.addCell(new PdfPCell(new Paragraph("0%",czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(netto0),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(brutto0-netto0),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(brutto0),czcionka10b)));
//}
//if(nettozw!=0.0 & bruttozw!=0.0){
//    listaVat.addCell(new PdfPCell(new Paragraph("zw",czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(nettozw),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(bruttozw-nettozw),czcionka10b)));
//    listaVat.addCell(new PdfPCell(new Paragraph(MyDouble.StringRet(bruttozw),czcionka10b)));
//}
//
//Paragraph formaPlatnosci = new Paragraph();
//formaPlatnosci.setAlignment(Paragraph.ALIGN_RIGHT);
//formaPlatnosci.add(new Chunk("\n"));
//formaPlatnosci.add(new Chunk("Forma płatności: "+platnosc+"\n", czcionka16));
//if(platnosc.endsWith("Przelew")){
//formaPlatnosci.add(new Chunk("Bank: "+sp.getBank()+" Numer konta: "+sp.getNumerKonta(), czcionka16));
//}else{
//formaPlatnosci.add(new Chunk("Zapłacono gotówką.", czcionka16));
//}
//
//Paragraph suma = new Paragraph();
//suma.setAlignment(Paragraph.ALIGN_RIGHT);
//suma.add(new Chunk("\n"));
//Double sumaDoDruku = brutto23+brutto8+brutto5+brutto0+bruttozw;
//suma.add(new Chunk("Do zapłaty:     "+MyDouble.StringRet(sumaDoDruku)+" PLN\n", czcionka16u));
//suma.add(new Chunk("\n"));
//suma.add(new Chunk("Słownie: "+slownie.ToWords(slownie.liczba_zlotych(((long)(sumaDoDruku/1.0))))+" "+Integer.toString((int) ((sumaDoDruku % 1.0) * 100))+"/100 PLN\n", czcionka10));
//
//PdfPTable formaPlatnosciOrazSuma = new PdfPTable(2);
//formaPlatnosciOrazSuma.setWidthPercentage(100);
//PdfPCell tmp = new PdfPCell(formaPlatnosci);
//tmp.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
//tmp.setBorder(Rectangle.NO_BORDER);
//formaPlatnosciOrazSuma.addCell(tmp);
//tmp = new PdfPCell(suma);
//tmp.setBorder(Rectangle.NO_BORDER);
//tmp.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);
//formaPlatnosciOrazSuma.addCell(tmp);
//
//Paragraph sprzedawcap = new Paragraph();
//sprzedawcap.setAlignment(Paragraph.ALIGN_CENTER);
//sprzedawcap.add(new Chunk("\n\n"));
//sprzedawcap.add(new Chunk("....................................\n", czcionka16));
//sprzedawcap.add(new Chunk("Imię i nazwisko osoby upoważnionej\ndo wystawienia faktury VAT", czcionka8));
//
//Paragraph nabywcap = new Paragraph();
//nabywcap.setAlignment(Paragraph.ALIGN_CENTER);
//nabywcap.add(new Chunk("\n\n"));
//nabywcap.add(new Chunk("....................................\n", czcionka16));
//nabywcap.add(new Chunk("Imię i nazwisko osoby upoważnionej\ndo odbioru faktury VAT", czcionka8));
//
//PdfPTable podpisy = new PdfPTable(2);
//podpisy.setWidthPercentage(90);
//PdfPCell tmp2 = new PdfPCell(sprzedawcap);
//tmp2.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//tmp2.setBorder(Rectangle.NO_BORDER);
//podpisy.addCell(tmp2);
//tmp2 = new PdfPCell(nabywcap);
//tmp2.setBorder(Rectangle.NO_BORDER);
//tmp2.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//podpisy.addCell(tmp2);
//
//
//            document.add(oryg);
////            PdfContentByte cb = writer.getDirectContent();
////            cb.setLineWidth(2.0f);	 // Make a bit thicker than 1.0 default
////            cb.moveTo(30, writer.getVerticalPosition(true));
////            cb.lineTo(PageSize.A4.getWidth()-30, writer.getVerticalPosition(true));
////            cb.stroke();
//            document.add(sn);
////            cb.moveTo(30, writer.getVerticalPosition(true));
////            cb.lineTo(PageSize.A4.getWidth()-30, writer.getVerticalPosition(true));
////            cb.stroke();
//            document.add(new Paragraph("\n"));
//            document.add(listaProduktow);
//            document.add(new Paragraph("\n"));
//            document.add(listaVat);
//            document.add(formaPlatnosciOrazSuma);
//            document.add(new Paragraph(dopisek, czcionka16));
//            document.add(new Paragraph("\n\n"));
//            document.add(podpisy);
//            //document.add(list);
//            //document.add(new Paragraph("\n"));
//            //document.add(overview);
//
//            document.newPage();
//
//            document.add(kop);
////            cb.moveTo(30, writer.getVerticalPosition(true));
////            cb.lineTo(PageSize.A4.getWidth()-30, writer.getVerticalPosition(true));
////            cb.stroke();
//            document.add(sn);
////            cb.moveTo(30, writer.getVerticalPosition(true));
////            cb.lineTo(PageSize.A4.getWidth()-30, writer.getVerticalPosition(true));
////            cb.stroke();
//            document.add(new Paragraph("\n"));
//            document.add(listaProduktow);
//            document.add(new Paragraph("\n"));
//            document.add(listaVat);
//            document.add(formaPlatnosciOrazSuma);
//            document.add(new Paragraph(dopisek, czcionka16));
//            document.add(new Paragraph("\n\n"));
//            document.add(podpisy);
//            //document.add(list);
//            //document.add(new Paragraph("\n"));
//            //document.add(overview);
//
//
//            document.addAuthor("duzydamian");
//            document.addProducer();
//            document.addCreator(sp.getNazwa());
//            document.addSubject("Faktrura Vat");
//            document.addTitle("Faktura nr " + numer);
//            document.addCreationDate();
//            document.addKeywords("faktura, vat, firma, klient, produkt");
//            document.addHeader(numer, numer);
//
//            } catch (DocumentException de) {
//                Error.println(de);
//            } catch (IOException ioe) {
//                Error.println(ioe);
//            }
//            document.close();
//            Out.println("Wygenerowano PDF");
//        //try {
////            PdfReader odczyt = new FdfReader("fakturyPDF/Faktura1_2011.pdf");
////        } catch (IOException ex) {
////            Logger.getLogger(DdFakturyView.class.getName()).log(Level.SEVERE, null, ex);
////        }
//    }
//
//    public void open(String numer){
//            File f = new File("fakturyPDF"+System.getProperty("file.separator")+"Faktura_"+numer.replaceAll("/", "_")+".pdf");
//        try {
//            Runtime.getRuntime().exec(DdFakturyView.programPDF + f.toString());
//        } catch (IOException ex) {
//            Error.println(ex);
//        }
//    }
//
//    public void print(String numer) throws FileNotFoundException, IOException{
//        // Create a PDFFile from a File reference
//        File f = new File("fakturyPDF"+System.getProperty("file.separator")+"Faktura_"+numer.replaceAll("/", "_")+".pdf");
//        
//        }
}
