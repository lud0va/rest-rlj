package server.rest;

import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.Usuario;
import server.Utils;
import server.di.KeyProvider;
import server.servlet.ServletMail;
import services.ServiciosUsuario;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {
    private final ServiciosUsuario serv;
    @Inject
    KeyProvider key22;
    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    @Context
    private SecurityContext securityContext;

    @Inject
    public RestLogin(ServiciosUsuario serv) {
        this.serv = serv;
    }

    @GET
    @Path("/login")
    public Response getLogin(@QueryParam("mail") String mail, @QueryParam("password") String passwrd) {


        Usuario usuario = serv.login(new Usuario(mail, passwrd));

        if (usuario != null && usuario.isActivado()) {


            String token = Jwts.builder()
                    .setSubject(usuario.getEmail())
                    .claim("role", usuario.getRol())
                    .setExpiration(Date
                            .from(LocalDateTime.now().plusSeconds(60)
                                    .atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(key22.key())
                    .compact();



            return Response.status(Response.Status.ACCEPTED)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }



    }


    @POST
    @Path("/register")
    public Boolean doRegister(@QueryParam("email") String email, @QueryParam("password") String password) {
        String code = Utils.randomBytes();
        request.getSession().setAttribute("email", email);
        request.getSession().setAttribute("code", code);
        return serv.register(new Usuario(email, password, code));
    }

    @GET
    @Path("/verify")
    @Produces(MediaType.TEXT_HTML)
    public Response  verify(@QueryParam("code") String code) {

        boolean verificationResult = serv.verify(new Usuario(code));

        // Customize HTML content based on the verification result
        String htmlResponse = verificationResult ? "<html><body><h1>Verification Successful</h1></body></html>"
                : "<html><body><h1>Verification Failed</h1></body></html>";

        // Build and return the response
        return Response.ok(htmlResponse).build();
    }
}