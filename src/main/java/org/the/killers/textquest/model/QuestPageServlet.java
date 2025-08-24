package org.the.killers.textquest.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.the.killers.textquest.model.entity.Scene;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "questPageServlet", value = "/quest-page")
public class QuestPageServlet extends HttpServlet {
    private List<Scene> scenes;
    private Scene scene;
    private String mode;
    private HashMap<String, Scene> mapScenes;

    @Override
    public void init() {
        mapScenes = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            scenes = objectMapper.readValue(this.getClass().getResourceAsStream("/scenes.json"), new TypeReference<List<Scene>>() {
            });
            for (Scene sc : scenes) {
                mapScenes.put(sc.getCode(), sc);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        mode = request.getParameter("mode");
        if (mode.equals("Start")) {
           setStartScene(session);
        } else if (mode.equals("Yes") || mode.equals("No")) {
            setOtherScene(session);
        }
        response.sendRedirect("/quest-page.jsp");
    }

    private void setStartScene(HttpSession session) {
        scene = mapScenes.get("s1");
        String text = scene.getText();
        String username = (String) session.getAttribute("username");
        text = text.replace("${username}", username);
        scene.setText(text);
        session.setAttribute("scene", scene);
    }

    private void setOtherScene(HttpSession session) {
        scene = mapScenes.getOrDefault(scene.getVariations().stream().filter(x -> x.getAnswer().equals(mode)).findAny().get().getNext(), mapScenes.get("sLose"));
        session.setAttribute("scene", scene);
    }
}