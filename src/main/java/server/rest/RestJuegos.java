package server.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Juegos;
import services.ServiciosJuegos;

import java.util.List;

@Path("/juegos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestJuegos {

    private final ServiciosJuegos serv;

    @Inject
    public RestJuegos(ServiciosJuegos serv) {
        this.serv = serv;
    }

    @GET
    @RolesAllowed({"admin","usuario"})
    public List<Juegos> getAll(){
        return serv.getAll();

    }

    @POST
    public Response addGame(Juegos juegos){
        if (serv.addGame(juegos))
            return Response.status(Response.Status.ACCEPTED).build();

       return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }


    @DELETE
    public Response deleteGame(@QueryParam("id") int id){
        if (serv.deleteGame(id)){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("/porjugador")
    public List<Juegos> getGamesPorJugador(@QueryParam("idjugador") int id){
        return serv.getGamesPorJugador(id);
    }

    @GET
    @Path("/porjuego")
    public Juegos getJuego(@QueryParam("id" )int id){
        return serv.getJuego(id);
    }

    @PUT
    public Response updateGame(Juegos juegos){
        if (serv.update(juegos)){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }



}
