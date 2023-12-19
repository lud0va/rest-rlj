package services;

import dao.DaoUsuario;
import jakarta.inject.Inject;

import model.Usuario;

public class UserServices {
    private final DaoUsuario daoUsuario;

    @Inject
    public UserServices(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
    }


    public Boolean login(Usuario usuario){
        Usuario us=daoUsuario.login(usuario);
        if (us!=null){
            return true;
        }

        return false ;
    }
}
