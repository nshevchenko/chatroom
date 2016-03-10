package org.jboss.as.quickstarts.servlet;

import javax.jms.Destination;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import java.io.StringReader;
import java.io.BufferedReader;

/**
 * Created by hs on 10/03/2016.
 */



@WebServlet("/auth")
public class AuthServletClient extends HttpServlet {

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


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Parse json request data
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = (BufferedReader)req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        final JsonParser parser = Json.createParser(new StringReader(data));
        while (parser.hasNext()) {
            final Event event = parser.next();
            String key = parser.getString();
            System.out.println("key " + key);
        }



        // String key = null;
        // String value = null;
        // while (parser.hasNext()) {
        //     final Event event = parser.next();
        //     switch (event) {
        //         case KEY_NAME:
        //             key = parser.getString();
        //             System.out.println(key);
        //             System.out.println(username +"\n\n");
        //             break;
        //         case VALUE_STRING:
        //             value = parser.getString();
        //             System.out.println(value);
        //             break;
        //     }
        // }
        parser.close();

        // Find






    }
}
