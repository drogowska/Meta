package meta;

public class MatyasFunction implements Function {
    @Override
    public double fun(double x, double y) {
        return 0.26*(Math.pow(x,2) + Math.pow(y,2)) - (0.48*x*y);
    }
}
