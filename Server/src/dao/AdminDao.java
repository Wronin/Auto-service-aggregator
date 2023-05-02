package dao;

import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
                    "request.id, request.description, model.name, brand.name, auto.reg_number, auto.vin, request.status " +
                    "from request " +
                    "join auto on request.auto_id = auto.id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "request.auto_service_id is NULL;");

            while (resultSet.next()) {
                Status status = switch (resultSet.getString("request.status")) {
                    case "SEARCH" -> Status.SEARCH;
                    default -> null;
                };

                requests.add(
                        new Request(
                                resultSet.getInt("request.id"),
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

    public void acceptRequestForAdmin(ServiceAdmin serviceAdmin, int id) {
        try {
            int idAuto_Service = 0;

            resultSet = statement.executeQuery(String.format("select " +
                    "auto_service.id " +
                    "from account " +
                    "join auto_service on account.id = auto_service.account_id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s';", serviceAdmin.getLogin(), serviceAdmin.getPassword()));

            while (resultSet.next()) {
                idAuto_Service = resultSet.getInt("id");
            }

            String sql;
            sql = String.format("insert into answer (`Status`, `auto_service_id`, `request_id`) values('Expect', '%d', '%d');", idAuto_Service, id);
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptRequestForAdminWithServices(ServiceAdmin serviceAdmin, int id, ArrayList<Service> services) {
        try {
            int idAuto_Service = 0;

            resultSet = statement.executeQuery(String.format("select " +
                    "auto_service.id " +
                    "from account " +
                    "join auto_service on account.id = auto_service.account_id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s';", serviceAdmin.getLogin(), serviceAdmin.getPassword()));

            while (resultSet.next()) {
                idAuto_Service = resultSet.getInt("id");
            }

            for (Service service: services) {
                statement.executeUpdate(String.format("insert into answer (`status`, `auto_service_id`, `request_id`, autoService_service_id`) values ('Expect', '%d', '%d', '%d');", idAuto_Service, id, service.getId()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Chat> getChatsForAdmin(ServiceAdmin serviceAdmin) {
        ArrayList<Chat> chats = new ArrayList<>();
        try {

            resultSet = statement.executeQuery(String.format("select " +
                    "chat.id, auto.vin, auto.reg_number, model.name, brand.name, auto_service.name " +
                    "from account " +
                    "join auto_service on account.id = auto_service.account_id " +
                    "join chat on auto_service.id = chat.auto_service_id " +
                    "join auto on chat.auto_id = auto.id " +
                    "join model on auto.model_id = model.id " +
                    "join brand on model.brand_id = brand.id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s';", serviceAdmin.getLogin(), serviceAdmin.getPassword()));

            while (resultSet.next()) {
                chats.add(
                        new Chat(
                                resultSet.getInt("id"),
                                new Car(
                                        resultSet.getString("auto.vin"),
                                        resultSet.getString("auto.reg_number"),
                                        resultSet.getString("model.name"),
                                        resultSet.getString("brand.name")
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

    public void sendAdminMassage(ServiceAdmin serviceAdmin, int idChat, String message) {
        try {
            int id = 0;

            resultSet = statement.executeQuery(String.format("select " +
                    "chat.id " +
                    "from account " +
                    "join auto_service on account.id = auto_service.account_id " +
                    "join chat on auto_service.id = chat.auto_service_id " +
                    "where " +
                    "account.login = '%s' and account.password = '%s' and chat.id = '%d';", serviceAdmin.getLogin(), serviceAdmin.getPassword(), idChat));

            while (resultSet.next()) {
                if (resultSet.getInt("chat.id") == idChat) {
                    id = resultSet.getInt("chat.id");
                }
            }

            statement.executeUpdate(String.format("insert into message (`text`, `chat_id`) values ('%s', '%d');", message, id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
