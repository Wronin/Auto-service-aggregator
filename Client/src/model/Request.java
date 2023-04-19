package model;

public class Request {
    private Client client;
    private String description;
    private Car car;
    private Status status;
    private int id;

    public Request(Client client, String description, Car car, Status status) {
        this.client = client;
        this.description = description;
        this.car = car;
        this.status = status;
    }

    public Request(int id, String description, Car car, Status status) {
        this.id = id;
        this.description = description;
        this.car = car;
        this.status = status;
    }

    public Request() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
