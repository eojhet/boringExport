package com.eojhet.boring;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

// https://kb.itextpdf.com/home/it7kb/ebooks/itext-7-jump-start-tutorial-for-java/chapter-1-introducing-basic-building-blocks
// https://github.com/itext/i7js-jumpstart/tree/develop/src/main/java/tutorial
// https://api.itextpdf.com/iText7/java/7.1.16/

public class BoringPDF {
    public final static String JSONOBJECT = "{" +
            "\"label\":\"MW-1\"," +
            "\"logBy\":\"Joe Gonzalez\"," +
            "\"company\":\"Bay Environmental inc.\"," +
            "\"location\":\"2048 Nags Head Rd\"," +
            "\"equip\":\"Hand Auger\"," +
            "\"date\":\"2022-03-17\"," +
            "\"time\":\"10:30\"," +
            "\"depths\":[\"1\",\"3\",\"8\",\"12\",\"16\"]," +
            "\"types\":[\"topSoil\",\"sandyClay\",\"clay\",\"sand\",\"siltySand\"]," +
            "\"descriptions\":[\"Top soil\",\"Sandy clay\",\"Dark red clay\",\"Beige sand\",\"Light beige silty sand\"]}";

    private final String[] header1 = {"Boring Label: \n", "Logged By: \n", "Company: \n"};
    private final String[] header2 = {"Location: \n", "Equipment: \n", "Date: \n", "Time: \n"};

    private BoringObjectDecoder boringData;

    public BoringPDF(String boringJson) {
        this.boringData = new BoringObjectDecoder(boringJson);
    }

    public void make() throws IOException {
        PdfWriter writer = new PdfWriter(new FileOutputStream("output/boringLog.pdf"));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.LETTER);

        document.setMargins(20, 30, 20, 30);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Table title = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
        Cell titleCell = new Cell().setHorizontalAlignment(HorizontalAlignment.RIGHT);
        titleCell.add(new Paragraph("BORING LOG by The Boring App").setFont(bold));
        titleCell.setBorderBottom(Border.NO_BORDER);

        title.addCell(titleCell);

        document.add(title);

        Table table1 = new Table(new float[]{1,1,1});
        table1.setWidth(UnitValue.createPercentValue(100));

        table1.addCell(new Cell().add(new Paragraph(header1[0] + boringData.getLabel()).setFont(font)).setBorderBottom(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(header1[1] + boringData.getLogBy()).setFont(font)).setBorderBottom(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(header1[2] + boringData.getCompany()).setFont(font)).setBorderBottom(Border.NO_BORDER));

        document.add(table1);

        Table table2 = new Table(new float[]{1,1,1,1});
        table2.setWidth(UnitValue.createPercentValue(100));

        table2.addCell(new Cell().add(new Paragraph(header2[0] + boringData.getLocation()).setFont(font)));
        table2.addCell(new Cell().add(new Paragraph(header2[1] + boringData.getEquipment()).setFont(font)));
        table2.addCell(new Cell().add(new Paragraph(header2[2] + boringData.getDate()).setFont(font)));
        table2.addCell(new Cell().add(new Paragraph(header2[3] + boringData.getTime()).setFont(font)));

        document.add(table2);


        document.close();
    }

    public static void main(String[] args) throws IOException {
        BoringPDF boringValues = new BoringPDF(JSONOBJECT);
        boringValues.make();
    }


}
