package model;

import java.util.ArrayList;
import java.util.Arrays;

public class CarService {
    private int id;
    private String name;
    private String specification;
    private ArrayList<Service> services;

    public CarService(int id, String name, String specification, ArrayList<Service> services) {
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.services = services;
    }

    public CarService(String name, String specification, ArrayList<Service> services) {
        this.name = name;
        this.specification = specification;
        this.services = services;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
