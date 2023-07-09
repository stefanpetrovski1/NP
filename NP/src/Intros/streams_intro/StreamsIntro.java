package Intros.streams_intro;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StreamsIntro {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("2a","1a","3a","1a","3a","4a","1a");
        Comparator<String> stringComparator = Comparator.comparing(s -> Integer.valueOf(s.charAt(1)));
        stringList.stream()
                .filter(s -> s.length()==2)
                .sorted()
                .forEach(System.out::println);


    }
}
