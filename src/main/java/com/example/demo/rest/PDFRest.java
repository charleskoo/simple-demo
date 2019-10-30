package com.example.demo.rest;

import com.example.demo.service.PDFService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PDFRest {

    @Autowired
    private PDFService pDFService;

    @GetMapping("pdf")
    public String pdfReader() {
//        log.info("hello, pdf");
        pDFService.read();
        return "success";
    }

}
