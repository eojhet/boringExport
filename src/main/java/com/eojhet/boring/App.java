package com.eojhet.boring;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class App {

    public static void main( String[] args ) throws FileNotFoundException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(new FileOutputStream("output/itextPDF.pdf")));
        Document document = new Document(pdf);
        String line = "Hello! Welcome to iTextPdf CORE 7!";
        document.add(new Paragraph(line));
        document.close();

    }
}
