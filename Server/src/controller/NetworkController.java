package controller;

import dao.NetworkDao;
import model.Client;

public class NetworkController {
    public static class NetworkControllerSingle {
        public static final NetworkController INSTANCE = new NetworkController();
    }
    public static NetworkController getInstance() {
        return NetworkController.NetworkControllerSingle.INSTANCE;
    }

    public boolean authorization(Client client) {
        return NetworkDao.getInstance().findMatchesLP(client);
    }

    public boolean registration(Client client) {
        if (NetworkDao.getInstance().findMatchesL(client.getLogin())) {
            NetworkDao.getInstance().registerClient(client);
            return true;
        }
        return false;
    }
}
