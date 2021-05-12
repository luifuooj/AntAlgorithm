package ru.oksana.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Поставщик.
 */
@Entity
@Table
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String id;

    /**
     * Имя поставщика.
     */
    @Column
    @NotNull
    private String name;

    /**
     * Продукт.
     */
    @Column
    @NotNull
    private String product;

    /**
     * Количество товара у поставщика
     */
    @Column
    @NotNull
    private Double quantity;

    /**
     * Стартовое количество товара
     */
    @Transient
    private Double startQuantity = 0.;

    /**
     * Цена за единицу товара
     */
    @Column
    @NotNull
    private Double unitPrice;

    /**
     * Вместительность машины
     */
    @Column
    @NotNull
    private Integer capacityOfCar;

    /**
     * Текущая загруженность машины
     */
    @Transient
    private Integer currentLoadOfCar = 0;

    /**
     * Вместительность поддона
     */
    @Column
    @NotNull
    private Integer capacityPallets;

    /**
     * Цена за машину
     */
    @Column
    @NotNull
    private Double carPrice;

    /**
     * Текущее количество машин
     */
    @Transient
    private Integer currentCarCount = 0;

    public Supplier(String name, String product, Double quantity, Double unitPrice, Integer capacityOfCar, Integer capacityPallets, Double carPrice) {
        this.name = name;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.capacityOfCar = capacityOfCar;
        this.capacityPallets = capacityPallets;
        this.carPrice = carPrice;
        this.startQuantity = quantity;
    }

    public Supplier(String id, String name, String product, Double quantity, Double unitPrice, Integer capacityOfCar, Integer capacityPallets, Double carPrice) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.capacityOfCar = capacityOfCar;
        this.capacityPallets = capacityPallets;
        this.carPrice = carPrice;
        this.startQuantity = quantity;
    }

    public Supplier() {
    }

    /**
     * Вычисление веса.
     * @return вес
     */
    public Double getWeight() {
        Double weight = .0;
        // проверка на наличие места в машине или что нужна самая первая машина
        if (currentCarCount == 0 || capacityOfCar.equals(currentLoadOfCar)) {
            weight += carPrice;
            currentCarCount++;
            currentLoadOfCar = 0;
        }
        currentLoadOfCar++;
        weight += unitPrice * capacityPallets;
        quantity -= capacityPallets;
        return weight;
    }

    /**
     * Процедура для сброса к начальному виду.
     */
    public void restore() {
        this.quantity = this.startQuantity;
        this.currentCarCount = 0;
        this.currentLoadOfCar = 0;
    }

    /**
     * Проверка на наличие товара у поставщика.
     * @return false - если пустой
     */
    public Boolean isNotEmpty() {
        return !(getQuantity() < getCapacityPallets());
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public Integer getCapacityPallets() {
        return capacityPallets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.startQuantity = quantity;
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCapacityOfCar() {
        return capacityOfCar;
    }

    public void setCapacityOfCar(Integer capacityOfCar) {
        this.capacityOfCar = capacityOfCar;
    }

    public void setCapacityPallets(Integer capacityPallets) {
        this.capacityPallets = capacityPallets;
    }

    public Double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(Double carPrice) {
        this.carPrice = carPrice;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", capacityOfCar=" + capacityOfCar +
                ", capacityPallets=" + capacityPallets +
                ", carPrice=" + carPrice +
                '}';
    }

    public void updateStartQuantity() {
        this.startQuantity = quantity;
    }
}
