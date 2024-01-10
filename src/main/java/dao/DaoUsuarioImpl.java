package dao;

import data.StaticList;
import model.Usuario;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DaoUsuarioImpl implements DaoUsuario {


    @Override
    public Usuario login(Usuario user) {
        return StaticList.usuarios.stream().filter(user1 -> user1.getEmail().equals(user.getEmail())).findFirst().orElse(null);
    }

    @Override
    public Boolean register(Usuario usuario) {
        return StaticList.usuarios.add(usuario);
    }

    @Override
    public Boolean verifyUser(Usuario usuario) {

        Usuario us = StaticList.usuarios.stream().filter(user -> user.getCodAct().equals(usuario.getCodAct())).findFirst().orElse(null);
        if (us.isActivado()){
            return true;
        }else{
            long seconds = Math.abs(us.getMinutoAct().until(usuario.getMinutoAct(), ChronoUnit.SECONDS));
            if (seconds< 30){
            StaticList.usuarios.stream().filter(user -> user.getCodAct().equals(usuario.getCodAct())).findFirst().orElse(null).setActivado(true);
            return true;
        }
        }
       return false;
    }

    @Override
    public Boolean addCodeUser(String email, String code) {
       Usuario user= StaticList.usuarios.stream().filter(usuario -> usuario.getEmail().equals(email)).findFirst().orElse(null);
        if ( user == null) {
            StaticList.usuarios.stream().filter(usuario -> usuario.getEmail().equals(email)).findFirst().orElse(null).setCodAct(code);
            return true;
        }
        return false;
    }

    @Override
    public Boolean cambiarPasswrd(String mail,String newpasswrd) {
     Usuario user=  StaticList.usuarios.stream().filter(usuario -> usuario.getEmail().equals(mail)).findFirst().orElse(null);

       if (user!=null){
           StaticList.usuarios.stream().filter(usuario -> usuario.getEmail().equals(mail)).findFirst().orElse(null).setPassword(newpasswrd);
           return true;
       }
       return false;
    }

    @Override
    public List<Usuario> getAll() {
        return StaticList.usuarios;
    }

}
