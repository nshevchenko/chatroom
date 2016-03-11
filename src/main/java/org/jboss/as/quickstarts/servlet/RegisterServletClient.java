package org.jboss.as.quickstarts.servlet;

import org.jboss.as.quickstarts.model.JSONParserKeyValue;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hs on 11/03/2016.
 */
@WebServlet("/auth")
public class RegisterServletClient extends HttpServlet {
    @Inject
    private EntityManager entityManager;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Get the username and password
        JSONParserKeyValue jsonParser = new JSONParserKeyValue(req);
        String username = jsonParser.getValueByKey("username");
        String password = jsonParser.getValueByKey("password");

        resp.setContentType("application/json");

        JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
        JsonObject model = null;

        if(unique(username)) {
            boolean register = register(username,password);
        }
    }

    private boolean unique(String username) {
        return true;
    }

    private boolean register(String username, String password) {
        return true;
    }

}
