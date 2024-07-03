package meta;

import java.util.concurrent.ThreadLocalRandom;

public class Particle implements Comparable<Particle>, Cloneable {

    private double x;
    private double y;
    private double bestX;
    private double bestY;
    private double fitness;
    private double bestFitness;
    private double velocityX;
    private double velocityY;

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
        velocityX = 0;
        velocityY = 0;
    }

    public Particle(int from, int to) {
        //x, y from range <-10,10>
        this.x = ThreadLocalRandom.current().nextInt(from, to+1);
        this.y = ThreadLocalRandom.current().nextInt(-10, 10+1);
        bestX = x;
        bestY = y;
        velocityX = 0;
        velocityY = 0;
    }

    public double getY() {
        return y;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }
    public double getFitness() {
        return fitness;
    }

    public double getBestX() {
        return bestX;
    }

    public void setBestX(double bestX) {
        this.bestX = bestX;
    }

    public double getBestY() {
        return bestY;
    }

    public void setBestY(double bestY) {
        this.bestY = bestY;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void countFitness(Function function) {
        fitness = function.fun(x,y);
    }

    public void updatePosition() {
        x = velocityX + x;
        y = velocityY + y;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(Particle o) {
        if (o.getFitness() < fitness) return 1;
        else if (o.getFitness() > fitness) return -1;
        return 0;
    }
}
