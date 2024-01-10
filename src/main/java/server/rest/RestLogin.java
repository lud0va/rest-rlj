package server.rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.Usuario;
import server.MandarMail;
import server.Utils;
import server.di.KeyProvider;
import services.ServiciosUsuario;

import java.io.IOException;
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
        request.getSession().setAttribute("usernow", usuario);
        if (usuario != null && usuario.isActivado()) {
            String accesToken = Jwts.builder()
                    .setSubject(usuario.getEmail())
                    .claim("role", usuario.getRol())
                    .setExpiration(Date
                            .from(LocalDateTime.now().plusSeconds(60)
                                    .atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(key22.key())
                    .compact();
            String refreshToken = Jwts.builder()
                    .setSubject(usuario.getEmail())
                    .claim("role", usuario.getRol())
                    .claim("email", usuario.getEmail())
                    .setExpiration(Date.from(LocalDateTime.now().plusMinutes(10)
                            .atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(key22.key())
                    .compact();


            return Response.status(Response.Status.ACCEPTED)
                    .header(HttpHeaders.AUTHORIZATION, accesToken)
                    .header("refresh", refreshToken).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }


    }


    @POST
    @Path("/register")
    public Boolean doRegister(@QueryParam("email") String email, @QueryParam("password") String password) {
        String codes = Utils.randomBytes();

        MandarMail mail = new MandarMail();
   if (serv.register(new Usuario(email, password, codes))){
       try {
           mail.generateAndSendEmail(email, "<html>generado <a href='http://localhost:8080/rest-rlj-1.0-SNAPSHOT/api/user/verify?code=" + codes + "'>href='http://localhost:8080/rest-rlj-1.0-SNAPSHOT/api/user/verify?code=" + codes + " </a></html>", "mail de prueba");
           response.getWriter().println("correo enviado");
           return true;
       } catch (Exception e) {
           try {
               response.getWriter().println(e.getMessage());
           } catch (IOException ex) {
               throw new RuntimeException(ex);
           }

       }
   }


        return false ;
    }

    @GET
    @Path("/verify")
    @Produces(MediaType.TEXT_HTML)
    public Response verify(@QueryParam("code") String code) {

        boolean verificationResult = serv.verify(new Usuario(code));

        // Customize HTML content based on the verification result
        String htmlResponse = verificationResult ? "<html><body><h1>Verification Successful</h1></body></html>"
                : "<html><body><h1>Verification Failed</h1></body></html>";

        // Build and return the response
        return Response.ok(htmlResponse).build();
    }

    @GET
    @Path("/getRefreshToken")
    public String getNewAccessToken() {

        String header = request.getHeader("refresh");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key22.key())
                .build()
                .parseClaimsJws(header)
                .getBody();
        claims.get("email");
        String accesToken = Jwts.builder()
                .setSubject(claims.get("email").toString())
                .claim("role", claims.get("role"))
                .setExpiration(Date
                        .from(LocalDateTime.now().plusSeconds(60)
                                .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key22.key())
                .compact();
        return accesToken;
    }

    @PUT
    @Path("/cambiarpassword")
    public Boolean cambiarPassword(@QueryParam("email") String email) {
        request.getSession().setAttribute("email", email);


        MandarMail mail = new MandarMail();
        var correo = request.getSession().getAttribute("email");
        var code = request.getSession().getAttribute("code");
        try {
            mail.generateAndSendEmail(correo.toString(), "<html>generado <a href='http://localhost:8080/rest-rlj-1.0-SNAPSHOT/newpassw"  + "'>href='http://localhost:8080/rest-rlj-1.0-SNAPSHOT/api/GetNewPassword" + " </a></html>", "cambiar contraseña");
            response.getWriter().println("ve al correo para cambiar la contraseña");
        } catch (Exception e) {
            try {
                response.getWriter().println(e.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        }
        return true;
    }
}