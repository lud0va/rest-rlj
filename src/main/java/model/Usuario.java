package model;

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
        rol="usuario";
        this.email = email;
        this.password = password;
        minutoAct=LocalDateTime.now();
    }

    public Usuario(String email, String password ,String codAct) {
        rol="usuario";
        this.email = email;
        this.password = password;
        this.codAct = codAct;
        minutoAct=LocalDateTime.now();
    }

    public Usuario(String code) {
        rol="usuario";
        this.codAct = code;
        minutoAct=LocalDateTime.now();
    }
}
