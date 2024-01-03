package server.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import server.MandarMail;

import java.io.IOException;

@WebServlet(name = "ServletMail", urlPatterns = {"/mail"})
public class ServletMail extends HttpServlet {

    private MandarMail mail;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        mail = new MandarMail();
        var correo = request.getSession().getAttribute("email");
        var code = request.getSession().getAttribute("code");
        try {
         mail.generateAndSendEmail(correo.toString(), "<html>generado <a href='http://localhost:8080/rest-rlj-1.0-SNAPSHOT/api/user/verify?code=" + code.toString() + "'>href='http://localhost:8080/rest-rlj-1.0-SNAPSHOT/api/user/verify?code="+ code.toString()+" </a></html>", "mail de prueba");
           // mail.generateAndSendEmail(correo.toString(),"<html><a href=\"http://localhost:8080/rest-rlj-1.0-SNAPSHOT/api/juegos\">Enlace a Ejemplo.com</a></html>","si");
            response.getWriter().println("correo enviado");
        } catch (Exception e) {
            response.getWriter().println(e.getMessage());

        }


    }
}
