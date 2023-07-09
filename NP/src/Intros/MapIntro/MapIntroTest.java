package Intros.MapIntro;

import java.util.*;


public class MapIntroTest {
    public static void main(String[] args) {

        Map<String,Integer> studentsByPoints = new LinkedHashMap<>();


        studentsByPoints.put("Stefan",84);
        studentsByPoints.put("Darko",75);
        studentsByPoints.put("Teemo",33);
        studentsByPoints.put("Ashe",45);
        studentsByPoints.put("Darius",21);
        studentsByPoints.put("Caitlyn",98);
        studentsByPoints.put("XinZhao",17);


        Set<Integer> numbers = new TreeSet<>();

        numbers.add(16);
        numbers.add(5);
        numbers.add(1);
        numbers.add(20);
        numbers.add(14);
        numbers.add(10);
        numbers.add(9);



        System.out.println(numbers);

    }
}
