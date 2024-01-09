package dao;

import model.Usuario;

import java.util.List;

public interface DaoUsuario {

       Usuario login(Usuario user);

       Boolean register(Usuario usuario);

       Boolean verifyUser(Usuario usuario);

       Boolean cambiarPasswrd(String mail, String newpasswrd);
       List<Usuario> getAll();












}
