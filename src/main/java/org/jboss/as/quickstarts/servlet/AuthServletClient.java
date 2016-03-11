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



@WebServlet("/auth")
public class AuthServletClient extends HttpServlet {

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
        String password = jsonParser.getValueByKey("password");
        // write response
        resp.setContentType("application/json");
        JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
        JsonObject model = null;
        if(auth(username, password)){
            model = Json.createObjectBuilder()
                    .add("SUCCESS", "TRUE")
                    .add("username", username)
                    .add("password", password)
                    .build();
        } else {
            model = Json.createObjectBuilder()
                    .add("SUCCESS", "FALSE")
                    .add("username", username)
                    .add("password", password)
                    .build();
        }
        jsonWriter.writeObject(model);
        jsonWriter.close();
    }

    @Inject
    private UserDao userDao;
    private boolean auth(String username, String password){
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
        // check for login & set loggedin = true
        if(user.getPassword().equals(password)){

            user.setLoggedIn(true);
            userDao.loggedInTrue(user);
            return true;
        }
        // otherwise return false
        return false;
    }
}
