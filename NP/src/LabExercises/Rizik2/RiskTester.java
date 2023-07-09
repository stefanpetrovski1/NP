package LabExercises.Rizik2;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;


class Player {
    int number1;
    int number2;
    int number3;


    public Player(String numbers) {
        int[] parts = Arrays.stream(numbers.split("\\s+"))
                .sorted().mapToInt(Integer::parseInt).toArray();
        this.number1 = parts[0];
        this.number2 = parts[1];
        this.number3 = parts[2];
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getNumber3() {
        return number3;
    }
}


class Attack {
    Player player1;
    Player player2;

    public Attack(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public static Attack createAttack(String line) {
        String[] attacks = line.split(";");  //5 3 4  -  2 4 1
        return new Attack(new Player(attacks[0]), new Player(attacks[1]));
    }

    public String getScore() {
        int wins=0;
        if(player1.getNumber3()>player2.getNumber3()){
            wins++;
        }
        if(player1.getNumber2()>player2.getNumber2()){
            wins++;
        }
        if(player1.getNumber1()>player2.getNumber1()){
            wins++;
        }

        return String.format("%d %d",wins,3-wins);
    }
}


class Risk {
    public void processAttacksData(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().map(Attack::createAttack).forEach(a -> System.out.println(a.getScore()));
    }
}


public class RiskTester {
    public static void main(String[] args) {
        Risk risk = new Risk();
        risk.processAttacksData(System.in);
    }
}