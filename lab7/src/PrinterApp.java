import strategy.*;
import printer.Printer;

public class PrinterApp {
    public static void main(String[] args) {
        Printer printer = new Printer(new InkjetPrinter());
        printer.printDocument("Документ 1");

        printer.setStrategy(new LaserPrinter());
        printer.printDocument("Документ 2");

        printer.setStrategy(new ThreeDPrinter());
        printer.printDocument("Документ 3");
    }
}
