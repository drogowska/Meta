package meta;

import java.util.ArrayList;
import java.util.List;


public class Population {

    public List<Ant> getColony() {
        return colony;
    }

    private final List<Ant> colony = new ArrayList<>();

    public void initial(int number, double antFactor) {
        int size = (int) Math.round(number * antFactor);
        for (int i = 0; i < size; i++) colony.add(new Ant());
    }

    public void initial(int colonySize) {
        for (int i = 0; i < colonySize; i++) colony.add(new Ant());
    }

    public double getDistance() {
        double sum = 0.0;
        for (Ant ant : colony)
            sum += ant.getDistance();
        return sum;
    }

    public double getAvgDistance() {
        return getDistance() / colony.size();
    }

    public void updatePheromone() {
        ACO.pathList.forEach(path -> path.setPheromone(path.getPheromone() * (1-ACO.evaporation)));
        double sum = 0;
        for(Ant ant : colony) {
            if(ant.getDistance() != 0)
                sum += 1.0 / ant.getDistance();
        }
        double finalSum = sum;
        colony.forEach(ant -> {
            ACO.pathList.forEach(path -> {
                if(ant.getDistance() != 0) {
                    path.setPheromone(path.getPheromone() + finalSum);
                }
            });
        });
    }
}
