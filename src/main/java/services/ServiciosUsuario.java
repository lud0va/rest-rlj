package services;

import dao.DaoUsuario;
import dao.DaoUsuarioImpl;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import model.Usuario;
@Default
public class ServiciosUsuario {
    private final DaoUsuarioImpl daoUsuario;

    @Inject
    public ServiciosUsuario(DaoUsuarioImpl daoUsuario) {
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
