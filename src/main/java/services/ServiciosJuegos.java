package services;

import dao.DaoJuegoImpl;

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

    public List<Juegos> getGamesPorJugador(int idjugadir){
        return daoJuegos.getGamePorPlayer(idjugadir);
    }
    public Boolean deleteGame(int id){
        return daoJuegos.deletegame(id);
    }

    public Boolean addGame(Juegos juegos){
        return daoJuegos.addGame(juegos);
    }
    public Boolean update(Juegos juegos){
        return daoJuegos.updateGame(juegos);
    }
    public Juegos getJuego(int id){
        return daoJuegos.getGame(id);
    }


}

