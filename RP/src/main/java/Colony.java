import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Colony extends ArrayList<Ant> {

    public Colony() {
        for(int i=0; i<Main.colonySize; i++) {
            add(new Ant());
        }
    }

    public void resetAnts() {
        forEach(ant -> {
            ant.allVisited.clear();
            ant.roundVisited.clear();
            ant.roundVisited.add(Main.depot);
            ant.time = 0;
            ant.round = 1;
        });
    }

    public Ant getBest() {
        AtomicReference<Ant> bestAnt = new AtomicReference<>(get(0));
        this.forEach(ant -> {
            if(ant.getAllDistance() < bestAnt.get().getAllDistance()) {
                bestAnt.set(ant);
            }
        });
        return bestAnt.get();
    }
}
