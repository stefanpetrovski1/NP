package CodeFuProblem;


import java.util.Arrays;
import java.util.stream.Collectors;

class Arranger{
    private String sentence;

    public Arranger(String sentence) {
        this.sentence = sentence;
    }

    public void arrange(){
////       return Arrays.stream(sentence.split("\\s+")).sorted().collect(Collectors.joining(" "));
//        sentence = Arrays.stream(sentence.split("\\s+"))
//                .flatMap(word -> Arrays.stream(word.split("")))
//
    }
//    private String arrangeWord(String word){
//        word.
//    }

    public void print(){
        System.out.println(sentence);
    }
}



public class ArrangerTest {
    public static void main(String[] args) {
        String sentence = "kO pSk sO";
        // should return: Ok Os Skp

        Arranger arranger = new Arranger(sentence);

        arranger.arrange();

    }
}
