package k1_prvKolok2022.Kviz;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

//is not allowed option for this question
class InvalidOperationException extends Exception {
    public InvalidOperationException(String message) {
        super(message);
    }
}

abstract class Question {
    String text;
    int points;

    public Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public abstract double calculatePoints(String ans);

    public abstract String getAnswer();

    @Override
    public abstract String toString();
}


class TrueFalseQuestion extends Question {
    boolean answer;  // true false

    public TrueFalseQuestion(String text, int points, boolean answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public double calculatePoints(String ans) {
        if (ans.equals(Boolean.toString(answer))) {
            return points;
        }
        return 0;
    }


    //True/False Question: Question3 Points: 2 Answer: false
    @Override
    public String toString() {
        return String.format("True/False Question: %s Points: %d Answer: %s",
                text, points, answer);
    }

    @Override
    public String getAnswer() {
        return Boolean.toString(answer);
    }
}

class MultipleChoiceQuestion extends Question {
    char answer;  // A B C D E

    public MultipleChoiceQuestion(String text, int points, char answer) throws InvalidOperationException {
        super(text, points);
        if (answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D' && answer != 'E') {
            throw new InvalidOperationException(answer + " is not allowed option for this question");
        }
        this.answer = answer;
    }

    @Override
    public double calculatePoints(String ans) {
        if (answer == ans.charAt(0)) {
            return points;
        }
        return -points * 0.2;
    }

    @Override
    public String getAnswer() {
        return Character.toString(answer);
    }

    @Override
    public String toString() {
        return String.format("Multiple Choice Question: %s Points %d Answer: %c",
                text, points, answer);
    }
}


class Quiz {
    List<Question> questions;

    public Quiz() {
        questions = new ArrayList<>();
    }

    public void addQuestion(String data) throws InvalidOperationException {
        String[] parts = data.split(";");
        // true-false question
        if (parts[0].equals("TF")) {
            TrueFalseQuestion tf = new TrueFalseQuestion(parts[1],
                    Integer.parseInt(parts[2]), Boolean.parseBoolean(parts[3]));
            questions.add(tf);
        } // multiple-choice question
        else if (parts[0].equals("MC")) {
            MultipleChoiceQuestion mc = null;
            try {
                mc = new MultipleChoiceQuestion(parts[1],
                        Integer.parseInt(parts[2]), parts[3].charAt(0));
                questions.add(mc);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public void printQuiz(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        questions.stream().sorted(Comparator.comparing(Question::getPoints).reversed())
                .forEach(pw::println);

        pw.flush();
        pw.close();
    }

    public void answerQuiz(List<String> answers, OutputStream os) throws InvalidOperationException {
        if (answers.size() != questions.size()) {
            throw new InvalidOperationException("Answers and questions must be of same length!");
        }
        PrintWriter pw = new PrintWriter(os);
        double totalPoints = 0;
        for (int i = 0; i < answers.size(); i++) {
            double currentPoints = questions.get(i).calculatePoints(answers.get(i));
            totalPoints += currentPoints;

            pw.printf("%d. %.2f\n", (i + 1),
                    currentPoints);

        }
        pw.printf("Total points: %.2f", totalPoints);
        pw.flush();
        pw.close();
    }
}


public class QuizTest {
    public static void main(String[] args) throws InvalidOperationException {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < questions; i++) {
            quiz.addQuestion(sc.nextLine());
        }

        List<String> answers = new ArrayList<>();

        int answersCount = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < answersCount; i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) {
            quiz.printQuiz(System.out);
        } else if (testCase == 2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}
