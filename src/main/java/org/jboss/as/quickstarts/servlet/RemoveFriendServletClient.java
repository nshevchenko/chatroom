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
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonParsingException;

import java.io.IOException;
import org.jboss.as.quickstarts.model.JSONParserKeyValue;
import org.jboss.as.quickstarts.model.User;
import org.jboss.as.quickstarts.model.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.NoResultException;
/**
 * Created by hs on 10/03/2016.
 */


 /**
  * Servlet responsible for logging out user
  * return TRUE or FALSE on result
  * sets user.loggedIn = FALSE;
  */

@WebServlet("/removeFriend")
public class RemoveFriendServletClient extends HttpServlet {

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserDao userDao;

    /**
     * Post request calls logout method
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Parse json request data
        // String data = getJsonDataFromRequest(req);
        JSONParserKeyValue jsonParser = new JSONParserKeyValue(req);
        String username = jsonParser.getValueByKey("username");

        // write response
        resp.setContentType("application/json");
        JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
        JsonObject model = null;
        if(logout(username)){
            model = Json.createObjectBuilder()
                    .add("SUCCESS", "TRUE")
                    .add("username", username)
                    .build();
        } else {
            model = Json.createObjectBuilder()
                    .add("SUCCESS", "FALSE")
                    .add("username", username)
                    .build();
        }
        jsonWriter.writeObject(model);
        jsonWriter.close();
    }

    /**
     * logout method, sets user's property LoggedIn = false
     */
    private boolean logout(String username) {
        //query select * users where user = user
        User user = User.getUserFromUsername(entityManager, username);
        user.setLoggedIn(false);
        userDao.loggedInFalse(user);
        return true;
    }

}
