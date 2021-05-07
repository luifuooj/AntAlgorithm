package ru.oksana;

import java.util.Objects;

/**
 * Вершина
 */
public class Node {

    /**
     * Поставщик
     */
    private final Supplier supplier;

    /**
     * Порядковый номер итерации
     */
    private final Long number;

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

    public Long getNumber() {
        return number;
    }

    public Double getPheromone() {
        return pheromone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return supplier.equals(node.supplier) &&
                number.equals(node.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, number);
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
