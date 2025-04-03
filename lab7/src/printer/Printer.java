package printer;

import strategy.PrintStrategy;

public class Printer {
    private PrintStrategy strategy;

    public Printer(PrintStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PrintStrategy strategy) {
        this.strategy = strategy;
    }

    public void printDocument(String document) {
        if (strategy != null) {
            strategy.print(document);
        } else {
            System.out.println("Стратегия печати не задана!");
        }
    }
}
