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



@WebServlet("/logout")
public class LogoutServletClient extends HttpServlet {

    @Inject
    private EntityManager entityManager;

    // @Override
    // protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //
    //     resp.setContentType("text/html");
    //     PrintWriter out = resp.getWriter();
    //
    //
    //     Query query = entityManager.createQuery("select u from User u where u.username = :username");
    //     query.setParameter("username", "nik");
    //     User user = (User) query.getSingleResult();
    //     out.write("<p>Sending messages to <em>" + user.getPassword() + "</em></p>");
    //
    //     if(req.getParameterMap().keySet().contains("login")) {
    //
    //         out.write("<h1>Fi-fah-foo!</h1>");
    //         out.write("<h2>/auth?login:</h2>");
    //         out.write("<p>Setting loggedIn=true in the session, check /home</p>");
    //         out.write("<p>You are now logged in! :)</p>");
    //
    //         HttpSession sess = req.getSession();
    //         sess.setAttribute("loggedIn", true);
    //     }
    //
    //     if(req.getParameterMap().keySet().contains("logout")) {
    //         out.write("<h1>Fi-fah-foo!</h1>");
    //         out.write("<h2>/auth?logout:</h2>");
    //         out.write("<p>Deleting loggedIn from the session, check /home</p>");
    //         out.write("<p>You are now logged out! :)</p>");
    //
    //         HttpSession sess = req.getSession();
    //         sess.removeAttribute("loggedIn");
    //     }
    //
    //     out.close();
    // }



    // private void login()

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

    @Inject
    private UserDao userDao;
    private boolean logout(String username) {
        //query select * users where user = user
        String querySQL = "select u from User u where u.username = :username";
        User user = null;
        try {
            Query query = entityManager.createQuery(querySQL);
            query.setParameter("username", username);
            user = (User) query.getSingleResult(); // retrieve user from result
        } catch (NoResultException e){
            //System.out.println("NoResultException" + e);
            return false;
        }
        user.setLoggedIn(false);
        userDao.loggedInFalse(user);
        return true;
    }

}
