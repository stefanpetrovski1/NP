package LabExercises.NPkurs;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class Student {
    String id;
    String name;
    int firstMidtermPoints;
    int secondMidtermPoints;
    int labPoints;


    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.firstMidtermPoints = 0;
        this.secondMidtermPoints = 0;
        this.labPoints = 0;
    }


    public double getTotalPoints() {
        return firstMidtermPoints * 0.45 + secondMidtermPoints * 0.45 + labPoints;
    }

    public int getGrade() {
        double points = getTotalPoints();
        if (points > 90) {
            return 10;
        } else if (points > 80) {
            return 9;
        } else if (points > 70) {
            return 8;
        } else if (points > 60) {
            return 7;
        } else if (points > 50) {
            return 6;
        } else {
            return 5;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setFirstMidtermPoints(int firstMidtermPoints) {
        this.firstMidtermPoints = firstMidtermPoints;
    }

    public void setSecondMidtermPoints(int secondMidtermPoints) {
        this.secondMidtermPoints = secondMidtermPoints;
    }

    public void setLabPoints(int labPoints) {
        this.labPoints = labPoints;
    }

    public static boolean checkLegalPoints(String activity, int points) {
        if (activity.equals("midterm1") || activity.equals("midterm2")) {
            return points>0 && points<=100;
        } else {
            return points>0 && points<=10;
        }
    }


    public static void update(Student s,String activity, int points) {
        if(activity.equals("labs")){
            s.setLabPoints(points);
        }
        else if(activity.equals("midterm1")){
            s.setFirstMidtermPoints(points);
        }
        else if(activity.equals("midterm2")){
            s.setSecondMidtermPoints(points);
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",
                id, name, firstMidtermPoints, secondMidtermPoints, labPoints, getTotalPoints(), getGrade());
    }
}


class AdvancedProgrammingCourse {
    Map<String, Student> studentsById;

    public AdvancedProgrammingCourse() {
        studentsById = new HashMap<>();
    }

    public void addStudent(Student s) {
        studentsById.putIfAbsent(s.getId(), s);
    }

    public void updateStudent(String idNumber, String activity, int points) throws Exception {
        if (!Student.checkLegalPoints(activity, points)) {
            throw new Exception();
        }

        Student.update(studentsById.get(idNumber),activity,points);
    }



    private List<Student> getPassedStudents(){
        return studentsById.values().stream()
                .filter(s -> s.getGrade() > 5).collect(Collectors.toList());
    }

    public List<Student> getFirstNStudents(int n) {
        return getPassedStudents().stream()
                .sorted(Comparator.comparing(Student::getTotalPoints).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public void printStatistics(){
        DoubleSummaryStatistics dss = getPassedStudents().stream().mapToDouble(Student::getTotalPoints)
                .summaryStatistics();

        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f",
                dss.getCount(),dss.getMin(),dss.getAverage(),dss.getMax());
    }

    public Map<Integer,Integer> getGradeDistribution(){
        Map<Integer,Integer> gradesCount = new HashMap<>();
        IntStream.range(5,11).forEach(i -> gradesCount.put(i,0));

        studentsById.values().stream().mapToInt(Student::getGrade)
                .forEach(grade -> {
                    gradesCount.computeIfPresent(grade,(k,v) -> v+1);
                });

        return gradesCount;
    }

}

public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                try {
                    advancedProgrammingCourse.updateStudent(idNumber, activity, points);
                } catch (Exception e) {
                    // do nothing
                }
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            }
            else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            }
            else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}
