package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PDFService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PDFRest {

	@Autowired
	private PDFService pDFService;

	@GetMapping("pdf")
	public String pdfReader() {
		String sourceFileName = "C:/Users/charles.kou/Desktop/pdf/3.2.P.8稳定性考察组合图谱.pdf";
		String destFileName = "C:/Users/charles.kou/Desktop/pdf/result/result";
		pDFService.read(sourceFileName, destFileName + 0 + ".pdf");
		return "success";
	}

}
