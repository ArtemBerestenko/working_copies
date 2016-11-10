/**
 * Created by aberestenko on 10/4/2016.
 */

import java.io.*;
import java.util.zip.*;


public class Readingfromfile {
    public static void main(String[] args) {
        String file = "C:\\Users\\aberestenko\\Desktop\\Studiing Courses\\BasicJava\\File_system_java\\textfile.txt";
        String zipfile = "C:\\Users\\aberestenko\\Desktop\\Studiing Courses\\BasicJava\\File_system_java\\textfile.zip";

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipfile)))
        {
            System.out.println("Размер файла: " + zin.available() + " байт(а)");

            int i=-1;
            while((i=zin.read())!=-1){

                System.out.print((char)i);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }




        /*

        try(FileInputStream fin=new FileInputStream(file))
        {
            System.out.println("Размер файла: " + fin.available() + " байт(а)");

            int i=-1;
            while((i=fin.read())!=-1){

                System.out.print((char)i);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    */
    }

}
