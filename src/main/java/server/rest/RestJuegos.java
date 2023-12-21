package server.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
    public List<Juegos> getAll(){
        return serv.getAll();

    }
}
