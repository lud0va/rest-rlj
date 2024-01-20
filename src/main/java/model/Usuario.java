package model;

import common.ConstantsServer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {

    String rol;
    String email;
    String password;
    String tempPasswrd;
    String codAct;
    LocalDateTime minutoAct;
    boolean activado;

    public Usuario(String email, String password) {
        rol= ConstantsServer.USER;
        this.email = email;
        this.password = password;
        minutoAct=LocalDateTime.now();
    }

    public Usuario(String email, String password ,String codAct) {
        rol= ConstantsServer.USER;
        this.email = email;
        this.password = password;
        this.codAct = codAct;
        minutoAct=LocalDateTime.now();
    }

    public Usuario(String code) {
        rol= ConstantsServer.USER;
        this.codAct = code;
        minutoAct=LocalDateTime.now();
    }
}
