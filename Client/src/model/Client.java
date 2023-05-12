package model;

import java.net.Socket;

public final class Client {
    private static String login;
    private static String password;
    private static Socket socket;

    public Client(String login, String password) {
        Client.login = login;
        Client.password = password;
    }


    public Client() {
    }

    public Client(String login, String password, Socket socket) {
        Client.login = login;
        Client.password = password;
        Client.socket = socket;
    }

    public static String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        Client.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Client.password = password;
    }

    public static Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        Client.socket = socket;
    }


}
