import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static double capacity = 1000;
    public static int colonySize = 100;
    public static double pheromoneAlpha = 1;
    public static double heuristicBeta = 1.5;
    public static double pheromoneEvaporation = 0.8;
    public static int iterations = 1000;
    public static String fileName = "path\\to\\file\\rc203.csv";

    public static Colony colony = new Colony();
    public static Tour tour;
    public static Client depot;

    public static void main(String[] args) {

        double bestDistance = 1000000;
        int bestAllVisited = 0;
        int bestRounds = 0;
        boolean bestIsValid = true;
        List<Client> bestVisited = null;

        initialize();
        for(int i=0; i<iterations; i++) {
            logger.debug("Iteration nr: " + i);
            colony.resetAnts();
            colony.forEach(ant -> {
                while (ant.goNext()) {
                }
            });
            tour.actualizePheromones();

            if(colony.getBest().getAllDistance() < bestDistance) {
                bestDistance = colony.getBest().getAllDistance();
                bestAllVisited = colony.getBest().allVisited.size();
                bestRounds = colony.getBest().round;
                bestIsValid = colony.getBest().isValid();
                bestVisited = new ArrayList<>(colony.getBest().allVisited);
            }

            logger.info(i + ": " + colony.getBest().toString());
        }

        logger.info("BEST: " + "Ant{ " +
                "distance=" + bestDistance +
                ", allVisited=" + bestAllVisited +
                ", round=" + bestRounds +
                ", isValid=" + bestIsValid +
                '}');
    }

    private static void initialize() {
        MainReader.read(fileName);
        colony = new Colony();
    }
}
