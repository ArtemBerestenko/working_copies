import javax.imageio.IIOException;
import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by aberestenko on 10/17/2016.
 */
public class Archiver {

    /*
    Просто создаёт рядом архивчик с таким же именем и копирует в него всё содержимое указанной директории
     */
    public static void zipFile(String fileToCompress) {
        File file = new File(fileToCompress);
        FileInputStream fin = null;
        ZipEntry ze;
        byte[] buffer = new byte[1024];
        int length;
        FileOutputStream fout;
        ZipOutputStream zout;
        try {
            if (file.isDirectory()) {

                File zipFile = FileRW.createFile(fileToCompress + ".zip");
                fout = new FileOutputStream(zipFile);
                zout = new ZipOutputStream(fout);
                File[] files = file.listFiles();

                for (int i = 0; i < files.length; i++) {
                    fin = new FileInputStream(files[i]);
                    ze = new ZipEntry(FileRW.getFileName(files[i].getName()));
                    zout.putNextEntry(ze);
                    while ((length = fin.read(buffer)) > 0) {
                        zout.write(buffer, 0, length);
                    }
                    zout.closeEntry();
                    fin.close();
                }
                zout.close();



            }else {

                File zipFile = FileRW.createFile(FileRW.deleteFileExtension(fileToCompress) + ".zip");
                fout = new FileOutputStream(zipFile);
                zout = new ZipOutputStream(fout);
                fin = new FileInputStream(file);
                ze = new ZipEntry(FileRW.getFileName(file.getName()));
                zout.putNextEntry(ze);
                while ((length = fin.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                }
                zout.closeEntry();
                fin.close();
                zout.close();
            }

        } catch (IOException e) {
             e.printStackTrace();
        }
    }

    public static void zipFiles(List files, String placeWhereZip, String zipName){}

    /*
    создаёт в месте назначения папку с таким же названием и копирует в неё всё содержимое зипника
     */


    // этот метод способен извлекать все файлы из архива
    public static void unzip(ZipFile z, String destinationFolder){
        try{
            //ZipFile z = new ZipFile("C:\\testfolder\\file2.zip");
            Enumeration en = z.entries();
            Vector<ZipEntry> zipEntries = new Vector<ZipEntry>();
            byte[] buffer = new byte[1024];
            int length;
            while(en.hasMoreElements())
            {
                zipEntries.addElement(
                        (ZipEntry)en.nextElement());
            }
            ZipEntry ze;
            InputStream is;
            OutputStream outStream = null;
            outStream = new FileOutputStream(new File("C:\\testfolder\\ffg.txt"));
            for (int i = 0; i < zipEntries.size(); i++){
                if (!zipEntries.get(i).isDirectory()){
                    is = z.getInputStream(zipEntries.get(i));//добавить проверку на .zip потому что это тоже файл
                    //BufferedReader br = new BufferedReader(new FileReader("foo.in"));
                    //FileRW.copyZipFile(is, "C:\\testfolder\\ffgssss.zip");
                    //FileRW.StreamToFile(is, new File("C:\\testfolder\\ffgmss.txt"));


                    while ((length = is.read(buffer)) > 0) {

                        outStream.write(buffer, 0, length);
                    }


                    System.out.println("Копирование файла завершено");

                }



            }


        }catch(Exception e){
            e.getMessage();
        }

    }


    /*
    public static File unzip(File zipfile, String placeToLocate){

    }

    public static File ungz(File zipfile, String placeToLocate){}
    */

}
