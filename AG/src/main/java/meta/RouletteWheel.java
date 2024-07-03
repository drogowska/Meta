package meta;

import java.util.ArrayList;
import java.util.List;

public abstract class RouletteWheel {

    public static List<Chromosome> getParents(List<Chromosome> chromosomeList) {
        long sumOfValues = 0;
        for(Chromosome chromosome : chromosomeList) {
            sumOfValues = sumOfValues + chromosome.getValue();
        }

        List<Double> probabilities = new ArrayList<>();
        probabilities.add(0d);
        for(int i=1; i<chromosomeList.size(); i++) {
            probabilities.add(probabilities.get(i-1) + (((double)chromosomeList.get(i-1).getValue()) / sumOfValues));
        }
        probabilities.add(1d);

        int firstParent = firstSpin(probabilities);
        int secondParent = secondSpin(probabilities, firstParent);
        return List.of(chromosomeList.get(firstParent), chromosomeList.get(secondParent));
    }

    private static int firstSpin(List<Double> probabilities) {
        double pick = Main.random.nextDouble();
        for(int i=1; i<probabilities.size(); i++) {
            if(probabilities.get(i-1) <= pick && pick < probabilities.get(i)) {
                return i-1;
            }
        }
        return -1;
    }

    private static int secondSpin(List<Double> probabilities, int firstParent) {
        double pick = Main.random.nextDouble();
        for(int i=1; i<probabilities.size(); i++) {
            if(probabilities.get(i-1) <= pick && pick < probabilities.get(i)) {
                if(i - 1 == firstParent) {
                    return secondSpin(probabilities, firstParent);
                } else {
                    return i - 1;
                }
            }
        }
        return -1;
    }
}
