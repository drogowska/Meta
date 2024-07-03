package meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static meta.Tour.reset;

public class ACO {

    public static String resultString = "";
    public static String resultString2 = "";

    private static final int maxIter = 100;
    public static double probability = 0.3;
    public static double evaporation = 0.5;
    public static int alpha = 1;
    public static int beta = 1;
    public static int colonySize = 10;

    public static List<Point> objectList = new ArrayList<>();
    public static List<Path> pathList = new ArrayList<>();
    public static List<Path> routeList = new ArrayList<>();
    public static int n;

    public static List<Integer> run(String fileName) {
        objectList = FileReader.read(fileName);
        n = objectList.size();
        fillRouteList();
        Ant best = null;

        for (int i = 0; i < maxIter; i++) {
            Population population = new Population();
            population.initial(colonySize);
            best = population.getColony().get(0);

            for (Ant ant : population.getColony()) {
                routeList = List.copyOf(pathList);
                ant.startRandom();
                while (!Tour.isFinished()) {
                    ant.visit();
                }
                if (ant.getDistance() < best.getDistance()) best = ant;
                population.updatePheromone();
                reset();
            }
        }
        resultString = Main.filePathName + " " + ACO.colonySize + " " + ACO.alpha + " " + ACO.beta + " " + ACO.evaporation + " " + best.getDistance();
        resultString2 = " [" + best.getVisitedList() + "]";
        System.out.print(resultString + resultString2);
        return Arrays.stream(best.getVisitedList().split(" ")).map(Integer::parseInt).collect(toList());
    }

    private static void fillRouteList() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j)
                    pathList.add(new Path(objectList.get(i), objectList.get(j)));
            }
        }

    }


}
