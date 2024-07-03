package meta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tournament {

    private static Comparator<Chromosome> comparator = (chromosome, t1) -> {
        if(chromosome.getValue() < t1.getValue()) return 1;
        else if (chromosome.getValue() > t1.getValue()) return -1;
        return 0;
    };

    public static List<Chromosome> getParents(List<Chromosome> chromosomeList) {
        return List.of(tournament(chromosomeList), tournament(chromosomeList));
    }

    private static Chromosome tournament(List<Chromosome> list) {
        List<Chromosome> group = new ArrayList<>();
        int tournamentSize = Main.random.nextInt(list.size() - 1) + 1;
        for (int i = 0; i < tournamentSize; i++) {
            int rnd = Main.random.nextInt(list.size());
            group.add(list.get(rnd));
        }
        group.sort(comparator);
        return group.get(0);
    }

}
