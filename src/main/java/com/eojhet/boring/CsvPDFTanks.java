package com.eojhet.boring;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

// https://kb.itextpdf.com/home/it7kb/ebooks/itext-7-jump-start-tutorial-for-java/chapter-1-introducing-basic-building-blocks
// https://github.com/itext/i7js-jumpstart/tree/develop/src/main/java/tutorial
// https://api.itextpdf.com/iText7/java/7.1.16/

public class CsvPDFTanks {
    public static final String DATA = "src/main/resources/data/asttankfoundationroof.txt";
    public static void main(String[] args) throws IOException {
        PdfWriter writer = new PdfWriter(new FileOutputStream("output/tankPDF.pdf"));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.LEGAL.rotate());

        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Table table = new Table(new float[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
        table.setWidth(UnitValue.createPercentValue(100));
        BufferedReader br = new BufferedReader(new FileReader(DATA));

        String line = br.readLine();
        process(table, line, bold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, font, false);
        }
        br.close();
        document.add(table);
        document.close();
    }

    public static void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, "\t");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }
}
