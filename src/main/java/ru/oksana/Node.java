package ru.oksana;

import java.util.Objects;

/**
 * Вершина
 */
public class Node {
    /**
     * Поставщик
     */
    private Supplier supplier;
    /**
     * Порядковый номер итерации
     */
    private Long number;
    /**
     * Феромон.
     */
    private Double pheromone;

    public Node(Supplier supplier, Long number, Double pheromone) {
        this.supplier = supplier;
        this.number = number;
        this.pheromone = pheromone;
    }

    public void addPheromone(Double val) {
        this.pheromone += val;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Double getPheromone() {
        return pheromone;
    }

    public void setPheromone(Double pheromone) {
        this.pheromone = pheromone;
    }

    @Override
    public String toString() {
        return "Вершина: { " +
                "\n\t\t Поставщик: " + supplier +
                ",\n\t\t Номер заказа = " + number +
                ",\n\t\t Феромон = " + pheromone +
                '}';
    }
}
