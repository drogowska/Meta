import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Ant {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    double time = 0;

    List<Client> roundVisited = new ArrayList<>();
    List<Client> allVisited = new ArrayList<>();
    int round = 1;

    Ant() {
        roundVisited.add(Main.depot);
    }

    public boolean goNext() {
        if(isRoundEnded()) { // no more available in this round
            logger.debug("Round "  + round + "have ended for our ant.");
            logger.debug("Load: " + getLoad());
            round++;
            allVisited.addAll(roundVisited);
            roundVisited.clear();
            time = 0;
            roundVisited.add(Main.depot);
        }
        if(isEnd()) { // restarted round but still no available
            logger.debug("Ant ended journey.");
            roundVisited.add(Main.depot);
            allVisited.addAll(roundVisited);
            return false;
        }
        List<Client> availableClients = Main.tour.getAvailableFrom(getAllVisitedPlusRoundVisited(), getLoad(), time);
        Client nextClient = chooseBest(availableClients);
        if(time < nextClient.startTime) {
            time = nextClient.startTime;
        }
        roundVisited.add(nextClient);
        return true;
    }

    private Client chooseBest(List<Client> availableClients) {
        List<Double> probabilities = availableClients.stream()
                .map(client ->
                    Math.pow(Main.tour.getPheromoneBetween(getCurrent(), client), Main.pheromoneAlpha)
                        * Math.pow((1 / Tour.getDistanceBetween(getCurrent(), client)), Main.heuristicBeta)
                ).collect(Collectors.toList());
        double probabilitySum = probabilities.stream().reduce(0d, Double::sum);
        logger.debug("probability sum: " + probabilitySum);
        for(int i=1; i<probabilities.size(); i++) {
            probabilities.set(i, probabilities.get(i) + probabilities.get(i-1));
        }
        double chosen = ThreadLocalRandom.current().nextDouble(0, probabilitySum);
        logger.debug("chosen: " + chosen);
        for(int i=0; i<probabilities.size(); i++) {
            if(chosen < probabilities.get(i)) {
                return availableClients.get(i);
            }
        }
        logger.error("Not even one chosen!");
        return null;
    }

    private double getLoad() {
        double load = 0;
        for(Client client : roundVisited) {
            load = load + client.demand;
        }
        return load;
    }

    private Client getCurrent() {
        return roundVisited.get(roundVisited.size() - 1);
    }

    public double getAllDistance() {
        double distance = 0;
        for(int i=1; i<allVisited.size(); i++) {
            distance = distance + Tour.getDistanceBetween(allVisited.get(i - 1), allVisited.get(i));
        }
        return distance;
    }

    private boolean isRoundEnded() {

        return Main.tour.getAvailableFrom(getAllVisitedPlusRoundVisited(), getLoad(), time).size() == 0;
    }

    public boolean isEnd() {
        return isRoundEnded() && roundVisited.size() == 1;
    }

    public List<Client> getAllVisitedPlusRoundVisited() {
        List<Client> result = new ArrayList<>();
        result.addAll(allVisited);
        result.addAll(roundVisited);
        return result;
    }

    public boolean isValid() {
        return allVisited.containsAll(Main.tour)
                && allVisited.stream().filter(client -> client.id == 0).count() + 100 == allVisited.size();
    }

    @Override
    public String toString() {
        return "Ant{ " +
                "distance=" + getAllDistance() +
                ", allVisited=" + allVisited.size() +
                ", round=" + round +
                ", isValid=" + isValid() +
                '}';
    }
}
