package strategy;

public class InkjetPrinter implements PrintStrategy {
    @Override
    public void print(String document) {
        System.out.println("Струйная печать: " + document);
    }
}