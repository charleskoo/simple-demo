package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class PDFService {

    public void read() {
        log.info("PDFService reds");
        // readPDF();
        addLabel();
    }

    private void readPDF() {
        PDDocument helloDocument = null;
        try {
            helloDocument = PDDocument.load(new File(
                    "C:\\Users\\charles.kou\\Desktop\\pdf\\origin.pdf"));
            PDFTextStripper textStripper = new PDFTextStripper();
            // "GBK"
            System.out.println(textStripper.getText(helloDocument));

            helloDocument.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void addLabel() {
        PDDocument doc = null;
        try {
            File file = new File(
                    "C:\\Users\\charles.kou\\Desktop\\pdf\\origin.pdf");
            doc = PDDocument.load(file);
            // doc.getPage(0).get

            // PDFTextStripper textStripper = new PDFTextStripper();
            // "GBK"
            // System.out.println(textStripper.getText(doc));

            // doc.addPage(new PDPage());

            // doc.save("C:\\Users\\charles.kou\\Desktop\\pdf\\result\\result.pdf");

            int noOfPages= doc.getNumberOfPages();
            System.out.print(noOfPages);

            doc.close();
        } catch (IOException e) {

            log.error(e.getMessage(), e);
        }
    }
}
