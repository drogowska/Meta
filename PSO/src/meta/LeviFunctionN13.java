package meta;

public class LeviFunctionN13 implements Function {
    @Override
    public double fun(double x, double y) {
        return Math.pow(Math.sin(3*Math.PI*x), 2) + Math.pow(x-1,2) * (1 + Math.pow(Math.sin(3*Math.PI*y), 2))
                + Math.pow(y-1, 2) * (1 + Math.pow(Math.sin(2*Math.PI*y), 2));
    }
}
