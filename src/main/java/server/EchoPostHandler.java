package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.ListElement;
import models.User;
import models.WishList;
import service.ListElementService;
import service.UserService;
import service.WishListService;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            User user = userService.findUser(parameters.get("username").toString());
            WishList wishList = new WishList(Integer.parseInt(parameters.get("id").toString()), parameters.get("title").toString(),
                    parameters.get("forWho").toString());
            wishList.setUser(user);
            user.addList(wishList);
            userService.updateUser(user);
            System.out.println(userService.findUser(parameters.get("username").toString()).toString());
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

        else if (parameters.get("type").equals("downloadLists")) {
            String responseString = "empty";
            List<WishList> wishLists;
            User user;
            UserService userService = new UserService();
            user = userService.findUser(parameters.get("user").toString());
            wishLists = user.getWishLists();

            StringBuilder response = new StringBuilder();
            if (!wishLists.isEmpty()) {
                for (WishList wishList : wishLists) {
                    response.append(wishList.toStringResponse()).append(";");
                }
                responseString = response.toString();
            }
            System.out.println(responseString);
            responseString = URLEncoder.encode(responseString, StandardCharsets.UTF_8);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(responseString);
            he.sendResponseHeaders(200, stringBuilder.length());
            OutputStream os = he.getResponseBody();
            os.write(stringBuilder.toString().getBytes());
            os.close();
        }

        else if (parameters.get("type").equals("downloadItems")) {
            String responseString = "empty";
            List<WishList> wishLists;
            List<ListElement> listElements = new ArrayList<>();
            User user;
            UserService userService = new UserService();
            user = userService.findUser(parameters.get("user").toString());
            wishLists = user.getWishLists();
            for (WishList wishList : wishLists) {
                if (!wishList.getListElements().isEmpty())
                    listElements.addAll(wishList.getListElements());
            }

            StringBuilder response = new StringBuilder();
            if (!listElements.isEmpty()) {
                for (ListElement listElement : listElements) {
                    response.append(listElement.toStringResponseElem()).append(";");
                }
                responseString = response.toString();
            }
            System.out.println(responseString);
            responseString = URLEncoder.encode(responseString, StandardCharsets.UTF_8);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(responseString);
            he.sendResponseHeaders(200, stringBuilder.length());
            OutputStream os = he.getResponseBody();
            os.write(stringBuilder.toString().getBytes());
            os.close();

        }
    }
}


