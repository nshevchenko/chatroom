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
        int idLastSeenMessage = -1;
        if(idLastSeenMessageStr != null)
            idLastSeenMessage = Integer.parseInt(idLastSeenMessageStr);
        else
            System.out.println("int idLastSeenMessage is NULL ");
        // get online users through sql query
        ArrayList<ChatMessage> messages = ChatMessage.getMessages(entityManager, username);

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
}
