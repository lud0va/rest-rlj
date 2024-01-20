package server.rest;

import common.ConstantsServer;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Jugadores;
import services.ServiciosJugadores;

import java.util.List;

@Path(ConstantsServer.JUGADORES)
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
    @GET
    @Path(ConstantsServer.IDPATH)

    public Jugadores get(@PathParam(ConstantsServer.ID) int id){
        return serv.getJugador(id);
    }

    @DELETE
    @RolesAllowed({ConstantsServer.ADMIN})
    public Response deleteJugador(@QueryParam(ConstantsServer.ID)int id){
        if (Boolean.TRUE.equals(serv.deleteJugador(id))){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @RolesAllowed({ConstantsServer.ADMIN})
    public Response addJugador(Jugadores jugadores){
        if (Boolean.TRUE.equals(serv.addJugador(jugadores))){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    @PUT
    @RolesAllowed({ConstantsServer.ADMIN})
    public Response updateJugador(Jugadores jugadores){
        if (Boolean.TRUE.equals(serv.updateJugador(jugadores))){
            return Response.status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }


}
