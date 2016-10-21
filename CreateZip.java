import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;




/**
 * Created by aberestenko on 10/13/2016.
 */
public class CreateZip {
    private String inputFolder;
    private String outputFolder;


    public static void fromarchive(){
        byte[] buffer = new byte[1024];
        int length;
        ZipFile z;
        Enumeration en;
        Vector<ZipEntry> zipEntries;
        ZipEntry ze;
        InputStream is;
        OutputStream outStream = null;
        try{
            //list of entities in zip file*************
            z= new ZipFile("C:\\testfolder\\inputs.zip");
            en = z.entries();
            zipEntries = new Vector<ZipEntry>();

            while(en.hasMoreElements())
            {
                zipEntries.addElement(
                        (ZipEntry)en.nextElement());
            }
            //********************************************
            for (int i = 0; i < zipEntries.size(); i++){
                
                if (!zipEntries.get(i).isDirectory()){
                    is = z.getInputStream(zipEntries.get(i));//добавить проверку на .zip потому что это тоже файл
                    //BufferedReader br = new BufferedReader(new FileReader("foo.in"));
                    while ((length = is.read(buffer)) > 0) {

                        outStream.write(buffer, 0, length);
                    }
                    System.out.println("Копирование файла завершено");

                }



            }


            outStream = new FileOutputStream(new File("C:\\testfolder\\ffg.txt"));
            for (int i = 0; i < zipEntries.size(); i++){
                if (!zipEntries.get(i).isDirectory()){
                    is = z.getInputStream(zipEntries.get(i));//добавить проверку на .zip потому что это тоже файл
                    //BufferedReader br = new BufferedReader(new FileReader("foo.in"));
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
    sourceZipFile
    destinationFolder - полное название папки назначения без экстеншна
    
     */
    public static void work(ZipFile sourceZipFile, String destinationFolder){

        try {
            File destinationZipFolder = FileRW.createFile(destinationFolder);
            //String zipFileTo = Path + "\\" + FileRW.deleteFileExtension(FileRW.getFileName(zipFileFrom));
            Vector<ZipEntry> zipEntries = getZipEntries(sourceZipFile);
            ZipEntry entryInZipFile = null;
            InputStream is = null;
            String file;
            for (int i = 0; i < zipEntries.size(); i++) {
                //если файл
                entryInZipFile = zipEntries.get(i);
                if (!entryInZipFile.isDirectory()) {
                    switch (FileRW.getFileExtension(entryInZipFile.getName())) {
                        //if text file
                        case "txt":

                            is = sourceZipFile.getInputStream(entryInZipFile);
                            FileRW.StreamToFile(is, new File(destinationFolder + "\\" + FileRW.getFileName(entryInZipFile.getName())));
                            break;
                        case "zip":
                            //то скопировать в папку назначения и запустить work(рекурсивно)
                            //Archiver.unzip()



                            break;
                        default:




                    }

                }
            }
            //заархивировала зип папку
            Archiver.zipFile(destinationFolder);
            //удалила ту, которую архивировала
            FileRW.deleteFile(destinationFolder);
        }catch(Exception e){
            e.printStackTrace();}


    }
    
    /*
    Returns vector of zip entries for incoming zip file
     */
    public static Vector<ZipEntry> getZipEntries(ZipFile zipFile) {
        Enumeration en;
        Vector<ZipEntry> zipEntries = null;

        try {
            //list of entities in zip file*************
            en = zipFile.entries();
            zipEntries = new Vector<ZipEntry>();
            while (en.hasMoreElements()) {
                zipEntries.addElement(
                        (ZipEntry) en.nextElement());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipEntries;
    }


    public static void main(String[] args) throws Exception {


    }




}
