package RandomExercises.MojDDV1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

    }
}

enum TaxType {
    // 18, 5, 0
    A, B, V
}

// 1789 А

class Item {
    private int price;
    private TaxType type;


    public Item(int price, TaxType type) {
        this.price = price;
        this.type = type;
    }

    public Item(int price) {
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setType(TaxType type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }
    public double calculateTax(){
        if (type == TaxType.A)
            return price*0.18;
        if (type == TaxType.B)
            return price*0.05;
        return 0;
    }
}

// 12334 1789 А 1238 B 1222 V 111 V

class Bill {
    private String id;
    private List<Item> items;


    public Bill(String id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public static Bill createBill(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        List<Item> itemList = new ArrayList<>();
        String id = parts[0];
        Arrays.stream(parts).skip(1)
                .forEach(data -> {
                    // item's price
                    if (Character.isDigit(data.charAt(0))) {
                        itemList.add(new Item(Integer.parseInt(data)));
                    }
                    // item's tax type
                    else {
                        itemList.get(itemList.size() - 1).setType(TaxType.valueOf(data));
                    }
                });
        int totalSum = calculateSum(itemList);
        if (totalSum > 30000)
            throw new AmountNotAllowedException(String.format("Receipt with amount %d is not allowed to be scanned", totalSum));
        return new Bill(id, itemList);
    }
    public static int calculateSum(List<Item> items){
        return items.stream().mapToInt(Item::getPrice).sum();
    }
    public double calculateReturn(){
        return 0.15* items.stream().mapToDouble(Item::calculateTax).sum();
    }
    @Override
    public String toString() {
        return String.format("%s %d %.2f",id,calculateSum(items),calculateReturn());
    }
}

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(String message) {
        super(message);
    }
}

class MojDDV {
    private List<Bill> bills;

    public void readRecords(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        bills = br.lines().map(line -> {
            try {
                return Bill.createBill(line);
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
                return null;
            }

        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void printTaxReturns(OutputStream out){
        PrintWriter pw = new PrintWriter(out);

        bills.stream().forEach(pw::println);
        pw.close();
    }
}
