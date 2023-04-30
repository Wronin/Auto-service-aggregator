package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class ClientDao {
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

            resultSet = statement.executeQuery("select account.id " +
                    "from account where account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                idAccount = resultSet.getInt("id");
            }

            String sql = "insert into auto (`vin`, `reg_number`, `model_id`, `account_id`) " +
                    "values('" + car.getVINNumber() + "', '" + car.getRegNumber() + "', '" + idModel + "', '" + idAccount + "');";
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Car> getCarList(Client client) {
        ArrayList<Car> cars = new ArrayList<>();
        try {

            resultSet = statement.executeQuery("select " +
                    "auto.vin, auto.reg_number, brand.name, model.name " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

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
    public void addRequest(Client client, Request request){
        try {
            int idAuto = 0;
            resultSet = statement.executeQuery("select " +
                    "auto.id, auto.vin " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "account.Login = '" + client.getLogin() + "' and account.Password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                if (resultSet.getString("auto.vin").equals(request.getCar().getVINNumber()))
                    idAuto = resultSet.getInt("auto.id");
            }

            String sql;
            sql = "insert into Request(Description, auto_id, Status) values('" + request.getDescription() + "', " + idAuto + ", '" + request.getStatus() + "');";
            statement.executeUpdate(sql);

         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    public ArrayList<RequestForClient> getAllClientRequest(Client client) {
        ArrayList<RequestForClient> requests = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select " +
                    "request.id, auto.Reg_number, request.Description, auto_service.name, request.Status " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join request on request.auto_id = auto.id " +
                    "left join auto_service on request.auto_service_id = auto_service.id " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                requests.add(new RequestForClient(Integer.parseInt(resultSet.getString("request.id")), resultSet.getString("auto.reg_number"), resultSet.getString("request.description"), resultSet.getString("auto_service.name"), resultSet.getString("request.status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }

    public ArrayList<AnswerAutoService> getAnswerAutoService(Client client) {
        ArrayList<AnswerAutoService> answers = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select " +
                    "answer.id, auto.Reg_number, Auto_service.name, answer.Status " +
                    "from answer " +
                    "join request on request.id = answer.request_id " +
                    "join auto_service on answer.auto_service_id = auto_service.id " +
                    "join auto on request.auto_id = auto.id " +
                    "join account on account.id = auto.account_id " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                answers.add(new AnswerAutoService(Integer.parseInt(resultSet.getString("id")), resultSet.getString("reg_number"), resultSet.getString("Name"), resultSet.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answers;
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
            resultSet = statement.executeQuery("select " +
                    "service.name, service.description " +
                    "from auto_service " +
                    "join autoService_service on auto_service.id = autoService_service.auto_service_id " +
                    "join service on autoService_service.service_id = service.id " +
                    "where " +
                    "auto_service_id = '" + idAutoService + "';");

            while (resultSet.next()) {
                services.add(new Service(resultSet.getString("service.name"), resultSet.getString("service.description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    public void acceptRequestForClient(Client client, int idAnswer) {
        try {
            int auto_service_id = 0, idRequest = 0, idAuto = 0;

            resultSet = statement.executeQuery("select " +
                    "answer.id, answer.Request_id, answer.Auto_Service_id, auto.id " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join request on auto.id = request.auto_id " +
                    "join answer on answer.request_id = request.id " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                if (resultSet.getInt("answer.id") == idAnswer) {
                    idRequest = Integer.parseInt(resultSet.getString("answer.request_id"));
                    idAuto = Integer.parseInt(resultSet.getString("auto.id"));
                    auto_service_id = Integer.parseInt(resultSet.getString("answer.auto_service_id"));
                }
            }

            String sql = "update request set auto_service_id = '" + auto_service_id + "', status = " + "'Accept'" + " where (id = '" + idRequest + "' and auto_id = '" + idAuto + "');";
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createChat(Client client, int id) {
        try {
            int idAutoService = 0, auto_id = 0;
            resultSet = statement.executeQuery("select " +
                    "auto_service.id " +
                    "from account " +
                    "join auto on auto.account_id = account.id " +
                    "join request on request.auto_id = auto.id " +
                    "join answer on answer.request_id = request.id " +
                    "join auto_service on answer.auto_service_id = auto_service.id " +
                    "where " +
                    "answer.id = '" + id + "' and account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                idAutoService = resultSet.getInt("id");
            }

            resultSet = statement.executeQuery("select " +
                    "auto.id " +
                    "from account " +
                    "join auto on auto.account_id = account.id " +
                    "join request on request.auto_id = auto.id " +
                    "join answer on answer.request_id = request.id " +
                    "where " +
                    "answer.id = '" + id + "' and account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                auto_id = resultSet.getInt("id");
            }

            statement.executeUpdate("insert into chat(auto_id, auto_service_id) " +
                    "values ('" + auto_id + "', '" + idAutoService + "');");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendClientMassage(Client client, int idChat, String message) {
        try {
            int id = 0;
            resultSet = statement.executeQuery("select " +
                    "chat.id " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join chat on auto.id = chat.auto_id " +
                    "where " +
                    "account.login ='" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                if (resultSet.getInt("id") == idChat) {
                    id = resultSet.getInt("id");
                }
            }
            statement.executeUpdate("insert into message (`text`, `chat_id`) values ('" + message + "', '" + id + "');");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Chat> getChatsForClient(Client client) {
        ArrayList<Chat> chats = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select " +
                    "chat.id, auto.vin, auto.reg_number, brand.name, model.name, auto_service.name " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "join chat on auto.id = chat.auto_id " +
                    "join message on chat.id = message.chat_id " +
                    "join auto_service on chat.auto_service_id = auto_service.id " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "' " +
                    "group by 5;");

            while (resultSet.next()) {
                chats.add(
                        resultSet.getInt("chat.id"),
                        new Chat(
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
            resultSet = statement.executeQuery("select " +
                    "message.text " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join chat on auto.id = chat.auto_id " +
                    "join message on chat.id = message.chat_id " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "' and chat.id = '" + idChat + "';");

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
            resultSet = statement.executeQuery("select " +
                    "auto.vin, auto.reg_number, brand.name, model.name, auto_service.name " +
                    "from account " +
                    "join auto on account.id = auto.account_id " +
                    "join chat on auto.id = chat.auto_id " +
                    "join auto_service on chat.auto_service_id = auto_service.id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "' and chat.id = '" + idChat + "';");

            while (resultSet.next()) {
                chat = new Chat(
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
}
