package server.rest;

import common.ConstantsServer;
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


@Path(ConstantsServer.USERPATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {
    private final ServiciosUsuario serv;
    private final MandarMail mail;

    final KeyProvider key22;
    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    @Context
    private SecurityContext securityContext;

    @Inject
    public RestLogin(ServiciosUsuario serv, MandarMail mandarMail, KeyProvider key22) {
        this.serv = serv;
        this.mail = mandarMail;
        this.key22 = key22;
    }

    @GET
    @Path(ConstantsServer.LOGINPATH)
    public Response getLogin(@QueryParam(ConstantsServer.MAIL) String mail, @QueryParam(ConstantsServer.PASSWORD) String passwrd) {


        Usuario usuario = serv.login(new Usuario(mail, passwrd));

        if (usuario != null && usuario.isActivado()) {
            String accesToken = Jwts.builder()
                    .setSubject(usuario.getEmail())
                    .claim(ConstantsServer.ROLE, usuario.getRol())
                    .setExpiration(Date
                            .from(LocalDateTime.now().plusSeconds(180)
                                    .atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(key22.key())
                    .compact();
            String refreshToken = Jwts.builder()
                    .setSubject(usuario.getEmail())
                    .claim(ConstantsServer.ROLE, usuario.getRol())
                    .claim(ConstantsServer.EMAIL, usuario.getEmail())
                    .setExpiration(Date.from(LocalDateTime.now().plusMinutes(10)
                            .atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(key22.key())
                    .compact();


            return Response.status(Response.Status.ACCEPTED)
                    .header(HttpHeaders.AUTHORIZATION, accesToken)
                    .header(ConstantsServer.REFRESH, refreshToken).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }


    }


    @POST
    @Path(ConstantsServer.REGISTER)
    public Response doRegister(@QueryParam(ConstantsServer.EMAIL) String email, @QueryParam(ConstantsServer.PASSWORD) String password) {
        String codes = Utils.randomBytes();


        if (Boolean.TRUE.equals(serv.register(new Usuario(email, password, codes)))) {
            try {
                serv.enviarCorreoCod(mail,codes,email);
               response.getWriter().println(ConstantsServer.CORREO_ENVIADO);
                return Response.status(Response.Status.ACCEPTED).build();
            } catch (Exception e) {
                try {
                    response.getWriter().println(e.getMessage());
                } catch (IOException ex) {
                    throw new IllegalArgumentException(ex);
                }

            }
        }


        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @GET
    @Path(ConstantsServer.VERIFY)
    @Produces(MediaType.TEXT_HTML)
    public Response verify(@QueryParam(ConstantsServer.CODE) String code) {

        boolean verificationResult = serv.verify(new Usuario(code));

        // Customize HTML content based on the verification result
        String htmlResponse = verificationResult ? ConstantsServer.BODY_HTML
                : ConstantsServer.HTML_BODY_H_1_VERIFICATION_FAILED_H_1_BODY_HTML;

        // Build and return the response
        return Response.ok(htmlResponse).build();
    }

    @GET
    @Path(ConstantsServer.GET_REFRESH_TOKEN)
    public String getNewAccessToken(@QueryParam("refreshtoken") String refresh) {


        String header = refresh;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key22.key())
                .build()
                .parseClaimsJws(header)
                .getBody();


        return Jwts.builder()
                .setSubject(claims.get(ConstantsServer.EMAIL).toString())
                .claim(ConstantsServer.ROLE, claims.get(ConstantsServer.ROLE))
                .setExpiration(Date
                        .from(LocalDateTime.now().plusSeconds(180)
                                .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key22.key())
                .compact();
    }

    @PUT
    @Path(ConstantsServer.CAMBIARPASSWORD)
    public Response cambiarPassword(@QueryParam(ConstantsServer.EMAIL) String email) {
        String codes = Utils.randomBytes();

        if (Boolean.TRUE.equals(serv.addCodAct(email, codes))) {


            try {
                serv.enviarCorreoNewPass(mail,codes,email);
                response.getWriter().println(ConstantsServer.CAMBIAR_LA_PASSWORD);
                return Response.status(Response.Status.ACCEPTED).build();
            } catch (Exception e) {
                try {
                    response.getWriter().println(e.getMessage());
                } catch (IOException ex) {
                    throw new IllegalArgumentException(ex);
                }


            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}