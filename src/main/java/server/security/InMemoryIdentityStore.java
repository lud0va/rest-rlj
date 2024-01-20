package server.security;



import common.ConstantsServer;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import model.Usuario;
import services.ServiciosUsuario;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;


import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;


public class InMemoryIdentityStore implements IdentityStore {

    private final ServiciosUsuario serviciosUsuarios;

    @Override
    public int priority() {
        return 10;
    }


    @Inject
    public InMemoryIdentityStore(ServiciosUsuario serviciosUsuarios) {
        this.serviciosUsuarios = serviciosUsuarios;
    }


    @Override
    public CredentialValidationResult validate(Credential credential) {


        if (credential instanceof BasicAuthenticationCredential user) {

                HashSet<String> roles = new HashSet<>();
                roles.add(ConstantsServer.ADMIN);
                roles.add(ConstantsServer.USUARIO);
            List<Usuario> users=serviciosUsuarios.getAll();
                user.getPassword().getValue();

             Usuario usuari= users.stream().filter(u->u.getEmail().equals(user.getCaller())).findFirst().orElse(null);

            assert usuari != null;
            if (usuari.isActivado()){
                 return new CredentialValidationResult(usuari.getEmail(),Collections.singleton(usuari.getRol()));
             }else {
             return NOT_VALIDATED_RESULT;
             }

            }
        else if (credential instanceof RememberMeCredential jwt) {
                jwt.getToken();

            }
        else { throw new IllegalStateException(ConstantsServer.UNEXPECTED_VALUE + credential);
        }


        return INVALID_RESULT;

    }

}
