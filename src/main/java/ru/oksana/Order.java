package ru.oksana;

/**
 * Заказ
 */
public class Order {
    /**
     * Вершина
     */
    private Node node;
    /**
     * Вес
     */
    private Double weight;

    public Order(Node node, Double weight) {
        this.node = node;
        this.weight = weight;
    }

    public Node getNode() {
        return node;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Заказ { " +
                "\n\tВершина = " + node +
                ",\n\tстоимость = " + weight +
                '}';
    }
}
