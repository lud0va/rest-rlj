package dao;

import data.StaticList;
import model.Jugadores;

import java.util.List;

public class DaoJugadoresImpl implements  DaoJugadores {
    @Override
    public List<Jugadores> getAll() {
        return StaticList.jugadores;
    }

    @Override
    public Boolean deleteJugador(int id) {
        return StaticList.jugadores.remove(StaticList.jugadores.stream().filter(jug->jug.getId()==id));
    }

    @Override
    public Boolean addJugador(Jugadores jugadores) {
        return StaticList.jugadores.add(jugadores);
    }

    @Override
    public Boolean updateJugador(Jugadores jugadores) {
        StaticList.jugadores.remove(StaticList.jugadores.stream().filter(jug->jug.getId()== jugadores.getId()));

        return StaticList.jugadores.add(jugadores);
    }

    @Override
    public Jugadores getJugador(int id) {
        return StaticList.jugadores.stream().filter(jugadores -> jugadores.getId()==id).findFirst().orElse(null);
    }
}
