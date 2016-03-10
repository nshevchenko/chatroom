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
@WebServlet("/auth")
public class AuthServletClient extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        if(req.getParameterMap().keySet().contains("login")) {

            out.write("<h1>Fi-fah-foo!</h1>");
            out.write("<h2>/auth?login:</h2>");
            out.write("<p>Setting loggedIn=true in the session, check /home</p>");
            out.write("<p>You are now logged in! :)</p>");

            HttpSession sess = req.getSession();
            sess.setAttribute("loggedIn", true);
        }

        if(req.getParameterMap().keySet().contains("logout")) {
            out.write("<h1>Fi-fah-foo!</h1>");
            out.write("<h2>/auth?logout:</h2>");
            out.write("<p>Deleting loggedIn from the session, check /home</p>");
            out.write("<p>You are now logged out! :)</p>");

            HttpSession sess = req.getSession();
            sess.removeAttribute("loggedIn");
        }

        out.close();
    }


}
