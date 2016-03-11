package org.jboss.as.quickstarts.servlet;

import javax.jms.Destination;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hs on 10/03/2016.
 */
@WebServlet("/home")
public class HomeServletClient extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {

            HttpSession sess = req.getSession();
            boolean loggedIn = (boolean) sess.getAttribute("loggedIn");





            // out.write("<p>Sending messages to <em>" + user.getPassword() + "</em></p>");


            if(loggedIn) {
                out.write("<h1>Fi-fah-foo!</h1>");
                out.write("<h2>/home:</h2>");
                out.write("<p>Welcome home! Only logged in users can see this ;)</p>");
                // out.write("<p>Total users loggedin : " + users.size() + " </p>");
            }

        } catch(NullPointerException e) {

            out.write("<h1>Fi-fah-foo!</h1>");
            out.write("<h2>/home:</h2>");
            out.write("<p>Access denied! You must be logged in!</p>");

        }

        out.close();
    }


}
