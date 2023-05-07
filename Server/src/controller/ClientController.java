package controller;

import model.*;
import service.ClientService;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientController {
    private final ClientService clientService = new ClientService();

    public ClientController() {
    }

    public void addCar(String login, String password, String brand, String model, String VINNumber, String regNumber) {
        Client client = new Client(login, password);
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

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(car.getBrand());
            printWriter.println(car.getModel());
            printWriter.println(car.getVINNumber());
            printWriter.println(car.getRegNumber());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCarList(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<Car> cars = clientService.getCarList(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(cars.size());
            for (Car car : cars) {
                printWriter.println(car.getVINNumber());
                printWriter.println(car.getRegNumber());
                printWriter.println(car.getBrand());
                printWriter.println(car.getModel());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCarNumbers(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<Car> cars = clientService.getCarList(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(cars.size());
            for (Car car : cars) {
                printWriter.println(car.getRegNumber());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRequest(String login, String password, String description, String brand, String model, String VINNumber, String regNumber) {
        Client client = new Client(login, password);
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
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(requests.size());

            for (var request : requests) {
                printWriter.println(request.getId());
                printWriter.println(request.getRegNumber());
                printWriter.println(request.getDescription());
                printWriter.println(request.getName());
                printWriter.println(request.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getAnswerAutoService(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<AnswerAutoService> answers = clientService.getAnswerAutoService(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(answers.size());
            for (var request : answers) {
                printWriter.println(request.getId());
                printWriter.println(request.getRegNumber());
                printWriter.println(request.getName());
                printWriter.println(request.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCarServiceById(Socket socket, int id) {
        CarService carService = clientService.getCarServiceById(id);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(carService.getName());
            printWriter.println(carService.getSpecification());

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

    public void getChatsForClient(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<Chat> chats = clientService.getChatsForClient(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(chats.size());

            for (Chat chat : chats) {
                printWriter.println(chat.getId());
                printWriter.println(chat.getCar().getVINNumber());
                printWriter.println(chat.getCar().getRegNumber());
                printWriter.println(chat.getCar().getBrand());
                printWriter.println(chat.getCar().getBrand());
                printWriter.println(chat.getCarServiceName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getCurrentChatForClient(Socket socket ,String login, String password, int idChat) {
        Client client = new Client(login, password);
        Chat chat = clientService.getCurrentChatForClient(client, idChat);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(chat.getId());
            printWriter.println(chat.getCar().getRegNumber());
            printWriter.println(chat.getCarServiceName());
            printWriter.println(chat.getMessages().size());

            for (Message message : chat.getMessages()) {
                printWriter.println(message.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
