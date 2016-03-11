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

import java.io.IOException;
import org.jboss.as.quickstarts.model.JSONParserKeyValue;
import org.jboss.as.quickstarts.model.User;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.NoResultException;
/**
 * Created by hs on 10/03/2016.
 */



@WebServlet("/getOnlineUsers")
public class GetOnlineUsersServletClient extends HttpServlet {

    @Inject
    private EntityManager entityManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONParserKeyValue jsonParser = new JSONParserKeyValue(req);
        String username = jsonParser.getValueByKey("username");

        // get online users through sql query
        ArrayList<String> onlineUsers = getOnlineUsers(username);

        // write response
        resp.setContentType("application/json");
        final JsonGenerator generator = Json.createGenerator(resp.getWriter());

        generator.writeStartObject();
        int count = 0;
        for(String str : onlineUsers){
            generator.write(count+"", str);
            count++;
        }
        generator.writeEnd();
        generator.close();

    }

    private ArrayList<String> getOnlineUsers(String username){
        //query select * users where user = user
        ArrayList<String> onlineUsers = new ArrayList<String>();
        String querySQL = "select u from User u";
        User user = null;
        try {
            Query query = entityManager.createQuery(querySQL);
            for (Object result : query.getResultList()) {
                onlineUsers.add(((User)result).getUsername());
            }
        } catch (NoResultException e){
            // System.out.println("NoResultException" + e);
            return null;
        }
        return onlineUsers;
    }
}
