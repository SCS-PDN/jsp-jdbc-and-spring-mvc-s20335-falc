package ac.pdn.interceptor;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = request.getRequestURI();
        String studentEmail = (String) request.getSession().getAttribute("studentEmail");

        if (uri.equals("/login") && request.getMethod().equalsIgnoreCase("POST")) {
            String email = request.getParameter("email");
            logger.info("Login attempt: " + email + " at " + LocalDateTime.now());
        }

        if (uri.startsWith("/register/") && studentEmail != null) {
            logger.info("Course registration attempt by: " + studentEmail + " to " + uri + " at " + LocalDateTime.now());
        }

        return true;
    }
}

