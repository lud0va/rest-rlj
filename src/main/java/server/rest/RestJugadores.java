package server.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Jugadores;
import services.ServiciosJugadores;

import java.util.List;

@Path("/jugadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestJugadores {
    private final ServiciosJugadores serv;

    @Inject
    public RestJugadores(ServiciosJugadores serv) {
        this.serv = serv;
    }

    @GET
    public List<Jugadores> getAll(){
        return serv.getAll();

    }

    @DELETE
    public Response deleteJugador(@QueryParam("id")int id){
        if (serv.deleteJugador(id)){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addJugador(Jugadores jugadores){
        if (serv.addJugador(jugadores)){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    @PUT
    public Response updateJugador(Jugadores jugadores){
        if (serv.updateJugador(jugadores)){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }


}
