package ru.job4j.mvc.controller.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ru.job4j.mvc.controller.ControllerConstants.ATTRIBUTE_SYSTEM_USER;

/**
 * The AuthenticationFilter class.
 */
public class AuthenticationFilter implements Filter {

    /**
     * The logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    /**
     * Overloaded doFilter method with http casted parameters.
     * @param req HttpServletRequest.
     * @param resp HttpServletResponse.
     * @param chain FilterChain.
     * @throws ServletException exception.
     * @throws IOException exception.
     */
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            if (req.getRequestURI().contains("/login")) {
                chain.doFilter(req, resp);
            } else {
                HttpSession session = req.getSession();
                if (session.getAttribute(ATTRIBUTE_SYSTEM_USER) != null) {
                    chain.doFilter(req, resp);
                } else {
                    resp.sendRedirect(String.format("%s/login", req.getContextPath()));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            resp.sendRedirect(String.format("%s/login", req.getContextPath()));
        }
    }

    /**
     * The init method.
     * @param config parameters.
     * @throws ServletException exception.
     */
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * The general method doFilter.
     * @param req ServletRequest.
     * @param resp ServletResponse.
     * @param chain FilterChain.
     * @throws ServletException exception.
     * @throws IOException exception.
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        doFilter((HttpServletRequest) req, (HttpServletResponse) resp, chain);
    }

    /**
     * The destroy method.
     */
    public void destroy() {
    }
}