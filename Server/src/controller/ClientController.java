package controller;

import model.*;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import service.ClientService;

import java.io.*;
import java.lang.reflect.Array;
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

    public void deleteCar(String login, String password, String brand, String model, String VINNumber, String regNumber) {
        Client client = new Client(login, password);
        Car car = new Car(VINNumber, regNumber, brand, model);
        clientService.deleteCar(client, car);
    }

    public void updateCarInformation(String login, String password, String brand, String model, String VINNumber, String regNumber, String newBrand, String newModel, String newVINNumber, String newRegNumber) {
        Client client = new Client(login, password);
        Car car = new Car(VINNumber, regNumber, brand, model);
        Car newCar = new Car(newVINNumber, newRegNumber, newBrand, newModel);
        clientService.updateCarInformation(client, car, newCar);
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

    public void addRequestWithServices(String login, String password, String description, String brand, String model, String vin, String regNumber, ArrayList<Service> services) {
        Client client = new Client(login, password);
        Car car = new Car(vin, regNumber, brand, model);
        Request request = new Request(client, description, car, services, Status.SEARCH);
        clientService.addRequestWithServices(request);
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

    public void getCarServiceById(Socket socket, int id) {
        CarService carService = clientService.getCarServiceById(id);

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

    //todo make send a date to client
    public void getChatsForClient(Socket socket ,String login, String password) {
        Client client = new Client(login, password);
        ArrayList<Chat> chats = clientService.getChatsForClient(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            printWriter.println(chats.size());
            printWriter.flush();

            for (Chat chat : chats) {
                printWriter.println(chat.getId());
                printWriter.println(chat.getCar().getVINNumber());
                printWriter.println(chat.getCar().getRegNumber());
                printWriter.println(chat.getCar().getBrand());
                printWriter.println(chat.getCar().getBrand());
                printWriter.println(chat.getCarServiceName());
                printWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getCurrentChatForClient(String login, String password, int idChat) {
        Client client = new Client(login, password);
        Chat chat = clientService.getCurrentChatForClient(client, idChat);
    }

}
