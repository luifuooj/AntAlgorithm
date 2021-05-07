package ru.oksana;

/**
 * Поставщик.
 */
public class Supplier {

    /**
     * Имя поставщика.
     */
    private String name;

    /**
     * Продукт.
     */
    private String product;

    /**
     * Количество товара у поставщика
     */
    private Double quantity;

    /**
     * Стартовое количество товара
     */
    private Double startQuantity;

    /**
     * Цена за единицу товара
     */
    private Double unitPrice;

    /**
     * Вместительность машины
     */
    private Integer capacityOfCar;

    /**
     * Текущая загруженность машины
     */
    private Integer currentLoadOfCar = 0;

    /**
     * Вместительность поддона
     */
    private Integer capacityPallets;

    /**
     * Цена за машину
     */
    private Double carPrice;

    /**
     * Текущее количество машин
     */
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
     * @return true - если пустой
     */
    public Boolean isEmpty() {
        return this.quantity < this.capacityPallets;
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

    @Override
    public String toString() {
        return "Поставщик: {" +
                "\n\t\t\tИмя = '" + name + '\'' +
                ",\n\t\t\t Товар='" + product + '\'' +
                '}';
    }
}
