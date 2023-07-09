package LabExercises.Iminja;

import java.util.*;
import java.util.stream.Collectors;


class Name{
    String name;
    int occ;

    public Name(String name, int occ) {
        this.name = name;
        this.occ = occ;
    }

    public int getOcc() {
        return occ;
    }

    public void setOcc(int occ) {
        this.occ = occ;
    }

    public String getName() {
        return name;
    }

    public int getUniqueLettersNumber(){
        Set<Character> uniqueLetters= new HashSet<>();
        char[] letters= name.toLowerCase().toCharArray();
        for(char letter: letters){
            uniqueLetters.add(letter);
        }
        return uniqueLetters.size();
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %d",name,occ,getUniqueLettersNumber());
    }
}

class Names{
    List<Name> names;

    public Names() {
        names=new ArrayList<>();
    }

    public void addName(String name){
        int idx=-1;
        for(int i=0;i< names.size();i++){
            if(names.get(i).name.equals(name)){
                idx=i;
                break;
            }
        }

        if (idx==-1) {
            names.add(new Name(name,1));
        }else {
            names.get(idx).setOcc(names.get(idx).getOcc()+1);
        }

    }

    public void printN(int n){
        names.stream().filter(name -> name.getOcc()>=n).sorted(Comparator.comparing(Name::getName))
                .forEach(System.out::println);
    }

    public String findName(int len, int x){
        List<Name> filteredNames = names.stream()
                .filter(name -> name.getName().length()<len)
                .sorted(Comparator.comparing(Name::getName))
                .collect(Collectors.toList());

        int position = x%filteredNames.size();

        return filteredNames.get(position).name;
    }
}


public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

