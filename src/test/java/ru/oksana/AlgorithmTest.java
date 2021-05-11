package ru.oksana;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.oksana.algorithm.Algorithm;
import ru.oksana.model.Supplier;

import java.util.ArrayList;
import java.util.List;

class AlgorithmTest {

    List<Supplier> supplierList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        addSupplier("Supplier #1", "Огурцы, кг", 100., 50., 20, 5, 6000.);
        addSupplier("Supplier #2", "Огурцы, кг", 50., 40., 20, 5, 4000.);
        addSupplier("Supplier #3", "Огурцы, кг", 80., 45., 25, 4, 5000.);
        addSupplier("Supplier #4", "Огурцы, кг", 1000., 55., 20, 5, 2500.);
        /*addSupplier("Supplier #5", "Огурцы, кг", 200., 45., 20, 6, 4500.);
        addSupplier("Supplier #5", "Огурцы, к", 300., 55., 40, 5, 8000.);
        addSupplier("Supplier #7", "Огурцы, к", 700., 65., 60, 6, 6000.);
        addSupplier("Supplier #8", "Огурцы, к", 235., 25., 15, 5, 6500.);
        addSupplier("Supplier #9", "Огурцы, к", 1400., 45., 30, 3, 4000.);
        addSupplier("Supplier #10", "Огурцы, к", 160., 45., 35, 4, 4600.);
        addSupplier("Supplier #11", "Огурцы, к", 1200., 45., 25, 5, 6700.);
        addSupplier("Supplier #12", "Огурцы, к", 225., 45., 10, 7, 5100.);
        addSupplier("Supplier #13", "Огурцы, к", 1100., 45., 20, 6, 5200.);
        addSupplier("Supplier #14", "Огурцы, к", 850., 45., 25, 8, 5300.);*/
    }

    private void addSupplier(String name, String product, Double quantity, Double unitPrice, Integer capacityOfCar, Integer capacityPallets, Double carPrice) {
        supplierList.add(new Supplier(name, product, quantity, unitPrice, capacityOfCar, capacityPallets, carPrice));
    }

    @Test
    void alg() {
        Algorithm algorithm = new Algorithm(1000., 10000L, 5000L, supplierList, 0.5, -0.2);
        algorithm.calculate();
        System.out.println(algorithm.getCheapestOrderInfo());
    }
}