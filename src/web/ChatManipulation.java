package web;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * @author Pedro Feiteira, n48119
 * Rest server interface
 */
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
