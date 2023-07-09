package LabExercises.Koli;

import java.util.*;
import java.util.stream.Collectors;

public class CarTest {
    public static void main(String[] args) {
        CarCollection carCollection = new CarCollection();
        String manufacturer = fillCollection(carCollection);
        carCollection.sortByPrice(true);
        System.out.println("=== Sorted By Price ASC ===");
        print(carCollection.getList());
        carCollection.sortByPrice(false);
        System.out.println("=== Sorted By Price DESC ===");
        print(carCollection.getList());
        System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
        List<Car> result = carCollection.filterByManufacturer(manufacturer);
        print(result);
    }

    static void print(List<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    static String fillCollection(CarCollection cc) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length < 4) return parts[0];
            Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
                    Float.parseFloat(parts[3]));
            cc.addCar(car);
        }
        scanner.close();
        return "";
    }
}


class Car {
    private String manufacturer;
    private String model;
    private int price;
    private double power;

    public Car(String manufacturer, String model, int price, double power) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%.0fKW) %d", manufacturer, model, power, price);
    }

    public int getPrice() {
        return price;
    }

    public double getPower() {
        return power;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }
}

class CarCollection {
    private List<Car> carList;

    public CarCollection() {
        carList = new ArrayList<>();
    }
    public void addCar(Car car) {
        carList.add(car);
    }

    public void sortByPrice(boolean ascending) {
        Comparator<Car> comparatorByPriceAndPower = Comparator.comparing(Car::getPrice).thenComparing(Car::getPower);
        carList = carList.stream().sorted(ascending ? comparatorByPriceAndPower : comparatorByPriceAndPower.reversed()).collect(Collectors.toList());
    }

    public List<Car> filterByManufacturer(String manufacturer) {
        return carList.stream()
                .filter(car -> car.getManufacturer().toLowerCase().equals(manufacturer.toLowerCase()))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }

    public List<Car> getList(){
        return carList;
    }

}