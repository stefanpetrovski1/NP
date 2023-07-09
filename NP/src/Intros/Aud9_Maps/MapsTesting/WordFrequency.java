package Intros.Aud9_Maps.MapsTesting;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class WordFrequency {
    public static void main(String[] args) {
        String sentence = readData(System.in);

        Map<String,Long> wordsByFreq = new HashMap<>();

        wordsByFreq = Arrays.stream(sentence.split("\\s+"))
                .collect(Collectors.groupingBy(
                                word -> clearWord(word.toLowerCase()),
                                Collectors.counting()));

//        wordsByFreq.entrySet().stream()
//                .forEach(entry -> System.out.printf("%s -> %d\n",entry.getKey(),entry.getValue()));
        sortMapByValue(wordsByFreq).entrySet().stream().forEach(entry -> System.out.printf("%s -> %d\n", entry.getKey(),entry.getValue()));
    }
    //he is Very GoOD player at league, but he do not plays league for money, just for fun. his brother is a league player too, and he is very good too, but he plays league not for fun, but for money
    private static String clearWord(String word){
        if(!Character.isAlphabetic(word.charAt(word.length()-1))){
            return word.substring(0,word.length()-1);
        }
        return word;
    }
    private static String readData(InputStream in) {
        Scanner sc = new Scanner(in);
        return sc.nextLine();
    }

    private static Map<String,Long> sortMapByValue(Map <String,Long> map){
        Map <String,Long> sortedMap = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(entry -> sortedMap.put(entry.getKey(),entry.getValue()));
        return sortedMap;
    }
}
