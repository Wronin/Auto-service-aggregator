package service;

import dao.ClientDao;
import model.*;

import java.util.ArrayList;

public class ClientService {
    private ClientDao clientDao = new ClientDao();

    public ClientService() {
    }

    public void addCar(Client client, String brand, String model, String VINNumber, String regNumber) {
        Car car = new Car(VINNumber, regNumber, brand, model);
        clientDao.addCar(client, car);
    }

    public Car getCar(Client client, String regNumber) {

        ArrayList<Car> cars = clientDao.getCarList(client);
        for (var car : cars) {
            if (car.getRegNumber().equals(regNumber))
                return car;
        }

        return new Car();
    }

    public ArrayList<Car> getCarList(Client client) {
        return clientDao.getCarList(client);
    }
    public void addRequest(Client client, String description, Car car, Status status) {
        Request request = new Request(client, description, car, status);
        clientDao.addRequest(client, request);
    }

    public ArrayList<RequestForClient> getAllClientRequest(Client client) {
        return clientDao.getAllClientRequest(client);
    }

    public ArrayList<AnswerAutoService> getAnswerAutoService(Client client) {
        return clientDao.getAnswerAutoService(client);
    }

    public CarService getCarServiceByName(String name) {
        ArrayList<CarService> carServices = clientDao.getCarServices(name);

        for (CarService carService : carServices) {
            if (carService.getName().equals(name)) {
                return carService;
            }
        }
        return new CarService();
    }
    public void acceptRequest(Client client, int idAnswer) {
        clientDao.acceptRequest(client, idAnswer);
    }
}
