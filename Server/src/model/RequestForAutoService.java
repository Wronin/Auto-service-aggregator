package model;

public class RequestForAutoService {
    private String regNumber;
    private String description;
    private String status;

    public RequestForAutoService(String regNumber, String description, String status) {
        this.regNumber = regNumber;
        this.description = description;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
