import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.the.killers.textquest.controller.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class ControllerTest {

    private Controller controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ServletContext servletContext;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws ServletException {
        controller = new Controller();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        servletContext = mock(ServletContext.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);

        ServletConfig config = mock(ServletConfig.class);
        when(config.getServletContext()).thenReturn(servletContext);

        controller.init(config);
    }

    @Test
    void testLoginForward() throws ServletException, IOException {
        when(request.getParameter("mode")).thenReturn("Login");
        when(servletContext.getRequestDispatcher("/init")).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testStartIncrementsTries() throws ServletException, IOException {
        when(request.getParameter("mode")).thenReturn("Start");
        when(servletContext.getRequestDispatcher("/quest-page")).thenReturn(dispatcher);

        when(session.getAttribute("countTries")).thenReturn(2);

        controller.doGet(request, response);

        verify(dispatcher).forward(request, response);

        verify(session).setAttribute("countTries", 3);
    }

    @Test
    void testStartFirstTimeSetsCountTriesTo1() throws ServletException, IOException {
        when(request.getParameter("mode")).thenReturn("Start");
        when(servletContext.getRequestDispatcher("/quest-page")).thenReturn(dispatcher);

        when(session.getAttribute("countTries")).thenReturn(null);

        controller.doGet(request, response);

        verify(session).setAttribute("countTries", 1);
    }

    @Test
    void testYesForward() throws ServletException, IOException {
        when(request.getParameter("mode")).thenReturn("Yes");
        when(servletContext.getRequestDispatcher("/quest-page")).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testNoForward() throws ServletException, IOException {
        when(request.getParameter("mode")).thenReturn("No");
        when(servletContext.getRequestDispatcher("/quest-page")).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testFinishIncrementsWinsAndRedirects() throws ServletException, IOException {
        when(request.getParameter("mode")).thenReturn("Finish");
        when(session.getAttribute("countWins")).thenReturn(5);

        controller.doGet(request, response);

        verify(session).setAttribute("countWins", 6);
        verify(response).sendRedirect("/start.jsp");
    }

    @Test
    void testFinishFirstWin() throws ServletException, IOException {
        when(request.getParameter("mode")).thenReturn("Finish");
        when(session.getAttribute("countWins")).thenReturn(null);

        controller.doGet(request, response);

        verify(session).setAttribute("countWins", 1);
        verify(response).sendRedirect("/start.jsp");
    }

    @Test
    void testRestartRedirect() throws ServletException, IOException {
        when(request.getParameter("mode")).thenReturn("Restart");

        controller.doGet(request, response);

        verify(response).sendRedirect("/start.jsp");
    }

    @Test
    void testDefaultRedirect() throws ServletException, IOException {
        when(request.getParameter("mode")).thenReturn("SomethingElse");

        controller.doGet(request, response);

        verify(response).sendRedirect("/start.jsp");
    }
}
