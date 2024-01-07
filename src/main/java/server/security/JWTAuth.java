package server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.annotation.WebFilter;
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

    @Inject
    private InMemoryIdentityStore identity;
    @Inject
    KeyProvider key22;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request,
                                                HttpServletResponse response
            , HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;


        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {

            String[] valores = header.split(" ");

            if (valores[0].equalsIgnoreCase("Bearer")) {
                String token = header.substring(7);
                try {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key22.key())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    String subject = claims.getSubject();
                    String role = (String) claims.get("role");

                    if (role.equals("admin") || role.equals("usuario")) {
                        request.getSession().setAttribute("USERLOGIN", new CredentialValidationResult(subject, Collections.singleton(role)));


                    }

                } catch (JwtException e) {

                    throw new RuntimeException(e);
                }
            }

        } else {
            if (request.getSession().getAttribute("USERLOGIN") != null)
                c = (CredentialValidationResult) request.getSession().getAttribute("USERLOGIN");
        }

        if (!c.getStatus().equals(CredentialValidationResult.Status.VALID)) {
            request.setAttribute("status", c.getStatus());
            return httpMessageContext.doNothing();
        }


        return httpMessageContext.notifyContainerAboutLogin(c);
    }


    @Override
    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        request.getSession().removeAttribute("USERLOGIN");
    }
}
