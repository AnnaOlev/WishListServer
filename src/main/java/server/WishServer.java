package server;

import com.sun.net.httpserver.*;
import models.ListElement;
import models.User;
import models.WishList;
import service.UserService;
import service.WishListService;

import java.io.IOException;
import java.net.InetSocketAddress;


public class WishServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        System.out.println("server started at port 8500");
        server.createContext("/", new RootHandler());
        server.setExecutor(null);
        server.start();

        /*UserService userService = new UserService();
        User user = new User("annaolev", "05042000");
        userService.saveUser(user);
        WishList wishList = new WishList(2, "новый год", "forOther");
        WishListService wishListService = new WishListService();
        wishList.setUser(user);
        wishListService.saveWishList(wishList);
        ListElement listElement = new ListElement(1, "игруха", "чето красивое");
        wishList.addElement(listElement);
        wishListService.updateWishList(wishList);
        userService.updateUser(user);*/
    }
}
