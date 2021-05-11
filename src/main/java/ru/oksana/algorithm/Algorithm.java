package ru.oksana.algorithm;

import ru.oksana.model.Node;
import ru.oksana.model.Order;
import ru.oksana.model.Supplier;

import java.util.*;
import java.util.stream.Collectors;

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
     * Количество муравьев, идущих случайным образом
     */
    private final Long randomAntNumber;

    /**
     * Список вершин
     */
    private final List<Node> nodeList;

    /**
     * Список поставщиков
     */
    private final List<Supplier> suppliers;

    /**
     * Список оптимальных закупок
     */
    private List<Order> minOrderList;

    /**
     * Общая стоимость самой оптимальной закупки
     */
    Double minFullWeight = Double.MAX_VALUE;

    /**
     * Максимальное количество возможных закупок
     */
    private final long maxOrders;

    /**
     * Самая минимальная стоимость заказа на каждой итерации
     */
    private final List<Double> minimalWeightList;

    /**
     * Скорость распространения феромона
     */
    final Double speedPher;

    /**
     * Скорость испарния феромона
     */
    final Double speedPherDecay;

    private Double currentFullOrder;

    public Algorithm(Double fullOrder, Long antNumber, Long randomAntNumber, List<Supplier> suppliers, Double speedPher, Double speedPherDecay) {
        this.fullOrder = fullOrder;
        this.antNumber = antNumber;
        this.randomAntNumber = randomAntNumber;
        this.suppliers = suppliers;
        this.speedPher = speedPher;
        this.speedPherDecay = speedPherDecay;

        this.nodeList = new ArrayList<>();

        this.maxOrders = (long) Math.ceil(fullOrder / suppliers.stream().min(Comparator.comparingInt(Supplier::getCapacityPallets)).get().getCapacityPallets());

        // создание графа
        this.minimalWeightList = new ArrayList<>();
        suppliers.forEach(supplier -> {
            for (long i = 0; i < this.maxOrders; i++) {
                this.nodeList.add(new Node(supplier, i, .0));
                this.minimalWeightList.add(Double.MAX_VALUE);
            }
        });

    }

    public Algorithm(Double fullOrder, List<Supplier> suppliersByProduct) {
        this.fullOrder = fullOrder;
        this.antNumber = 10000L;
        this.randomAntNumber = 5000L;
        this.suppliers = suppliersByProduct;
        this.speedPher = 0.5;
        this.speedPherDecay = -0.2;

        this.nodeList = new ArrayList<>();

        this.maxOrders = (long) Math.ceil(fullOrder / suppliers.stream().min(Comparator.comparingInt(Supplier::getCapacityPallets)).get().getCapacityPallets());

        // создание графа
        this.minimalWeightList = new ArrayList<>();
        suppliers.forEach(supplier -> {
            this.nodeList.add(new Node(supplier, 0L, .0));
            supplier.updateStartQuantity();
        });
        this.minimalWeightList.add(Double.MAX_VALUE);
    }

    /**
     * Рассчет минимальной стоимости закупок
     * @return стоимость
     */
    public Double calculate() {
        long ant = 0;
        while (ant < this.antNumber) {

            // Текущий заказ.
            Double currentOrder = .0;

            // Стоимость текущего заказа.
            Double fullWeight = .0;

            List<Order> orders = new ArrayList<>();

            long currentOrderNumber = 0;

            while (currentOrder < this.fullOrder) {

                if (this.nodeList.size() / this.suppliers.size() <= currentOrderNumber) {
                    long finalCurrentOrderNumber = currentOrderNumber;
                    suppliers.forEach(supplier -> this.nodeList.add(new Node(supplier, finalCurrentOrderNumber, .0)));
                    this.minimalWeightList.add(Double.MAX_VALUE);
                }

                // Список возможных вершин
                List<Node> availableNodes = new ArrayList<>();
                for (Node node : nodeList) {
                    if (node.getNumber() == currentOrderNumber && !node.getSupplier().isEmpty()) {
                        availableNodes.add(node);
                    }
                }

                Node currentNode;

                // Если это муравей, проходящий случайным образом
                if (ant < this.randomAntNumber) {
                    Random random = new Random();
                    int rand = random.nextInt(availableNodes.size());
                    currentNode = availableNodes.get(rand);
                } else { // если обычный муравей
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

                    List<Node> nodesInOrder = orders.stream().map(order -> order.getNode()).collect(Collectors.toList());
                    for (Node node: nodesInOrder) {
                        node.addPheromone(this.speedPher);
                    }
                    for (Node node: this.nodeList) {
                        if (!nodesInOrder.contains(node)) {
                            node.addPheromone(this.speedPherDecay);
                        }
                    }
                }

                currentOrderNumber++;
            }

            if (this.minFullWeight > fullWeight) {
                this.minFullWeight = fullWeight;
                this.minOrderList = orders;
                this.currentFullOrder = currentOrder;
            }

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

    public List<String> getCheapestOrderInfo() {
        List<String> stringList = new ArrayList<>();
        stringList.add(String.format("<strong>Сумма заказа: <em>%s</em></strong>", this.minFullWeight.toString()));

        stringList.add(String.format("<strong>Заказ: <em>%s</em></strong>", this.currentFullOrder.toString()));

        Map<Supplier, Integer> supplierDoubleMap = new HashMap<>();

        for (Order order: this.minOrderList) {
            Supplier currentSupplier = order.getNode().getSupplier();
            if (supplierDoubleMap.containsKey(currentSupplier)) {
                supplierDoubleMap.replace(currentSupplier, supplierDoubleMap.get(currentSupplier) + currentSupplier.getCapacityPallets());
            } else {
                supplierDoubleMap.put(currentSupplier, currentSupplier.getCapacityPallets());
            }
        }

        for (Supplier supplier: supplierDoubleMap.keySet()) {
            stringList.add(String.format("<strong>%s</strong>, %s: <em>%d</em>", supplier.getName(), supplier.getProduct(), supplierDoubleMap.get(supplier)));
        }
        return stringList;
    }

    public Double getFullOrder() {
        return fullOrder;
    }
}
