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
    private Integer number;
    /**
     * Феромон.
     */
    private Double pheromone;

    public Node(Supplier supplier, Integer number, Double pheromone) {
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getPheromone() {
        return pheromone;
    }

    public void setPheromone(Double pheromone) {
        this.pheromone = pheromone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return supplier.equals(node.supplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier);
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
