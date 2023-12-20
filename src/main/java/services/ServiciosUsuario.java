package services;

import dao.DaoUsuario;
import jakarta.inject.Inject;

import model.Usuario;

public class ServiciosUsuario {
    private final DaoUsuario daoUsuario;

    @Inject
    public ServiciosUsuario(DaoUsuario daoUsuario) {
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
