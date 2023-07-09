package LabExercises.Shapes;


import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


class Canvas implements Comparable<Canvas>{
    private String id;
    private List<Integer> squares;

    public Canvas(String id, List<Integer> squares) {
        this.id = id;
        this.squares = squares;
    }
    public Canvas(String line){
        String[] parts= line.split("\\s+");
        this.id=parts[0];
        this.squares= Arrays.stream(parts).skip(1)
                .map(Integer::parseInt).collect(Collectors.toList());
    }
    public int calculatePerimeter(){
        return squares.stream().mapToInt(i -> i*4).sum();
    }

    @Override
    public int compareTo(Canvas o) {
        return calculatePerimeter()-o.calculatePerimeter();
    }

    public int canvasSize(){
        return squares.size();
    }
    public String toString(){
        return String.format("%s %d %d",id,canvasSize(),calculatePerimeter());
    }
}

class ShapesApplication{
    private List<Canvas> canvases;

    public ShapesApplication() {
    }

    public int readCanvases(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        this.canvases = br.lines().map(Canvas::new).collect(Collectors.toList());
        return this.canvases.stream().mapToInt(Canvas::canvasSize).sum();
    }

    public void printLargestCanvasTo (OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(canvases.stream().max(Canvas::compareTo).get());
        printWriter.close();
    }
}

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}