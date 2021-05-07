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
     * Вычисление веса
     * @return вес
     */
    public Double getWeight() {
        Double weight = .0;
        if (quantity < capacityPallets) {
            return Double.MAX_VALUE;
        }
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

    public void restore() {
        this.quantity = this.startQuantity;
        this.currentCarCount = 0;
        this.currentLoadOfCar = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
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

    public Integer getCurrentLoadOfCar() {
        return currentLoadOfCar;
    }

    public void setCurrentLoadOfCar(Integer currentLoadOfCar) {
        this.currentLoadOfCar = currentLoadOfCar;
    }

    public Integer getCapacityPallets() {
        return capacityPallets;
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

    public Integer getCurrentCarCount() {
        return currentCarCount;
    }

    public void setCurrentCarCount(Integer currentCarCount) {
        this.currentCarCount = currentCarCount;
    }

    @Override
    public String toString() {
        return "Поставщик: {" +
                "\n\t\t\tИмя = '" + name + '\'' +
                ",\n\t\t\t Товар='" + product + '\'' +
                '}';
    }
}
