package data;

import model.Juegos;
import model.Jugadores;
import model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaticList {

        public static final List<Usuario> usuarios=new ArrayList<>(List.of(
           new Usuario("admin","luis.decena@educa.madrid.org","luis","","",null,true),
           new Usuario("usuario","luis@s.s.s","s","","",null,true)
        ));


        public static final List<Jugadores> jugadores=new ArrayList<>(List.of(
           new Jugadores(1,"Si", LocalDate.now())
        ));

        public static final List<Juegos> juegos=new ArrayList<>(List.of(
           new Juegos(1,1,"Pokemon")

        ));








}
