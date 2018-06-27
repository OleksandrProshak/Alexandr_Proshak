package ru.job4j.servlet.presentation;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.crudservlet.logic.ValidateService;
import ru.job4j.crudservlet.logic.entity.User;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static ru.job4j.servlet.presentation.UserCreateServlet.PARAMETER_USER_ID;
import static ru.job4j.servlet.presentation.UserCreateServlet.PARAMETER_USER_NAME;
import static ru.job4j.servlet.presentation.UserCreateServlet.PARAMETER_USER_LOGIN;
import static ru.job4j.servlet.presentation.UserCreateServlet.PARAMETER_USER_EMAIL;
import static ru.job4j.servlet.presentation.UserCreateServlet.ATTRIBUTE_STORAGE;
import static uk.org.lidalia.slf4jtest.LoggingEvent.error;

/**
 * The UserCreateServlet's class test.
 */
public class UserCreateServletTest {

    /**
     * The servlet's reference.
     */
    private UserCreateServlet servlet;

    /**
     * The request's reference.
     */
    private HttpServletRequest request;

    /**
     * The response's reference.
     */
    private HttpServletResponse response;

    /**
     * The session's reference.
     */
    private HttpSession session;

    /**
     * The special test logger.
     */
    private TestLogger logger;

    /**
     * The set upping method initial data.
     */
    @Before
    public void setUp() {
        this.servlet = new UserCreateServlet();
        this.request = mock(HttpServletRequest.class);
        this.response = mock(HttpServletResponse.class);
        this.session = mock(HttpSession.class);
        this.logger = TestLoggerFactory.getTestLogger(UserCreateServlet.class);
    }

    /**
     * The cleaning data.
     */
    @After
    public void clearLoggers() {
        TestLoggerFactory.clear();
    }

    /**
     * The doGet's method test.
     * @throws IOException exception.
     */
    @Test
    public void whenCallThanShowCreateFields() throws IOException {
        //init
        PrintWriter writer = mock(PrintWriter.class);
        when(request.getContextPath()).thenReturn("http://localhost:8080/item");
        when(response.getWriter()).thenReturn(writer);
        String result =  "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<p>Write user's info into the form below</p>"
                + "<form action='http://localhost:8080/item/create' method='post'>"
                + "  <fieldset>"
                + "    <legend>Personal user's information:</legend>"
                + "    id:<br><input type='text' name='id'><br>"
                + "    name: <br><input type='text' name='name'><br>"
                + "    login:<br><input type='text' name='login'><br>"
                + "    email:<br><input type='email' name='email'><br>"
                + "          <br><input type='submit' value='create'><br><br>"
                + " </form>"
                + " <form action='http://localhost:8080/item/list'>"
                + "     <button type='submit'>show all users</button>"
                + " </form>"
                + " </fieldset>"
                + "</body>"
                + "</html>";
        //use
        servlet.doGet(request, response);

        //check
        verify(response).getWriter();
        verify(request, atLeastOnce()).getContextPath();
        verify(response).setContentType("text/html");
        verify(writer).append(result);
        verify(writer).flush();
        verifyNoMoreInteractions(request, response, session, writer);
    }

    /**
     * The doPost's method test in the case of creating a new user with an existing id in a db.
     * @throws IOException exception.
     */
    @Test
    public void whenCreateNewUserWitExistingIdThanReturnSpecialMassage() throws IOException {
        //init
        PrintWriter writer = mock(PrintWriter.class);
        ValidateService serviceMemory = mock(ValidateService.class);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("http://localhost:8080/item");
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(ATTRIBUTE_STORAGE)).thenReturn(serviceMemory);
        when(request.getParameter(PARAMETER_USER_ID)).thenReturn("2");
        when(request.getParameter(PARAMETER_USER_NAME)).thenReturn("name");
        when(request.getParameter(PARAMETER_USER_LOGIN)).thenReturn("login");
        when(request.getParameter(PARAMETER_USER_EMAIL)).thenReturn("user@mail.ru");
        when(serviceMemory.add(new User())).thenReturn(false);
        String result = "User with id: 2 is already used. "
                + "New user was not created. "
                + "To create new user please select id which is not used in the db.";
        String resultDoGet =  "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<p>Write user's info into the form below</p>"
                + "<form action='http://localhost:8080/item/create' method='post'>"
                + "  <fieldset>"
                + "    <legend>Personal user's information:</legend>"
                + "    id:<br><input type='text' name='id'><br>"
                + "    name: <br><input type='text' name='name'><br>"
                + "    login:<br><input type='text' name='login'><br>"
                + "    email:<br><input type='email' name='email'><br>"
                + "          <br><input type='submit' value='create'><br><br>"
                + " </form>"
                + " <form action='http://localhost:8080/item/list'>"
                + "     <button type='submit'>show all users</button>"
                + " </form>"
                + " </fieldset>"
                + "</body>"
                + "</html>";

