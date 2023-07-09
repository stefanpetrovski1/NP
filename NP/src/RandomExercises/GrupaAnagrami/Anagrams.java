package RandomExercises.GrupaAnagrami;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static boolean isAnagram(String s1, String s2) {

        if (s1.length() != s2.length())
            return false;

        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();

        Arrays.sort(c1);
        Arrays.sort(c2);

        for (int i = 0; i < c1.length; i++) {
            if (c1[i] != c2[i]) return false;
        }

        return true;

    }

    public static void findAll(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        Set<String> allWords = new HashSet<>();
        Map<String, List<String>> anagramsByFirstWord = new HashMap<>();
//        while (sc.hasNext()) {
//            String line = sc.nextLine();
//            String word = line.trim();
//
//            // add to set
//            allWords.add(word);
//
//
//        }

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> allWords.add(line.trim()));


        allWords.stream().forEach(word ->{
//            if(anagramsByFirstWord.containsKey(word)){
//
//            }else {
//                anagramsByFirstWord.put(word)
//            }
        });
    }
}


