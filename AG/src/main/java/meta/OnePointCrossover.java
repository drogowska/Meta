package meta;

import java.util.ArrayList;
import java.util.List;

public class OnePointCrossover {

    public static List<Chromosome> crossoverChildren(List<Chromosome> parents) {
        if(Main.crossoverProbability >= Main.random.nextDouble()) {
            Chromosome parent1 = (Chromosome) parents.get(0).clone();
            Chromosome parent2 = (Chromosome) parents.get(1).clone();

            int splitPoint = Main.random.nextInt(parent1.getGeneList().size() - 1);
            List<Gene> genes1 = new ArrayList<>();
            List<Gene> genes2 = new ArrayList<>();

            for(int i=0; i<parent1.getGeneList().size(); i++) {
                if(i>=splitPoint) {
                    genes1.add((Gene) parent1.getGeneList().get(i).clone());
                    genes2.add((Gene) parent2.getGeneList().get(i).clone());
                } else {
                    genes1.add((Gene) parent2.getGeneList().get(i).clone());
                    genes2.add((Gene) parent1.getGeneList().get(i).clone());
                }
            }
            return List.of(new Chromosome(genes1),  new Chromosome(genes2));
        } else {
            return parents;
        }
    }
}
