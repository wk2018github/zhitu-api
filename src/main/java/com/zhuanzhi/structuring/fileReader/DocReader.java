package com.zhuanzhi.structuring.fileReader;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;


public class DocReader extends Reader {


    public static ArrayList<String> readlines(String path) {
        return readDoc(path);
    }


    private static ArrayList<String> readDoc(String path) {
        File file;
        WordExtractor extractor;
        ArrayList<String> file_content = new ArrayList<String>();

        try {
            file = new File(path);
            System.out.println(path);
            System.out.println(file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            String[] fileData = extractor.getParagraphText();
            String temp;
            for (int i = 0; i < fileData.length; i++) {
                temp = fileData[i];
                temp = temp.trim();
                if (!temp.equals("") && !temp.equals("FORMTEXT"))
                    file_content.add(temp);
            }
        } catch (Exception exep) {
            exep.printStackTrace();
        }
        return file_content;
    }

    @Override
    public String readFile(String path) {
        ArrayList<String> lines = readDoc(path);
        StringBuilder fileContent = new StringBuilder();
        for (String line : lines) {
            fileContent.append(line).append("\n");
        }
        return fileContent.toString();
    }
}
