package RandomExercises.Popusti;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Product {
    private int priceOnDiscount;
    private int price;

    public Product(int priceOnDiscount, int price) {
        this.priceOnDiscount = priceOnDiscount;
        this.price = price;
    }

    public double getDiscount(){
        return (1-priceOnDiscount/(double)price)*100.0;
    }
    public int getAbsoluteDiscount(){
        return price-priceOnDiscount;
    }

    @Override
    public String toString() {
        //50% 5380/10760
        return String.format("\n%.0f%% %d/%d",Math.floor(getDiscount()),priceOnDiscount,price);
    }

    public int getPriceOnDiscount() {
        return priceOnDiscount;
    }

    public int getPrice() {
        return price;
    }
}

class Store {
    private String name;
    private List<Product> products;


    public Store(String line) {
        String[] parts = line.split("\\s+");
        products = new ArrayList<>();
        this.name = parts[0];
        Arrays.stream(parts).skip(1)
                .forEach(product -> {
                    String[] prices = product.split(":");
                    products.add(new Product(Integer.parseInt(prices[0]), Integer.parseInt(prices[1])));
                });
    }

    public String toString(){
        StringBuilder sb =new StringBuilder();

        sb.append(name);
        sb.append(String.format("\nAverage discount: %.1f%%",getAvgDiscount()));
        sb.append(String.format("\nTotal discount: %d",getTotalAbsoluteDiscount()));
        products.stream().sorted(Comparator.comparing(Product::getDiscount).thenComparing(Product::getPrice).reversed()).forEach(sb::append);
        return sb.toString();
    }

    public double getAvgDiscount() {
        return products.stream().mapToDouble(Product::getDiscount).average().orElse(0);
    }
    public int getTotalAbsoluteDiscount(){
        return products.stream().mapToInt(Product::getAbsoluteDiscount).sum();
    }

    public String getName() {
        return name;
    }
}

class Discounts {
    private List<Store> stores;


    public Discounts() {
        stores = new ArrayList<>();
    }

    public int readStores(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        bufferedReader.lines().map(Store::new).forEach(store -> stores.add(store));
        return stores.size();
    }

    public List<Store> byAverageDiscount() {
        return stores.stream().sorted(Comparator.comparing(Store::getAvgDiscount).reversed().thenComparing(Store::getName))
                .limit(3).collect(Collectors.toList());
    }

    public List<Store> byTotalDiscount() {
        return stores.stream().sorted(Comparator.comparing(Store::getTotalAbsoluteDiscount).thenComparing(Store::getName))
                .limit(3).collect(Collectors.toList());
    }
}


public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

// Vashiot kod ovde