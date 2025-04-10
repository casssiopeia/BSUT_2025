package strategy;

public class LaserPrinter implements PrintStrategy {
    @Override
    public void print(String document) {
        System.out.println("Лазерная печать: " + document);
    }
}