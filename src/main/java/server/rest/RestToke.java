package server.rest;

import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import model.Juegos;
import model.Usuario;
import server.di.KeyProvider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Path("/token")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestToke {

    @Inject
    KeyProvider key22;
    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    @Context
    private SecurityContext securityContext;


    @GET
    public String getNewAccessToken() {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usernow");

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("role", usuario.getRol())
                .setExpiration(Date
                        .from(LocalDateTime.now().plusSeconds(60)
                                .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key22.key())
                .compact();
    }


}
