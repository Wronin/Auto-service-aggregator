package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class ClientDao {
    public static class ClientDaoSingle {
        public static final ClientDao INSTANCE = new ClientDao();
    }

    public static ClientDao getInstance() {
        return ClientDaoSingle.INSTANCE;
    }

    private Statement statement;
    private ResultSet resultSet;

    public ClientDao() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aggregator", "root", "1234");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCar(Client client, Car car) {
        try {
            int idAccount = 0, idModel = 0;

            resultSet = statement.executeQuery("select " +
                    "model.id, model.Name, Brand.Name " +
                    "from Model " +
                    "join Brand on Model.Brand_id = Brand.id");

            while (resultSet.next()) {
                if (resultSet.getString("model.Name").equals(car.getModel()) && resultSet.getString("brand.Name").equals(car.getBrand()))
                    idModel = resultSet.getInt("model.id");
            }

            resultSet = statement.executeQuery(String.format("select account.id " +
                    "from account where account.login = '%s' and account.password = '%s';", client.getLogin(), client.getPassword()));

            while (resultSet.next()) {
                idAccount = resultSet.getInt("id");
            }

            String sql = String.format("insert into auto (`vin`, `reg_number`, `model_id`, `account_id`) " +
                    "values('%s', '%s', '%d', '%d');", car.getVINNumber(), car.getRegNumber(), idModel, idAccount);
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCar(Client client, Car car) {
        try {
            statement.executeUpdate(String.format("delete auto " +
                    "from auto " +
                    "join account on auto.account_id = account.id " +
                    "where auto.vin = '%s' and auto.reg_number = '%s' and account.login = '%s' and account.password = '%s';", car.getVINNumber(), car.getRegNumber(), client.getLogin(), client.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCarInformation(Client client, Car car, Car newCar) {
        try {
            int idModel = 0, idAuto = 0;

            resultSet = statement.executeQuery("select " +
                    "model.id, model.Name, Brand.Name, Brand.id " +
                    "from Model " +
                    "join Brand on Model.Brand_id = Brand.id");

            while (resultSet.next()) {
                if (resultSet.getString("model.Name").equals(newCar.getModel()) && resultSet.getString("brand.Name").equals(newCar.getBrand())) {
                    idModel = resultSet.getInt("model.id");
                }
            }
            resultSet = statement.executeQuery(String.format("select " +
                    "auto.id, auto.vin, auto.reg_number " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s' and auto.vin = '%s' and auto.reg_number = '%s';", client.getLogin(), client.getPassword(), car.getVINNumber(), car.getRegNumber()));

            while (resultSet.next()) {
                if (resultSet.getString("auto.vin").equals(car.getVINNumber()) && resultSet.getString("auto.reg_number").equals(car.getRegNumber())) {
                    idAuto = resultSet.getInt("auto.id");
                }
            }

            statement.executeUpdate(String.format("update auto " +
                    "set vin = '%s', reg_number = '%s', model_id = '%d' " +
                    "where auto.id = '%d';", newCar.getVINNumber(), newCar.getRegNumber(), idModel, idAuto));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Car> getCarList(Client client) {
        ArrayList<Car> cars = new ArrayList<>();
        try {

            resultSet = statement.executeQuery(String.format("select " +
                    "auto.vin, auto.reg_number, brand.name, model.name " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s';", client.getLogin(), client.getPassword()));

            while (resultSet.next()) {
                cars.add(new Car(resultSet.getString("auto.vin"),
                        resultSet.getString("auto.reg_number"),
                        resultSet.getString("brand.name"),
                        resultSet.getString("model.name"))
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cars;
    }

    public void addRequest(Client client, Request request) {
        try {
            int idAuto = 0;
            resultSet = statement.executeQuery(String.format("select " +
                    "auto.id, auto.vin " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "account.Login = '%s' and account.Password = '%s';", client.getLogin(), client.getPassword()));

            while (resultSet.next()) {
                if (resultSet.getString("auto.vin").equals(request.getCar().getVINNumber()))
                    idAuto = resultSet.getInt("auto.id");
            }

            String sql;
            sql = String.format("insert into Request(`Description`, `auto_id`, `Status`) values('%s', '%d', '%s');", request.getDescription(), idAuto, request.getStatus());
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRequestWithServices(Request request) {
        try {
            int idAuto = 0, idRequest = 0;
            resultSet = statement.executeQuery(String.format("select " +
                    "auto.id, auto.vin " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "account.Login = '%s' and account.Password = '%s';", request.getClient().getLogin(), request.getClient().getPassword()));

            while (resultSet.next()) {
                if (resultSet.getString("auto.vin").equals(request.getCar().getVINNumber()))
                    idAuto = resultSet.getInt("auto.id");
            }

            statement.executeUpdate(String.format("insert into request (`description`, `auto_id`, `status`) values('%s', '%d', '%s');", request.getDescription(), idAuto, request.getStatus()));

            resultSet = statement.executeQuery(String.format("select " +
                    "auto.id, request.id, auto.Reg_number, request.Description, auto_service.name, request.Status " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join request on request.auto_id = auto.id " +
                    "left join auto_service on request.auto_service_id = auto_service.id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s';", request.getClient().getLogin(), request.getClient().getPassword()));

            while (resultSet.next()) {
                if (resultSet.getString("description").equals(request.getDescription()) && resultSet.getInt("auto.id") == idAuto) {
                    idRequest = resultSet.getInt("request.id");
                }
            }

            for (var service : request.getServices()) {
                statement.executeUpdate(String.format("insert into status_service(`request_id`, `service_id`) values('%d', '%d');", idRequest, service.getId()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptRequestForClient(Client client, int idAuto_service, int idRequest, ArrayList<Service> services) {
        ArrayList<Integer> serviceIds = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(String.format("select " +
                    "service.id " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join request on auto.id = request.auto_id " +
                    "join answer on request.id = answer.request_id " +
                    "join auto_service on answer.auto_service_id = auto_service.id " +
                    "left join autoService_service on answer.autoService_service_id = autoService_service.id " +
                    "left join service on autoService_service.service_id = service.id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s' and auto_service.id = '%d' and request.id = '%d';", client.getLogin(), client.getPassword(), idAuto_service, idRequest));

            while (resultSet.next()) {
                serviceIds.add(resultSet.getInt("service.id"));
            }

            statement.executeUpdate(String.format("delete from status_service where request_id = '%d';", idRequest));
            for (Integer id : serviceIds) {
                statement.executeUpdate(String.format("insert into status_service (`status`, `request_id`, `service_id`) values ('Waiting', '%d', '%d');", idRequest, id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Service> getAllServices() {
        ArrayList<Service> services = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select " +
                    "service.id, service.name, service.description " +
                    "from service;");

            while (resultSet.next()) {
                services.add(new Service(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return services;
    }

    public ArrayList<Car> getAllBrands() {
        ArrayList<Car> brands = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select brand.id, brand.name from brand;");
            while (resultSet.next()) {
                brands.add(
                        new Car(
                                resultSet.getInt("brand.id"),
                                resultSet.getString("brand.name")
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brands;
    }

    public ArrayList<RequestForClient> getAllClientRequest(Client client) {
        ArrayList<RequestForClient> requests = new ArrayList<>();

        try {
            resultSet = statement.executeQuery(String.format("select " +
                    "request.id, auto.Reg_number, auto.vin, model.name, brand.name, request.Description, auto_service.name, request.Status " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "join request on request.auto_id = auto.id " +
                    "left join auto_service on request.auto_service_id = auto_service.id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s';", client.getLogin(), client.getPassword()));

            while (resultSet.next()) {
                requests.add(
                        new RequestForClient(
                                resultSet.getInt("request.id"),
                                new Car(
                                        resultSet.getString("auto.vin"),
                                        resultSet.getString("auto.reg_number"),
                                        resultSet.getString("brand.name"),
                                        resultSet.getString("model.name")

                                ),
                                resultSet.getString("request.description"),
                                resultSet.getString("auto_service.name"),
                                resultSet.getString("request.status"),
                                new ArrayList<>()
                        )
                );
            }

            resultSet = statement.executeQuery(String.format("select " +
                    "request.id, status_service.id, service.name, service.description " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join request on request.auto_id = auto.id " +
                    "left join auto_service on request.auto_service_id = auto_service.id " +
                    "left join status_service on request.id = status_service.request_id " +
                    "left join service on service.id = status_service.service_id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s';", client.getLogin(), client.getPassword()));

            while (resultSet.next()) {
                for (RequestForClient request : requests) {
                    if (request.getId() == resultSet.getInt("request.id")) {
                        request.addService(new Service(resultSet.getInt("status_service.id"), resultSet.getString("service.name"), resultSet.getString("service.description")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }

    public ArrayList<AnswerAutoService> getAnswerAutoService(Client client) {
        ArrayList<AnswerAutoService> answers = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(String.format("select " +
                    "auto_service.name, auto_service.id, request.id, answer.id, auto.Reg_number, answer.Status " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "join request on auto.id = request.auto_id " +
                    "join answer on request.id = answer.request_id " +
                    "left join auto_service on answer.auto_service_id = auto_service.id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s'" +
                    "group by 3;", client.getLogin(), client.getPassword()));

            while (resultSet.next()) {
                answers.add(
                        new AnswerAutoService(
                                resultSet.getInt("answer.id"),
                                resultSet.getInt("auto_service.id"),
                                resultSet.getInt("request.id"),
                                resultSet.getString("reg_number"),
                                resultSet.getString("Name"),
                                resultSet.getString("status")
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answers;
    }

    public ArrayList<AnswerAutoService> getAllServicesForAnswerAutoService(Client client) {
        ArrayList<AnswerAutoService> answer = new ArrayList<>();

        try {
            resultSet = statement.executeQuery(String.format("select " +
                    "auto_service.id, auto_service.name, request.id " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join request on auto.id = request.auto_id " +
                    "join answer on request.id = answer.request_id " +
                    "join auto_service on answer.auto_service_id = auto_service.id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s'" +
                    "group by 3;", client.getLogin(), client.getPassword()));
            while (resultSet.next()) {
                answer.add(
                        new AnswerAutoService(
                                resultSet.getInt("request.id"),
                                resultSet.getInt("auto_service.id"),
                                resultSet.getString("auto_service.name")
                        )
                );
            }
            for (AnswerAutoService answerAutoService : answer) {
                resultSet = statement.executeQuery(String.format("select " +
                                "auto_service.Name, autoService_service.id, service.id, service.name, service.description " +
                                "from account " +
                                "join auto on account.id = auto.account_id " +
                                "join request on auto.id = request.auto_id " +
                                "join answer on request.id = answer.request_id " +
                                "join auto_service on answer.auto_service_id = auto_service.id " +
                                "left join autoService_service on answer.autoService_service_id = autoService_service.id " +
                                "left join service on autoService_service.service_id = service.id " +
                                "where " +
                                "account.login = '%s' and account.password = '%s' and auto_service.id = '%d' and request.id = '%d';",
                        client.getLogin(), client.getPassword(), answerAutoService.getIdAutoService(), answerAutoService.getIdRequest()));
                ArrayList<Service> services = new ArrayList<>();
                while (resultSet.next()) {
                    services.add(
                            new Service(
                                    resultSet.getInt("service.id"),
                                    resultSet.getString("service.name"),
                                    resultSet.getString("service.description")
                            )
                    );
                }
                answerAutoService.setServices(services);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }

    public ArrayList<CarService> getCarServices() {
        ArrayList<CarService> carServices = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select * from auto_Service");
            while (resultSet.next()) {
                carServices.add(new CarService(resultSet.getInt("id"), resultSet.getNString("Name"), resultSet.getString("description"), new ArrayList<>()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carServices;
    }

    public ArrayList<Service> getCarServiceServicesById(int idAutoService) {
        ArrayList<Service> services = new ArrayList<>();

        try {
            resultSet = statement.executeQuery(String.format("select " +
                    "service.id, service.name, service.description " +
                    "from auto_service " +
                    "join autoService_service on auto_service.id = autoService_service.auto_service_id " +
                    "join service on autoService_service.service_id = service.id " +
                    "where " +
                    "auto_service_id = '%d';", idAutoService));

            while (resultSet.next()) {
                services.add(new Service(resultSet.getInt("id"), resultSet.getString("service.name"), resultSet.getString("service.description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    public ArrayList<Car> getCarBrandFromCarService(int idAutoService) {
        ArrayList<Car> brands = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(String.format("select " +
                    "brand.id, brand.name " +
                    "from auto_service " +
                    "join autoService_service on auto_service.id = autoService_service.auto_service_id " +
                    "join model on autoService_service.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "auto_service.id = '%d' " +
                    "group by 1;", idAutoService));

            while (resultSet.next()) {
                brands.add(
                        new Car(
                                resultSet.getInt("brand.id"),
                                resultSet.getString("brand.name")
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brands;
    }

    public void createChat(Client client, int id) {
        try {
            int idAutoService = 0, auto_id = 0;
            resultSet = statement.executeQuery(String.format("select " +
                    "auto_service.id " +
                    "from account " +
                    "join auto on auto.account_id = account.id " +
                    "join request on request.auto_id = auto.id " +
                    "join answer on answer.request_id = request.id " +
                    "join auto_service on answer.auto_service_id = auto_service.id " +
                    "where " +
                    "answer.id = '%d' and account.login = '%s' and account.password = '%s';", id, client.getLogin(), client.getPassword()));

            while (resultSet.next()) {
                idAutoService = resultSet.getInt("id");
            }

            resultSet = statement.executeQuery(String.format("select " +
                    "auto.id " +
                    "from account " +
                    "join auto on auto.account_id = account.id " +
                    "join request on request.auto_id = auto.id " +
                    "join answer on answer.request_id = request.id " +
                    "where " +
                    "answer.id = '%d' and account.login = '%s' and account.password = '%s';", id, client.getLogin(), client.getPassword()));

            while (resultSet.next()) {
                auto_id = resultSet.getInt("id");
            }

            statement.executeUpdate(String.format("insert into chat(auto_id, auto_service_id) " +
                    "values ('%d', '%d');", auto_id, idAutoService));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendClientMessage(Client client, int idChat, String message) {
        try {
            int id = 0;
            resultSet = statement.executeQuery(String.format("select " +
                    "chat.id " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join chat on auto.id = chat.auto_id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s';", client.getLogin(), client.getPassword())
            );

            while (resultSet.next()) {
                if (resultSet.getInt("chat.id") == idChat) {
                    id = resultSet.getInt("chat.id");
                }
            }
            statement.executeUpdate(String.format("insert into message (`Text`, `chat_id`) values ('%s', '%d');", message, id));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Chat> getChatsForClient(Client client) {
        ArrayList<Chat> chats = new ArrayList<>();

        try {
            resultSet = statement.executeQuery(String.format("select " +
                            "chat.id, auto.vin, auto.reg_number, brand.name, model.name, auto_service.name " +
                            "from account " +
                            "join auto on account.id = auto.account_id " +
                            "join model on auto.model_id = model.id " +
                            "join brand on model.brand_id = brand.id " +
                            "join chat on auto.id = chat.auto_id " +
                            "join auto_service on chat.auto_service_id = auto_service.id " +
                            "where " +
                            "account.login = '%s' and account.password = '%s';",
                    client.getLogin(), client.getPassword())
            );

            while (resultSet.next()) {
                chats.add(
                        new Chat(
                                resultSet.getInt("chat.id"),
                                new Car(
                                        resultSet.getString("auto.vin"),
                                        resultSet.getString("auto.reg_number"),
                                        resultSet.getString("brand.name"),
                                        resultSet.getString("model.name")
                                ),
                                resultSet.getString("auto_service.name")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chats;
    }

    public ArrayList<Message> getMessagesByChatId(Client client, int idChat) {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(String.format("select " +
                    "message.text " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join chat on auto.id = chat.auto_id " +
                    "join message on chat.id = message.chat_id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s' and chat.id = '%d';", client.getLogin(), client.getPassword(), idChat)
            );

            while (resultSet.next()) {
                messages.add(new Message(resultSet.getString("message.text")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    public Chat getCurrentChatForClient(Client client, int idChat) {
        Chat chat = new Chat();
        try {
            resultSet = statement.executeQuery(String.format("select " +
                    "auto.vin, auto.reg_number, brand.name, model.name, auto_service.name, chat.id " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join chat on auto.id = chat.auto_id " +
                    "join auto_service on chat.auto_service_id = auto_service.id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s' and chat.id = '%d';", client.getLogin(), client.getPassword(), idChat)
            );

            while (resultSet.next()) {
                chat = new Chat(
                        resultSet.getInt("chat.id"),
                        new Car(
                                resultSet.getString("auto.vin"),
                                resultSet.getString("auto.reg_number"),
                                resultSet.getString("brand.name"),
                                resultSet.getString("model.name")
                        ),
                        resultSet.getString("auto_service.name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return chat;
    }

    public ArrayList<CarService> getCarServicesName() {
        ArrayList<CarService> carServices = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select " +
                    "auto_service.id, auto_service.name " +
                    "from auto_service;");

            while (resultSet.next()) {
                carServices.add(
                        new CarService(
                                resultSet.getInt("auto_service.id"),
                                resultSet.getString("auto_service.name")
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carServices;
    }
}
