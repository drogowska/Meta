package meta;

import java.util.ArrayList;
import java.util.List;

public class Chromosome implements Cloneable {

    private final List<Gene> geneList = new ArrayList<>();
    private int weight;
    private int value;

    public Chromosome(List<Gene> geneList) {
        this.getGeneList().addAll(new ArrayList<>(geneList));
        generateWeight();
        generateValue();
    }

    public Chromosome() {
        Main.geneList.forEach(gene -> {
            boolean isActive = Main.random.nextDouble() >= 0.5;
            this.geneList.add(gene.clone(isActive));
        });
        generateWeight();
        generateValue();
    }

    private void generateWeight() {
        for(Gene gene : geneList) {
            if(gene.isActive()) {
                weight = weight + gene.getWeight();
            }
        }
    }

    private void generateValue() {
        if(weight > Main.maxCapacity) {
            value = 0;
        } else {
            for(Gene gene : geneList) {
                if(gene.isActive()) {
                    value = value + gene.getValue();
                }
            }
        }
    }

    public List<Gene> getGeneList() {
        return geneList;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    protected Object clone() {
        Chromosome result = new Chromosome();
        result.weight = this.weight;
        result.value = this.value;
        result.geneList.clear();
        this.geneList.forEach(gene -> {
            result.geneList.add((Gene) gene.clone());
        });
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        for (Gene gene : geneList) {
            result = result + gene.toString();
        }
        return result;
    }
}
