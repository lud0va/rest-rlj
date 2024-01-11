package services;

import dao.DaoUsuario;
import dao.DaoUsuarioImpl;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import model.Usuario;

import java.util.List;

@Default
public class ServiciosUsuario {
    private final DaoUsuarioImpl daoUsuario;

    @Inject
    public ServiciosUsuario(DaoUsuarioImpl daoUsuario) {
        this.daoUsuario = daoUsuario;
    }


    public Usuario login(Usuario usuario){

        return daoUsuario.login(usuario);
    }


    public Boolean register(Usuario usuario){
        return daoUsuario.register(usuario);

    }

    public Boolean verify(Usuario user){
        return daoUsuario.verifyUser(user);
    }

    public List<Usuario> getAll(){return daoUsuario.getAll();}

    public Boolean cambiarContraseña(String email,String passw){
        return daoUsuario.cambiarPasswrd(email,passw);
    }

    public Boolean addCodAct(String email,String code){
        return daoUsuario.addCodeUser(email,code);

    }
}
