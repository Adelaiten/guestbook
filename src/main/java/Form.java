import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.CommentsDao;
import dao.CommentsDaoInterface;
import models.Comment;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form implements HttpHandler {
    private CommentsDaoInterface commentsDao;
    Form(Connection connection){
        this.commentsDao = new CommentsDao(connection);
    }
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            try {
                getMethod(httpExchange);
            } catch (SQLException e) {
                String response = "404";
                httpExchange.sendResponseHeaders(404, response.getBytes().length);
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println("Wrong sql query");
            }


        } else if (method.equals("POST")) {
            InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            System.out.println(line);
            Map inputs = parseFormData(line);
            Date date = new Date();
            String today = 2018 + "." + date.getMonth() + "." + date.getDay();
            System.out.println(today);
            try {
                commentsDao.addComment(inputs.get("name").toString(), inputs.get("description").toString(), today);
                getMethod(httpExchange);
            }catch(IOException | SQLException e){
                e.printStackTrace();
            }
        }

    }
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private void getMethod(HttpExchange httpExchange) throws IOException, SQLException{
        List<Comment> commentsList = commentsDao.readComments();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("jtwig/index.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();
        model.with("commentsList", commentsList);
        // render a template to a string
        String response = template.render(model);
        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}