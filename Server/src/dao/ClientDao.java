package dao;

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
            String idModel = null, idAuto = null;

            resultSet = statement.executeQuery("select model.idModel, model.Name, Brand.Name from Model join Brand on Model.Brand_idBrand = Brand.idBrand");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("model.Name") + " " + resultSet.getString("Brand.Name"));
                if (resultSet.getString("model.Name").equals(car.getModel()) && resultSet.getString("brand.Name").equals(car.getBrand()))
                    idModel = resultSet.getString("model.idModel");
            }

            String sql;

            sql = "insert into auto values(last_insert_id(), '" + car.getVINNumber() + "', '" + car.getRegNumber() + "', " + idModel + ");";
            statement.executeUpdate(sql);

            resultSet = statement.executeQuery("select * from auto;");
            while (resultSet.next()) {
                if (resultSet.getString("VIN").equals(car.getVINNumber()))
                    idAuto = resultSet.getString("idAuto");
            }

            sql = "insert into client values(last_insert_id(), '" + client.getLogin() + "', '" + client.getPassword() + "', " + idAuto + ");";
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Car> getCarList(Client client) {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select " +
                    "brand.name, model.name, auto.reg_number, auto.vin, client.login, auto.reg_number " +
                    "from client " +
                    "join auto on client.auto_idAuto = auto.idAuto " +
                    "join model on auto.model_idModel = model.idModel " +
                    "join brand on model.Brand_idBrand = brand.idBrand " +
                    "where client.Login = '" + client.getLogin() + "' and client.Password = '" + client.getPassword() + "';");

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
                    "client.idClient, client.Login, client.Password, Model.Name, Brand.Name, client.Auto_idAuto, auto.vin " +
                    "from Client " +
                    "join Auto on client.Auto_idAuto = Auto.idAuto " +
                    "join Model on Auto.Model_idModel = Model.idModel " +
                    "join Brand on Model.Brand_idBrand = Brand.idBrand "  +
                    "where " +
                    "client.Login = '" + client.getLogin() + "' and client.Password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("model.name") + " "
                        + resultSet.getString("brand.name") + " "
                        + resultSet.getString("client.Auto_idAuto"));
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
                    "request.idRequest, auto.Reg_number, request.Description, auto_service.login, request.Status " +
                    "from Request " +
                    "join client on client.idClient = request.Client_idClient " +
                    "join auto on client.auto_idAuto = auto.idAuto " +
                    "left join auto_service on request.auto_service_idAuto_service = idAuto_service " +
                    "where " +
                    "client.login = '" + client.getLogin() + "' and client.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                requests.add(new RequestForClient(Integer.parseInt(resultSet.getString("request.idRequest")), resultSet.getString("auto.reg_number"), resultSet.getString("request.description"), resultSet.getString("auto_service.login"), resultSet.getString("request.status")));
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
                    "answer.idAnswer, auto.Reg_number, Auto_service.login, answer.Status " +
                    "from answer " +
                    "join request on request.idRequest = answer.request_idRequest " +
                    "join client on client.idClient = request.Client_idClient " +
                    "join auto on client.auto_idAuto = auto.idAuto " +
                    "join auto_service on answer.auto_service_idAuto_service = auto_service.idAuto_service " +
                    "where " +
                    "client.login = '" + client.getLogin() + "' and client.password = '" + client.getPassword() + "';");

            while (resultSet.next()) {
                answers.add(new AnswerAutoService(Integer.parseInt(resultSet.getString("idAnswer")), resultSet.getString("reg_number"), resultSet.getString("login"), resultSet.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answers;
    }

    public ArrayList<CarService> getCarServices(String name) {
        ArrayList<CarService> carServices = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select * from auto_Service");
            while (resultSet.next()) {
                carServices.add(new CarService(resultSet.getNString("login"), resultSet.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carServices;
    }

    public void acceptRequest(Client client, int idAnswer) {
        try {
            int auto_service_idAuto_service = 0, idRequest = 0, idClient = 0;
//            resultSet = statement.executeQuery("select " +
//                    "answer.auto_service_idAuto_service " +
//                    "from answer " +
//                    "join request on request.idRequest = answer.request_idRequest " +
//                    "join client on client.idClient = request.client_idClient " +
//                    "where " +
//                    "client.login = '" + client.getLogin() + "' and client.password = '" + client.getPassword() + "';");
//            while (resultSet.next()) {
//                if (resultSet.getInt("answer.auto_service_idAuto_service") ==  idAnswer) {
//                    auto_service_idAuto_service = resultSet.getInt("answer.auto_service_idAuto_service");
//                }
//            }

            resultSet = statement.executeQuery("select " +
                    "answer.idAnswer, answer.Request_idRequest, answer.Auto_Service_idAuto_Service, request.idRequest, client.idClient " +
                    "from answer " +
                    "join request on request.idRequest = answer.request_idRequest " +
                    "join client on client.idClient = request.client_idClient " +
                    "where " +
                    "client.login = '" + client.getLogin() + "' and client.password = '" + client.getPassword() + "';");
            while (resultSet.next()) {
                if (Integer.parseInt(resultSet.getString("answer.idAnswer")) == idAnswer) {
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
}
