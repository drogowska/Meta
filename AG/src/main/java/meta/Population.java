package meta;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private final List<Chromosome> chromosomeList = new ArrayList<>();

    public Population() {
        generateRandomChromosomes();
    }

    public Population(List<Chromosome> chromosomeList) {
        this.chromosomeList.addAll(new ArrayList<>(chromosomeList));
    }

    private void generateRandomChromosomes() {
        int i = 0;
        while(i < Main.populationSize) {
            Chromosome chromosome = new Chromosome();
            if(chromosome.getValue() != 0) {
                chromosomeList.add(chromosome);
                i++;
            }
        }
    }

    public List<Chromosome> getChromosomeList() {
        return chromosomeList;
    }

    @Override
    public String toString() {
        int max = getMax();
        String result = "Population values: ";
        for (Chromosome chromosome : chromosomeList) {
            result = result +
                    (chromosome.getValue() == max ? "(" + chromosome.getValue() + ")" : chromosome.getValue())
                    + " ";
        }
        return result;
    }

    public int getMax() {
        int max = 0;
        for (Chromosome chromosome : chromosomeList) {
            if(chromosome.getValue() > max) {
                max = chromosome.getValue();
            }
        }
        return max;
    }

    public int getMin() {
        int min = chromosomeList.get(0).getValue();
        for (Chromosome chromosome : chromosomeList) {
            if(chromosome.getValue() < min) {
                min = chromosome.getValue();
            }
        }
        return min;
    }

    public int getAverageValue() {
        int avg = 0;
        for (Chromosome chromosome : chromosomeList) {
            avg = avg + chromosome.getValue();
        }
        return avg/Main.populationSize;
    }

    public int getAverageWithoutZero() {
        int avg = 0;
        int count = 0;
        for (Chromosome chromosome : chromosomeList) {
            if(chromosome.getValue() != 0) {
                avg = avg + chromosome.getValue();
                count++;
            }
        }
        return count != 0 ? avg/count : 0;
    }
}
