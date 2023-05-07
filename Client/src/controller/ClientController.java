package controller;

import model.*;
import org.json.simple.JSONObject;

import javax.naming.ldap.SortKey;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientController {
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
                cars.add(
                        new Car(
                                bufferedReader.readLine(),
                                bufferedReader.readLine(),
                                bufferedReader.readLine(),
                                bufferedReader.readLine()
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cars;
    }

    public ArrayList<String> getCarNumbers(Socket socket, String login, String password) {
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

        return carNumbers;
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
            car = new Car(
                    bufferedReader.readLine(),
                    bufferedReader.readLine(),
                    bufferedReader.readLine(),
                    bufferedReader.readLine()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
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
            throw new RuntimeException(e);
        }

        ArrayList<RequestForClient> requests = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String content = bufferedReader.readLine();

            for (int i = 0; i < Integer.parseInt(content); i++) {
                requests.add(
                        new RequestForClient(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine(),
                                bufferedReader.readLine(),
                                bufferedReader.readLine(),
                                bufferedReader.readLine()
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
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

            for (int i = 0; i < Integer.parseInt(content); i++) {
                answers.add(
                        new AnswerAutoService(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine(),
                                bufferedReader.readLine(),
                                bufferedReader.readLine()
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answers;
    }

    public CarService getCarServiceByName(Socket socket, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getCarServiceByName");
        jsonObject.put("name", name);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String nameCarService = null, descriptionCarService = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            nameCarService = bufferedReader.readLine();
            descriptionCarService = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new CarService(nameCarService, descriptionCarService);
    }

    public void acceptRequest(Socket socket, String login, String password, int idAnswer) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "acceptRequest");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("idAnswer", idAnswer);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(jsonObject.toJSONString());
            printWriter.flush();

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
                chats.add(
                        new Chat(
                                Integer.parseInt(bufferedReader.readLine()),
                                new Car(
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine()
                                ),
                                bufferedReader.readLine()
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chats;
    }
}
