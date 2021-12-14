import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class parser {
    private final String fileName;

    public parser(String fileName) {this.fileName = fileName;}

    public polymer getPolymer() {
        ArrayList<Character> template    = new ArrayList<>();
        Map<String, Character> insertion = new HashMap<>();

        try {
            FileInputStream stream = new FileInputStream(this.fileName);
            Scanner sc             = new Scanner(stream);;

            if (sc.hasNext()) {
                for  (char c : sc.next().toCharArray()) template.add(c);
            }

            while (sc.hasNext()) {
                String key   = sc.next();
                String value = "";

                if ((sc.hasNext()) && (sc.next().equals("->"))) {
                    if (sc.hasNext()) {value = sc.next();}
                }

                insertion.put(key, value.charAt(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new polymer(template, insertion);
    }
}