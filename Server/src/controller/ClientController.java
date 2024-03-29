package controller;

import model.*;
import service.ClientService;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientController {
    public static class ClientControllerSingle {
        public static final ClientController INSTANCE = new ClientController();
    }

    public static ClientController getInstance() {
        return ClientControllerSingle.INSTANCE;
    }

    public void addCar(String login, String password, String brand, String model, String VINNumber, String regNumber) {
        Client client = new Client(login, password);
        ClientService.getInstance().addCar(client, brand, model, VINNumber, regNumber);
    }

    public void deleteCar(String login, String password, String brand, String model, String VINNumber, String regNumber) {
        Client client = new Client(login, password);
        Car car = new Car(VINNumber, regNumber, brand, model);
        ClientService.getInstance().deleteCar(client, car);
    }

    public void updateCarInformation(String login, String password, String brand, String model, String VINNumber, String regNumber, String newBrand, String newModel, String newVINNumber, String newRegNumber) {
        Client client = new Client(login, password);
        Car car = new Car(VINNumber, regNumber, brand, model);
        Car newCar = new Car(newVINNumber, newRegNumber, newBrand, newModel);
        ClientService.getInstance().updateCarInformation(client, car, newCar);
    }

    public void getCar(Socket socket, String login, String password, String regNumber) {
        Client client = new Client(login, password);
        Car car = ClientService.getInstance().getCar(client, regNumber);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(car.getVINNumber());
            printWriter.println(car.getRegNumber());
            printWriter.println(car.getBrand());
            printWriter.println(car.getModel());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCarList(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<Car> cars = ClientService.getInstance().getCarList(client);

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
        ArrayList<Car> cars = ClientService.getInstance().getCarList(client);

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
        ClientService.getInstance().addRequest(client, description, car, Status.SEARCH);
    }

    public void addRequestWithServices(BufferedReader bufferedReader, String login, String password, String description, String brand, String model, String vin, String regNumber) {
        Client client = new Client(login, password);
        Car car = new Car(vin, regNumber, brand, model);
        ArrayList<Service> services = new ArrayList<>();

        try {
            int size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                services.add(new Service(Integer.parseInt(bufferedReader.readLine()), bufferedReader.readLine(), bufferedReader.readLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Request request = new Request(client, description, car, services, Status.SEARCH);
        ClientService.getInstance().addRequestWithServices(request);
    }

    public void getAllClientRequest(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<RequestForClient> requests = ClientService.getInstance().getAllClientRequest(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(requests.size());

            for (var request : requests) {
                printWriter.println(request.getId());
                printWriter.println(request.getCar().getVINNumber());
                printWriter.println(request.getCar().getRegNumber());
                printWriter.println(request.getCar().getBrand());
                printWriter.println(request.getCar().getModel());
                printWriter.println(request.getDescription());
                printWriter.println(request.getName());
                printWriter.println(request.getStatus());
                printWriter.println(request.getServices().size());
                for (var service : request.getServices()) {
                    printWriter.println(service.getId());
                    printWriter.println(service.getName());
                    printWriter.println(service.getDescription());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getAnswerAutoService(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<AnswerAutoService> answers = ClientService.getInstance().getAnswerAutoService(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(answers.size());
            for (var request : answers) {
                printWriter.println(request.getId());
                printWriter.println(request.getIdAutoService());
                printWriter.println(request.getIdRequest());
                printWriter.println(request.getRegNumber());
                printWriter.println(request.getName());
                printWriter.println(request.getStatus());
                printWriter.println(request.getServices().size());
                for (Service service : request.getServices()) {
                    printWriter.println(service.getId());
                    printWriter.println(service.getName());
                    printWriter.println(service.getDescription());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCarServiceById(Socket socket, int id) {
        CarService carService = ClientService.getInstance().getCarServiceById(id);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(carService.getName());
            printWriter.println(carService.getSpecification());

            printWriter.println(carService.getServices().size());
            for (Service service : carService.getServices()) {
                printWriter.println(service.getId());
                printWriter.println(service.getName());
                printWriter.println(service.getDescription());
            }

            printWriter.println(carService.getBrands().size());
            for (Car brand : carService.getBrands()) {
                printWriter.println(brand.getBrand());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptRequestForClient(BufferedReader bufferedReader, String login, String password, int idAuto_service, int idRequest) {
        Client client = new Client(login, password);
        ArrayList<Service> services = new ArrayList<>();
        try {
            int size = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < size; i++) {
                services.add(
                        new Service(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine(),
                                bufferedReader.readLine()
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ClientService.getInstance().acceptRequestForClient(client, idAuto_service, idRequest, services);
    }

    public void sendClientMessage(String login, String password, int idChat, String message) {
        Client client = new Client(login, password);
        ClientService.getInstance().sendClientMessage(client, idChat, message);
    }

    public void getChatsForClient(Socket socket, String login, String password) {
        Client client = new Client(login, password);
        ArrayList<Chat> chats = ClientService.getInstance().getChatsForClient(client);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(chats.size());

            for (Chat chat : chats) {
                printWriter.println(chat.getId());
                printWriter.println(chat.getCar().getVINNumber());
                printWriter.println(chat.getCar().getRegNumber());
                printWriter.println(chat.getCar().getBrand());
                printWriter.println(chat.getCar().getModel());
                printWriter.println(chat.getCarServiceName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getCurrentChatForClient(Socket socket, String login, String password, int idChat) {
        Client client = new Client(login, password);
        Chat chat = ClientService.getInstance().getCurrentChatForClient(client, idChat);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(chat.getId());
            printWriter.println(chat.getCar().getRegNumber());
            printWriter.println(chat.getCar().getVINNumber());
            printWriter.println(chat.getCar().getBrand());
            printWriter.println(chat.getCar().getModel());
            printWriter.println(chat.getCarServiceName());
            printWriter.println(chat.getMessages().size());

            for (Message message : chat.getMessages()) {
                printWriter.println(message.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCarServices(Socket socket) {
        ArrayList<CarService> carServices = ClientService.getInstance().getCarServices();

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(carServices.size());

            for (CarService carService : carServices) {
                printWriter.println(carService.getId());
                printWriter.println(carService.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllServices(Socket socket) {
        ArrayList<Service> services = ClientService.getInstance().getAllServices();

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(services.size());
            for (Service service : services) {
                printWriter.println(service.getId());
                printWriter.println(service.getName());
                printWriter.println(service.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllBrands(Socket socket) {
        ArrayList<Car> brands = ClientService.getInstance().getAllBrands();

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(brands.size());
            for (Car car : brands) {
                printWriter.println(car.getId());
                printWriter.println(car.getBrand());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSearchResult(Socket socket, String brandName, int idService) {
        ArrayList<CarService> carServices = ClientService.getInstance().getSearchResult(brandName, idService);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(carServices.size());
            for (CarService carService : carServices) {
                printWriter.println(carService.getId());
                printWriter.println(carService.getName());
                printWriter.println(carService.getSpecification());

                printWriter.println(carService.getServices().size());
                for (Service service : carService.getServices()) {
                    printWriter.println(service.getId());
                    printWriter.println(service.getName());
                    printWriter.println(service.getDescription());
                }

                printWriter.println(carService.getBrands().size());
                for (Car brand : carService.getBrands()) {
                    printWriter.println(brand.getBrand());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
