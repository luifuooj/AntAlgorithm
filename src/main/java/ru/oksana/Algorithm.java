package ru.oksana;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.*;

/**
 * Алгоритм
 */
public class Algorithm {

    /**
     * Цель заказа
     */
    private final Double fullOrder;
    /**
     * Общее число муравьев
     */
    private final Integer antNumber;
    /**
     * Список вершин
     */
    private final List<Node> nodeList;

    private final Integer randomAntNumber;

    private final List<Supplier> suppliers;

    private final List<List<Order>> allOrdersLists = new ArrayList<>();

    private List<Order> minOrderList;

    Double minFullWeight = Double.MAX_VALUE;

    private int minOrderSize;

    final Double speedPher;
    final Double speedPherDecay;

    public Algorithm(Double fullOrder, Integer antNumber, Integer randomAntNumber, List<Supplier> suppliers, Double speedPher, Double speedPherDecay) {
        this.fullOrder = fullOrder;
        this.antNumber = antNumber;
        this.randomAntNumber = randomAntNumber;
        this.suppliers = suppliers;
        this.speedPher = speedPher;
        this.speedPherDecay = speedPherDecay;

        this.nodeList = new ArrayList<>();

        minOrderSize = (int) Math.ceil(fullOrder / suppliers.stream().min(Comparator.comparingInt(Supplier::getCapacityPallets)).get().getCapacityPallets());

        suppliers.forEach(supplier -> {
            for (int i = 0; i < minOrderSize; i++) {
                this.nodeList.add(new Node(supplier, i, .0));
            }
        });

    }

    public Double alg() {
        int ant = 0;
        while (ant < this.antNumber) {

            Double currentOrder = .0;

            //Стоимость текущего заказа.
            Double fullWeight = .0;

            List<Order> orders = new ArrayList<>();

            int currentOrderNumber = 0;

            while (currentOrder < this.fullOrder) {
                Node currentNode;
                List<Node> availableNodes = new ArrayList<>();
                if (ant < this.randomAntNumber) {
                    for (Node node : nodeList) {
                        if (node.getNumber() == (currentOrderNumber)) {
                            availableNodes.add(node);
                        }
                    }
                    currentNode = availableNodes.stream().findAny().get();
                } else {
                    for (Node node : nodeList) {
                        if (node.getNumber() == (currentOrderNumber)) {
                            availableNodes.add(node);
                        }
                    }
                    currentNode = availableNodes.stream().max(Comparator.comparingDouble(Node::getPheromone)).get();
                }

                Supplier supplier = currentNode.getSupplier();
                Double weight = supplier.getWeight();
                orders.add(new Order(currentNode, weight));
                currentOrder += supplier.getCapacityPallets();
                fullWeight += weight;
                currentOrderNumber++;
            }

            if (this.minFullWeight > fullWeight) {
                for (Order eachOrder: orders) {
                    eachOrder.getNode().addPheromone(this.speedPher);

                    for (Node node: nodeList) {
                        if (node.getNumber().equals(eachOrder.getNode().getNumber())
                                && !(node.getSupplier().getName().equals(eachOrder.getNode().getSupplier().getName()))) {
                            node.addPheromone(this.speedPherDecay);
                        }
                    }
                }
                this.minFullWeight = fullWeight;
                this.minOrderList = orders;
            }

            this.allOrdersLists.add(orders);

            for (Supplier s: suppliers) {
                s.restore();
            }

            ant++;
        }

        return this.minFullWeight;
    }

    public List<Order> getMinOrderList() {
        return this.minOrderList;
    }

    public Double getFullOrder() {
        return fullOrder;
    }
}
