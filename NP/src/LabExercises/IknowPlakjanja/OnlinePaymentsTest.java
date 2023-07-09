package LabExercises.IknowPlakjanja;

import java.io.*;
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
    String index;
    List<Item> items;

    public Student(String index) {
        this.index = index;
        this.items =new ArrayList<>();
    }

    public int totalPrice(){
        return items.stream().mapToInt(Item::getPrice).sum();
    }
    public int fee(){
        int fee = (int) Math.round(totalPrice()*0.0114);
        if(fee<3){
            fee=3;
        }else if(fee>300){
            fee=300;
        }
        return fee;
    }

    public String getIndex() {
        return index;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Student: %s Net: %d Fee: %d Total: %d\n"
        ,index,totalPrice(),fee(),totalPrice()+fee()));
        sb.append("Items:\n");
        items = items.stream().sorted(Comparator.comparing(Item::getPrice).reversed()).collect(Collectors.toList());
        IntStream.range(0,items.size()).forEach(i -> {
            sb.append((i+1)+". "+items.get(i));
            if(i<items.size()-1){
                sb.append("\n");
            }
        });

        return sb.toString();
    }
}


class OnlinePayments{
    Map<String, Student> students;

    public OnlinePayments() {
        students=new HashMap<>();
    }

    public void readItems (InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(line -> {
            String[] parts = line.split(";");
            students.putIfAbsent(parts[0],new Student(parts[0]));
            Item item = new Item(parts[1],Integer.parseInt(parts[2]));
            students.get(parts[0]).getItems().add(item);
        });
    }

    public void printStudentReport (String index, OutputStream os){
        PrintWriter pw= new PrintWriter(os);

        Student student  = students.get(index);
        if(student==null){
            pw.printf("Student %s not found!\n",index);
        }else {
            pw.println(student);
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