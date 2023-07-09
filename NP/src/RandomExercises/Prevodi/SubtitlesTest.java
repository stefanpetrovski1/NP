package RandomExercises.Prevodi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//        2
//        00:00:48,321 --> 00:00:50,837
//        Let's see a real bet.

class SubtitleItem {
    private int number;
    private String startTime;
    private String endTime;
    private String text;

    public SubtitleItem(int number, String startTime, String endTime, String text) {
        this.number = number;
        this.startTime = startTime;
        this.endTime = endTime;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("%d\n%s --> %s\n%s", number, startTime, endTime, text);
    }

}

class Subtitles {
    private List<SubtitleItem> subtitleItems;

    public Subtitles() {
        subtitleItems = new ArrayList<>();
    }

    public int loadSubtitles(InputStream inputStream) {
        BufferedReader br =new BufferedReader(new InputStreamReader(inputStream));

        br.lines()
                .forEach(line -> {

                });

        return subtitleItems.size();

    }
    public int loadSubtitlesScanner(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {
            int number= Integer.parseInt(scanner.nextLine());
            String[] times = scanner.nextLine().split("-->");

            String startTime = times[0].trim();
            String endTime= times[1].trim();
            String text = scanner.nextLine();
            subtitleItems.add(new SubtitleItem(number,null,null,text));
        }
        scanner.close();

        return subtitleItems.size();

    }

    public void print(){
        subtitleItems.stream().forEach(System.out::println);
    }

    public void shift(int ms){

    }
}


public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

// Вашиот код овде
