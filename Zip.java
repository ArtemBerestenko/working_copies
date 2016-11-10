import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.nio.file.*;

import java.io.File;

class Iter {
    public void performIter() {
        ArrayList a = new ArrayList();
        a.add("a");
        a.add("fkvs");
        Iterator iter = a.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        }
    public void performTreeSet(){

    }

    }




class ZipArchive{
    public void perform(){
        try{
            java.util.zip.ZipFile z = new ZipFile(new File("C:\\zipfolder.zip"));
        }
        catch(Exception ex){
            System.out.print(ex.getMessage());

        }

    }

    public void perform2() throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream("C:\\Users\\archive.zip"));

        File file = new File("C:\\folder");

        doZip(file, out);

        out.close();
    }

    private void doZip(File dir, ZipOutputStream out) throws IOException {
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
                doZip(f, out);
            else {
                out.putNextEntry(new ZipEntry(f.getPath()));
                write(new FileInputStream(f), out);
            }
        }
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }

}
 
class CountFiles {
 
    public void perform() {
        
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream("C:\\Users\\aberestenko\\Desktop\\Studiing Courses\\BasicJava\\File_system_java\\Ourzip.zip")))
        {
            ZipEntry entry;
            String name;
            long size;
            byte b = 0;

            while((entry=zin.getNextEntry())!=null){
                 
                name = entry.getName();
                size=entry.getSize();
                System.out.printf("Name: %s \t size: %d \n", name, size);

            }
        }
        catch(Exception ex){
             
            System.out.println(ex.getMessage());
        } 
    } 
}


class Pas{
    public void perform(){
        Path testFilePath = Paths.get("C:\\test");
        System.out.println(testFilePath.getFileName());
        System.out.println(testFilePath.getFileSystem());
        System.out.println(testFilePath.getName(0));
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void perform2() {
        String folder = "C:\\test";
        List<String> folderNames = new ArrayList();
        List<String> fileNames = new ArrayList();
        for (File file : new File(folder).listFiles()) {
            if (file.isDirectory()) {
                folderNames.add(file.getName());
            } else {
                fileNames.add(file.getName());
            }
        }
        for(String folderName : folderNames){
            System.out.println("директория: " + folderName);
        }
        for(String fileName : fileNames){
            System.out.println("файл: " + fileName);
        }
    }

}

class Z{
    public void perform(){
        try
        {
            ZipFile zf = new ZipFile("C:\\Users\\aberestenko\\Desktop\\Studiing Courses\\BasicJava\\File_system_java\\Ourzip.zip");
            Vector zipEntries = new Vector();
            Enumeration en = zf.entries();

            while(en.hasMoreElements())
            {
                zipEntries.addElement(
                        (ZipEntry)en.nextElement());
            }
            System.out.println("mgmewf");
            System.out.println(zipEntries.elements());

        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
}

public class Zip {
    //метод определения расширения файла
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".")+1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }

    public static void showFilesAndDirectoryes (File f) throws Exception  {

        if (!f.isFile()) {
            if (Zip.getFileExtension(f) == "zip") {

            }
            //System.out.println (f.getName ());
            return;
        }

        if (f.isDirectory ()) {
            System.out.println(f.getCanonicalFile());

            try {
                //System.out.println(f.getCanonicalFile());
                File[] child = f.listFiles();
                if (child == null){
                    return;
                }

                for (int i = 0; i < child.length; i++) {
                    System.out.println(child[i].getParent());
                    showFilesAndDirectoryes (child[i]);
                }//for

            }//try
            catch(Exception e){
                e.printStackTrace();
            }//catch
        } //if
    }



    public static void main(String[] args) throws Exception {
        //CountFiles s = new CountFiles();
        //s.perform();


        //showFilesAndDirectoryes(new File("C:\\inputs.zip"));


        /*
        Pas p = new Pas();
        p.perform2();
        */
        /*
        Z z = new Z();
        z.perform();
        */
    }
}
