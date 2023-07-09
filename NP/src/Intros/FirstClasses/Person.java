package Intros.FirstClasses;

import java.util.Objects;

public class Person {
    String firstName;
    String lastName;
    int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d",firstName,lastName,age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    public static void main(String[] args) {
        Person p = new Person("Stefan","P",21);
        Person p1 = new Person("Stefan","P",21);
        System.out.println(p);
        System.out.println(p.equals(p1));  // ako nema equals - sporeduva po referenci
    }
}
class SmartPerson extends Person{

    public SmartPerson(String firstName, String lastName, int age) {
        super(firstName, lastName, age);
    }
}