package meta;

import java.util.ArrayList;

public class Gene {

    private boolean isActive = true;
    private int number;
    private int weight;
    private int value;

    private Gene() {
    }

    public Gene(int number, int weight, int value) {
        this.number = number;
        this.weight = weight;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Gene clone(boolean isActive) {
        Gene gene = new Gene(this.number, this.weight, this.value);
        gene.setActive(isActive);
        return gene;
    }

    @Override
    protected Object clone() {
        Gene gene = new Gene(this.number, this.weight, this.value);
        gene.setActive(this.isActive);
        return gene;
    }

    @Override
    public String toString() {
        return isActive ? "1" : "0";
    }
}
