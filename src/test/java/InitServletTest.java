import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.the.killers.textquest.model.InitServlet;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InitServletTest {

    private InitServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        servlet = new InitServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testFirstInitSetsValuesFromRequest() throws IOException {
        when(request.getParameter("username")).thenReturn("Иван");

        servlet.doGet(request, response);

        verify(session).setAttribute("username", "Иван");
        verify(session).setAttribute("countTries", 0);
        verify(session).setAttribute("countWins", 0);

        verify(response).sendRedirect("/start.jsp");

        assertTrue(servlet.checkAlreadyInit(), "После первого вызова должен считаться инициализированным");
    }

    @Test
    void testSecondInitDoesNotOverrideUsername() throws IOException {
        when(request.getParameter("username")).thenReturn("Иван");
        servlet.doGet(request, response);

        when(request.getParameter("username")).thenReturn("Мария");
        servlet.doGet(request, response);

        verify(session, atLeastOnce()).setAttribute("username", "Иван");
        verify(session, never()).setAttribute("username", "Мария");
    }

    @Test
    void testCheckAlreadyInitReturnsFalseInitially() {
        assertFalse(servlet.checkAlreadyInit(), "До первого вызова сервлет не инициализирован");
    }
}
