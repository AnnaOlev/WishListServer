package server;

import com.sun.net.httpserver.*;
import models.User;
import models.WishList;
import service.UserService;

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
        User user = userService.findUser("test");
        //User user = new User("annaolevskayaass", "05042000");
        //userService.saveUser(user);
        WishList wishList = new WishList(99, "новый год", "forOther");
        user.addList(wishList);
        userService.updateUser(user);*/
    }
}
