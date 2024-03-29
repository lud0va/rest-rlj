package server.security;

import common.ConstantsServer;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;

import jakarta.security.enterprise.identitystore.CredentialValidationResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import server.di.KeyProvider;

import java.util.Collections;


@ApplicationScoped

public class JWTAuth implements HttpAuthenticationMechanism {
    @Context
    private SecurityContext securityContext;




   final KeyProvider  key22;
    @Inject
    public JWTAuth( KeyProvider key22) {

        this.key22 = key22;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request,
                                                HttpServletResponse response
            , HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;


        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {

            String[] valores = header.split(ConstantsServer.SPACE);

            if (valores[1]==null || valores[1].equals(ConstantsServer.NULL)){
                return httpMessageContext.doNothing();
            }else if (valores[0].equalsIgnoreCase(ConstantsServer.BEARER)) {
                String token = header.substring(7);

                try {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key22.key())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    String subject = claims.getSubject();
                    String role = (String) claims.get(ConstantsServer.ROLE);

                    if (role.equals(ConstantsServer.ADMIN) || role.equals(ConstantsServer.USUARIO)) {
                     c= new CredentialValidationResult(subject, Collections.singleton(role));


                    }


                } catch (JwtException e) {

                    throw new IllegalStateException(e);
                }
            }

            return httpMessageContext.notifyContainerAboutLogin(c);

        } else {
            return httpMessageContext.doNothing();
        }



    }


    @Override
    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        request.getSession().removeAttribute(ConstantsServer.USERLOGIN);
    }
}
