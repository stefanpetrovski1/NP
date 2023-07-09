package Intros.streams_intro;

import java.util.Arrays;

public class FlatMapTest {
    public static void main(String[] args) {
        String sentence = "Ovoj Stefan e mnogu jak vo Fortnite :D";

        System.out.println(Arrays.stream(sentence.split("\\s+"))
                .flatMap(w -> Arrays.stream(w.split("")))
                .toList());
    }
}
