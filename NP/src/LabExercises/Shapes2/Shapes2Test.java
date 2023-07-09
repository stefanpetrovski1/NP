package LabExercises.Shapes2;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

enum ShapeSign{
    C,S
}

abstract class Shape{
    protected double size;



    public abstract double calculateArea();
    public abstract ShapeSign getShapeSign();

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}

class Square extends Shape{


    @Override
    public double calculateArea() {
        return size*size;
    }

    @Override
    public ShapeSign getShapeSign() {
        return ShapeSign.S;
    }
}

class Circle extends Shape{


    @Override
    public double calculateArea() {
        return size*size*Math.PI;
    }
    @Override
    public ShapeSign getShapeSign() {
        return ShapeSign.C;
    }
}

class Canvas implements Comparable<Canvas>{
    private String id;
    private List<Shape> shapes;


    public Canvas(String id, List<Shape> shapes) {
        this.id = id;
        this.shapes = shapes;
    }

    public static Canvas createCanvas(String line,double maxArea) throws IrregularCanvasException {
        String []parts =line.split("\\s+");
        String idCanvas = parts[0];
        List<Shape> shapeList = new ArrayList<>();

        Arrays.stream(parts).skip(1)
                .forEach(el -> {
                    if (Character.isAlphabetic(el.charAt(0))){
                        if(el.equals("C")){
                            shapeList.add(new Circle());
                        } else if (el.equals("S")) {
                            shapeList.add(new Square());
                        }
                    }else {
                        Shape shape = shapeList.get(shapeList.size()-1);
                        shape.setSize(Integer.parseInt(el));
                    }
                });

        if(shapeList.stream().anyMatch(s -> s.calculateArea() > maxArea))
            throw new IrregularCanvasException(idCanvas,maxArea);

//        double totalsum=shapeList.stream().mapToDouble(Shape::calculateArea).sum();
//        if(totalsum>maxArea)
//            throw new IrregularCanvasException(idCanvas,maxArea);
        return new Canvas(idCanvas,shapeList);
    }




    public int countCircles(){
        return (int)shapes.stream().filter(s -> s.getShapeSign()==ShapeSign.C).count();
    }

    public double calculateTotalCirclesSize(){
        return shapes.stream().filter(s -> s.getShapeSign()==ShapeSign.C).mapToDouble(Shape::calculateArea).sum();
    }

    public DoubleSummaryStatistics getStats(){
        return shapes.stream().mapToDouble(Shape::calculateArea).summaryStatistics();
    }

    public String toString(){
        DoubleSummaryStatistics stats = getStats();
        return String.format("%s %d %d %d %.2f %.2f %.2f",
                id,shapes.size(),countCircles(),shapes.size()-countCircles(),stats.getMin(),
                stats.getMax(),stats.getAverage());
    }
    @Override
    public int compareTo(Canvas o) {
        return Double.compare(
                this.shapes.stream().mapToDouble(Shape::calculateArea).sum(),
                o.shapes.stream().mapToDouble(Shape::calculateArea).sum()
        );
    }
}

class ShapesApplication{
    private double maxArea;
    private List<Canvas> canvases;

    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
        this.canvases = new ArrayList<>();
    }
    public void readCanvases (InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        canvases = br.lines()
                .map(line -> {
                    try {
                        return Canvas.createCanvas(line,maxArea);
                    } catch (IrregularCanvasException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void printCanvases(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);

        canvases.stream().sorted(Comparator.reverseOrder()).forEach(pw::println);

        pw.flush();
    }
}


class IrregularCanvasException extends Exception {
    IrregularCanvasException(String id, double maxArea) {
        super(String.format("Canvas %s has a shape with area larger than %.2f", id, maxArea));
    }
}

public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}