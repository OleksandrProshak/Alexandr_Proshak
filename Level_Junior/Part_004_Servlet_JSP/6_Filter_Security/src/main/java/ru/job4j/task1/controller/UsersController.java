package ru.job4j.task1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.task1.model.entity.User;
import ru.job4j.task1.model.logic.ValidateService;
import ru.job4j.task1.model.logic.impl.ValidateServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

import static ru.job4j.task1.controller.ControllerConstants.ATTRIBUTE_STORAGE;
import static ru.job4j.task1.controller.ControllerConstants.PREFIX_PAGE;
import static ru.job4j.task1.controller.ControllerConstants.ALL_USERS_PAGE;
import static ru.job4j.task1.controller.ControllerConstants.EMPTY_PAGE;
import static ru.job4j.task1.controller.ControllerConstants.PARAMETER_USER_ID;

/**
 * The UsersController class.
 *
 * @author Alex Proshak (olexandr_proshak@ukr.net)
 */
public class UsersController extends HttpServlet {

    /**
     * The logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            ValidateService storage = ValidateServiceImpl.getInstance();
            session.setAttribute(ATTRIBUTE_STORAGE, storage);
            if (storage == null) {
                req.getRequestDispatcher(PREFIX_PAGE + EMPTY_PAGE).forward(req, resp);
            } else {
                Collection<User> all = storage.findAll();
                if (all.isEmpty()) {
                    req.getRequestDispatcher(PREFIX_PAGE + EMPTY_PAGE).forward(req, resp);
                } else {
                    req.getRequestDispatcher(PREFIX_PAGE + ALL_USERS_PAGE).forward(req, resp);
                }
            }
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
            try {
                resp.sendRedirect(PREFIX_PAGE + ALL_USERS_PAGE);
            } catch (IOException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
    }

    /**
     * The doPost method is used for removing user from db.
     * @param req HttpServletRequest.
     * @param resp HttpServletResponse.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id = req.getParameter(PARAMETER_USER_ID);
            if (id != null) {
                ValidateService storage = (ValidateService) req.getSession().getAttribute(ATTRIBUTE_STORAGE);
                Integer integerId = Integer.valueOf(id);
                storage.delete(integerId);
                doGet(req, resp);
            } else {
                LOG.error("Null users to delete.");
                doGet(req, resp);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            doGet(req, resp);
        }
    }

}
