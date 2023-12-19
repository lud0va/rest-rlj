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
    LocalDateTime minutoAct;
    boolean activado;

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
