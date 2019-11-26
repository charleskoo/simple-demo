package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FontFlagText;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PDFService {

	private static final String FONT_FILE_PATH_SIMSUN = "C:/pdf/fonts/SimSun.ttf";
	private static final String FONT_FILE_PATH_TIMES = "C:/pdf/fonts/TIMES.TTF";

	public void read(File pdfFile, String textFileName, String destFileName) {
		PDDocument doc = null;
		try {
			List<String> lines = readTextFile(textFileName);

			doc = PDDocument.load(pdfFile);
			PDPageTree pages = doc.getPages();
			int count = pages.getCount();
			int size = lines.size();
			if (count != size) {
				log.error("PDF页数与文本内容行数不匹配");
				log.error("PDF页数: {}", count);
				log.error("文本内容行数: {}", size);
			}

			// 1 - 字体加载
			PDType0Font simSunFont = PDType0Font.load(doc, new File(FONT_FILE_PATH_SIMSUN));
			PDType0Font timesFont = PDType0Font.load(doc, new File(FONT_FILE_PATH_TIMES));

			// 2 - 偏移量
			float offsetx = 40;
			float offsety = 50;

			// 3 - 循环处理每页
			for (int index = 0; index < count; index++) {
				log.info("开始处理第{}页", (index + 1));
				PDPage page = pages.get(index);
				String text = lines.get(index);

				PDPageContentStream contents = new PDPageContentStream(doc, page,
						PDPageContentStream.AppendMode.PREPEND, true);
				contents.beginText();
				contents.newLineAtOffset(offsetx, offsety);

				List<FontFlagText> textList = convertToFontFlagText(timesFont, text);
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
			}

			// 保存文件
			doc.save(destFileName);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (null != doc) {
				try {
					// 关闭文件
					doc.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}

	}

	private List<FontFlagText> convertToFontFlagText(PDType0Font enFont, String text) {
		List<FontFlagText> textList = Lists.newArrayList();
		for (int i = 0; i < text.length(); i++) {
			char charAt = text.charAt(i);
			FontFlagText flagText = new FontFlagText();
			flagText.setText(String.valueOf(charAt));
			if (!isCharacterEncodeable(enFont, charAt)) {
				flagText.setFont_flag(1);
			}
			textList.add(flagText);
		}
		return textList;
	}

	private List<String> readTextFile(String textFileName) throws IOException {
		List<String> lines = Lists.newArrayList();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(textFileName), "UTF-8"));
		String line = null;
		while ((line = br.readLine()) != null) {

			if (line.trim().length() > 0) {
				lines.add(line);
			}

		}
		br.close();
		return lines;
	}

	private boolean isCharacterEncodeable(PDType0Font font, char character) {
		if (isChinese(character)) {
			return false;
		}
		
		try {
			font.encode(Character.toString(character));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean isChinese(char c) {
		return c >= 0x4E00 && c <= 0x9FA5;
	}

}
