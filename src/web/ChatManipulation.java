package web;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/")
public interface ChatManipulation {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getInitialMessage();

    @POST
    @Path("/conversation")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    String sendQuestion(String message);
}
