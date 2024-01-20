package server.servlet;

import common.ConstantsServer;
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
import services.ServiciosUsuario;

import java.io.IOException;

@WebServlet(name = ConstantsServer.CAMBIARPASSW, urlPatterns = {ConstantsServer.CAMBIARPASSWPATH})
public class ServletSendPassw extends HttpServlet {



    private final ServiciosUsuario serviciosUsuario;

    @Inject
    public ServletSendPassw(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeLeafListener.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);


        var password =  request.getParameter(ConstantsServer.PASSWORD);

        String c= request.getParameter(ConstantsServer.CODE);



        if (Boolean.TRUE.equals(serviciosUsuario.cambiarPasswrd(c, password))) {
            context.setVariable(ConstantsServer.TEXT, ConstantsServer.CAMBIADA_CORRECTAMENTE);
        }


        templateEngine.process(ConstantsServer.HOME, context, response.getWriter());
    }

}
