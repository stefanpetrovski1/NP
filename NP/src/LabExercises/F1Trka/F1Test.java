package LabExercises.F1Trka;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Driver {
    private String name;
    private String[] times;


    // line: Webber 2:32:103 2:49:182 2:18:132

    public Driver(String line) {
        times = new String[3];
        String[] parts = line.split("\\s+");

        this.name = parts[0];
        times[0] = parts[1];  // first lap
        times[1] = parts[2];  // second lap
        times[2] = parts[3];  // third lap
    }

    public String getBestTime() {
        return Arrays.stream(times).sorted().findFirst().get();
    }


    public String toString() {
        return String.format("%-10s%10s", name, getBestTime());
    }

}

class F1Race {
    private List<Driver> drivers;

    public F1Race() {
        drivers = new ArrayList<>();
    }

    public void readResults(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        drivers = bufferedReader.lines().map(Driver::new).collect(Collectors.toList());
    }

    public void printSorted(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);

        drivers = drivers.stream().sorted(Comparator.comparing(Driver::getBestTime)).collect(Collectors.toList());
        for (int i = 0; i < drivers.size(); i++) {
            pw.println((i + 1) + ". " + drivers.get(i));
        }

        pw.close();
    }
}


public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

