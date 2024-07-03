package meta;

import java.util.List;

public class Mutation {

    public static void mutate(List<Chromosome> chromosomeList) {
        for(Chromosome chromosome : chromosomeList) {
            chromosome.getGeneList().forEach(gene -> {
                if(Main.random.nextDouble() <= Main.mutationProbability) {
                    gene.setActive(!gene.isActive());
                }
            });
        }
    }
}
