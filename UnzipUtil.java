/**
 * Created by aberestenko on 10/19/2016.
 */
import java.io.*;
import java.net.URI;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.*;


public class UnzipUtil {
    //работает корректно
    //destFolder - "kjkv\svkls"
    //if zip file contains folder A, then new way it works will be: "destFolder\A...":
    //zipFile/
    //        A/
    //          ...
    //result:
    //destFolder/
    //           A/
    //             ...

    public static void unzip(String zipFile, String destFolder){
        System.out.println("");
        System.out.println("unzip parameters:");
        System.out.println(destFolder);
        System.out.println(zipFile);
        System.out.println("");

        InputStream is = null;
        File file = new File(zipFile);
        ZipFile zip = null;
        Enumeration entries = null;
        ZipEntry entry;
        String sdirectory;
        String szipFile;
        //test line
        new File(destFolder).mkdirs();
        try {
            zip = new ZipFile(zipFile);
            entries = zip.entries();

            while (entries.hasMoreElements()) {
                entry = (ZipEntry) entries.nextElement();
                System.out.println(entry.getName());


                if (entry.isDirectory()) {
                    new File(destFolder, entry.getName()).mkdirs();
                } else {
                    switch (FileRW.getFileExtension(entry.getName())) {
                        //if text file
                        case "txt":

                            is = zip.getInputStream(entry);
                            FileRW.StreamToFile(is, new File(destFolder + "\\" + entry.getName()));
                            is.close();
                            break;

                        case "gz":
                            write(zip.getInputStream(entry),
                                    new BufferedOutputStream(new FileOutputStream(
                                            new File(destFolder + "/", entry.getName()))));
                            GzipToFile(destFolder + "\\" + entry.getName(), destFolder + "\\" + entry.getName().replaceAll(".gz", ".txt"));
                            recursiveDelete(destFolder + "\\" + entry.getName());
                            compressGzipFile(destFolder + "\\" + entry.getName().replaceAll(".gz", ".txt"), destFolder + "\\" + entry.getName());
                            recursiveDelete(destFolder + "\\" + entry.getName().replaceAll(".gz", ".txt"));

                            break;
                        case "zip":
                            String returnStringEntityName = FileRW.deleteFileExtension(FileRW.getFileName(entry.getName()));
                            write(zip.getInputStream(entry),
                                    new BufferedOutputStream(new FileOutputStream(
                                            new File(destFolder + "/", entry.getName()))));
                            unzip(destFolder + "\\" + entry.getName(), FileRW.deleteFileExtension(destFolder + "\\" + entry.getName()));
                            //it is done to consider Intermediary folder
                            //unzip(destFolder + "\\" + entry.getName(), FileRW.deleteFileExtension(new File(destFolder + "\\" + entry.getName()).getAbsolutePath()));

                            sdirectory = FileRW.deleteFileExtension(destFolder + "\\" + entry.getName());
                            szipFile = destFolder + "\\" + entry.getName();
                            recursiveDelete(destFolder + "\\" + entry.getName());
                            directoryToZip(sdirectory, szipFile);
                            recursiveDelete(sdirectory);


                            break;
                        //default:

                    /*
                    write(zip.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(
                                    new File(destFolder, entry.getName()))));
                                    */
                    }

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                zip.close();
                //is.close();
            }catch (Exception e){
                e.printStackTrace();

            }
        }

    }

    /*
    way it works:
    sdirectory/
               A/
      szipfile/
                A/

     */

    private static void directoryToZip(String sdirectory, String szipFile) throws IOException {
        System.out.println("");
        System.out.println("!!!!!!I was called!!!!!");
        System.out.println("directoryToZipParameters:");
        System.out.println(sdirectory);
        System.out.println(szipFile);
        System.out.println("");
        File directory = new File(sdirectory);
        File zipFile = new File(szipFile);
        // experiment to compress not only files, but file with directory, SUCCESSFUL
        URI base = directory.toURI();
        //URI base = directory.getParentFile().toURI();
        Deque<File> queue = new LinkedList<File>();
        queue.push(directory);
        OutputStream out = new FileOutputStream(zipFile);
        Closeable res = out;

        try {
            ZipOutputStream zout = new ZipOutputStream(out);
            res = zout;
            while (!queue.isEmpty()) {
                directory = queue.pop();
                for (File child : directory.listFiles()) {
                    String name = base.relativize(child.toURI()).getPath();
                    if (child.isDirectory()) {
                        queue.push(child);
                        name = name.endsWith("/") ? name : name + "/";
                        zout.putNextEntry(new ZipEntry(name));
                    } else {
                        zout.putNextEntry(new ZipEntry(name));


                        InputStream in = new FileInputStream(child);
                        try {
                            byte[] buffer = new byte[1024];
                            while (true) {
                                int readCount = in.read(buffer);
                                if (readCount < 0) {
                                    break;
                                }
                                zout.write(buffer, 0, readCount);
                            }
                        } finally {
                            in.close();
                        }
                        zout.closeEntry();
                    }
                }
            }
        } finally {
            res.close();
        }
    }

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

    public static File GzipToFile(String gzipFile, String newFile) {
        FileInputStream fis = null;
        GZIPInputStream gis = null;
        FileOutputStream fos = null;
        File file = new File(newFile);
        try {
            fis = new FileInputStream(gzipFile);
            gis = new GZIPInputStream(fis);
            fos = new FileOutputStream(file);

            FileRW.StreamToFile(gis, new File(newFile));


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                fis.close();
                fos.close();
                gis.close();
            }catch (Exception e){}

        }
        return file;

    }

    private static void compressGzipFile(String file, String gzipFile) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public static void main(String[] args) {
        /*
        try {
            directoryToZip("C:\\testfolder\\data\\A", "C:\\testfolder\\data\\data.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        if (args.length != 2){
            System.out.println("Uncorrect number of parameters!");
            return;
        }

        File file = new File(args[0]);
        if(!file.exists()) {
            System.out.println("Zip Archive" + args[0] + " doesn't exists!");
        }
        */

        //unzip("C:\\testfolder\\file2.zip", "C:\\testfolder\\data\\MY_LOCATION");
        /*
        unzip("C:\\testfolder\\data\\inputs/--11--/S.zip", "C:\\testfolder\\data\\inputs/--11--/MY_LOCATION_FOR_ZIP_CONTENT");
        try {
            directoryToZip("C:\\testfolder\\data\\inputs/--11--/MY_LOCATION_FOR_ZIP_CONTENT", "C:\\testfolder\\data\\inputs/--11--/MY_ZIP.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        //FileRW.getFileName("nkbnn;m,/knojhno/");

        unzip("C:\\testfolder\\inputs.zip", "C:\\testfolder\\data");
        //GzipToFile("C:\\testfolder\\bb.bb.gz", "C:\\testfolder\\bhn.bb.txt");
        //recursiveDelete("C:\\testfolder\\bb.bb.gz");
        //compressGzipFile("C:\\testfolder\\bhn.bb.txt", "C:\\testfolder\\bb.bb.gz");

        //work("C:\\testfolder\\file2.zip", "C:\\testfolder\\data");




    }

    public static void recursiveDelete(String sfile) {
        System.out.println("");
        System.out.println("recursiveDelete:");
        System.out.println(sfile);
        System.out.println("");

        File file = new File(sfile);
        // до конца рекурсивного цикла
        if (!file.exists())
            return;

        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                // рекурсивный вызов
                recursiveDelete(f.getAbsolutePath());
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
        System.out.println("Удаленный файл или папка: " + file.getAbsolutePath());
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }
}