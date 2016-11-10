/**
 * Created by aberestenko on 10/4/2016.
 */

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Hello {

    private static void readUsingFileReader(){
        try{
            File file = new File("C:\\testfolder\\parse_file.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                //обрабатываем считанную строку - пишем ее в консоль
                System.out.println(line);
            }
            br.close();
            fr.close();
        }catch(Exception e){}


    }

    public static String getFileName(File file){
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf("\\")+1, fileName.lastIndexOf("."));
    }


    static void copyFile(File zipFile, File newFile) throws IOException {
        ZipFile zipSrc = new ZipFile(zipFile);
        FileOutputStream fout = new FileOutputStream(newFile);

        Enumeration srcEntries = zipSrc.entries();
        while (srcEntries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) srcEntries.nextElement();
            ZipEntry newEntry = new ZipEntry(entry.getName());
            //fout = new File("");
            //fout.putNextEntry(newEntry);

            BufferedInputStream bis = new BufferedInputStream(zipSrc
                    .getInputStream(entry));

            while (bis.available() > 0) {
                fout.write(bis.read());
            }
            //fout.closeEntry();

            bis.close();
        }
        //fout.finish();
        fout.close();
        zipSrc.close();
    }




    public static void main(String[] args) {
        A cof = new coffe();
        System.out.println(cof);
        Decorator dec = new ConcrDecor(cof);
        System.out.println(dec.price());





   }
}


//декоратор
interface A{
    public String price();
}
abstract class Decorator implements A{
    protected A obj;
    public Decorator(A obj){
        this.obj = obj;
    }

}
class ConcrDecor extends Decorator{
    public ConcrDecor(A obj){
        super(obj);

    }
    public  String price(){
        return obj.price() + " wfwfwf";
    }

}
class coffe implements A{
    public String price(){
        return "10";
    }

}