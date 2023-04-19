package controller;

import model.Car;
import model.Request;
import model.Status;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class AdminController {
    public ArrayList<Request> getAllAdminRequest(Socket socket, String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getAllAdminRequest");
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        ArrayList<Request> requestForAutoServices = new ArrayList<>();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String content = bufferedReader.readLine();

            for (int i = 0; i < Integer.parseInt(content); i++) {
                requestForAutoServices.add(
                        new Request(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine(),
                                new Car(
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine()
                                ),
                                Status.valueOf(bufferedReader.readLine())
                        ));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return requestForAutoServices;
    }

}
