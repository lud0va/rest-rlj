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
    public Boolean doRegister(@QueryParam(ConstantsServer.EMAIL) String email, @QueryParam(ConstantsServer.PASSWORD) String password) {
        String codes = Utils.randomBytes();


        if (serv.register(new Usuario(email, password, codes))) {
            try {
                mail.generateAndSendEmail(email, ConstantsServer.VERIFY_CODE_PATH + codes + ConstantsServer.VERIFY_CODE + codes + ConstantsServer.A_HTML, ConstantsServer.ACTIVAR_CUENTA);
                response.getWriter().println(ConstantsServer.CORREO_ENVIADO);
                return true;
            } catch (Exception e) {
                try {
                    response.getWriter().println(e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }


        return false;
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
    public String getNewAccessToken() {

        String header = request.getHeader(ConstantsServer.REFRESH);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key22.key())
                .build()
                .parseClaimsJws(header)
                .getBody();
        claims.get(ConstantsServer.EMAIL);
        String accesToken = Jwts.builder()
                .setSubject(claims.get(ConstantsServer.EMAIL).toString())
                .claim(ConstantsServer.ROLE, claims.get(ConstantsServer.ROLE))
                .setExpiration(Date
                        .from(LocalDateTime.now().plusSeconds(180)
                                .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key22.key())
                .compact();
        return accesToken;
    }

    @PUT
    @Path(ConstantsServer.CAMBIARPASSWORD)
    public Boolean cambiarPassword(@QueryParam(ConstantsServer.EMAIL) String email) {
        String codes = Utils.randomBytes();

        if (serv.addCodAct(email, codes)) {


            try {
                mail.generateAndSendEmail(email, ConstantsServer.NEWPASSW_CODE +codes + ConstantsServer.API_NEWPASSW_CODE +codes + ConstantsServer.A_HTML, ConstantsServer.CAMBIAR_PASSWORD);
                response.getWriter().println(ConstantsServer.CAMBIAR_LA_PASSWORD);

            } catch (Exception e) {
                try {
                    response.getWriter().println(e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        }

        return false;
    }
}