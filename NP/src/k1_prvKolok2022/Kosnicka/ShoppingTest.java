package k1_prvKolok2022.Kosnicka;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class InvalidOperationException extends Exception {
    public InvalidOperationException(String message) {
        super(message);
    }
}

// purchase bill

// (product 1  x 3 quantity) -> 1 item on the bill
//  product 2  x 4 quantity


class Product {
    String id;
    String name;
    double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

abstract class Item {
    Product product;

    public Item(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }


    public static Item createItem(String data) throws InvalidOperationException {
        String[] parts = data.split(";");
        String type = parts[0];  // WS or PS
        String id = parts[1];
        String name = parts[2];
        int price = Integer.parseInt(parts[3]);
        Product p = new Product(id, name, price);
        if (type.equals("WS")) {
            if (Integer.parseInt(parts[4]) == 0)
                throw new InvalidOperationException(String.format("The quantity of the product with id %s can not be 0.", id));
            return new WholeItem(p, Integer.parseInt(parts[4]));
        } else if (type.equals("PS")) {
            if (Double.parseDouble(parts[4]) == 0) {
                throw new InvalidOperationException(String.format("The quantity of the product with id %s can not be 0.", id));
            }
            return new KgItem(p, Double.parseDouble(parts[4]));
        }
        return null;
    }

    public abstract double calculatePrice();

    @Override
    public String toString() {
        return String.format("%s - %.2f", product.id, calculatePrice());
    }
}


class WholeItem extends Item {
    int quantity;

    public WholeItem(Product product, int quantity) {
        super(product);
        this.quantity = quantity;
    }

    @Override
    public double calculatePrice() {
        return quantity * product.price;
    }

}

class KgItem extends Item {
    double quantityInGrams;  // in grams

    public KgItem(Product product, double quantityInGrams) throws InvalidOperationException {
        super(product);
        this.quantityInGrams = quantityInGrams;
    }

    @Override
    public double calculatePrice() {
        double kg = quantityInGrams / 1000;
        return kg * product.price;
    }
}


class ShoppingCart {
    List<Item> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(String itemData) throws InvalidOperationException {
        items.add(Item.createItem(itemData));

    }

    public void printShoppingCart(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        items.stream().sorted(Comparator.comparing(Item::calculatePrice).reversed())
                .forEach(pw::println);

        pw.flush();
        pw.close();
    }

    public void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException {
        if (discountItems.isEmpty())
            throw new InvalidOperationException("There are no products with discount.");

        PrintWriter pw = new PrintWriter(os);
        List<Item> discountedItems = items.stream()
                .filter(i -> discountItems.contains(Integer.parseInt(i.product.id)))
                .collect(Collectors.toList());

        discountedItems.forEach(i -> {
            double oldItemPrice = i.calculatePrice();
            i.product.setPrice(i.product.price * 0.9);
            double newItemPrice = i.calculatePrice();
            System.out.printf("%s - %.2f\n", i.product.id, oldItemPrice - newItemPrice);
        });

        pw.flush();
        pw.close();
    }
}


public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}