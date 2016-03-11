/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.NoResultException;
import java.util.*;

import java.io.IOException;

/**
 * Definition of the chat Message entity
 * Contains:
 - ID
 - username (author)
 - message (content)
 */

@Entity
@Table(name="CHATMESSAGES")
public class ChatMessage implements Serializable {

    @Id
    @GeneratedValue
    private int id;             // id

    private String username;    // username
    private String message;     // message

    public ChatMessage(){}

    public ChatMessage(String username, String message){
      this.username = username;
      this.message = message;
    }


    //  GETTERS
    public int getId() {
      return id;
    }

    public String getUserName() {
      return username;
    }

    public String getMessage() {
      return message;
    }


    // SETTERS

    public void setId(int id) {
      this.id = id;
    }

    public void setUserName(String username) {
      this.username = username;
    }

    public void setMessage(String username) {
      this.message = message;
    }


    // UTIL METHODS

    public String toString(){
      return id + "." + username + ": "  + message;
    }

    public static ArrayList<ChatMessage> getMessages(EntityManager entityManager, int idLastSeen){
        //query select * users where user = user
        ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
        String querySQL = "select m from ChatMessage m where m.id > :idLastSeen"; //ORDER BY m.id desc

        ChatMessage msg = null;
        try {
            Query query = entityManager.createQuery(querySQL)
            .setParameter("idLastSeen", idLastSeen)
            .setMaxResults(50);
            for (Object result : query.getResultList())
                chatMessages.add((ChatMessage)result);  // cast the obj result from query to User obj
        } catch (NoResultException e){return null;}
        return chatMessages;
    }

}
