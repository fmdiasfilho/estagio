package web;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/chat")
public interface ChatManipulation {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    String getInitialMessage();

    @POST
    @Path("/conversation")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    String sendQuestion(String message);
}
