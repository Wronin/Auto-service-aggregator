package model;

public class RequestForClient {
    private int id;
    private String regNumber;
    private String description;
    private String name;
    private String status;

    public RequestForClient(String regNumber, String description, String status) {
        this.regNumber = regNumber;
        this.description = description;
        this.status = status;
    }

    public RequestForClient(int id, String regNumber, String description, String name, String status) {
        this.id = id;
        this.regNumber = regNumber;
        this.description = description;
        this.name = name;
        this.status = status;
    }

    public RequestForClient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
