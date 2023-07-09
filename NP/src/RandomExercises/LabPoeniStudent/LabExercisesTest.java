package RandomExercises.LabPoeniStudent;


import java.util.*;
import java.util.stream.Collectors;


// TODO: metod za statistika

class Student {
    String id;
    List<Integer> labPoints;   // max 10

    public Student(String id, List<Integer> points) {
       this.id=id;
       this.labPoints=points;
    }

    public double getAverage() {
        int sum = labPoints.stream().mapToInt(i -> i).sum();
        return (double) sum / 10;
    }

    public boolean hasSignature() {
        return labPoints.size() >= 8;
    }

    public String getId() {
        return id;
    }

    public int getYear() {
        return 20 - Integer.parseInt(id.substring(0, 2));
    }

    @Override
    public String toString() {
        String signature = hasSignature() ? "YES" : "NO";
        return String.format("%s %s %.2f", id, signature, getAverage());
    }
}


// vo LabExercises cuvaj list od godini
// vo Year implementiraj ja logikata za avg
// implementiraj contructor / static create method

class Year{
    List<Student> students = new ArrayList<>();
    int year;
    double averagePoints;


    Year(Student s){
        year=s.getYear();
        students.add(s);
        averagePoints=getAvgFromYear();
    }

    public int getYear() {
        return year;
    }

    public double getAvgFromYear(){
        return students.stream()
                .filter(Student::hasSignature)
                .mapToDouble(Student::getAverage).average().orElse(0);
    }

    @Override
    public String toString() {
        return String.format("%d : %.2f",year,getAvgFromYear());
    }
}




class LabExercises{
    Map <Integer, Year> yearMap;
    Set<Student> students;


    public LabExercises() {
        yearMap= new HashMap<>();
        students= new HashSet<>();
    }

    public void addStudent(Student student) {
        Year year = new Year(student);
        yearMap.put(year.getYear(),year);
        students.add(student);
    }

    public void printByAveragePoints(boolean ascending, int n) {
        Comparator<Student> comparatorByAvgAndId =
                Comparator.comparing(Student::getAverage).thenComparing(Student::getId);

        if (!ascending) comparatorByAvgAndId = comparatorByAvgAndId.reversed();

        students.stream().sorted(comparatorByAvgAndId).limit(n)
                .forEach(System.out::println);
    }
    public List<Student> failedStudents() {
        Comparator<Student> comparatorByIdAndAvg =
                Comparator.comparing(Student::getId).thenComparing(Student::getAverage);

        return students.stream().filter(s -> !s.hasSignature()).sorted(comparatorByIdAndAvg)
                .collect(Collectors.toList());
    }

    public Map<Integer,Double> getStatisticsByYear(){
        Map <Integer, Double> result= new HashMap<>();

        yearMap.forEach((k,v) -> {
            int year = k;
            double avg = v.averagePoints;
            result.put(year,avg);
        });
        return result;
    }
}









//class LabExercises {
//    List<Student> students;
//
//    public LabExercises() {
//        students = new ArrayList<>();
//    }
//
//    public void addStudent(Student student) {
//        students.add(student);
//    }
//

//

//
//    public List<Student> getPassedStudents() {
//        return students.stream().filter(Student::hasSignature).collect(Collectors.toList());
//    }
//    //            2 : 5.91
////            3 : 4.68
////            4 : 3.28
////            5 : 4.06
////            6 : 2.89
////            7 : 4.07
////            8 : 3.10
////            9 : 4.30
//    public Map<Integer, Double> getStatisticsByYear() {
//        List<Student> passedStudents = getPassedStudents();
//        Map <Integer,List<Student>> studentsByYear = new HashMap<>();
//        Map <Integer,Double> resultMap = new HashMap<>();
//
//        for (Student s : passedStudents){
//            studentsByYear.putIfAbsent(s.getYear(),new ArrayList<>());
//            studentsByYear.get(s.getYear()).add(s);
//        }
//
//        studentsByYear.entrySet().stream().forEach(entry->{
//            double avg = entry.getValue().
//        });
//    }
//}


public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}