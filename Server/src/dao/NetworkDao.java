package dao;

import network.Network;

import java.util.ArrayList;

public class NetworkDao {
    public static class NetworkDaoSingle {
        public static final NetworkDao INSTANCE = new NetworkDao();
    }
    private ArrayList<Network> threads = new ArrayList<>();
    public static final NetworkDao getInstance() {
        return NetworkDaoSingle.INSTANCE;
    }
    public void addServerThread(Network network) {
        threads.add(network);
    }

}
