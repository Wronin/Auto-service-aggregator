package dao;

import model.Client;
import network.Network;

import java.beans.PropertyEditorSupport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class NetworkDao {
    public static class NetworkDaoSingle {
        public static final NetworkDao INSTANCE = new NetworkDao();
    }
    public static NetworkDao getInstance() {
        return NetworkDaoSingle.INSTANCE;
    }

    public NetworkDao() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/aggregator", "root", "1234");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Statement statement;
    private ResultSet resultSet;
    private ArrayList<Network> threads = new ArrayList<>();
    public void addServerThread(Network network) {
        threads.add(network);
    }

    public boolean findMatchesLP(Client client) {
        try {
            resultSet = statement.executeQuery(String.format("select count(*) from account " +
                    "where account.login = '%s' and account.password = '%s';", client.getLogin(), client.getPassword()));
            while (resultSet.next()) {
                if (resultSet.getInt("count(*)") == 1)
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean findMatchesL(String login) {
        try {
            resultSet = statement.executeQuery(String.format("select count(*) from account " +
                    "where account.login = '%s';", login));
            while (resultSet.next()) {
                if (resultSet.getInt("count(*)") == 1)
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void registerClient(Client client) {
        try {
            statement.executeUpdate(String.format("insert into account (`login`, `password`) values('%s', '%s');", client.getLogin(), client.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
