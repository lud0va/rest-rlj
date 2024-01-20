package server.servlet;

import common.ConstantsServer;
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

@WebServlet(name = ConstantsServer.RECUPERAR_PASSWRD, urlPatterns = {ConstantsServer.NEWPASSWPATH})
public class ServletPasswrd extends HttpServlet {







    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeLeafListener.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);
        String template = ConstantsServer.HOME;


        String code=request.getParameter(ConstantsServer.CODE);
        context.setVariable(ConstantsServer.CODE,code);
        templateEngine.process(template, context, response.getWriter());


    }
}
