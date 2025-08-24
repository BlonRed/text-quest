
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.the.killers.textquest.model.QuestPageServlet;
import org.the.killers.textquest.model.entity.Scene;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestPageServletTest {

    private QuestPageServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        servlet = new QuestPageServlet();

        servlet.init();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testStartScene() throws IOException {
        when(request.getParameter("mode")).thenReturn("Start");
        when(session.getAttribute("username")).thenReturn("Илья");

        servlet.doGet(request, response);

        // Проверка редиректа
        verify(response).sendRedirect("/quest-page.jsp");

        // Проверка, что в сессии лежит сцена
        ArgumentCaptor<Scene> captor = ArgumentCaptor.forClass(Scene.class);
        verify(session).setAttribute(eq("scene"), captor.capture());

        Scene scene = captor.getValue();
        assertNotNull(scene);
        assertTrue(scene.getText().contains("Илья"), "В текст сцены должно подставиться имя пользователя");
    }

    @Test
    void testOtherSceneYes() throws IOException {
        when(request.getParameter("mode")).thenReturn("Start");
        when(session.getAttribute("username")).thenReturn("Иван");
        servlet.doGet(request, response);

        when(request.getParameter("mode")).thenReturn("Yes");
        servlet.doGet(request, response);

        ArgumentCaptor<Scene> captor = ArgumentCaptor.forClass(Scene.class);
        verify(session, atLeast(2)).setAttribute(eq("scene"), captor.capture());

        Scene lastScene = captor.getValue();
        assertNotNull(lastScene);
        assertNotEquals("s1", lastScene.getCode(), "Должна измениться сцена после Yes");
    }

    @Test
    void testOtherSceneNo() throws IOException {
        when(request.getParameter("mode")).thenReturn("Start");
        when(session.getAttribute("username")).thenReturn("Петр");
        servlet.doGet(request, response);

        when(request.getParameter("mode")).thenReturn("No");
        servlet.doGet(request, response);

        ArgumentCaptor<Scene> captor = ArgumentCaptor.forClass(Scene.class);
        verify(session, atLeast(2)).setAttribute(eq("scene"), captor.capture());

        Scene lastScene = captor.getValue();
        assertNotNull(lastScene);
    }
}