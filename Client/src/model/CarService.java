package model;

import java.util.ArrayList;

public class CarService {
    private String name;
    private String specification;
    private ArrayList<Service> services;

    public CarService(String name, String specification, ArrayList<Service> services) {
        this.name = name;
        this.specification = specification;
        this.services = services;
    }

    public CarService(String name, String specification) {
        this.name = name;
        this.specification = specification;
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
}
