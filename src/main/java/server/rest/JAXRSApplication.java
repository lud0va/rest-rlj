package server.rest;


import common.ConstantsServer;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath(ConstantsServer.API)
@DeclareRoles({ConstantsServer.ADMIN,ConstantsServer.USUARIO})
public class JAXRSApplication extends Application {



}
