import java.util.ArrayList;
import java.util.Map;

public class polymer {
    public ArrayList<Character>   template;
    public Map<String, Character> insertion;

    public polymer(ArrayList<Character> template, Map<String, Character> insertion) {
        this.template = template;
        this.insertion = insertion;
    }
}
