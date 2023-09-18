package LabExercises.Kviz;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


class InvalidOperationException extends Exception {
    public InvalidOperationException(String message) {
        super(message);
    }
}

abstract class Question {
    String text;
    double points;

    public Question(String text, double points) {
        this.text = text;
        this.points = points;
    }

    public static Question createQuestion(String data) throws InvalidOperationException {
        String[] parts = data.split(";");
        if (parts[0].equals("TF")) {
            return new TrueFalseQuestion(parts[1], Integer.parseInt(parts[2]), Boolean.parseBoolean(parts[3]));
        } else {
            if (!("ABCDE".contains(parts[3]))) {
                throw new InvalidOperationException(parts[3] + " is not allowed option for this question");
            }
            return new MultipleChoiceQuestion(parts[1], Integer.parseInt(parts[2]), parts[3].charAt(0));
        }
    }

    public double getPoints() {
        return points;
    }

    public abstract double calculatePoints(String answer);
}

class TrueFalseQuestion extends Question {
    boolean answer;

    public TrueFalseQuestion(String text, int points, boolean answer) {
        super(text, points);
        this.answer = answer;
    }


    @Override
    public double calculatePoints(String answer) {
        if (this.answer == Boolean.parseBoolean(answer)) {
            return points;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("True/False Question: %s Points: %.0f Answer: %s",
                text, points, answer);
    }
}


class MultipleChoiceQuestion extends Question {
    char answer;

    public MultipleChoiceQuestion(String text, int points, char answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public double calculatePoints(String answer) {
        if (answer.charAt(0) == this.answer) {
            return points;
        }
        return -points * 0.2;
    }

    @Override
    public String toString() {
        return String.format("Multiple Choice Question: %s Points %.0f Answer: %c"
                , text, points, answer);
    }
}


class Quiz {
    List<Question> questions;

    public Quiz() {
        questions = new ArrayList<>();
    }

    public void addQuestion(String questionData) {
        try {
            Question question = Question.createQuestion(questionData);
            questions.add(question);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }

    }

    public void printQuiz(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        questions.stream().sorted(Comparator.comparing(Question::getPoints).reversed())
                .forEach(pw::println);

        pw.flush();
    }

    public void answerQuiz (List<String> answers, OutputStream os){
        try {
            if(answers.size()!=questions.size()){
                throw  new InvalidOperationException("Answers and questions must be of same length!");
            }
            PrintWriter pw= new PrintWriter(os);
            double finalScore=0;
            for(int i=0;i<questions.size();i++){
                double points = questions.get(i).calculatePoints(answers.get(i));
                finalScore+=points;
                pw.printf("%d. %.2f\n",(i+1),points);
            }
            pw.printf("Total points: %.2f",finalScore);
            pw.flush();
        }
        catch (InvalidOperationException e){
            System.out.println(e.getMessage());
        }
    }
}

public class QuizTest {
    public static void main(String[] args) {

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
            quiz.answerQuiz(answers, System.out);
        } else {
            System.out.println("Invalid test case");
        }

    }
}
