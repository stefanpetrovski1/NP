package Intros.MapIntro;

import java.util.ArrayList;
import java.util.List;

class Student{
    private String name;
    private int points;
    private String city;

    public Student(String name, int points, String city) {
        this.name = name;
        this.points = points;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.format("Name: %s; Points: %d; City: %s;",name,points,city);
    }
}

public class MapWithStreamsTest {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();

        studentList.add(new Student("John", 70, "New York"));
        studentList.add(new Student("Emily", 92, "New York"));
        studentList.add(new Student("Michael", 78, "LA"));
        studentList.add(new Student("Sophia", 43, "LA"));
        studentList.add(new Student("Daniel", 12, "Miami"));
        studentList.add(new Student("Olivia", 81, "LA"));
        studentList.add(new Student("William", 83, "New York"));
        studentList.add(new Student("Ava", 89, "San Francisco"));
        studentList.add(new Student("James", 55, "Dallas"));
        studentList.add(new Student("Mia", 94, "New York"));

//        Map<String,List<Student>> studentsByCity = studentList.stream()
//                .collect(Collectors.groupingBy(Student::getCity,
//                        ));

//        System.out.println(studentsByCity);
//
    }
}
