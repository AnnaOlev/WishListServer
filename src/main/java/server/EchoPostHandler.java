package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.ListElement;
import models.User;
import models.WishList;
import service.ListElementService;
import service.UserService;
import service.WishListService;

import javax.persistence.criteria.CriteriaBuilder;
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

        if (parameters.get("type").equals("auth")) {

            String responseString = URLEncoder.encode("Авторизация неудачна", StandardCharsets.UTF_8);
            UserService userService = new UserService();
            User user;
            user = userService.findUser(parameters.get("username").toString());
            if (user != null)
                if (user.getPassword().equals(parameters.get("password"))) {
                    responseString = URLEncoder.encode("Авторизация успешна", StandardCharsets.UTF_8);
                }

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

        else if (parameters.get("type").equals("registration")) {
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

        else if (parameters.get("type").equals("newList")) {
            UserService userService = new UserService();
            WishListService wishListService = new WishListService();

            User user = userService.findUser(parameters.get("username").toString());
            WishList wishList = new WishList(Integer.parseInt(parameters.get("id").toString()), parameters.get("title").toString(),
                    parameters.get("forWho").toString());
            wishList.setUser(user);
            wishListService.saveWishList(wishList);
            //userService.updateUser(user);
            StringBuilder response = new StringBuilder();
            response.append(URLEncoder.encode("Список добавлен", StandardCharsets.UTF_8));
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }

        else if (parameters.get("type").equals("newItem")) {
            ListElement listElement = new ListElement(Integer.parseInt(parameters.get("id").toString()),
                    parameters.get("title").toString(), parameters.get("text").toString());
            WishListService wishListService = new WishListService();
            WishList wishList = wishListService.findWishList(Integer.parseInt(parameters.get("listId").toString()));
            listElement.setWishList(wishList);
            ListElementService listElementService = new ListElementService();
            listElementService.saveListElement(listElement);
            StringBuilder response = new StringBuilder();
            response.append(URLEncoder.encode("Элемент добавлен", StandardCharsets.UTF_8));
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }


    }
}
