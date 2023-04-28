package controller;

import model.*;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import service.ClientService;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientController {
    private ClientService clientService = new ClientService();

    public ClientController() {
    }

    public void addCar(String login, String password, String brand, String model, String VINNumber, String regNumber) {
        Client client  = new Client(login, password);
        clientService.addCar(client, brand, model, VINNumber, regNumber);
    }

    public void getCar(Socket socket, String login, String password, String regNumber) {
        Client client = new Client(login, password);
        Car car = clientService.getCar(client, regNumber);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("brand", car.getBrand());
        jsonObject.put("model", car.getModel());
        jsonObject.put("VINNumber", car.getVINNumber());
        jsonObject.put("regNumber", car.getRegNumber());

        File file = new File(System.getProperty("user.dir"), "file.json");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCarList(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<Car> cars = clientService.getCarList(client);

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        for (var item : cars) {
            array.put(item.getRegNumber());
        }
        jsonObject.put("regNumber", array);

        File file = new File(System.getProperty("user.dir"), "file.json");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCarNumbers(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<Car> cars = clientService.getCarList(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            printWriter.println(cars.size());
            printWriter.flush();

            for (Car car : cars) {
                printWriter.println(car.getRegNumber());
                printWriter.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRequest(String login, String password, String description, String brand, String model, String VINNumber, String regNumber) {
        Client client  = new Client(login, password);
        Car car = new Car(VINNumber, regNumber, brand, model);
        clientService.addRequest(client, description, car, Status.SEARCH);
    }

    public void getAllClientRequest(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<RequestForClient> requests = clientService.getAllClientRequest(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            printWriter.println(requests.size());
            printWriter.flush();

            for (var request : requests) {
                printWriter.println(request.getId());
                printWriter.flush();

                printWriter.println(request.getRegNumber());
                printWriter.flush();

                printWriter.println(request.getDescription());
                printWriter.flush();

                printWriter.println(request.getName());
                printWriter.flush();

                printWriter.println(request.getStatus());
                printWriter.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getAnswerAutoService(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<AnswerAutoService> answers = clientService.getAnswerAutoService(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            printWriter.println(answers.size());
            printWriter.flush();

            for (var request : answers) {
                printWriter.println(request.getId());
                printWriter.flush();

                printWriter.println(request.getRegNumber());
                printWriter.flush();

                printWriter.println(request.getName());
                printWriter.flush();

                printWriter.println(request.getStatus());
                printWriter.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCarServiceByName(Socket socket, String name) {
        CarService carService = clientService.getCarServiceByName(name);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            printWriter.println(carService.getName());
            printWriter.flush();

            printWriter.println(carService.getSpecification());
            printWriter.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptRequestForClient(String login, String password, int idAnswer) {
        Client client = new Client(login, password);
        clientService.acceptRequestForClient(client, idAnswer);
    }

    public void sendClientMassage(String login, String password, int idChat, String message) {
        Client client = new Client(login, password);
        clientService.sendClientMassage(client, idChat, message);
    }
}
