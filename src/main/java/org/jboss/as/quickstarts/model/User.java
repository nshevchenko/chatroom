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
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.NoResultException;
/**
 * Definition of the chat Message entity
 * Contains:
 - ID
 - username (author)
 - message (content)
 - loggedIn (boolean)
 */

@Entity
@Table(name="USERS")
public class User implements Serializable {


    @Id
    @GeneratedValue
    private int id;     // primary key ID

    @Column(unique = true)
    private String username;    //username

    private String password;    // password

    private boolean loggedIn;   // session information

    private String friends;
    /*
    Privacy ID :
        0 - everyone
        1 - friendsStr
        2 - none
    */
    private int privacy;

    // empty constructor
    public User(){
        privacy = 0;
        friends = "";
    }


    // Constructor with username and password
    public User(String username, String password){
      this.username = username;
      this.password = password;
      this.loggedIn = false;
      friends = "";
      privacy = 0;
    }


    // Construtor with all user fields
    public User(String username, String password, boolean loggedIn){
      this.username = username;
      this.password = password;
      this.loggedIn = loggedIn;
      friends = "";
      privacy = 0;
    }

    //  GETTERS

    // get ID
    public int getId() {
      return id;
    }

    // get username
    public String getUsername() {
      return username;
    }

    // get username
    public String getPassword() {
      return password;
    }

    // get friendsStr
    public String getFriendsStr(){
        return friends;
    }

    // privacy
    public int getPrivacy(){
        return privacy;
    }

    // get username
    public boolean isLoggedIn() {
      return loggedIn;
    }

    // SETTERS

    // set id
    public void setId(int id) {
      this.id = id;
    }

    //set username
    public void setUseranme(String username) {
      this.username = username;
    }

    // set password
    public void setPassword(String password) {
      this.password = password;
    }

    // set privacy
    public void setPrivacy(int privacy){
        this.privacy = privacy;
    }

    // set if user is logged in
    public void setLoggedIn(boolean loggedIn) {
      this.loggedIn = loggedIn;
    }

    // UTIL METHODS

    // add friend
    public void addFriend(String username){
        friends += username + ",";
    }

    // remove friend
    public void removeFriend(String username){
        String[] friendsStr = friends.split(",");
        String result = "";

        for(int i = 0; i < friendsStr.length; i++){
            System.out.println(username + " " + friendsStr[i]);
            if(!friendsStr[i].equals(username)){
                result += friendsStr[i] + ",";
            }
        }
        System.out.println("new result " + result);
        friends = result;
    }

    public String toString(){
      return id + " " + username;
    }

    // check if a user is logged in
    public static boolean userIsLoggedIn(EntityManager em, String username){
        ArrayList<String> onlineUsers = getOnlineUsers(em, username);
        if(onlineUsers.contains(username))
            return true;
        return false;
    }


    // get a list of online users
    public static ArrayList<String> getOnlineUsers(EntityManager entityManager, String username){
        //query select * users where user = user
        ArrayList<String> onlineUsers = new ArrayList<String>();
        String querySQL = "select u from User u";
        User user = null;
        try {
            Query query = entityManager.createQuery(querySQL);
            for (Object result : query.getResultList()) {
                user = (User)result;           // cast the obj result from query to User obj
                if(user.isLoggedIn())               // add online one user
                    onlineUsers.add(user.getUsername());
            }
        } catch (NoResultException e){return null;}
        return onlineUsers;
    }

    // get user from query
    public static User getUserFromUsername(EntityManager entityManager, String username) {
        User user = null;
        String querySQL = "select u from User u where u.username = :username";
        try {
            Query query = entityManager.createQuery(querySQL);
            query.setParameter("username", username);
            user = (User) query.getSingleResult(); // retrieve user from result
        } catch (NoResultException e){
            System.out.println("NoResultException" + e);
            return null;
        }
        return user;
    }
}
