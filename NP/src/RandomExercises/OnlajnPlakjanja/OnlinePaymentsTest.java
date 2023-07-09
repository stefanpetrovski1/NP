package RandomExercises.OnlajnPlakjanja;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Item{
    String name;
    int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s %d",name,price);
    }
}

class Student{
    String name;
    List<Item> items;

    public Student(String name) {
        this.name = name;
        items = new ArrayList<>();
    }

    private int getSumOfItems(){
        return items.stream().mapToInt(Item::getPrice).sum();
    }
    private int getFee(){
        int fee = (int) Math.round(getSumOfItems()*0.0114);
        if (fee<3){
            return 3;
        }else if (fee > 300){
            return 300;
        }
        return fee;
    }


    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder();
        //Student: 151020 Net: 13050 Fee: 149 Total: 13199
        sb.append(String.format("Student: %s Net: %d Fee: %d Total: %d\n",name,getSumOfItems(),getFee(),getFee()+getSumOfItems()));
        sb.append("Items:\n");
        items = items.stream().sorted(Comparator.comparing(Item::getPrice).reversed()).collect(Collectors.toList());
        sb.append(IntStream.range(0, items.size())
                .mapToObj(i -> (i + 1) + ". " + items.get(i))
                .collect(Collectors.joining("\n")));

        return sb.toString();
    }
}

class OnlinePayments{
    Map<String, Student> itemsByStudent;
    public OnlinePayments() {
        itemsByStudent=new HashMap<>();
    }




    public void readItems(InputStream in) {
        Scanner sc =  new Scanner(in);
        while(sc.hasNext()){
            String [] parts = sc.nextLine().split(";");
            String studentName = parts[0];
            Item item = new Item(parts[1],Integer.parseInt(parts[2]));

            // the key is not present
            itemsByStudent.putIfAbsent(studentName,new Student(studentName));
            // executes anyway
            itemsByStudent.get(studentName).items.add(item);

        }
    }
    void printStudentReport (String index, OutputStream os){
        PrintWriter pw = new PrintWriter(os);

        if (!itemsByStudent.containsKey(index)){
            pw.printf("Student %s not found!\n",index);
        }else {
            pw.println(itemsByStudent.get(index));
        }


        pw.flush();
    }
}


public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}