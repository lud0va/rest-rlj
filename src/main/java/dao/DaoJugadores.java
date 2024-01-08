package dao;

import model.Jugadores;

import java.util.List;

public interface DaoJugadores {

    List<Jugadores> getAll();
    Boolean deleteJugador(int id);
    Boolean addJugador(Jugadores jugadores);
    Boolean updateJugador(Jugadores jugadores);
    Jugadores getJugador(int id);


}
