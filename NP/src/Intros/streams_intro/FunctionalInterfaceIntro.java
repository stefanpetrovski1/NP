package Intros.streams_intro;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfaceIntro {
    public static void main(String[] args) {
        // predicate

        Predicate<String> stringPredicate = (s) -> s.length()!=0;

        System.out.println(stringPredicate.test(""));
        System.out.println(stringPredicate.test("NP 2023"));

        Function<String,Integer> function = String::length;

        System.out.println(function.apply("NP 2023"));
        System.out.println(function.apply(""));

        Supplier<List<Integer>> listSupplier = ArrayList::new;

        System.out.println(listSupplier.get());

        List<Integer> integerList=listSupplier.get();
        integerList.add(1);
        integerList.add(2);
        integerList.add(5);

        Consumer <List<Integer>> printingConsumer = System.out::println;

        printingConsumer.accept(integerList);

    }
}
