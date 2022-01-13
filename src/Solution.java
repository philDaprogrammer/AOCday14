import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;


public class Solution {
    private String template;
    private Map<String, String> insertions;

    public Solution(String filename) { parse(filename); }

    private void parse(String filename) {
        this.insertions = new HashMap<>();

        try {
            File f            = new File(filename);
            FileReader fr     = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains(" -> ")) {
                    String[] insertion = line.split(" -> ");
                    this.insertions.put(insertion[0], insertion[1]);
                } else if (!line.isBlank()) {
                    this.template = line;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEntry(Map<String, Long> newPairs, String pair, Long occurrences) {
        if (newPairs.containsKey(pair)) {
            newPairs.put(pair, newPairs.get(pair) + occurrences);
        } else {
            newPairs.put(pair, occurrences);
        }
    }

    private void dumpSolution(Map<String, Long> totalPairs) {
        Map<String, Long> totalLetters = new HashMap<>();
        Long min = null;
        Long max = null;

        for (Character c : this.template.toCharArray()) { addEntry(totalLetters, c.toString(), (long)1); }
        for (Map.Entry<String, Long> entry : totalPairs.entrySet()) { addEntry(totalLetters, this.insertions.get(entry.getKey()), entry.getValue()); }

        for (Long n : totalLetters.values()) {
            if ((min == null) || (n < min)) { min = n;}
            if ((max == null) || (n > max)) { max = n;}
        }

        System.out.println(max + " - " + min + " = " + (max - min));
    }

    public void solve(int steps) {
        if (steps == 0) { return; }

        Map<String, Long> totalPairs   = new HashMap<>();
        Map<String, Long> currentPairs = new HashMap<>();
        Map<String, Long> newPairs;
        String pair;

        for (int i=0; i < this.template.length() - 1; ++i){
            pair = this.template.charAt(i) + "" + this.template.charAt(i+1);
            totalPairs.put(pair, (long)1);
            currentPairs.put(pair, (long)1);
        }

        for (int i=1; i < steps; ++i) {
            newPairs = new HashMap<>();

            for (Map.Entry<String, Long> entry: currentPairs.entrySet()) {
                String mid   = this.insertions.get(entry.getKey());
                String left  = entry.getKey().charAt(0) + mid;
                String right = mid + entry.getKey().charAt(1);

                addEntry(newPairs, left, entry.getValue());
                addEntry(newPairs, right, entry.getValue());
            }

            currentPairs = newPairs;

            for (Map.Entry<String, Long> entry : currentPairs.entrySet()) {
                addEntry(totalPairs, entry.getKey(), entry.getValue());
            }
        }

        dumpSolution(totalPairs);
    }
}