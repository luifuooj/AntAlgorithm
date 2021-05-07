package ru.oksana;

/**
 * Заказ
 */
public class Order {

    /**
     * Вершина
     */
    private final Node node;

    /**
     * Вес
     */
    private final Double weight;

    public Order(Node node, Double weight) {
        this.node = node;
        this.weight = weight;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "Заказ { " +
                "\n\tВершина = " + node +
                ",\n\tстоимость = " + weight +
                '}';
    }
}
