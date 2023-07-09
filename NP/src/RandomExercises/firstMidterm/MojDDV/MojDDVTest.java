package RandomExercises.firstMidterm.MojDDV;


//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//enum TaxType {
//    A, B, V
//}
//
//class Item {
//    private int price;
//    private TaxType taxType;
//
//
//    public Item(int price, String taxType) {
//        this.price = price;
//        switch (taxType) {
//            case "A" -> this.taxType = TaxType.A;
//            case "B" -> this.taxType = TaxType.B;
//            case "V" -> this.taxType = TaxType.V;
//        }
//    }
//
//
//    public int getPrice() {
//        return price;
//    }
//
//    public TaxType getTaxType() {
//        return taxType;
//    }
//
//    public double getDDV() {
//        if (taxType == TaxType.A)
//            return 0.18 * price;
//        if (taxType == TaxType.B)
//            return 0.05 * price;
//
//        return 0;
//    }
//
//    public double getBackDDV() {
//        return getDDV() * 0.15;
//    }
//
//}
//
//class Bill {
//    private String id;
//    private List<Item> items;
//
//
//    public Bill(String id, List<Item> items) {
//        this.id = id;
//        this.items = items;
//    }
//
//    public Bill(String line) {
//        String[] parts = line.split("\\s+");
//        id = parts[0];
//        for (int i = 1; i < parts.length - 1; i = i + 2) {
//            Item item = new Item(Integer.parseInt(parts[i]), parts[i + 1]);
//            items.add(item);
//        }
//
//    }
//
//    public double getTotalValue() {
//        return items.stream().mapToDouble(Item::getPrice).sum();
//    }
//}
//
//
//class MojDDV {
//
//    List<Bill> bills;
//
//    public MojDDV() {
//        bills = new ArrayList<>();
//    }
//
//    public void readRecords(InputStream inputStream) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        br.lines().map(Bill::new).forEach(bill -> {
//            if (bill.getTotalValue() > 30000) try {
//                throw new AmountNotAllowedException();
//            } catch (AmountNotAllowedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    public void printTaxReturns(OutputStream outputStream) {
//
//    }
//}
//
//class AmountNotAllowedException extends Exception {
//
//}
//
//
//public class MojDDVTest {
//
//    public static void main(String[] args) {
//
//        MojDDV mojDDV = new MojDDV();
//
//        System.out.println("===READING RECORDS FROM INPUT STREAM===");
//        mojDDV.readRecords(System.in);
//
//        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
//        mojDDV.printTaxReturns(System.out);
//
//    }
//}