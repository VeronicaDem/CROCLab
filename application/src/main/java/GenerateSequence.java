import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateSequence {
    /*
    private static List<String> generatesWithK(int kolvo1, int k, List<String> sequences) {
        final int maxk = kolvo1 - 2;
        if(k > maxk) {
            sequences.add("1".repeat(kolvo1));
            return sequences;
        }
        // k - количество _
        for(int start = 1; start <= kolvo1 + k - 1; start++) {
            for(int d = 0; start + d < kolvo1 + k; d++) {
                generatesFromStart(start, k, kolvo1, sequences, d);
            }
        }
        return generatesWithK(kolvo1,k + 1, sequences);
    }
    */

    /*

    public static List<String> generates(int kolvo1) {
       List<String> res = new ArrayList<>();
       res = generatesWithK(kolvo1, 1, res);
       return res;
    }
    private static void generatesFromStart(int start, int k, int kolvo1, List<String> sequences, int d) {
       for(int i = start; i < k - d; i += d) {
           String seq = "1";
           for(int j = 1; j < kolvo1 + 1; j++) {
               if(i == j) seq += "_";
               else seq += "1";
           }
           System.out.println(seq);
           sequences.add(seq);
       }
    }
*/
    /*
    public void generate(String[] p, int L, int R) {
        if (L == R) { // all numbers are set
            // do something with permutation in array p[]
        } else { // numbers at positions [0, L-1] are set, try to set L-th position
            for (int i = L; i <= R; i++) {
                String tmp = p[L];
                p[L] = p[i]; p[i] = tmp;
                generate(p, L+1, R);
                tmp = p[L]; p[L] = p[i]; p[i] = tmp;
            }
        }
    }

    */

    /*
    public static ArrayList<String> permutation(String s) {
        // The result
        ArrayList<String> res = new ArrayList<String>();
        // If input string's length is 1, return {s}
        if (s.length() == 1) {
            res.add(s);
        } else if (s.length() > 1) {
            int lastIndex = s.length() - 1;
            // Find out the last character
            String last = s.substring(lastIndex);
            // Rest of the string
            String rest = s.substring(0, lastIndex);
            // Perform permutation on the rest string and
            // merge with the last character
            res = merge(permutation(rest), last);
        }
        return res;
    }
*/

    /*
    public static ArrayList<String> merge(ArrayList<String> list, String c) {
        ArrayList<String> res = new ArrayList<>();
        // Loop through all the string in the list
        for (String s : list) {
            // For each string, insert the last character to all possible positions
            // and add them to the new list
            for (int i = 0; i <= s.length(); ++i) {
                String ps = new StringBuffer(s).insert(i, c).toString();
                res.add(ps);
            }
        }
        return res;
    }
    */
    /*
    public static void permutation(String str) {
        permutation("", str);
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)

                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }
*/
     public static void generates(int kolvo1, int k, List<String> res, String currentString) {
         if(k == kolvo1) {
             res.add(currentString);
         }
         else if(currentString.endsWith("1")) {
             generates(kolvo1, k, res, currentString + "_");
             generates(kolvo1, k + 1, res, currentString + "1");
         }
         else if(currentString.isEmpty()){
             generates(kolvo1, k + 1, res, currentString + "1");
         }
         else {
             generates(kolvo1, k + 1, res, currentString + "1");
         }
     }

}
