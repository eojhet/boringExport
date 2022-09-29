package com.eojhet.boring;


import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.font.constants.StandardFonts;

import java.io.FileOutputStream;
import java.io.IOException;

public class App {

    public static void main( String[] args ) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(new FileOutputStream("output/itextPDF.pdf")));
        Document document = new Document(pdf);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        String line = "iText is:";
        document.add(new Paragraph(line).setFont(font));

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022")
                .setFont(font);

        list.add(new ListItem("Nevergonna give you up"))
                .add(new ListItem("Never gonna let you down"))
                .add(new ListItem("Never gonna run around and desert you"))
                .add(new ListItem("Never gonna make you cry"))
                .add(new ListItem("Never gonna say goodbye"))
                .add(new ListItem("Never gonna tell a lie and hurt you"));
        document.add(list);
        document.close();

    }
}
