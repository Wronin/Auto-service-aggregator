package model;

public class Car {
    private String VINNumber;
    private String regNumber;
    private String brand;
    private String model;

    public Car(String VINNumber, String regNumber, String brand, String model) {
        this.VINNumber = VINNumber;
        this.regNumber = regNumber;
        this.brand = brand;
        this.model = model;
    }

    public Car() {
    }

    public String getVINNumber() {
        return VINNumber;
    }

    public void setVINNumber(String VINNumber) {
        this.VINNumber = VINNumber;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
