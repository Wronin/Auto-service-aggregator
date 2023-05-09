package dao;

import network.Network;

import java.util.ArrayList;

public class NetworkDao {
    public static class NetworkDaoSingle {
        public static final NetworkDao INSTANCE = new NetworkDao();
    }
    public static NetworkDao getInstance() {
        return NetworkDaoSingle.INSTANCE;
    }
    private ArrayList<Network> threads = new ArrayList<>();
    public void addServerThread(Network network) {
        threads.add(network);
    }

}
