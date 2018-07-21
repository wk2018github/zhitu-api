package zhitu.util;

import org.apache.commons.io.FileUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import java.io.*;
import java.nio.file.Files;


public class TikaUtils {


    /**
     * xxxxx
     * @param file aaaa
     * @return dfwdf
     */
    public static String parseFile(File file){
        String content = "";
        Parser parser = new AutoDetectParser();
        InputStream input = null;
        try{
            Metadata metadata = new Metadata();
            metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            input = new FileInputStream(file);
            ContentHandler handler = new BodyContentHandler();//当文件大于100000时，new BodyContentHandler(1024*1024*10);
            ParseContext context = new ParseContext();
            context.set(Parser.class,parser);
            parser.parse(input,handler, metadata,context);
            /*for(String name:metadata.names()) {
                System.out.println(name+":"+metadata.get(name));
            }
            System.out.println(handler.toString());*/
            content = handler.toString();
            return handler.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(input!=null)input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;

    }

    public static String parseFileByte(byte[] file){
        String content = "";
        Parser parser = new AutoDetectParser();
        InputStream input = null;
        try{
            Metadata metadata = new Metadata();
            metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
            //metadata.set(Metadata.RESOURCE_NAME_KEY, file.);
            input = new ByteArrayInputStream(file);
            ContentHandler handler = new BodyContentHandler();//当文件大于100000时，new BodyContentHandler(1024*1024*10);
            ParseContext context = new ParseContext();
            context.set(Parser.class,parser);
            parser.parse(input,handler, metadata,context);
            /*for(String name:metadata.names()) {
                System.out.println(name+":"+metadata.get(name));
            }
            System.out.println(handler.toString());*/
            content = handler.toString();
            return handler.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(input!=null)input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;

    }

    public static void main(String argt0[])throws Exception{
        /*String str = parseFile(new File("E:\\学习手册pdf\\jin.pdf"));
        System.out.println(str.substring(0,10));
        System.out.println(str.length());*/
        byte[] array = Files.readAllBytes(new File("E:\\学习手册pdf\\wang.pdf").toPath());
        System.out.println(array.toString());
        System.out.println(parseFileByte(array));
    }
}
