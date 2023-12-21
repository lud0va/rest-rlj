package services;

import dao.DaoJuegoImpl;
import dao.DaoJuegos;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import model.Juegos;

import java.util.List;
@Default
public class ServiciosJuegos {

    private final DaoJuegoImpl daoJuegos;

    @Inject
    public ServiciosJuegos(DaoJuegoImpl juegos) {
        this.daoJuegos = juegos;
    }

    public List<Juegos> getAll(){
        return daoJuegos.getAll();
    }
}
