package server.servlet;

import dao.DaoUsuarioImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import server.listeners.ThymeLeafListener;

import java.io.IOException;

@WebServlet(name = "cambiarpassw", urlPatterns = {"/cambiarpassw"})
public class ServletSendPassw extends HttpServlet {


    @Inject
    private DaoUsuarioImpl daoUsuario;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeLeafListener.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);


        var password =  request.getParameter("password");

     String email =request.getSession().getAttribute("email").toString();

        if (daoUsuario.cambiarPasswrd(email, password.toString())) {
            context.setVariable("text", "contraseña cambiada correctamente");
        }


        templateEngine.process("home", context, response.getWriter());
    }

}
