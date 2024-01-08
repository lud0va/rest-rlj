package dao;

import data.StaticList;
import model.Juegos;

import java.util.List;

public class DaoJuegoImpl implements DaoJuegos{


    @Override
    public Boolean deletegame(int id) {
        return StaticList.juegos.remove(StaticList.juegos.stream().filter(game->game.getId()==id).findFirst().orElse(null));
    }

    @Override
    public Boolean addGame(Juegos juegos) {
        return StaticList.juegos.add(juegos);
    }

    @Override
    public Boolean updateGame(Juegos juegos) {
        StaticList.juegos.remove(StaticList.juegos.stream().filter(game->game.getId()== juegos.getId()).findFirst().orElse(null));

        return  StaticList.juegos.add(juegos);  }


    @Override
    public Juegos getGame(int id) {
        return StaticList.juegos.stream().filter(game->game.getId()==id).findFirst().orElse(null);
    }

    @Override
    public List<Juegos> getGamePorPlayer(int idjugador) {
        return StaticList.juegos.stream().filter(game->game.getIdJugador()==idjugador).toList();
    }

    @Override
    public List<Juegos> getAll() {
        return StaticList.juegos;
    }
}
