package com.eojhet.boring;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
            "\"depths\":[\"0.25\",\"1\",\"8\",\"12\",\"16\"]," +
            "\"types\":[\"topSoil\",\"sandyClay\",\"clay\",\"sand\",\"siltySand\"]," +
            "\"descriptions\":[\"Top soil\",\"Sandy clay\",\"Dark red clay\",\"Beige sand\",\"Light beige silty sand\"]}";

    private final String[] info1 = {"Boring Label: \n", "Logged By: \n", "Company: \n"};
    private final String[] info2 = {"Location: \n", "Equipment: \n", "Date: \n", "Time: \n"};
    private final String[] header = {"Graphical\nLog", "Top Depth\n(FT)", "Thick.\n(FT)", "Bt.Elev.\n(FT)", "Material\nDescription"};

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

        Table title = new Table(new float[]{1.5f,2,1.1f},true);
        Cell titleCell = new Cell().setHorizontalAlignment(HorizontalAlignment.RIGHT);
        titleCell.add(new Paragraph("BORING LOG by The Boring App").setFont(bold));
        titleCell.setBorderBottom(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER);

        title.addCell(new Cell().add(new Paragraph("")).setBorderRight(Border.NO_BORDER));
        title.addCell(titleCell);
        title.addCell(new Cell().add(new Paragraph("")).setBorderLeft(Border.NO_BORDER));

        document.add(title);

        Table tableInfo = new Table(new float[]{1,1,1}, true);

        tableInfo.addCell(new Cell().add(new Paragraph(info1[0] + boringData.getLabel()).setFont(font)).setFontSize(10).setBorderBottom(Border.NO_BORDER));
        tableInfo.addCell(new Cell().add(new Paragraph(info1[1] + boringData.getLogBy()).setFont(font)).setFontSize(10).setBorderBottom(Border.NO_BORDER));
        tableInfo.addCell(new Cell().add(new Paragraph(info1[2] + boringData.getCompany()).setFont(font)).setFontSize(10).setBorderBottom(Border.NO_BORDER));

        document.add(tableInfo);

        Table tableInfo2 = new Table(new float[]{3,2,1,1}, true);

        tableInfo2.addCell(new Cell().add(new Paragraph(info2[0] + boringData.getLocation()).setFont(font)).setFontSize(10).setBorderBottom(Border.NO_BORDER));
        tableInfo2.addCell(new Cell().add(new Paragraph(info2[1] + boringData.getEquipment()).setFont(font)).setFontSize(10).setBorderBottom(Border.NO_BORDER));
        tableInfo2.addCell(new Cell().add(new Paragraph(info2[2] + boringData.getDate()).setFont(font)).setFontSize(10).setBorderBottom(Border.NO_BORDER));
        tableInfo2.addCell(new Cell().add(new Paragraph(info2[3] + boringData.getTime()).setFont(font)).setFontSize(10).setBorderBottom(Border.NO_BORDER));

        document.add(tableInfo2);

        Table tableBoring = new Table(new float[]{1,1,1,1,5},true);

        for (String label : header) {
            tableBoring.addCell(new Cell().add(new Paragraph(label).setFont(bold)).setFontSize(9));
        }

        tableBoring.addCell(new Cell(1,5).add(new Paragraph(" ")).setBorderRight(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER));

        ArrayList<Float> depths = boringData.getDepths();
        ArrayList<String> types = boringData.getTypes();
        ArrayList<String> descriptions = boringData.getDescriptions();

        Float topDepth = 0f;
        for(int i = 0; i < depths.size(); i++) {
            Float depth = depths.get(i);
            Float thickness = depth - topDepth;
            // Graphical Log
            tableBoring.addCell(new Cell().add(new Paragraph(types.get(i)).setFont(font)).setFontSize(10).setHeight(thickness*35));
            // Top Depth
            tableBoring.addCell(new Cell().add(new Paragraph("\t"+topDepth.toString()).setFont(font)).setFontSize(10).setBorderTop(new DashedBorder(0.6f)).setBorderRight(Border.NO_BORDER));
            // Thickness
            tableBoring.addCell(new Cell().add(new Paragraph("\t"+thickness.toString()).setFont(font)).setFontSize(10).setBorderTop(new DashedBorder(0.6f)).setBorderRight(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER));
            // Bottom Elevation
            tableBoring.addCell(new Cell().add(new Paragraph("\t-" + depth).setFont(font)).setFontSize(10).setBorderTop(new DashedBorder(0.6f)).setBorderRight(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER));
            // Material Description
            tableBoring.addCell(new Cell().add(new Paragraph(descriptions.get(i)).setFont(font)).setFontSize(10).setBorderTop(new DashedBorder(0.6f)).setBorderLeft(Border.NO_BORDER));
            topDepth += thickness;
        }


        document.add(tableBoring);
        tableBoring.complete();
        document.close();
    }

    public static void main(String[] args) throws IOException {
        BoringPDF boringValues = new BoringPDF(JSONOBJECT);
        boringValues.make();
    }


}
