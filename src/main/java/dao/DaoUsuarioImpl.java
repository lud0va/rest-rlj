package dao;

import data.StaticList;
import model.Usuario;

public class DaoUsuarioImpl implements DaoUsuario{


    @Override
    public Usuario login(Usuario user) {
        return StaticList.usuarios.stream().filter(user1 -> user1.getEmail().equals(user.getEmail())).findFirst().orElse(null);
    }
}
