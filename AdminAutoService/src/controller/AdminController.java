package controller;

import model.*;
import org.json.simple.JSONObject;
import org.json.simple.JsonObject;

import javax.naming.ldap.SortKey;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class AdminController {

    public ArrayList<Request> getRequests(Socket socket, String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getRequests");
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        ArrayList<Request> requests = new ArrayList<>();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                requests.add(
                        new Request(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine(),
                                new Car(
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine()
                                ),
                                bufferedReader.readLine()
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public ArrayList<Request> getAllAdminRequest(Socket socket, String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getAllAdminRequest");
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        ArrayList<Request> requestForAutoServices = new ArrayList<>();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                requestForAutoServices.add(
                        new Request(
                                Integer.parseInt(bufferedReader.readLine()),
                                bufferedReader.readLine(),
                                new Car(
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine()
                                ),
                                Status.valueOf(bufferedReader.readLine())
                        ));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return requestForAutoServices;
    }

    public Request getCurrentAdminRequest(Socket socket, String login, String password, int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getCurrentAdminRequest");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("id", id);

        Request request = new Request();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            request = new Request(
                    Integer.parseInt(bufferedReader.readLine()),
                    bufferedReader.readLine(),
                    new Car(
                            bufferedReader.readLine(),
                            bufferedReader.readLine(),
                            bufferedReader.readLine(),
                            bufferedReader.readLine()
                    ),
                    bufferedReader.readLine()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return request;
    }

    public void acceptRequestForAdmin(Socket socket, String login, String password, int idRequest) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "acceptRequestForAdmin");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("id", idRequest);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptRequestForAdminWithServices(Socket socket, String login, String password, int idRequest, ArrayList<Service> services) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "acceptRequestForAdminWithServices");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("idRequest", idRequest);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());

            printWriter.println(services.size());
            for (Service service : services) {
                printWriter.println(service.getId());
                printWriter.println(service.getName());
                printWriter.println(service.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeStatusServiceRequest(Socket socket, String login, String password, int idRequest, int idService, String status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "changeStatusServiceRequest");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("idRequest", idRequest);
        jsonObject.put("idService", idService);
        jsonObject.put("status", status);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Chat> getChatsForAdmin(Socket socket, String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getChatsForAdmin");
        jsonObject.put("login", login);
        jsonObject.put("password", password);

        ArrayList<Chat> chats = new ArrayList<>();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                chats.add(
                        new Chat(
                                Integer.parseInt(bufferedReader.readLine()),
                                new Car(
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine(),
                                        bufferedReader.readLine()
                                ),
                                bufferedReader.readLine()
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chats;
    }

    public Chat getCurrentChatForAdmin(Socket socket, String login, String password, int idChat) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "getCurrentChatForAdmin");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("idChat", idChat);

        Chat chat = new Chat();
        ArrayList<Message> messages = new ArrayList<>();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            chat = new Chat(
                    Integer.parseInt(bufferedReader.readLine()),
                    new Car(
                            bufferedReader.readLine(),
                            bufferedReader.readLine(),
                            bufferedReader.readLine(),
                            bufferedReader.readLine()
                    ),
                    bufferedReader.readLine()
            );

            int size = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < size; i++) {
                messages.add(new Message(bufferedReader.readLine()));
            }
            chat.setMessages(messages);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return chat;
    }

    public void sendAdminMassage(Socket socket, String login, String password, int idChat, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("func", "sendAdminMassage");
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("idChat", idChat);
        jsonObject.put("message", message);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
