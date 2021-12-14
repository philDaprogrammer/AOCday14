public class Main {
    public static void main(String[] args) {
        parser p      = new parser("input.txt");
        polymer input =  p.getPolymer();
        Solution solution = new Solution(input, 10);
        solution.solve();
    }
}