package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RootHandler implements HttpHandler {

    @Override

    public void handle(HttpExchange he) throws IOException {
        String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + 8500 + "</h1>";
        if (he.getRequestMethod().equalsIgnoreCase("GET")){
            EchoGetHandler echoGetHandler = new EchoGetHandler();
            echoGetHandler.handle(he);
        }
        else if (he.getRequestMethod().equalsIgnoreCase("POST")) {
            EchoPostHandler echoPostHandler = new EchoPostHandler();
            echoPostHandler.handle(he);
        }
        else {
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static void parseQuery(String query, Map<String,
            Object> parameters) {

        if (query != null) {
            String[] pairs = query.split("[&]");
            for (String pair : pairs) {
                String[] param = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            StandardCharsets.UTF_8);
                }

                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                            StandardCharsets.UTF_8);
                }

                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);

                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }
}
