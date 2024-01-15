package data;

import common.ConstantsServer;
import model.Juegos;
import model.Jugadores;
import model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaticList {
        private StaticList() {
        }

        public static final List<Usuario> usuarios=new ArrayList<>(List.of(
           new Usuario(ConstantsServer.ADMIN, ConstantsServer.LUIS_DECENA_EDUCA_MADRID_ORG, ConstantsServer.LUIS, ConstantsServer.TEMP_PASSWRD1, ConstantsServer.COD_ACT1,null,true),
           new Usuario(ConstantsServer.USUARIO, ConstantsServer.LUIS_S_S_S, ConstantsServer.S, ConstantsServer.TEMP_PASSWRD2, ConstantsServer.COD_ACT2,null,true)
        ));


        public static final List<Jugadores> jugadores=new ArrayList<>(List.of(
           new Jugadores(1, ConstantsServer.NOMBRE1, LocalDate.now())
        ));

        public static final List<Juegos> juegos=new ArrayList<>(List.of(
           new Juegos(1,1, ConstantsServer.JUEGO1)

        ));








}
