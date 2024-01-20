package server.rest;

import common.ConstantsServer;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Juegos;
import services.ServiciosJuegos;

import java.util.List;

@Path(ConstantsServer.JUEGOSPATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestJuegos {

    private final ServiciosJuegos serv;

    @Inject
    public RestJuegos(ServiciosJuegos serv) {
        this.serv = serv;
    }

    @GET
    @RolesAllowed({ConstantsServer.ADMIN,ConstantsServer.USUARIO})
    public List<Juegos> getAll(){
        return serv.getAll();

    }

    @POST

    public Response addGame(Juegos juegos){
        if (Boolean.TRUE.equals(serv.addGame(juegos)))
            return Response.status(Response.Status.ACCEPTED).build();

       return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }


    @DELETE
    @RolesAllowed({ConstantsServer.ADMIN})
    public Response deleteGame(@QueryParam(ConstantsServer.ID) int id){
        if (Boolean.TRUE.equals(serv.deleteGame(id))){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path(ConstantsServer.PORIDJUGADOR)

    public List<Juegos> getGamesPorJugador(@QueryParam(ConstantsServer.ID_JUGADOR) int id){
        return serv.getGamesPorJugador(id);
    }

    @GET
    @Path(ConstantsServer.PORJUEGO)
    @RolesAllowed({ConstantsServer.ADMIN})
    public Juegos getJuego(@PathParam(ConstantsServer.ID) int id){
        return serv.getJuego(id);
    }

    @PUT
    @RolesAllowed({ConstantsServer.ADMIN})
    public Response updateGame(Juegos juegos){
        if (Boolean.TRUE.equals(serv.update(juegos))){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }



}
