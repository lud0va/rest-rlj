package services;

import common.ConstantsServer;
import dao.DaoUsuarioImpl;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import jakarta.mail.MessagingException;
import model.Usuario;
import server.MandarMail;

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
    public void enviarCorreoCod(MandarMail mail,String codes,String email){
        try {
            mail.generateAndSendEmail(email, ConstantsServer.VERIFY_CODE_PATH + codes + ConstantsServer.VERIFY_CODE + codes + ConstantsServer.A_HTML, ConstantsServer.ACTIVAR_CUENTA);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }

    }

    public void enviarCorreoNewPass(MandarMail mail,String codes, String email){
        try {
            mail.generateAndSendEmail(email, ConstantsServer.NEWPASSW_CODE + codes + ConstantsServer.API_NEWPASSW_CODE + codes + ConstantsServer.A_HTML, ConstantsServer.CAMBIAR_PASSWORD);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }


    }

    public Boolean register(Usuario usuario){
        return daoUsuario.register(usuario);

    }

    public Boolean verify(Usuario user){
        return daoUsuario.verifyUser(user);
    }

    public List<Usuario> getAll(){return daoUsuario.getAll();}

    public Boolean cambiarPasswrd(String email, String passw){
        return daoUsuario.cambiarPasswrd(email,passw);
    }

    public Boolean addCodAct(String email,String code){
        return daoUsuario.addCodeUser(email,code);

    }
}
