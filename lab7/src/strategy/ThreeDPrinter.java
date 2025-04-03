package strategy;

public class ThreeDPrinter implements PrintStrategy{
    @Override
    public void print(String document) {
        System.out.println("3D печать: " + document);
    }
}
