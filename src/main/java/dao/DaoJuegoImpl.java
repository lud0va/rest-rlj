package dao;

import data.StaticList;
import model.Juegos;

import java.util.List;

public class DaoJuegoImpl implements DaoJuegos{


    @Override
    public List<Juegos> getAll() {
        return StaticList.juegos;
    }
}
