package server.rest.restUsers;

import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Usuario;
import services.ServiciosUsuario;


@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {
    private ServiciosUsuario serv;

    @Inject
    public RestLogin(ServiciosUsuario serv) {
        //this.serv = serv;
    }

    @GET
    public Response getLogin(@QueryParam("mail") String mail, @QueryParam("password") String passwrd) {
        if (serv.login(new Usuario(mail, passwrd))) {
            return Response.status(Response.Status.ACCEPTED).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/register")
    public Boolean doLogin(@QueryParam("email") String email, @QueryParam("password") String password) {
        // Tu lógica de registro aquí
        return true;
    }
}