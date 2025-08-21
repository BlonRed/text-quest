package org.the.killers.textquest.model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "InitServlet", value = "/init")
public class InitServlet extends HttpServlet {
    private String username;
    private Integer countTries;
    private Integer countWins;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        if (!checkAlreadyInit()) {
            username = req.getParameter("username");
            countTries = 0;
            countWins = 0;
        }

        session.setAttribute("username", username);
        session.setAttribute("countTries", countTries);
        session.setAttribute("countWins", countWins);

        resp.sendRedirect("/start.jsp");
    }

    public boolean checkAlreadyInit(){
        return username != null;
    }
}
