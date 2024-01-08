package dao;

import model.Juegos;

import java.util.List;

public interface DaoJuegos {



     Boolean deletegame(int id);
     Boolean addGame(Juegos juegos);
     Boolean updateGame(Juegos juegos);

     Juegos getGame(int id);

     List<Juegos> getGamePorPlayer(int idjugador);
     List<Juegos> getAll();
}
