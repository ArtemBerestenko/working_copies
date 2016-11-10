/**
 * Created by aberestenko on 10/12/2016.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


// при выполнении FileOutputStream fos = new FileOutputStream(newFile); файл будет создан на диске




public class ZipUtil {
    public static void del(){
        File folder = new File("C:\\testfolder\\f");
        folder.delete();
    }
    public static void copy(){
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            File fromFile = new File("C:\\testfolder\\file1.txt");
            File toFile = new File("C:\\testfolder\\file2.txt");
            //toFile.createNewFile();
            inStream = new FileInputStream(fromFile);
            outStream = new FileOutputStream(toFile);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            System.out.println("Копирование файла завершено");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
            } catch (Exception e) {
            }
            try {
                outStream.close();
            } catch (Exception e) {
            }
        }
    }
    // этот метод способен извлекать все файлы из архива
    public static void fromarchive(){
        try{
            ZipFile z= new ZipFile("C:\\testfolder\\file2.zip");
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
                    /*

                    while ((length = is.read(buffer)) > 0) {

                        outStream.write(buffer, 0, length);
                    }
                    */

                    System.out.println("Копирование файла завершено");

                }



            }


        }catch(Exception e){
            e.getMessage();
        }

    }
    //этот метод должен парсить содержимое файла: мы получаем поток символов, который можем парсить
    public static void parse_file(){
        String str;
        BufferedReader in = null;
        try{
            in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\testfolder\\parse_file.txt"))));
            while ((str = in.readLine()) != null) {
                System.out.println(str);
                /*
                String arr[] = str.split("\t");
                rlst = new ArrayList<String>(Arrays.asList(arr));
                list.addLast(rlst);
                */
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    // распаковка файла gz с помощью GZIP
    public static void decompressGzipFile(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int factorial(int i){
        if (i == 1){return 1;}
        return i*ZipUtil.factorial(i-1);

    }

    //как заархивировать файл
    public static void compress_to_zip(){
        try{
            FileInputStream fin = new FileInputStream("C:\\testfolder\\file_to_be_compressed.txt");
            FileOutputStream fout = new FileOutputStream("C:\\testfolder\\test.zip");

            ZipOutputStream zout = new ZipOutputStream(fout);
            //Для всех файлов:
            {
                //ZipEntry ze = new ZipEntry("C:\\testfolder\\test.zip\\file_to_be_compressed.txt");//Имя файла - имя файла в архиве
                ZipEntry ze = new ZipEntry("file_to_be_compressed.txt");

                zout.putNextEntry(ze);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fin.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                }
                //отправка данных в поток zout
                zout.closeEntry();
            }
            zout.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        //System.out.println(factorial(4));
        compress_to_zip();
        //decompressGzipFile("C:\\testfolder\\ann@domain.org.gz", "C:\\testfolder\\ffgg.txt");
        //from_gz();
        //parse_file();
        //fromarchive();
        //copy();
        //del();
        /*

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream("C:\\Users\\aberestenko\\Desktop\\archive.zip"));

        File file = new File("C:\\folder");

        doZip(file, out);

        out.close();
        */
    }

    private static void doZip(File dir, ZipOutputStream out) throws IOException {
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
                doZip(f, out);
            else {
                out.putNextEntry(new ZipEntry(f.getPath()));
                write(new FileInputStream(f), out);
            }
        }
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }
}