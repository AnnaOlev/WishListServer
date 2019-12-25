package server;

import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.InetSocketAddress;

public class WishServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        System.out.println("server started at port 8500");
        server.createContext("/", new RootHandler());
        server.setExecutor(null);
        server.start();
    }
}
