package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.User;
import service.UserService;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static server.RootHandler.parseQuery;

public class EchoPostHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        // parse request
        Map<String, Object> parameters = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(he.getRequestBody(), StandardCharsets.UTF_8));
        String query = br.readLine();
        if (query!=null)
            System.out.println(query);
        parseQuery(query, parameters);

        if (parameters.containsKey("username")) {

            String responseString = URLEncoder.encode("Авторизация неудачна", StandardCharsets.UTF_8);
            UserService userService = new UserService();
            User user;
            user = userService.findUser(parameters.get("username").toString());
            if (user != null)
                if (user.getPassword().equals(parameters.get("password"))) {
                    responseString = URLEncoder.encode("Авторизация успешна", StandardCharsets.UTF_8);
                }
            /*User user = new User("annaolev", "05042000");
            UserService userService = new UserService();
            WishList wishList = new WishList(2, "новый год", "forOther");
            wishList.setUser(user);
            WishListService wishListService = new WishListService();
            ListElement listElement = new ListElement(5, parameters.get("title").toString(), parameters.get("text").toString());
            wishList.addElement(listElement);
            wishListService.updateWishList(wishList);
            userService.updateUser(user);*/
            //это пример работы с бд, который потом будет тусить не тут
            // send response
            StringBuilder response = new StringBuilder();
            response.append(responseString);
            /*for (String key : parameters.keySet())
                response.append(key).append(" = ").append(URLEncoder.encode(parameters.get(key).toString(), StandardCharsets.UTF_8)).append("\n");*/
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
            }

        else if (parameters.containsKey("newusername")) {
            UserService userService = new UserService();
            User user;
            user = userService.findUser(parameters.get("newusername").toString());
            String responseString = URLEncoder.encode("Регистрация успешна", StandardCharsets.UTF_8);
            if (user != null){
                responseString = URLEncoder.encode("Логин уже занят", StandardCharsets.UTF_8);
            }
            else {
                user = new User(parameters.get("newusername").toString(), parameters.get("newpassword").toString());
                userService.saveUser(user);
            }
            StringBuilder response = new StringBuilder();
            response.append(responseString);
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }
}
