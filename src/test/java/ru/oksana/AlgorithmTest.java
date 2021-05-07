package ru.oksana;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AlgorithmTest {

    List<Supplier> supplierList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        addSupplier("sup1", "prod1", 100., 50., 20, 5, 6000.);
        addSupplier("sup2", "prod1", 50., 40., 20, 5, 4000.);
        addSupplier("sup3", "prod1", 80., 45., 25, 4, 5000.);
        addSupplier("sup4", "prod1", 1000., 55., 30, 10, 2500.);
        addSupplier("sup5", "prod1", 200., 45., 20, 6, 4500.);
        addSupplier("sup6", "prod1", 300., 45., 40, 5, 8000.);
        addSupplier("sup7", "prod1", 70., 45., 60, 6, 6000.);
        addSupplier("sup8", "prod1", 235., 45., 15, 4, 6500.);
        addSupplier("sup9", "prod1", 140., 45., 30, 3, 4000.);
        addSupplier("sup10", "prod1", 160., 45., 35, 4, 4600.);
        addSupplier("sup11", "prod1", 120., 45., 25, 5, 6700.);
        addSupplier("sup12", "prod1", 225., 45., 10, 7, 5100.);
        addSupplier("sup13", "prod1", 110., 45., 20, 6, 5200.);
        addSupplier("sup14", "prod1", 85., 45., 25, 8, 5300.);

    }

    private void addSupplier(String name, String product, Double quantity, Double unitPrice, Integer capacityOfCar, Integer capacityPallets, Double carPrice) {
        supplierList.add(new Supplier(name, product, quantity, unitPrice, capacityOfCar, capacityPallets, carPrice));
    }

    @Test
    void alg() {
        Algorithm algorithm = new Algorithm(1000., 10000L, 9000L, supplierList, 0.5, -0.2);
        System.out.println(algorithm.alg());
        System.out.println(algorithm.getMinOrderList());
    }
}