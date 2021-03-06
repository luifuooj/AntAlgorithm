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
     * Скорость распространения феромона
     */
    final Double speedPher;

    /**
     * Скорость испарния феромона
     */
    final Double speedPherDecay;

    private Double currentFullOrder;

    public Algorithm(Double fullOrder, List<Supplier> suppliersByProduct) {
        this.fullOrder = fullOrder;
        this.antNumber = 10000L;
        this.randomAntNumber = 5000L;
        this.suppliers = suppliersByProduct;
        this.speedPher = 0.5;
        this.speedPherDecay = -0.2;

        this.nodeList = new ArrayList<>();

        // создание графа
        suppliers.forEach(supplier -> {
            this.nodeList.add(new Node(supplier, 0, .0));
            supplier.updateStartQuantity();
        });
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
            // Список заказов
            List<Order> orders = new ArrayList<>();
            // Номер текущего заказа
            int currentOrderNumber = 0;

            while (currentOrder < this.fullOrder) {

                if (this.nodeList.size() / this.suppliers.size() <= currentOrderNumber) {
                    int finalCurrentOrderNumber = currentOrderNumber;
                    suppliers.forEach(supplier -> this.nodeList.add(new Node(supplier, finalCurrentOrderNumber, .0)));
                }

                // Список возможных вершин
                int finalCurrentOrderNumber1 = currentOrderNumber;
                List<Node> availableNodes = this.nodeList.stream()
                        .filter(node -> node.getNumber() == finalCurrentOrderNumber1 && node.getSupplier().isNotEmpty())
                        .collect(Collectors.toList());

                Node currentNode;

                if (ant < this.suppliers.size()) {
                    if (availableNodes.size() > ant) {
                        currentNode = availableNodes.get((int) ant);
                    } else {
                        currentNode = availableNodes.get(0);
                    }
                } else if (ant < this.randomAntNumber) { // Если это муравей, проходящий случайным образом
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

                currentOrderNumber++;
            }

            if (this.minFullWeight > fullWeight) {
                this.minFullWeight = fullWeight;
                this.minOrderList = orders;
                this.currentFullOrder = currentOrder;
            }

            List<Node> nodesInMinOrderList = this.minOrderList.stream().map(Order::getNode).collect(Collectors.toList());
            if (!nodesInMinOrderList.isEmpty()) {
                for (Node node: nodesInMinOrderList) {
                    node.addPheromone(this.speedPher);
                }
                for (Node node: this.nodeList) {
                    if (!nodesInMinOrderList.contains(node)) {
                        node.addPheromone(this.speedPherDecay);
                    }
                }
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
        stringList.add(String.format("<strong>Сумма заказа: %s ₽</strong>", this.minFullWeight.toString()));

        stringList.add(String.format("<strong>Заказ: %s</strong>", this.currentFullOrder.toString()));

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
            stringList.add(String.format("<strong>%s</strong>, %s: %d", supplier.getName(), supplier.getProduct(), supplierDoubleMap.get(supplier)));
        }
        return stringList;
    }
}
