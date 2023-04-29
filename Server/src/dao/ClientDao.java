package dao;

import com.mysql.cj.protocol.x.XMessage;
import model.*;

import java.sql.*;
import java.util.ArrayList;

public class ClientDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public ClientDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aggregator", "root", "1234");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCar(Client client, Car car) {
        try {
            int idAccount = 0, idModel = 0, idClient = 0;

            resultSet = statement.executeQuery("select " +
                    "model.idModel, model.Name, Brand.Name " +
                    "from Model " +
                    "join Brand on Model.Brand_idBrand = Brand.idBrand");

            while (resultSet.next()) {
                if (resultSet.getString("model.Name").equals(car.getModel()) && resultSet.getString("brand.Name").equals(car.getBrand()))
                    idModel = resultSet.getInt("model.idModel");
            }

            resultSet = statement.executeQuery("select account.id " +
                    "from account where account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                idAccount = resultSet.getInt("id");
            }

            statement.executeUpdate("insert into client (`account_id`) values('" + idAccount + "');");

            resultSet = statement.executeQuery("select " +
                    "client.idClient " +
                    "from account " +
                    "join client on account.id = client.account_id " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                idClient = resultSet.getInt("idClient");
            }

            String sql = "insert into auto (`vin`, `reg_number`, `model_idModel`, `client_idClient`) " +
                    "values('" + car.getVINNumber() + "', '" + car.getRegNumber() + "', '" + idModel + "', '" + idClient + "');";
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
                    "join client on account.id = client.account_id " +
                    "join auto on client.idClient = auto.client_idClient " +
                    "join model on auto.model_idModel = model.idModel " +
                    "join brand on model.brand_idBrand = brand.idBrand " +
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
            String client_idClient = null;
            resultSet = statement.executeQuery("select " +
                    "client.idClient, auto.vin " +
                    "from account " +
                    "join client on account.id = client.account_id " +
                    "join auto on client.idClient = auto.client_idClient " +
                    "join model on auto.model_idModel = model.idModel " +
                    "join brand on model.brand_idBrand = brand.idBrand " +
                    "where " +
                    "account.Login = '" + client.getLogin() + "' and account.Password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                if (resultSet.getString("auto.vin").equals(request.getCar().getVINNumber()))
                    client_idClient = resultSet.getString("client.idClient");
            }

            String sql;
            sql = "insert into Request(idRequest, Description, Client_idClient, Status) values(last_insert_id(), '" + request.getDescription() + "', " + client_idClient + ", '" + request.getStatus() + "');";
            statement.executeUpdate(sql);

         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    public ArrayList<RequestForClient> getAllClientRequest(Client client) {
        ArrayList<RequestForClient> requests = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select " +
                    "request.idRequest, auto.Reg_number, request.Description, auto_service.name, request.Status " +
                    "from account " +
                    "join client on account.id = client.account_id " +
                    "join auto on client.idClient = auto.client_idClient " +
                    "join request on request.client_idClient = client.idClient " +
                    "left join auto_service on request.auto_service_idAuto_service = idAuto_service " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                requests.add(new RequestForClient(Integer.parseInt(resultSet.getString("request.idRequest")), resultSet.getString("auto.reg_number"), resultSet.getString("request.description"), resultSet.getString("auto_service.name"), resultSet.getString("request.status")));
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
                    "answer.idAnswer, auto.Reg_number, Auto_service.name, answer.Status " +
                    "from answer " +
                    "join request on request.idRequest = answer.request_idRequest " +
                    "join auto_service on answer.auto_service_idAuto_service = auto_service.idAuto_service " +
                    "join client on client.idClient = request.client_idClient " +
                    "join auto on client.idClient = auto.client_idClient " +
                    "join account on account.id = client.account_id " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                answers.add(new AnswerAutoService(Integer.parseInt(resultSet.getString("idAnswer")), resultSet.getString("reg_number"), resultSet.getString("Name"), resultSet.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answers;
    }

    public ArrayList<CarService> getCarServiceByName() {
        ArrayList<CarService> carServices = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select * from auto_Service");
            while (resultSet.next()) {
                carServices.add(new CarService(resultSet.getNString("Name"), resultSet.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carServices;
    }

    public void acceptRequestForClient(Client client, int idAnswer) {
        try {
            int auto_service_idAuto_service = 0, idRequest = 0, idClient = 0;

            resultSet = statement.executeQuery("select " +
                    "answer.idAnswer, answer.Request_idRequest, answer.Auto_Service_idAuto_Service, client.idClient " +
                    "from account " +
                    "join client on account.id = client.account_id " +
                    "join request on client.idClient = request.client_idClient " +
                    "join answer on answer.request_idRequest = request.idRequest " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                if (resultSet.getInt("answer.idAnswer") == idAnswer) {
                    idRequest = Integer.parseInt(resultSet.getString("answer.request_idRequest"));
                    idClient = Integer.parseInt(resultSet.getString("client.idClient"));
                    auto_service_idAuto_service = Integer.parseInt(resultSet.getString("answer.auto_service_idAuto_service"));
                }
            }

            String sql = "update request set auto_service_idAuto_service = '" + auto_service_idAuto_service + "', status = " + "'Accept'" + " where (idRequest = '" + idRequest + "' and client_idClient = '" + idClient + "');";
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createChat(Client client, int id) {
        try {
            int idAutoService = 0, idClient = 0;
            resultSet = statement.executeQuery("select " +
                    "auto_service.idAuto_service " +
                    "from account " +
                    "join client on client.account_id = account.id " +
                    "join request on request.Client_idClient = client.idClient " +
                    "join answer on answer.request_idRequest = request.idRequest " +
                    "join auto_service on answer.auto_service_idAuto_service = auto_service.idAuto_service " +
                    "where " +
                    "answer.idAnswer = '" + id + "' and account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                idAutoService = resultSet.getInt("idAuto_service");
            }

            resultSet = statement.executeQuery("select " +
                    "client.idClient " +
                    "from account " +
                    "join client on client.account_id = account.id " +
                    "join request on request.Client_idClient = client.idClient " +
                    "join answer on answer.request_idRequest = request.idRequest " +
                    "where " +
                    "answer.idAnswer = '" + id + "' and account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                idClient = resultSet.getInt("idClient");
            }

            statement.executeUpdate("insert into chat(client_idClient, auto_service_idAuto_service) " +
                    "values ('" + idClient + "', '" + idAutoService + "');");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendClientMassage(Client client, int idChat, String message) {
        try {

            resultSet = statement.executeQuery("select " +
                    "chat.idChat " +
                    "from account " +
                    "join client on account.id = client.account_id " +
                    "join chat on client.idClient = chat.client_idClient " +
                    "where " +
                    "account.login ='" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                if (resultSet.getInt("idChat") == idChat) {
                    statement.executeUpdate("insert into message (`text`, `chat_idChat`) values ('" + message + "', '" + idChat + "');");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Chat> getChatsForClient(Client client) {
        ArrayList<Chat> chats = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select " +
                    "auto.vin, auto.reg_number, brand.name, model.name, auto_service.name " +
                    "from account " +
                    "join client on account.id = client.account_id " +
                    "join auto on client.idClient = auto.client_idClient " +
                    "join model on auto.model_idModel = model.idModel " +
                    "join brand on model.brand_idBrand = brand.idBrand " +
                    "join chat on client.idClient = chat.client_idClient " +
                    "join message on chat.idChat = message.chat_idChat " +
                    "join auto_service on chat.auto_service_idAuto_service = auto_service.idAuto_service " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "' " +
                    "group by 5;");

            while (resultSet.next()) {
                chats.add(
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

    public ArrayList<Message> getMessagesByChat(Client client, Chat chat) {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select " +
                    "message.text " +
                    "from account " +
                    "join client on account.id = client.account_id " +
                    "join chat on client.idClient = chat.client_idClient " +
                    "join message on chat.idChat = message.chat_idChat " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "';");

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
                    "auto.vin, auto.reg_number, brand.name, model.name, auto_service.name  " +
                    "from account " +
                    "join client on account.id = client.account_id " +
                    "join chat on client.idClient = chat.client_idClient " +
                    "join auto on client.idClient = auto.client_idClient " +
                    "join model on auto.model_idModel = model.idModel " +
                    "join brand on model.brand_idBrand = brand.idBrand " +
                    "where " +
                    "account.login = '" + client.getLogin() + "' and account.password = '" + client.getPassword() + "' and chat.client_idClient = '" + idChat + "';");
        chat = new Chat(
                new Car(
                        resultSet.getString("auto.vin"),
                        resultSet.getString("auto.reg_number"),
                        resultSet.getString("brand.name"),
                        resultSet.getString("model.name")
                ),
                resultSet.getString("auto_service.name")
        );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return chat;
    }
}
