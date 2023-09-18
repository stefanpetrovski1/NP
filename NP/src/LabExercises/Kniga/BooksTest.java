package LabExercises.Kniga;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


class Book {
    private String name;
    private String category;
    private double price;

    public Book(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f",name,category,price);
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}


class BookCollection{
    private List<Book> bookCollection;

    public BookCollection(){
        bookCollection= new ArrayList<>();
    }

    public void addBook(Book book){
        bookCollection.add(book);
    }
    // TreeSet .........
    public void printByCategory(String category){
        Predicate<Book> bookPredicateCategory = (a) -> (a.getCategory().toLowerCase()) .equals(category.toLowerCase());
        bookCollection.stream()
                .sorted(Comparator.comparing(Book::getName).thenComparing(Book::getPrice))
                .filter(bookPredicateCategory)
                .forEach(System.out::println);
    }
    public List<Book> getCheapestN(int n){
        if(bookCollection.size()<n){
            return bookCollection;
        }

        return bookCollection.stream().sorted(Comparator.comparing(Book::getPrice).thenComparing(Book::getName)).collect(Collectors.toList());
    }
}


public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

