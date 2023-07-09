package Intros.Aud9_Maps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class NamesTest {

    public static Map<String, Integer> createFromFileWithScanner(String path) throws FileNotFoundException {
        Map<String, Integer> result = new HashMap<>();
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            String name = parts[0];
            Integer freq = Integer.parseInt(parts[1]);
            result.put(name, freq);
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Integer> boyNamesMap = createFromFileWithScanner("C:\\Users\\DELL\\IdeaProjects\\NP_2023_NewStart\\src\\Intros.Aud9_Maps\\Data\\boynames.txt");
        Map<String, Integer> girlNamesMap = createFromFileWithScanner("C:\\Users\\DELL\\IdeaProjects\\NP_2023_NewStart\\src\\Intros.Aud9_Maps\\Data\\girlnames.txt");


        Set<String> allNames = new HashSet<>();
        allNames.addAll(boyNamesMap.keySet());
        allNames.addAll(girlNamesMap.keySet());

//        allNames.stream().filter(name -> boyNamesMap.containsKey(name) && girlNamesMap.containsKey(name))
//                .forEach(name -> System.out.printf("Name: %s Male: %d Female: %d%n", name, boyNamesMap.get(name), girlNamesMap.get(name)));

        Map<String, Integer> unisexNames = new HashMap<>();
        allNames.stream()
                .filter(name -> boyNamesMap.containsKey(name) && girlNamesMap.containsKey(name))
                .forEach(name -> unisexNames.put(name,boyNamesMap.get(name)+girlNamesMap.get(name)));

        //System.out.println(unisexNames);

        // Sortiranje na mapata spored vrednost
        // System.out.println(unisexNames.entrySet());
        unisexNames.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.printf("%s : %d%n",entry.getKey(),entry.getValue()));


    }
}
