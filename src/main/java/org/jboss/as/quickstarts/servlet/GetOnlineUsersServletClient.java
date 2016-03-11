package org.jboss.as.quickstarts.servlet;

import javax.jms.Destination;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonParsingException;
import java.util.*;
import javax.persistence.EntityManager;
import java.io.IOException;
import org.jboss.as.quickstarts.model.JSONParserKeyValue;
import org.jboss.as.quickstarts.model.User;
/**
 * Created by hs on 10/03/2016.
 */

@WebServlet("/getOnlineUsers")
public class GetOnlineUsersServletClient extends HttpServlet {

    @Inject
    private EntityManager entityManager;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONParserKeyValue jsonParser = new JSONParserKeyValue(req);    // json parser
        String username = jsonParser.getValueByKey("username");         // get username as get paramater

        // get online users through sql query
        ArrayList<String> onlineUsers = User.getOnlineUsers(entityManager, username);
        for(String asdf : onlineUsers)
            System.out.println("username element : " + asdf);
        // write response
        resp.setContentType("application/json");
        final JsonGenerator generator = Json.createGenerator(resp.getWriter());     // init json generator

        generator.writeStartObject();    // start obj
        int count = 0;
        for(String str : onlineUsers){
            generator.write(count+"", str);     // loop through users
            count++;    // id as key
        }
        generator.writeEnd();
        generator.close();  // close
    }
}
