package WithoutXPaths;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    String filepath = "src/signatories_model_info.xml";

    public static void main(String[] args) {

        Main run = new Main();

        run.getElements();
        run.count();
        run.duplicate();
        run.remove();
        run.update();

    }

    //get field type API_BASED and list by tag name
    public  void getElements(){
        try {
            String file = Files.readString(Paths.get(filepath));

            Pattern pattern = Pattern.compile("field_type=\"API_BASED\"[^>]*tag_name=\"([^>]+)\"");
            Matcher matcher = pattern.matcher(file);

            while (matcher.find()){
                System.out.println(matcher.group(1));
            }
        } catch (Exception e) {
            System.out.println("error: " +e.getMessage());
        }
    }


    //count element with field type table based
    public void  count (){
        try {
            String file = Files.readString(Paths.get(filepath));

            Pattern pattern = Pattern.compile("field_type=\"TABLE_BASED\"");
            Matcher matcher = pattern.matcher(file);

            int count =0;

            while (matcher.find()){
                count ++;
            }
            System.out.println("The size is: " + count);
        } catch (Exception e) {
            System.out.println("error: " +e.getMessage());
        }
    }


    //check for duplicate
    public void duplicate (){
        try {
            String file = Files.readString(Paths.get(filepath));

            Pattern pattern = Pattern.compile(".+(?s)(check_duplicate value=\"YES\".+ </check_duplicate>)");
            Matcher matcher = pattern.matcher(file);

            while (matcher.find()) {
                System.out.println("Element: \n" + matcher.group(1));

            }

        } catch (Exception e) {
            System.out.println("error: " +e.getMessage());
        }

    }


    //remove the xml document
    public  void remove (){
        try {
            String file = Files.readString(Paths.get(filepath));

            Pattern pattern = Pattern.compile(".+(?s)tag_name=\"(RESTRICTED_ACCESS_NATIONALITIES_MATCH_TYPE|MAX_RESTRICTED_ACCESS_NATIONALITIES|RESTRICTED_ACCESS_NATIONALITIES)\".+</item>$");
            Matcher matcher = pattern.matcher(file);

            while (matcher.find()){
                System.out.println(matcher.group());
                System.out.println("--------------------------------------------------");
                String xml = matcher.replaceAll("");
                System.out.println("AFTER CHANGES \n" + xml);
            }
        } catch (Exception e) {
            System.out.println("error: " +e.getMessage());
        }
    }


    //update
    public void update (){
        try {
            String file = Files.readString(Paths.get(filepath));

            Pattern pattern = Pattern.compile("use=\"MANDATORY\"");
            Matcher matcher = pattern.matcher(file);

            while (matcher.find()){
                System.out.println("before update: " +matcher.group());
                System.out.println();
                String s = matcher.replaceAll("use=\"OPTIONAL\"");
                System.out.println("After update "+ s);
            }
        } catch (Exception e) {
            System.out.println("error: " +e.getMessage());
        }
    }

}