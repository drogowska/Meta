package meta;

import java.util.*;

import static meta.ACO.*;

public class Tour {

    public static boolean isFinished() {
        int tmp = 0;
        for (Point object : objectList) {
            if (object.isVisited()) tmp++;
        }
        return tmp == objectList.size();
    }

    public static void reset() {
        objectList.forEach(object -> object.setVisited(false));
    }

    public static Path getObjectToVisit(Point current) {
        countProbabilities(routeList);
        List<Path> paths = new ArrayList<>(routeList);
        List<Path> tmp = new ArrayList<>(routeList);
        paths.removeIf(path -> path.getFrom() != current);
        paths.removeIf(path -> path.getTo().isVisited());
        paths.sort(Path::compareTo);
        Path chosen = rouletteWheel(paths);
        tmp.remove(chosen);
        tmp.removeIf(path -> (path.getFrom() == chosen.getTo() && path.getTo() == chosen.getFrom()));
        routeList = List.copyOf(tmp);
        return chosen;
    }

    private static Path rouletteWheel(List<Path> paths) {
        double rnd = new Random().nextDouble();
        if (rnd < probability) {
            List<Double> list = new ArrayList<Double>();
            list.add(0.0);
            list.add(paths.get(0).getProbability());
            for (int i = 1; i < paths.size(); i++) {
                list.add(paths.get(i).getProbability() + list.get(i - 1));
            }
            double rand = 0 + (list.get(paths.size() - 1) - 0) * new Random().nextDouble();
            for (int i = 0; i < list.size(); i++) {
                if (rand > list.get(i) && rand <= list.get(i + 1)) return paths.get(i);
            }
        }
        return paths.get(0);
    }

    private static void countProbabilities(List<Path> list) {
        double sum = 0.0;//list.stream().mapToDouble(Path::getProbability).sum();
        for (Path path : list) {
                 sum += path.getProbability();
        }
        double finalSum = sum;
        list.forEach(path -> {
            if(finalSum != 0)
                path.setProbability(finalSum);
        });
    }


}
