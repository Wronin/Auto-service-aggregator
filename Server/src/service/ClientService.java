package service;

import dao.*;
import model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClientService {

    public static class ClientServiceSingle {
        public static final ClientService INSTANCE = new ClientService();
    }

    public static ClientService getInstance() {
        return ClientService.ClientServiceSingle.INSTANCE;
    }

    public ClientService() {
    }

    public void addCar(Client client, String brand, String model, String VINNumber, String regNumber) {
        Car car = new Car(VINNumber, regNumber, brand, model);
        ClientDao.getInstance().addCar(client, car);
    }


    public void deleteCar(Client client, Car car) {
        ClientDao.getInstance().deleteCar(client, car);
    }

    public void updateCarInformation(Client client, Car car, Car newCar) {
        ClientDao.getInstance().updateCarInformation(client, car, newCar);
    }

    public Car getCar(Client client, String regNumber) {

        ArrayList<Car> cars = ClientDao.getInstance().getCarList(client);
        for (var car : cars) {
            if (car.getRegNumber().equals(regNumber))
                return car;
        }

        return new Car();
    }

    public ArrayList<Car> getCarList(Client client) {
        return ClientDao.getInstance().getCarList(client);
    }

    public ArrayList<Service> getAllServices() {
        return ClientDao.getInstance().getAllServices();
    }

    public ArrayList<Car> getAllBrands() {
        return ClientDao.getInstance().getAllBrands();
    }

    public void addRequest(Client client, String description, Car car, Status status) {
        Request request = new Request(client, description, car, status);
        ClientDao.getInstance().addRequest(client, request);
    }

    public void addRequestWithServices(Request request) {
        ClientDao.getInstance().addRequestWithServices(request);
    }

    public ArrayList<RequestForClient> getAllClientRequest(Client client) {
        return ClientDao.getInstance().getAllClientRequest(client);
    }

    public ArrayList<AnswerAutoService> getAnswerAutoService(Client client) {
        return ClientDao.getInstance().getAnswerAutoService(client);
    }

    public CarService getCarServiceById(int id) {
        ArrayList<CarService> carServices = ClientDao.getInstance().getCarServices();

        for (CarService carService : carServices) {
            if (carService.getId() == id) {
                carService.setServices(removeDuplicates(ClientDao.getInstance().getCarServiceServicesById(id)));
                carService.setBrands(ClientDao.getInstance().getCarBrandFromCarService(id));
                return carService;
            }
        }
        return new CarService();
    }

    public ArrayList<CarService> getSearchResult(String brandName, int idService) {
        ArrayList<CarService> carServices = ClientDao.getInstance().getCarServices();
        ArrayList<CarService> resultSearch = new ArrayList<>();

        for (CarService carService : carServices) {
            carService.setServices(removeDuplicates(ClientDao.getInstance().getCarServiceServicesById(carService.getId()))); //todo fix removeDuplicates
            carService.setBrands(ClientDao.getInstance().getCarBrandFromCarService(carService.getId()));
        }

        boolean serviceExist = false, brandExist = false;
        if (!brandName.equals("") && idService != 0) {
            for (CarService carService : carServices) {
                for (Service service : carService.getServices()) {
                    if (service.getId() == idService) {
                        serviceExist = true;
                        break;
                    }
                }
                for (Car brand : carService.getBrands()) {
                    if (brand.getBrand().equals(brandName)) {
                        brandExist = true;
                        break;
                    }
                }
                if (serviceExist && brandExist) {
                    resultSearch.add(carService);
                }
                serviceExist = false;
                brandExist = false;
            }
        } else if (!brandName.equals("")) {
            for (CarService carService : carServices) {
                for (Car brand : carService.getBrands()) {
                    if (brand.getBrand().equals(brandName)) {
                        resultSearch.add(carService);
                    }
                }
            }
        } else if (idService != 0) {
            for (CarService carService : carServices) {
                for (Service service : carService.getServices()) {
                    if (service.getId() == idService) {
                        resultSearch.add(carService);
                    }
                }
            }
        }
        return resultSearch;
    }


    public ArrayList<Service> removeDuplicates(ArrayList<Service> services) {
        ArrayList<Service> newServices = new ArrayList<>();
        ArrayList<String> newServicesName = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        for (Service service : services) {
            newServicesName.add(service.getName());
        }

        for (String newServiceName : newServicesName) {
            if (!names.contains(newServiceName)) {
                names.add(newServiceName);
            }
        }

        for (String name : names) {
            newServices.add(new Service(name));
        }

        return newServices;
    }

    public void acceptRequestForClient(Client client, int idAnswer) {
        ClientDao.getInstance().acceptRequestForClient(client, idAnswer);
        ClientDao.getInstance().createChat(client, idAnswer);
    }

    public void sendClientMessage(Client client, int idChat, String message) {
        message = String.format("Client: %s", message);
        ClientDao.getInstance().sendClientMessage(client, idChat, message);
    }

    public ArrayList<Chat> getChatsForClient(Client client) {
        ArrayList<Chat> chats = ClientDao.getInstance().getChatsForClient(client);

        for (Chat chat : chats) {
            chat.setMessages(ClientDao.getInstance().getMessagesByChatId(client, chat.getId()));
        }

        return chats;
    }

    public Chat getCurrentChatForClient(Client client, int idChat) {
        Chat chat = ClientDao.getInstance().getCurrentChatForClient(client, idChat);
        chat.setMessages(ClientDao.getInstance().getMessagesByChatId(client, idChat));

        return chat;
    }

    public ArrayList<CarService> getCarServices() {
        return ClientDao.getInstance().getCarServicesName();
    }
}
