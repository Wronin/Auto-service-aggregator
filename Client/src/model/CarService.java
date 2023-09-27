package model;

import java.util.ArrayList;

public class CarService {
    private int id;
    private String name;
    private String specification;
    private ArrayList<Service> services;
    private ArrayList<String> brands;

    public CarService(int id, String name, String specification, ArrayList<Service> services, ArrayList<String> brands) {
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.services = services;
        this.brands = brands;
    }

    public CarService(String name, String specification, ArrayList<Service> services) {
        this.name = name;
        this.specification = specification;
        this.services = services;
    }

    public CarService(int id, String name, String specification) {
        this.id = id;
        this.name = name;
        this.specification = specification;
    }

    public CarService(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public CarService(String name, String specification) {
        this.name = name;
        this.specification = specification;
    }

    public CarService() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public int getCurrentId(String line, CarService carService) {
        if (carService.getName().equals(line)) {
            return carService.id;
        }
        return 0;
    }

    public ArrayList<String> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<String> brands) {
        this.brands = brands;
    }

    public String arrayBrandToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : brands) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    public String arrayServiceToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Service service : services) {
            stringBuilder.append(service.getName());
        }
        return stringBuilder.toString();
    }
}
