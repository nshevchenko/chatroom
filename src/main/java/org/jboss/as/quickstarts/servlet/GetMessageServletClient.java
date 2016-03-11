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
import org.jboss.as.quickstarts.model.ChatMessage;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.NoResultException;

/**
 * Created by nik on 10/03/2016.
 */



@WebServlet("/getMessages")
public class GetMessageServletClient extends HttpServlet {

    @Inject
    private EntityManager entityManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONParserKeyValue jsonParser = new JSONParserKeyValue(req);    // json parser
        String username = jsonParser.getValueByKey("username");         // get username as get paramater
        String idLastSeenMessageStr = jsonParser.getValueByKey("idLastSeen");
        int idLastSeenMessage = Integer.parseInt(idLastSeenMessageStr);
        // get online users through sql query
        ArrayList<ChatMessage> messages = getMessages(username);

        // write response
        resp.setContentType("application/json");
        final JsonGenerator generator = Json.createGenerator(resp.getWriter());     // init json generator

        generator.writeStartObject();    // start obj
        int count = 0;
        for(ChatMessage msg : messages){
            generator.write(msg.getId() + "", msg.getMessage());     // loop through users
        }
        generator.writeEnd();
        generator.close();  // close
    }

    private ArrayList<ChatMessage> getMessages(String username){
        //query select * users where user = user
        ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
        String querySQL = "select top 10 msg from CHATMESSAGE msg";
        ChatMessage msg = null;
        try {
            Query query = entityManager.createQuery(querySQL);
            for (Object result : query.getResultList())
                chatMessages.add((ChatMessage)result);  // cast the obj result from query to User obj
        } catch (NoResultException e){return null;}
        return chatMessages;
    }
}