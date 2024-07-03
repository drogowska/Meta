package meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ant {

    private final List<Point> visited = new ArrayList<>();
    private String visitedList = "";
    private double distance;
    private final List<Path> tour = new ArrayList<>();
    private Point current;

    public void visit() {
        Path path = Tour.getObjectToVisit(current);
        if (!path.getTo().isVisited()) {
            visited.add(path.getTo());
            visitedList += path.getTo().getNumber() + " ";
            path.getTo().setVisited(true);
            current = path.getTo();
            tour.add(path);
        }
        countDistance();
    }

    public Ant() {
        current = null;
        distance = 0;
    }

    public void visitNextRandom() {
        double rnd = new Random().nextDouble();
        if (rnd < ACO.probability) {
            Point next = ACO.objectList.get(new Random().nextInt(ACO.n));
            visited.add(next);
            current.setVisited(true);
            tour.add(new Path(current, next));
            current = next;
        }
    }

    public void startRandom() {
        Point next = ACO.objectList.get(new Random().nextInt(ACO.n));
        visited.add(next);
        visitedList += next.getNumber() + " ";
        next.setVisited(true);
        current = next;
    }

    public double countDistance() {
        double distance = 0;
        for (Path route : tour)
            distance += route.getDistance();
        this.distance = distance;
        return distance;
    }

    public double getDistance() {
        return distance;
    }

    public String getVisitedList() {
        return visitedList;
    }
}
