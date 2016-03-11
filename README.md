chatroom RMI
============================================================
Author: Mykola Shevchenko, Henrik Skogmo
Technologies: JMS, EJB, MDB, JSON, JPA  
Summary: The `chatroom` webapp uses *JMS* and *EJB Message-Driven Bean* (MDB) to create and deploy a fully functional chat.  
Target Product: WildFly  
Source: <https://github.com/nshevchenko/chatroom>  

What is it?
-----------

The `chatroom` projects demonstrates the usage of EJB with implementation of multiple endpoints and a front-end in Java.


    ![alt tag](/chatExample.png)


This project creates the following endpoints resources:
 - login
 - register
 - logout
 - getFriends
 - addFriend
 - removeFriend
 - privacy ( everyone | friends | noone)

System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform 7 or later.

All you need to build this project is Java 8.0 (Java SDK 1.8) or later and Maven 3.1.1 or later. See [Configure Maven for WildFly 10](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN_JBOSS_EAP7.md#configure-maven-to-build-and-deploy-the-quickstarts) to make sure you are configured correctly for testing the quickstarts.


Use of WILDFLY_HOME
---------------

In the following instructions, replace `WILDFLY_HOME` with the actual path to your WildFly installation. The installation path is described in detail here: [Use of WILDFLY_HOME and JBOSS_HOME Variables](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/USE_OF_EAP7_HOME.md#use-of-eap_home-and-jboss_home-variables).


Start the WildFly Server with the Full Profile
---------------

1. Open a command prompt and navigate to the root of the WildFly directory.
2. The following shows the command line to start the server with the full profile:

        For Linux:   WILDFLY_HOME/bin/standalone.sh -c standalone-full.xml
        For Windows: WILDFLY_HOME\bin\standalone.bat -c standalone-full.xml


Build and Deploy the Quickstart
-------------------------

1. Make sure you have started the WildFly server as described above.
2. Open a command prompt and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean install wildfly:deploy

Access the application
---------------------

The application will be running at the following URL: <http://localhost:8080/wildfly-helloworld-mdb/chat//> and will send some messages to the queue.

To send messages to the topic, use the following URL: <http://localhost:8080/wildfly-helloworld-mdb/HelloWorldMDBServletClient?topic>


Debug the Application
------------------------------------

Use the chat with multiple tabs to register new user and send messages.
Play around with the privacy settings and add users as friends.


<!-- Build and Deploy the Quickstart to OpenShift - Coming soon! -->
