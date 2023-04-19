package model;

public class AnswerAutoService {
    private int id;
    private String regNumber;
    private String name;
    private String status;

    public AnswerAutoService(int id, String regNumber, String name, String status) {
        this.id = id;
        this.regNumber = regNumber;
        this.name = name;
        this.status = status;
    }

    public AnswerAutoService() {
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
