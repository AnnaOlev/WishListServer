package server;

import com.sun.net.httpserver.*;
import models.ListElement;
import models.User;
import models.WishList;
import service.UserService;
import service.WishListService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class WishServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(WishServer::handleRequest);

        UserService userService = new UserService();
        User user = new User("annaolev", "05042000");
        userService.saveUser(user);
        WishList wishList = new WishList(2, "новый год", "forOther");
        WishListService wishListService = new WishListService();
        wishList.setUser(user);
        wishListService.saveWishList(wishList);
        ListElement listElement = new ListElement(1, "игруха", "чето красивое");
        wishList.addElement(listElement);
        wishListService.updateWishList(wishList);
        userService.updateUser(user);


        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        printRequestInfo(exchange);

        String response = "This is the response at " + requestURI;
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void printRequestInfo(HttpExchange exchange) {
        System.out.println("-- headers --");
        Headers requestHeaders = exchange.getRequestHeaders();
        requestHeaders.entrySet().forEach(System.out::println);

        System.out.println("-- principle --");
        HttpPrincipal principal = exchange.getPrincipal();
        System.out.println(principal);

        System.out.println("-- HTTP method --");
        String requestMethod = exchange.getRequestMethod();
        System.out.println(requestMethod);

        System.out.println("-- query --");
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        System.out.println(query);
    }
}
