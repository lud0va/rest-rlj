package services;

import dao.DaoJugadores;
import jakarta.inject.Inject;
import model.Jugadores;

import java.util.List;

public class ServiciosJugadores {

    private final DaoJugadores daoJugadores;

    @Inject
    public ServiciosJugadores(DaoJugadores daoJugadores) {
        this.daoJugadores = daoJugadores;
    }

    public List<Jugadores> getAll(){
        return daoJugadores.getAll();
    }
    public Boolean deleteJugador(int id){
        return daoJugadores.deleteJugador(id);
    }
    public Boolean addJugador(Jugadores jugadores){
        return daoJugadores.addJugador(jugadores);
    }

    public Boolean updateJugador(Jugadores jugadores){
        return daoJugadores.updateJugador(jugadores);
    }
    public Jugadores getJugador(int id){
        return daoJugadores.getJugador(id);

    }



}
