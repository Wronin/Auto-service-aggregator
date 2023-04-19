package model;

public class ServiceAdmin {
    private String login;
    private String password;

    public ServiceAdmin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public ServiceAdmin() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
