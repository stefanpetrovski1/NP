package Intros.OOP_intro;


abstract class Animal{
    private String name;

    public Animal(String name) {
        this.name = name;
    }
    public abstract void makeSound();
}

class Cat extends Animal{
    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println("MEOW MEEOOOOWW MEOOOWWW");
    }
}

class Dog extends Animal{
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println("WOOF WOOF WOOF");
    }
}

public class AnimalTest {
    public static void main(String[] args) {
        Animal cat = new Cat("Macka");
        Animal dog = new Dog("Kuce");

        cat.makeSound();
        dog.makeSound();
    }
}