package org.jboss.as.quickstarts.servlet;

import javax.jms.Destination;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonParsingException;
import java.util.*;

import java.io.StringReader;
import java.io.BufferedReader;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // JSONParserKeyValue jsonParser = new JSONParserKeyValue(req);

        // StringBuilder sb = new StringBuilder();
        // BufferedReader br = req.getReader();
        // String str;
        // while( (str = br.readLine()) != null ){
        //     sb.append(str);
        // }
        // System.out.println("idLastSeenidLastSeenidLastSeenidLastSeenidLastSeen " + req.getParameter("idLastSeen"));

        //
        //  Enumeration<String> parameterNames = req.getParameterNames();
        //  Systemz.out.println(parameterNames.toString());
        //  while (parameterNames.hasMoreElements()) {
        //     String paramName = parameterNames.nextElement();
        //     System.out.println(" paramName " + paramName);
        //     System.out.println(" paramName END" );
        // }
        JSONParserKeyValue jsonParser = new JSONParserKeyValue(req);    // json parser
        String idLastSeenMessageStr = jsonParser.getValueByKey("idLastSeen");         // get username as get paramater
        // String idLastSeenMessageStr = "";
        System.out.println("idLastSeenMessageStr  " + idLastSeenMessageStr);
        int idLastSeenMessage = -1;
        if(idLastSeenMessageStr != null && !idLastSeenMessageStr.equals(""))
            idLastSeenMessage = Integer.parseInt(idLastSeenMessageStr);
        else
            System.out.println("int idLastSeenMessage is NULL ");
        // get online users through sql query
        ArrayList<ChatMessage> messages = ChatMessage.getMessages(entityManager, idLastSeenMessage);

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
