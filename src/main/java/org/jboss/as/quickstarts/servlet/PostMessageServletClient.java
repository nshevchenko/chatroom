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

import org.jboss.as.quickstarts.model.JSONParserKeyValue;
import org.jboss.as.quickstarts.model.ChatMessage;
import org.jboss.as.quickstarts.model.ChatMsgDao;
import org.jboss.as.quickstarts.model.User;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.NoResultException;
import java.util.*;

import java.io.IOException;
/**
 * Created by nik on 10/03/2016.
 */


 /**
  * Servlet responsible for storing messages by logged in users
  */
@WebServlet("/postMessage")
public class PostMessageServletClient extends HttpServlet {

    @Inject
    private ChatMsgDao chatMsgDao;

    @Inject
    private EntityManager entityManager;

    /**
     * Post request responsible to check if user's loggedin to send message
     * Persists the msg obj in database
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONParserKeyValue jsonParser = new JSONParserKeyValue(req);    // json parser
        String username = jsonParser.getValueByKey("username");         // get username as get paramater
        String message = jsonParser.getValueByKey("message");

        if (User.userIsLoggedIn(entityManager, username)){
            ChatMessage chatMsg = new ChatMessage(username, message);
            // entityManager.persist(chatMsg);
            System.out.println("persisting");
            chatMsgDao.createChatMessage(chatMsg);
            // persist object message here
        }
        // write response
        resp.setContentType("application/json");
        final JsonGenerator generator = Json.createGenerator(resp.getWriter());     // init json generator

        generator.writeStartObject();    // start obj
        generator.write("RESULT", "200");
        generator.writeEnd();
        generator.close();  // close
    }
}
