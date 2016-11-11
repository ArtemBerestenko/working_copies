import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by aberestenko on 10/17/2016.
 */
public class FileRW {

    public static String[] pathSeparators = {"\\", "//"};

    //Can write from one file to another line by line
    public static void StreamToFile(InputStream is, File fileTo){
        try {

            /*
            FileReader fr = new FileReader(fileFrom);
            */
            FileWriter fw = new FileWriter(fileTo);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            BufferedWriter writer = new BufferedWriter(fw);

            System.out.println(is.toString());
            String line = reader.readLine();
            while (line != null) {
                /*
                сдесь блок в котором изменяется line согласно правилам, а также из line вычитаются все емейлы и телефоны
                 */
                Parser.readEmails(line);
                Parser.readPhones(line);
                writer.write(Parser.changeCodeNumbers(line) + (char)13 + (char)10);
                writer.flush();
                line = reader.readLine();
            }
            writer.close();
            //fr.close();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public static String deleteFileExtension(String file){
        return file.substring(0, file.lastIndexOf("."));
    }


    //метод определения расширения файла
    public static String getFileExtension(String fileName) {
        //String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".")+1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }

    /*
    Returns relative name of the file(with extension) or directory
     */
    public static String getFileName(String fileName){
        //String fileName = file.getName();
        Integer length = fileName.length() - 1;
        char usedseparator = fileName.lastIndexOf('\\') > fileName.lastIndexOf('/')? '\\':'/';
        if (fileName.charAt(length) == usedseparator){
            fileName = fileName.substring(0, length);
        }
        usedseparator = fileName.lastIndexOf('\\') > fileName.lastIndexOf('/')? '\\':'/';
        return fileName.substring(fileName.lastIndexOf(usedseparator)+1, fileName.length());
    }


    public static List<String> readFile (File name){
        ArrayList<String> states = new ArrayList<String>();
        return states;
    }
    public static void writeFile(File name, Set set){}

    public static BufferedReader wrapper(File f){
        BufferedReader a = null;
        try {
             a = new BufferedReader(new FileReader("G:\\programs\\java\\testGUI\\src\\testgui\\filelist.java"));
        }
        catch(Exception e){}
        return a;
    }

    public static void writeParsedText(File in, File out){}

    public static File createFile(String filename){
        File file = new File(filename);
        try{
            file.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
        return file;
    }

    public static void deleteFile(String filename){
        File file = new File(filename);
        try{
            file.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void copyZipFile(ZipFile z, InputStream inStream, String filename) {
        ZipOutputStream zipstream = null;
        File toFile = null;
        byte[] buffer = new byte[1024];
        int length;
        try {
            toFile = new File(filename);
            zipstream = new ZipOutputStream(new FileOutputStream(toFile));


            while ((length = inStream.read(buffer)) > 0) {
                zipstream.write(buffer, 0, length);
            }
            System.out.println("Копирование файла завершено");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                zipstream.close();
            } catch (Exception e) {
            }
        }
    }




}
