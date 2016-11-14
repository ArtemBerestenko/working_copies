import java.io.File;
import java.util.*;

/**
 * Created by aberestenko on 10/17/2016.
 */
public class Parser {
    private static Set<String> Numbers = new TreeSet<String>();
    private static Set<String> Emails = new TreeSet<String>();


    public static Set getNumbers() {
        return Numbers;
    }
    public static Set getEmails(){
        return Emails;
    }

    public static String changeCodeNumbers(String line){
        String changedString = null;
        HashMap<String, String> replaceMap = new HashMap();
        replaceMap.put("101", "401");
        replaceMap.put("202", "802");
        replaceMap.put("301", "321");

        Set<String> keys = replaceMap.keySet();
        for (String key : keys){
            changedString = line.replace(key, replaceMap.get(key));
            line = changedString;
        }
        return changedString;

    }
    //доделать
    public static void readPhones(String line){
        String buffer = "";
        String phone = null;
        if (line.contains("@")){
            buffer = line.substring(line.indexOf('+'), line.indexOf("@"));
            phone = buffer.replaceAll("[^\\d|(|)|+]", "");
            Numbers.add(phone);
        }

    }

    public static void readEmails(String line){
        String buffer = "";
        String email = null;
        line = line.replaceAll("\\t|;|,", " ");
        String[] stringsLimitedBySpaces = line.split(" ");
        for (String str : stringsLimitedBySpaces){
            if (str.contains("@")){
                //email = str.replaceAll(";|,", "");
                    Emails.add(str.trim());
                }
            }

        }
        /*

        if (line.contains("@")){
            /*
            buffer = line.replaceAll("[^\\t|;|,]", " ");
            email = buffer.split(" ");

            email = line.replaceAll("[^@ \\w]|\\d|_", " ").trim().split(" ");
            for (String str : email){
                Emails.add(str);
            }
        }
        */





    public static String removeZipFromFolderNames(String str){
        return str.substring(0, str.lastIndexOf("\\")).replaceAll(".zip", "") + str.substring(str.lastIndexOf("\\"), str.length());

    }


}
