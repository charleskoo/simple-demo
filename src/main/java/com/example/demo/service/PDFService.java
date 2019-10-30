package com.example.demo.service;

import java.io.File;
import java.io.IOException;

import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PDFService {

	private static final float INCH = 72;

	public void read() {
		PDDocument doc = null;
		try {
			File file = new File("C:\\Users\\charles.kou\\Desktop\\pdf\\origin.pdf");
			doc = PDDocument.load(file);

			log.info("PDF {} Loaded.", file.getName());

			PDPage page = doc.getPage(0);

//			float pw = page.getMediaBox().getUpperRightX();
//			float ph = page.getMediaBox().getUpperRightY();

			PDType0Font font;
//			TrueTypeCollection trueTypeCollection = new TrueTypeCollection(new File("c:/windows/fonts/simsun.ttc"));
//			TrueTypeFont trueTypeFont = trueTypeCollection.getFontByName("SimSun");
//			if (null == trueTypeFont) {
			String fontFilePath = "C:\\simsun.ttf";
			font = PDType0Font.load(doc, new File(fontFilePath));
//			} else {
//				font = PDType0Font.load(doc, trueTypeFont, false);
//			}
//			trueTypeCollection.close();

			PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.PREPEND,
					true);
			contents.beginText();
			contents.setFont(font, 10);
			float offsetx = 40;
			float offsety = 105;
			contents.newLineAtOffset(offsetx, offsety);
			contents.showText("3.2.P.5.3.3-21   含量方法学验证/溶液稳定性/对照品溶液（0h） HPLC图谱");
			contents.endText();
			contents.close();

			doc.save("C:\\Users\\charles.kou\\Desktop\\pdf\\result\\result.pdf");

			doc.close();
		} catch (IOException e) {

			log.error(e.getMessage(), e);
		}

	}

}
