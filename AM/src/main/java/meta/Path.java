package meta;

import static meta.ACO.*;

public class Path implements Comparable<Path>, Cloneable {

    private Point from;
    private Point to;
    private double distance;
    private double pheromone;
    private double probability;

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Path(Point from, Point to) {
        this.from = from;
        this.to = to;
        distance = Math.sqrt(Math.pow(from.getX() - to.getX(), 2) +
                Math.pow(from.getY() - to.getY(), 2));
        pheromone = 1.0;
        probability = 1.0;
    }

    public Path() {
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPheromone() {
        return pheromone;
    }

    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double factor) {
        if(distance != 0)
            probability = (Math.pow(pheromone, alpha) * Math.pow(1.0 / distance, beta)) / factor;
    }

    @Override
    public int compareTo(Path o) {
        if (o.probability < this.probability) return -1;
        else if (o.probability > this.probability) return 1;
        return 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Path clone = new Path();
        clone.distance = this.distance;
        clone.pheromone = this.pheromone;
        clone.probability = this.probability;
        clone.from = this.from;
        clone.to = this.to;
        return clone;
    }
}
