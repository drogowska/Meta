import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tour extends ArrayList<Client> {

    double[][] pheromoneMatrix;

    private Tour() {
    }

    public Tour(int clientCount) {
        pheromoneMatrix = new double[clientCount][clientCount];
        for(int from=0; from<clientCount; from++) {
            for(int to=0; to<clientCount; to++) {
                pheromoneMatrix[from][to] = 1;
            }
        }
    }

    public List<Client> getAvailableFrom(List<Client> visited, double load, double time) {
        return this.stream().filter(client ->
                (Main.capacity >= load + client.demand) // capacity is correct?
                && (time <= client.endTime) // is before end time?
                && (!visited.contains(client)) // it was not visited!
        ).collect(Collectors.toList());
    }

    public static double getDistanceBetween(Client a, Client b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public double getPheromoneBetween(Client a, Client b) {
        int from = indexOf(a) + 1;
        int to = indexOf(b) + 1;
        if(a == Main.depot) {
            from = 0;
        }
        if(b == Main.depot) {
            to = 0;
        }
        return pheromoneMatrix[from][to];
    }

    public void actualizePheromones() {
        for(int from=0; from<size(); from++) {
            for(int to=0; to<size(); to++) {
                pheromoneMatrix[from][to] = pheromoneMatrix[from][to] * Main.pheromoneEvaporation;
            }
        }
        Main.colony.forEach(ant -> {
            double antHeuristic = 1 / ant.getAllDistance();
            for(int i=1; i<ant.allVisited.size(); i++) {
                int from = indexOf(ant.allVisited.get(i-1)) + 1;
                int to = indexOf(ant.allVisited.get(i)) + 1;
                if(ant.allVisited.get(i-1) == Main.depot) {
                    from = 0;
                }
                if(ant.allVisited.get(i) == Main.depot) {
                    to = 0;
                }
                pheromoneMatrix[from][to] = pheromoneMatrix[from][to] + antHeuristic;
            }
        });
    }

}
