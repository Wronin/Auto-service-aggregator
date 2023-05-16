package controller;

import model.*;
import org.json.simple.JSONObject;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class ClientController {

    public static class ClientControllerSingle {
        public static final ClientController INSTANCE = new ClientController();
    }

    public static ClientController getInstance() {
        return ClientControllerSingle.INSTANCE;
    }

    public void addCar(Socket socket, String login, String password, String brand, String model, String VINNumber, String regNumber) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "addCar");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("brand", brand);
        jsonObject.put("model", model);
        jsonObject.put("VINNumber", VINNumber);
        jsonObject.put("regNumber", regNumber);

        File file = new File(System.getProperty("user.dir"), "file.json");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCar(Socket socket, String login, String password, String brand, String model, String VINNumber, String regNumber) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "deleteCar");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("brand", brand);
        jsonObject.put("model", model);
        jsonObject.put("VINNumber", VINNumber);
        jsonObject.put("regNumber", regNumber);

        File file = new File(System.getProperty("user.dir"), "file.json");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toJSONString());

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCarInformation(Socket socket, String login, String password, String brand, String model, String VINNumber, String regNumber, String newBrand, String newModel, String newVINNumber, String newRegNumber) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "updateCarInformation");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("brand", brand);
        jsonObject.put("model", model);
        jsonObject.put("VINNumber", VINNumber);
        jsonObject.put("regNumber", regNumber);
        jsonObject.put("newBrand", newBrand);
        jsonObject.put("newModel", newModel);
        jsonObject.put("newVINNumber", newVINNumber);
        jsonObject.put("newRegNumber", newRegNumber);

        File file = new File(System.getProperty("user.dir"), "file.json");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toJSONString());

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRequest(Socket socket, String login, String password, String description, String brand, String model, String VINNumber, String regNumber) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "addRequest");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("description", description);
        jsonObject.put("brand", brand);
        jsonObject.put("model", model);
        jsonObject.put("VINNumber", VINNumber);
        jsonObject.put("regNumber", regNumber);

        File file = new File(System.getProperty("user.dir"), "file.json");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            outputStreamWriter.write(jsonObject.toJSONString());

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRequestWithServices(Socket socket, String login, String password, String description, String brand, String model, String vin, String regNumber, ArrayList<Service> services) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "addRequestWithServices");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("description", description);
        jsonObject.put("brand", brand);
        jsonObject.put("model", model);
        jsonObject.put("VINNumber", vin);
        jsonObject.put("regNumber", regNumber);

        File file = new File(System.getProperty("user.dir"), "file.json");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toJSONString());

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject);

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

    public ArrayList<Car> getCarList(Socket socket, String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getCarList");
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        File file = new File(System.getProperty("user.dir"), "file.json");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toJSONString());

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Car> cars = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            int size = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < size; i++) {
                cars.add(new Car(bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cars;
    }

    public String[] getCarNumbers(Socket socket, String login, String password) {
        ArrayList<String> carNumbers = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("func", "getCarNumbers");
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        try {
            File file = new File(System.getProperty("user.dir"), "file.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                carNumbers.add(bufferedReader.readLine());
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return carNumbers.toArray(new String[0]);
    }

    public Car getCar(Socket socket, String login, String password, String regNumber) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getCar");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("regNumber", regNumber);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Car car = new Car();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            car = new Car(bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
    }

    public ArrayList<AnswerAutoService> getAnswerAutoService(Socket socket, String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getAnswerAutoService");
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<AnswerAutoService> answers = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String content = bufferedReader.readLine();
            int serviceSize;
            for (int i = 0; i < Integer.parseInt(content); i++) {
                answers.add(
                        new AnswerAutoService(
                                Integer.parseInt(bufferedReader.readLine()),
                                Integer.parseInt(bufferedReader.readLine()),
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine(),
                                bufferedReader.readLine(),
                                bufferedReader.readLine()
                        )
                );
                serviceSize = Integer.parseInt(bufferedReader.readLine());
                if (serviceSize != 0) {
                    ArrayList<Service> services = new ArrayList<>();
                    for (int j = 0; j < serviceSize; j++) {
                        services.add(
                                new Service(
                                        Integer.parseInt(bufferedReader.readLine()),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine()
                                )
                        );
                    }
                    answers.get(i).setServices(services);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answers;
    }

    public CarService getCarServiceById(Socket socket, int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getCarServiceByName");
        jsonObject.put("id", id);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CarService carService;
        try {
            int size = 0;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            carService = new CarService(
                    bufferedReader.readLine(),
                    bufferedReader.readLine()
            );
            size = Integer.parseInt(bufferedReader.readLine());
            ArrayList<Service> services = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                services.add(
                        new Service(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine(),
                                bufferedReader.readLine()
                        )
                );
            }

            size = Integer.parseInt(bufferedReader.readLine());
            ArrayList<String> brands = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                brands.add(bufferedReader.readLine());
            }

            carService.setServices(services);
            carService.setBrands(brands);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return carService;
    }

    public void acceptRequest(Socket socket, String login, String password, int idAuto_service, int idRequest, ArrayList<Service> services) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "acceptRequestForClient");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("idAuto_service", idAuto_service);
        jsonObject.put("idRequest", idRequest);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());

            printWriter.println(services.size());
            for (Service service : services) {
                printWriter.println(service.getId());
                printWriter.println(service.getName());
                printWriter.println(service.getDescription());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Chat> getChatsForClient(Socket socket, String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getChatsForClient");
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        int size = 0;
        ArrayList<Chat> chats = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                chats.add(new Chat(Integer.parseInt(bufferedReader.readLine()), new Car(bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine()), bufferedReader.readLine()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chats;
    }

    public Chat getCurrentChatForClient(Socket socket, String login, String password, int idChat) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getCurrentChatForClient");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("idChat", idChat);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Chat chat = new Chat();
        ArrayList<Message> messages = new ArrayList<>();
        try {
            int size = 0;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            chat.setId(Integer.parseInt(bufferedReader.readLine()));
            chat.setCar(new Car(bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine()));
            chat.setCarServiceName(bufferedReader.readLine());
            size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                messages.add(new Message(bufferedReader.readLine()));
            }
            chat.setMessages(messages);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return chat;
    }

    public void sendClientMessage(Socket socket, String login, String password, int idChat, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "sendClientMessage");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("idChat", idChat);
        jsonObject.put("message", message);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RequestForClient> getAllClientRequest(Socket socket, String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getAllClientRequest");
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<RequestForClient> requests = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int sizeRequests = Integer.parseInt(bufferedReader.readLine());
            int sizeServices = 0;

            RequestForClient requestForClient;
            for (int i = 0; i < sizeRequests; i++) {
                requestForClient = new RequestForClient(
                        Integer.parseInt(bufferedReader.readLine()),
                        new Car(
                                bufferedReader.readLine(),
                                bufferedReader.readLine(),
                                bufferedReader.readLine(),
                                bufferedReader.readLine()
                        ),
                        bufferedReader.readLine(),
                        bufferedReader.readLine(),
                        bufferedReader.readLine(),
                        new ArrayList<>()
                );
                sizeServices = Integer.parseInt(bufferedReader.readLine());
                for (int j = 0; j < sizeServices; j++) {
                    requestForClient.addService(new Service(
                                    Integer.parseInt(bufferedReader.readLine()),
                                    bufferedReader.readLine(),
                                    bufferedReader.readLine()
                            )
                    );
                }
                requests.add(requestForClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public ArrayList<CarService> getCarServices(Socket socket) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getCarServices");

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<CarService> carServices = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                carServices.add(
                        new CarService(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine()
                        )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carServices;
    }

    public ArrayList<Service> getAllServices(Socket socket) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getAllServices");

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Service> services = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

        return services;
    }

    public ArrayList<Car> getAllBrands(Socket socket) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getAllBrands");

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Car> brands = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                brands.add(
                        new Car(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine()
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brands;
    }

    public ArrayList<CarService> getSearchResult(Socket socket, int idService, String brandName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getSearchResult");
        jsonObject.put("idService", idService);
        jsonObject.put("brandName", brandName);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<CarService> carServices = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int size1, size = Integer.parseInt(bufferedReader.readLine());
            CarService carService;
            for (int i = 0; i < size; i++) {
                carService =
                        new CarService(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine(),
                                bufferedReader.readLine()
                        );

                ArrayList<Service> services = new ArrayList<>();
                size1 = Integer.parseInt(bufferedReader.readLine());
                for (int j = 0; j < size1; j++) {
                    services.add(
                            new Service(
                                    Integer.parseInt(bufferedReader.readLine()),
                                    bufferedReader.readLine(),
                                    bufferedReader.readLine()
                            )
                    );
                }
                ArrayList<String> brands = new ArrayList<>();
                size1 = Integer.parseInt(bufferedReader.readLine());
                for (int j = 0; j < size1; j++) {
                    brands.add(bufferedReader.readLine());
                }
                carService.setServices(services);
                carService.setBrands(brands);
                carServices.add(carService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carServices;
    }
}

