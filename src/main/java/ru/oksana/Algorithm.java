package ru.oksana;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
    private final Long antNumber;
    /**
     * Список вершин
     */
    private final List<Node> nodeList;

    private final Long randomAntNumber;

    private final List<Supplier> suppliers;

    private final List<List<Order>> allOrdersLists = new ArrayList<>();

    private List<Order> minOrderList;

    Double minFullWeight = Double.MAX_VALUE;

    private long minOrderSize;

    final Double speedPher;
    final Double speedPherDecay;

    private List<Double> minimalWeightList;

    public Algorithm(Double fullOrder, Long antNumber, Long randomAntNumber, List<Supplier> suppliers, Double speedPher, Double speedPherDecay) {
        this.fullOrder = fullOrder;
        this.antNumber = antNumber;
        this.randomAntNumber = randomAntNumber;
        this.suppliers = suppliers;
        this.speedPher = speedPher;
        this.speedPherDecay = speedPherDecay;

        this.nodeList = new ArrayList<>();

        minOrderSize = (long) Math.ceil(fullOrder / suppliers.stream().min(Comparator.comparingInt(Supplier::getCapacityPallets)).get().getCapacityPallets());

        System.out.println("max order size:" + minOrderSize);
        minimalWeightList = new ArrayList<>();
        suppliers.forEach(supplier -> {
            for (long i = 0; i < minOrderSize; i++) {
                this.nodeList.add(new Node(supplier, i, .0));
                minimalWeightList.add(.0);
            }
        });

    }

    public Double alg() {
        long ant = 0;
        while (ant < this.antNumber) {

            Double currentOrder = .0;

            //Стоимость текущего заказа.
            Double fullWeight = .0;

            List<Order> orders = new ArrayList<>();

            long currentOrderNumber = 0;

            while (currentOrder < this.fullOrder) {
                Node currentNode;
                List<Node> availableNodes = new ArrayList<>();
                for (Node node : nodeList) {
                    if (node.getNumber() == currentOrderNumber) {
                        availableNodes.add(node);
                    }
                }
                if (ant < this.randomAntNumber) {
                    Random random = new Random();
                    int rand = random.nextInt(availableNodes.size());
                    currentNode = availableNodes.get(rand);
                } else {
                    currentNode = availableNodes.stream().max(Comparator.comparingDouble(Node::getPheromone)).get();
                }

                Supplier supplier = currentNode.getSupplier();
                Double weight = supplier.getWeight();
                orders.add(new Order(currentNode, weight));
                currentOrder += supplier.getCapacityPallets();
                fullWeight += weight;
                if (this.minimalWeightList.get((int) currentOrderNumber) > fullWeight) {
                    this.minimalWeightList.remove(this.minimalWeightList.get((int) currentOrderNumber));
                    this.minimalWeightList.add((int) currentOrderNumber, fullWeight);

                    for (Order order: orders) {
                        order.getNode().addPheromone(this.speedPher);
                    }
                }
                //updatePheromones(currentOrderNumber);
                currentOrderNumber++;
            }

            if (this.minFullWeight > fullWeight) {
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

    private void updatePheromones(long orderNumber) {
        if (this.allOrdersLists.isEmpty()) {
            return;
        }

        List<Order> cheapestOrderList = this.allOrdersLists.get(0);
        Double cheapestWeight = getOrderListWeight(orderNumber, cheapestOrderList);

        for (List<Order> orderList : this.allOrdersLists) {
            Double currentWeight = getOrderListWeight(orderNumber, orderList);
            if (currentWeight < cheapestWeight) {
                cheapestOrderList = orderList;
                cheapestWeight = currentWeight;
            }
        }

        for (Order order: cheapestOrderList) {
            if (order.getNode().getNumber() <= orderNumber) {
                order.getNode().addPheromone(this.speedPher);
            }
        }

        for (List<Order> orderList : this.allOrdersLists) {
            if (!orderList.equals(cheapestOrderList)) {
                for (Order order: orderList) {
                    if (order.getNode().getNumber() <= orderNumber) {
                        order.getNode().addPheromone(this.speedPherDecay);
                    }
                }
            }
        }
    }

    private Double getOrderListWeight(long orderNumber, List<Order> orderList) {
        Double currentWeight = .0;
        for (Order order: orderList) {
            if (order.getNode().getNumber() <= orderNumber) {
                currentWeight += order.getWeight();
            }
        }
        return currentWeight;
    }

    public List<Order> getMinOrderList() {
        return this.minOrderList;
    }

    public Double getFullOrder() {
        return fullOrder;
    }
}
