package com.example.demo.rest;

import com.example.demo.service.PDFService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@Slf4j
public class PDFImageRest {

    @Autowired
    private PDFService pDFService;

    @GetMapping("pdftoimage")
    public String pdfReader() throws FileNotFoundException {
        List<File> pdfFileList = getFileList("C:/pdf/YAMI batch 1 image", ".pdf");
        for(File pdfFile : pdfFileList) {
            String imageFileName = getImageFileName(pdfFile);
            try {
                log.info(imageFileName);
                pDFService.toImage(pdfFile, imageFileName);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return "success";
    }

    private List<File> getFileList(String path, String suffix) throws FileNotFoundException {
        List<File> fileList = Lists.newArrayList();
        File dir = new File(path);
        File[] tempList = dir.listFiles();
        for (File file : tempList) {
            String fileName = file.getName();
            if (file.isFile()) {
                String fileSuffix = fileName.substring(fileName.lastIndexOf('.'));
                if (suffix.equals(fileSuffix)) {
                    fileList.add(file);
                }
            }
        }

        if (fileList.isEmpty()) {
            throw new FileNotFoundException("PDF File not exists");
        }
        return fileList;
    }

    private String getImageFileName(File sourceFile) {
        String sourceFileName = sourceFile.getAbsolutePath();
        String pathPreFix = sourceFileName.substring(0, sourceFileName.lastIndexOf('.') + 1);
        return pathPreFix + "png";
    }

}