        //use
        servlet.doPost(request, response);

        //check
        verify(response, times(2)).setContentType("text/html");
        verify(response, times(2)).getWriter();
        verify(request, atLeastOnce()).getContextPath();
        verify(request).getSession();
        verify(session, atLeastOnce()).getAttribute(ATTRIBUTE_STORAGE);
        verify(request).getParameter(PARAMETER_USER_ID);
        verify(request).getParameter(PARAMETER_USER_NAME);
        verify(request).getParameter(PARAMETER_USER_LOGIN);
        verify(request).getParameter(PARAMETER_USER_EMAIL);
        verify(writer).append(result);
        verify(writer).append(resultDoGet);
        verify(writer, times(2)).flush();
        verifyNoMoreInteractions(request, response, session, writer);
    }

    /**
     * The doPost's method test in the case of creating a new user with incorrect id.
     * @throws IOException exception.
     */
    @Test (expected = AssertionError.class)
    public void whenUserIdIsNotNumberThanItLogError() throws IOException {
        //init
        PrintWriter writer = mock(PrintWriter.class);
        ValidateService serviceMemory = mock(ValidateService.class);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("http://localhost:8080/item");
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(ATTRIBUTE_STORAGE)).thenReturn(serviceMemory);
        when(request.getParameter(PARAMETER_USER_ID)).thenReturn("abc");
        String result = "Something incorrect, please check the data";

        //use
        servlet.doPost(request, response);

        //check
        verify(response, times(2)).setContentType("text/html");
        verify(response, times(2)).getWriter();
        verify(request, times(2)).getContextPath();
        verify(request, times(2)).getSession();
        verify(session, times(2)).getAttribute(ATTRIBUTE_STORAGE);
        verify(request, times(1)).getParameter(PARAMETER_USER_ID);
        verify(writer, times(1)).append(result);
        verify(writer, times(2)).flush();
        assertThat(logger.getLoggingEvents(), is(asList(error("abc"))));
        verifyNoMoreInteractions(request, response, session, writer);
    }

    /**
     * The doPost's method test in the case of creating a new user in a db.
     * @throws IOException exception.
     */
    @Ignore
    @Test
    public void whenCreateNewUserThanItCreatedAndAddToDb() throws IOException {
        //init
        PrintWriter writer = mock(PrintWriter.class);
        ValidateService serviceMemory = mock(ValidateService.class);
        when(request.getSession()).thenReturn(session);
        User user = mock(User.class);
        when(serviceMemory.add(user)).thenReturn(true);
        when(request.getContextPath()).thenReturn("http://localhost:8080/item");
        when(response.getWriter()).thenReturn(writer);
        when(session.getAttribute(ATTRIBUTE_STORAGE)).thenReturn(serviceMemory);
        when(request.getParameter(PARAMETER_USER_ID)).thenReturn("2");
        when(request.getParameter(PARAMETER_USER_NAME)).thenReturn("name");
        when(request.getParameter(PARAMETER_USER_LOGIN)).thenReturn("login");
        when(request.getParameter(PARAMETER_USER_EMAIL)).thenReturn("user@mail.ru");
        when(user.getId()).thenReturn(2);
        String result = "New user was successfully created";
        String resultDoGet =  "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<p>Write user's info into the form below</p>"
                + "<form action='http://localhost:8080/item/create' method='post'>"
                + "  <fieldset>"
                + "    <legend>Personal user's information:</legend>"
                + "    id:<br><input type='text' name='id'><br>"
                + "    name: <br><input type='text' name='name'><br>"
                + "    login:<br><input type='text' name='login'><br>"
                + "    email:<br><input type='email' name='email'><br>"
                + "          <br><input type='submit' value='create'><br><br>"
                + " </form>"
                + " <form action='http://localhost:8080/item/list'>"
                + "     <button type='submit'>show all users</button>"
                + " </form>"
                + " </fieldset>"
                + "</body>"
                + "</html>";

        //use
        servlet.doPost(request, response);

        //check
        verify(response, times(2)).setContentType("text/html");
        verify(response, times(2)).getWriter();
        verify(request, atLeastOnce()).getContextPath();
        verify(request).getSession();
        verify(session, atLeastOnce()).getAttribute(ATTRIBUTE_STORAGE);
        verify(request).getParameter(PARAMETER_USER_ID);
        verify(request).getParameter(PARAMETER_USER_NAME);
        verify(request).getParameter(PARAMETER_USER_LOGIN);
        verify(request).getParameter(PARAMETER_USER_EMAIL);
        verify(writer).append(result);
        verify(writer).append(resultDoGet);
        verify(writer, times(2)).flush();
        verifyNoMoreInteractions(request, response, session, writer);
    }
}