package org.the.killers.textquest.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {
    private String mode;
    private HttpSession session;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        session = req.getSession();
        mode = req.getParameter("mode");

        switch (mode) {
            case "Login":
                getServletContext().getRequestDispatcher("/init").forward(req, resp);
                break;
            case "Start":
                incrementCountTries();
                getServletContext().getRequestDispatcher("/quest-page").forward(req, resp);
                break;
            case "Yes":
            case "No":
                getServletContext().getRequestDispatcher("/quest-page").forward(req, resp);
                break;
            case "Finish":
                incrementWins();
                resp.sendRedirect("/start.jsp");
                break;
            case "Restart":
            default:
                resp.sendRedirect("/start.jsp");
        }

    }

    private void incrementCountTries() {
        Integer countTries = (Integer) session.getAttribute("countTries");
        countTries = countTries == null ? 1 : countTries + 1;
        session.setAttribute("countTries", countTries);
    }
    private void incrementWins(){
        Integer countWins = (Integer) session.getAttribute("countWins");
        countWins = countWins == null ? 1 : countWins + 1;
        session.setAttribute("countWins", countWins);
    }
}
