package com.zhuanzhi.structuring.factory;

import com.zhuanzhi.structuring.fileReader.*;

public class FileReaderFactory {
    public static final int DOC = 0;
    public static final int DOCX = 1;
    public static final int PDF = 2;
    public static final int IMAGE = 3;

    public static Reader CreateFileReader(int type) throws Exception {
        switch (type) {
            case DOC:
                return new DocReader();
            case DOCX:
                return new DocxReader();
            case PDF:
                return new PdfReader();
            case IMAGE:
                return new ImageReader();
        }
        throw new Exception("暂未实现该类型文件读取功能");
    }
}
