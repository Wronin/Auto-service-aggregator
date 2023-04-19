package dao;

import model.Car;
import model.Request;
import model.ServiceAdmin;
import model.Status;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class AdminDao {
//    public static class AdminDaoSingle {
//        public static final AdminDao INSTANCE = new AdminDao();
//        public static AdminDao getInstance() {
//            return AdminDaoSingle.INSTANCE;
//        }
//    }
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public AdminDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aggregator", "root", "1234");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Request> getAllAdminRequest(ServiceAdmin serviceAdmin) {
        ArrayList<Request> requests = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select " +
                    "request.idrequest, request.description, model.Name, brand.Name, auto.reg_number, auto.vin, request.status " +
                    "from request " +
                    "join client on client.idClient = request.client_idclient " +
                    "join auto on client.auto_idAuto = auto.idauto " +
                    "join model on auto.model_idModel = model.idmodel " +
                    "join brand on model.brand_idBrand = brand.idbrand " +
                    "where request.auto_service_idAuto_service is NULL ");

            while (resultSet.next()) {
                Status status = switch (resultSet.getString("request.status")) {
                    case "SEARCH" -> Status.SEARCH;
                    default -> null;
                };

                requests.add(
                        new Request(
                                Integer.parseInt(resultSet.getString("request.idrequest")),
                                resultSet.getString("request.description"),
                                new Car(
                                        resultSet.getString("auto.vin"),
                                        resultSet.getString("auto.reg_number"),
                                        resultSet.getString("brand.name"),
                                        resultSet.getString("model.name")
                                ),
                                status
                                )
                        );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }

    public void acceptRequest(ServiceAdmin serviceAdmin, int id) {
        try {
            int idAuto_Service = 0;

            resultSet = statement.executeQuery("select * from auto_service;");

            while (resultSet.next()) {
                if (resultSet.getString("login").equals(serviceAdmin.getLogin()))
                    idAuto_Service = Integer.parseInt(resultSet.getString("idAuto_Service"));
            }

            String sql;
            sql = "insert into answer values(last_insert_id(), " + id + ", " + idAuto_Service + ", 'Expect');";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createChat(ServiceAdmin serviceAdmin, Request request) {
        try {
            int idClient = 0, idAuto_service = 0;
            String sql = "select " +
                    "client.idclient " +
                    "from request " +
                    "join client on client.idclient = request.idrequest;";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("client.idClient"));
                if (Integer.parseInt(resultSet.getString("idClient")) == request.getId())
                    idClient = Integer.parseInt(resultSet.getString("idClient"));
            }
            sql = "select * from auto_service;";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (Objects.equals(resultSet.getString("login"), serviceAdmin.getLogin()))
                    idAuto_service = Integer.parseInt(resultSet.getString("idAuto_Service"));
            }

            statement.executeUpdate("insert into chat(Client_idClient, Auto_service_idAuto_service) " +
                    "values(" + idClient + ", " + idAuto_service + ");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
