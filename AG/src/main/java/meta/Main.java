package meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static List<Gene> geneList;
    public static String geneListFileName = "data.csv";
    public static int maxCapacity = 6404180;
    public static int populationSize = 100;
    public static int maxIterations = 1000;
    public static double crossoverProbability = 0.5;
    public static double mutationProbability = 0.1;
    public static final Random random = new Random();

    public static void main(String[] args) {
        geneList = GeneFileReader.read(geneListFileName);
        Population population = new Population();

        for(int i=0; i<maxIterations; i++) {
            List<Chromosome> newGeneration = new ArrayList<>();
            List<Chromosome> actualGeneration = new ArrayList<>();
            population.getChromosomeList().forEach(chromosome -> actualGeneration.add((Chromosome) chromosome.clone()));
            while (newGeneration.size() != actualGeneration.size()) {
                actualGeneration.clear();
                population.getChromosomeList().forEach(chromosome -> actualGeneration.add((Chromosome) chromosome.clone()));
                List<Chromosome> parents = Tournament.getParents(actualGeneration);
                List<Chromosome> nextGeneration = OnePointCrossover.crossoverChildren(parents);
                newGeneration.addAll(nextGeneration);
            }
            Mutation.mutate(newGeneration);
            population = new Population(newGeneration);
        }

        System.out.println(population.getMax() + ";" + population.getMin() + ";" + population.getAverageValue() + ";" + population.getAverageWithoutZero());
    }
}
