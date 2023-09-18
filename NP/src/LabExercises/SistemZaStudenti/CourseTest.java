package LabExercises.SistemZaStudenti;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class Student {
    String index;
    String name;
    int firstMidtermPoints;  // 0-100
    int secondMidtermPoints;   //0-100
    int labPoints;    //0-10

    public Student(String index, String name) {
        this.index = index;
        this.name = name;
        this.firstMidtermPoints = 0;
        this.secondMidtermPoints = 0;
        this.labPoints = 0;
    }


    public double getSummaryPoints() {
        return 0.45 * firstMidtermPoints + 0.45 * secondMidtermPoints + labPoints;
    }

    public int getGrade() {
        if (getSummaryPoints() < 50)
            return 5;
        if (getSummaryPoints() < 60)
            return 6;
        if (getSummaryPoints() < 70)
            return 7;
        if (getSummaryPoints() < 80)
            return 8;
        if (getSummaryPoints() < 90)
            return 9;

        return 10;
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

    @Override
    public String toString() {
        //ID: 151020 Name: Stefan First midterm: 78 Second midterm 80 Labs: 8 Summary points: 79.10 Grade: 8
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",
                index, name, firstMidtermPoints, secondMidtermPoints, labPoints,
                getSummaryPoints(), getGrade());
    }
}


class AdvancedProgrammingCourse {
    Map<String, Student> studentsById;

    public AdvancedProgrammingCourse() {
        studentsById = new HashMap<>();
    }

    public void addStudent(Student s) {
        studentsById.put(s.index, s);
    }

    public void updateStudent(String idNumber, String activity, int points) throws Exception {
        Student s = studentsById.get(idNumber);
        switch (activity) {
            case "midterm1":
                s.setFirstMidtermPoints(points);
                break;
            case "midterm2":
                s.setSecondMidtermPoints(points);
                break;
            case "labs":
                s.setLabPoints(points);
                break;
            default:
                throw new Exception();
        }
    }

    private List<Student> getPassedStudents(){
        return studentsById.values().stream()
                .filter(s -> s.getSummaryPoints() >= 50)
                .collect(Collectors.toList());
    }

    public List<Student> getFirstNStudents(int n) {
        return studentsById.values().stream()
                .sorted(Comparator.comparing(Student::getSummaryPoints).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public Map<Integer, Integer> getGradeDistribution() {
//        newMap = studentsById.entrySet().stream()
//                .collect(Collectors.groupingBy(Student::getGrade)
//                        );
        Map <Integer,Integer> gradesByOcc = new HashMap<>();
        IntStream.range(5,11).forEach(i -> gradesByOcc.put(i,0));
        studentsById.entrySet().stream().forEach(entry -> {
            int grade = entry.getValue().getGrade();
            //gradesByOcc.putIfAbsent(grade,1);
            gradesByOcc.put(grade,gradesByOcc.get(grade)+1);
        });
        return gradesByOcc;
    }

    public void printStatistics(){
        DoubleSummaryStatistics dss = getPassedStudents().stream().mapToDouble(s -> s.getSummaryPoints())
                .summaryStatistics();
        System.out.println(String.format("Count: %d Min: %.2f Average: %.2f Max: %.2f",
                dss.getCount(),dss.getMin(),dss.getAverage(),dss.getMax()));
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
                    // Do nothing
                }
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}
