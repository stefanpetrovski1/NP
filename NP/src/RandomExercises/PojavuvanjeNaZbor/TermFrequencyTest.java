package RandomExercises.PojavuvanjeNaZbor;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;


class TermFrequency {
    Map<String, Long> wordsByFreq;

    public TermFrequency(InputStream is, String[] stopWords) {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // HashMap by default
        wordsByFreq = br.lines()
                .map(this::clearLine)
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .filter(word -> !(word.equals("") || word.equals("-")))
                .map(this::clearWord)
                .filter(word -> !Arrays.asList(stopWords).contains(word))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
    }

    private String clearWord(String word) {
        if (word.charAt(0) == '-') return word.substring(1);
        return word;
    }

    private String clearLine(String line) {

        String l = line.toLowerCase()
                .replace("(", "")
                .replace(")", "")
                .replace(".", " ")
                .replace(",", "")
                .replace("!", "")
                .replace("„", "")
                .replace("”", "");

        return l;
    }


    public int countTotal() {
        return (int) wordsByFreq.values().stream().mapToLong(v -> v).sum();
    }

    public int countDistinct() {
        return wordsByFreq.size();
    }

    public List<String> mostOften(int k) {
        return wordsByFreq.entrySet().stream().sorted(Comparator.comparing(Map.Entry<String, Long>::getValue)
                        .reversed()
                        .thenComparing(Entry::getKey))
                .limit(k)
                .map(Entry::getKey)
                .collect(Collectors.toList());
    }


}


public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[]{"во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја"};
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}

