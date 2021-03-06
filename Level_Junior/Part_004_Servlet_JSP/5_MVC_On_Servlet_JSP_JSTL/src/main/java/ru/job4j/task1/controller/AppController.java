package ru.job4j.task1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.job4j.task1.controller.ControllerConstants.HOME_PAGE;

/**
 * The AppController class.
 *
 * @author Alex Proshak (olexandr_proshak@ukr.net)
 */
public class AppController extends HttpServlet {
    /**
     * The logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(HOME_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}