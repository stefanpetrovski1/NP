package Intros.streams_intro.college;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

enum CSField{
    Web, ML, QA
}

enum DoctorField{
    Cardiology, Neurology
}

abstract class Student{
    protected String name;
    protected int index;
    protected int year;

    public Student(String name, int index,  int year) {
        this.index = index;
        this.name = name;
        this.year = year;
    }

    public int getIndex() {
        return index;
    }

    public int getYear() {
        return year;
    }

    abstract public boolean canDoAnIntership();

    // im not sure if this is OK xD
    abstract public String toString();
}

class DoctorStudent extends Student{
    private final DoctorField field;

    public DoctorStudent(String name, int index, int year, DoctorField field) {
        super(name, index, year);
        this.field = field;
    }

    @Override
    public boolean canDoAnIntership() {
        return year>=3;
    }

    @Override
    public String toString() {
        return String.format("\nName: %s; Index: %d; Major: Medicine; Field: %s",name,index,field);
    }
}

class ProgrammerStudent extends Student{
    private final CSField field;

    public ProgrammerStudent(String name, int index, int year, CSField field) {
        super(name, index, year);
        this.field = field;
    }

    @Override
    public boolean canDoAnIntership() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("\nName: %s; Index: %d; Major: Computer Science; Field: %s",name,index,field);
    }
}

class College{
    private String name;
    private List<Student> students;

    public College(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("College: %s Student count: %d",name,students.size()));
        students.forEach(s -> sb.append(s.toString()));
        return sb.toString();
    }

    public double calculateAverageMethod(){
        return students.stream()
                .mapToInt(Student::getYear)
                .average()
                .orElse(0.0);
    }

    public List<Student> getListOfStudentsInSameYear(int year){
        return students.stream()
                .filter(s -> s.getYear()==year)
                .toList();
    }
}

public class CollegeTest {
    public static void main(String[] args) {



        Student s1 = new DoctorStudent("Tea",206002,3,DoctorField.Cardiology);
        Student s2 = new DoctorStudent("Ana",220013,1,DoctorField.Neurology);
        Student s3 = new DoctorStudent("Andrej",218234,2,DoctorField.Neurology);
        Student s4 = new DoctorStudent("Anastasija",193011,5,DoctorField.Neurology);
        Student s5 = new DoctorStudent("Nada",206117,3,DoctorField.Cardiology);
        Student s6 = new DoctorStudent("Filip",211025,2,DoctorField.Cardiology);

        Student s7 = new ProgrammerStudent("Stefan",206023,3,CSField.Web);
        Student s8 = new ProgrammerStudent("Jordan",206012,3,CSField.ML);
        Student s9 = new ProgrammerStudent("Jovan",206016,3,CSField.ML);
        Student s10 = new ProgrammerStudent("Stefan",216055,2,CSField.Web);
        Student s11 = new ProgrammerStudent("Ivana",203123,3,CSField.QA);
        Student s12 = new ProgrammerStudent("Anastasija",186060,5,CSField.QA);
        Student s13 = new ProgrammerStudent("Stefan",196075,4,CSField.QA);
        Student s14 = new ProgrammerStudent("Stefan",201034,2,CSField.QA);
        Student s15 = new ProgrammerStudent("Viki",221073,1,CSField.ML);

        List<Student> students = Arrays.asList(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15);

        College ukim = new College("UKIM",students);

        Scanner scanner = new Scanner(System.in);

        int testNumber = scanner.nextInt();

        if (testNumber==1){

            System.out.println("==== Testing college constructor ====");
            System.out.println(ukim);

        }else if (testNumber==2){

            System.out.println("==== Testing calculateAverageYear method ====");
            System.out.println(ukim.calculateAverageMethod());
        }else if (testNumber==3){

            System.out.println("==== Testing getListOfStudentsInSameYear method ====");
            System.out.println("3rd Year: ");
            System.out.println(ukim.getListOfStudentsInSameYear(3));
            System.out.println("1st Year: ");
            System.out.println(ukim.getListOfStudentsInSameYear(1));
            System.out.println("6th Year: ");
            System.out.println(ukim.getListOfStudentsInSameYear(6));
        }



    }
}
