import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Solution {
    private final polymer input;
    private final int steps;

    public Solution(polymer input, int steps) {
        this.input = input;
        this.steps = steps;
    }

    public void solve() {
        ArrayList<Character> newPolymer = this.input.template;

        for (int i=0; i < steps; i++) newPolymer = this.solveRec(newPolymer);
        this.findDifference(newPolymer);
    }

    private void findDifference(ArrayList<Character> polymer) {
        Map<Character, Integer> occurrences = new HashMap<>();
        Integer max = null;
        Integer min = null;

        for (Character c : polymer) {
            if (occurrences.get(c) == null) {
                occurrences.put(c, 1);
            } else {
                occurrences.replace(c, occurrences.get(c) + 1);
            }
        }

        for (Integer val : occurrences.values()) {
            if ((max == null) || (val > max)) { max = val; }
            if ((min == null) || (val < min)) { min = val; }
        }

        System.out.println("Difference: " + (max - min));
    }

    private ArrayList<Character> solveRec(ArrayList<Character> chunk) {
        if (chunk.size() == 1) {
            return chunk;
        } else if  (chunk.size() == 2) {
            ArrayList<Character> newChuck = new ArrayList<>();
            String    key                 = chunk.get(0).toString() + chunk.get(1).toString();
            Character value               = this.input.insertion.get(key);

            newChuck.add(chunk.get(0));
            newChuck.add(value);
            newChuck.add(chunk.get(1));
            return newChuck;
        } else {
            ArrayList<Character> l = new ArrayList<>();
            ArrayList<Character> u = new ArrayList<>();

            for (int i=0; i < chunk.size()/2; ++i)            { l.add(chunk.get(i)); }
            for (int i=chunk.size()/2; i < chunk.size(); ++i) { u.add(chunk.get(i)); }

            ArrayList<Character> lower = this.solveRec(l);
            ArrayList<Character> upper = this.solveRec(u);

            String    key   = lower.get(lower.size()-1).toString() + upper.get(0);
            Character value = this.input.insertion.get(key);

            ArrayList<Character> mergeChunk = new ArrayList<>(lower);
            mergeChunk.add(value);
            mergeChunk.addAll(upper);
            return mergeChunk;
        }
    }
}