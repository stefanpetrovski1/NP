package RandomExercises.firstMidterm.Risk1and2;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


class Attempt{
    List<Integer> attacks;
    List<Integer> defenses;


    Attempt(String line){
        attacks = new ArrayList<>(3);
        defenses = new ArrayList<>(3);

        String[] parts = line.split(";");
        String[] x = parts[0].split("\\s+");
        String[] y = parts[1].split("\\s+");

        Arrays.stream(x)
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::parseInt)
                .forEach(i -> attacks.add(i));
        Arrays.stream(y)
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::parseInt)
                .forEach(i -> defenses.add(i));
    }

    public boolean didWin(){
        return attacks.get(0)>defenses.get(0)
                && attacks.get(1)>defenses.get(1)
                && attacks.get(2)>defenses.get(2);
    }
    public int countTroops(){
        int counter =0;
        for(int i=0;i<3;i++){
            if(attacks.get(i)>defenses.get(i)) counter++;
        }
        return counter;
    }

}

class Risk{
        // 86 zadaca
//    public int processAttacksData(InputStream is){
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
//        return (int) bufferedReader.lines()
//                .map(Attempt::new)
//                .filter(Attempt::didWin)
//                .count();
//    }

    // 87 zadaca
    public void processAttacksData (InputStream is){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        bufferedReader.lines()
                .map(Attempt::new)
                .forEach(at -> System.out.println(at.countTroops()+" "+(3-at.countTroops())));
    }
}


public class RiskTester {
    public static void main(String[] args) {

        Risk risk = new Risk();

        //System.out.println(risk.processAttacksData(System.in));
        risk.processAttacksData(System.in);

    }
}