package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FontFlagText;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PDFService {

	private static final String FONT_FILE_PATH_SIMSUN = "C:/Users/charles.kou/Desktop/pdf/fonts/SimSun.ttf";
	private static final String FONT_FILE_PATH_TIMES = "C:/Users/charles.kou/Desktop/pdf/fonts/TIMES.TTF";

	public void read(String sourceFileName, String destFileName) {
		PDDocument doc = null;
		try {
			File file = new File(sourceFileName);
			doc = PDDocument.load(file);

			PDPage page = doc.getPage(0);

			PDType0Font simSunFont = PDType0Font.load(doc, new File(FONT_FILE_PATH_SIMSUN));
			PDType0Font timesFont = PDType0Font.load(doc, new File(FONT_FILE_PATH_TIMES));
			PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.PREPEND,
					true);
			contents.beginText();
			float offsetx = 40;
			float offsety = 50;
			contents.newLineAtOffset(offsetx, offsety);

			String str = "附图3.2.P.8- 1稳定性试验/影响因素/0天/有关物质/空白溶剂HPLC图谱";
			List<FontFlagText> textList = Lists.newArrayList();
			for (int i = 0; i < str.length(); i++) {
				char charAt = str.charAt(i);
				FontFlagText text = new FontFlagText();
				text.setText(String.valueOf(charAt));
				if (isChinese(charAt)) {
					text.setFont_flag(1);
				}
				textList.add(text);
			}

			for (FontFlagText fontFlagText : textList) {
				if (fontFlagText.getFont_flag() == 1) {
					contents.setFont(simSunFont, 10);
				} else {
					contents.setFont(timesFont, 10);
				}
				contents.showTextWithPositioning(new String[] { fontFlagText.getText() });
			}

			contents.endText();
			contents.close();

			doc.save(destFileName);

			doc.close();

		} catch (IOException e) {

			log.error(e.getMessage(), e);
		}

	}

//	public static void main(String[] args) {
//		String str = "附图3.2.P.8- 1稳定性试验/影响因素/0天/有关物质/空白溶剂HPLC图谱";
//		List<FontFlagText> textList = Lists.newArrayList();
//		for (int i = 0; i < str.length(); i++) {
//			char charAt = str.charAt(i);
//			FontFlagText text = new FontFlagText();
//			text.setText(String.valueOf(charAt));
//			if (isChinese(charAt)) {
//				text.setFont_flag(1);
//			}
//			textList.add(text);
//		}
//
//		List<FontFlagText> result = Lists.newArrayList();
//		for (FontFlagText fontFlagText : textList) {
//			System.out.println(fontFlagText.getFont_flag() + "\t" + fontFlagText.getText());
//		}
//	}

	public static boolean isChinese(char c) {
		return c >= 0x4E00 && c <= 0x9FA5;
	}

}
