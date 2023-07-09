package Intros.OOP_intro;


interface Drawable{
    void draw();
}

class Circle implements Drawable{
    private int length;

    public Circle(int length) {
        this.length = length;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a circle with a length of: "+length+"...");
    }
}

class Square implements Drawable{
    private int length;
    public Square(int length) {
        this.length = length;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a square with a length of: "+length+"...");
    }
}

public class DrawableTest {
    public static void main(String[] args) {
        Square square = new Square(5);
        Circle circle = new Circle(10);

        circle.draw();
        square.draw();
    }
}
