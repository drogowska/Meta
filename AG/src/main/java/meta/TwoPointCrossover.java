package meta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TwoPointCrossover {

    public static List<Chromosome> crossoverChildren(List<Chromosome> parents) {
        if(Main.crossoverProbability >= Main.random.nextDouble()) {
            Chromosome child1 = (Chromosome) parents.get(0).clone();
            Chromosome child2 = (Chromosome) parents.get(1).clone();

            List<Integer> points = generatePoints(parents.get(0).getGeneList().size());

            List<Gene> genes1 = child1.getGeneList();
            List<Gene> genes2 = child2.getGeneList();

            for (int i = points.get(0) + 1; i < points.get(1) + 1; i++) {
                genes1.set(i, parents.get(1).getGeneList().get(i));
                genes2.set(i, parents.get(0).getGeneList().get(i));
            }
            return List.of(new Chromosome(genes1),  new Chromosome(genes2));
        }
        return parents;
    }

    private static List<Integer> generatePoints(int len) {
        List<Integer> points = new ArrayList<>();
        points.add(Main.random.nextInt(len-1));
        points.add(Main.random.nextInt(len-1));
        points.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                if(integer < t1) return -1;
                else if (integer > t1) return 1;
                else return 0;
            }
        });
        return points;
    }

}
