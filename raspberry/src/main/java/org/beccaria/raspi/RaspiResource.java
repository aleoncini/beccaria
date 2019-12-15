package org.beccaria.raspi;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/raspi")
public class RaspiResource {

    @Inject
    @Named("board")
    Board board;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogs() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ \"boardId\" : ");
        buffer.append("\"").append(board.serial()).append("\", ");
        buffer.append("\"pins\" : [ ");
        buffer.append("{ \"pin\" : 0, ").append("\"state\" : ").append(board.status(0)).append(" }, ");
        buffer.append("{ \"pin\" : 1, ").append("\"state\" : ").append(board.status(1)).append(" }, ");
        buffer.append("{ \"pin\" : 2, ").append("\"state\" : ").append(board.status(2)).append(" }, ");
        buffer.append("{ \"pin\" : 3, ").append("\"state\" : ").append(board.status(3)).append(" } ");
        buffer.append(" ] }");
        return Response.status(200).entity(buffer.toString()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{pin}/{command}")
    public Response operatePin(@PathParam("pin") int pin, @PathParam("command") String command) {
        StringBuffer answer = new StringBuffer("{ \"pin\" : ").append(pin).append(", ");

        if ( (pin < 0) || (pin > 3) ){
            return Response.status(404).build();
        }

        if ( command.equalsIgnoreCase("on") ){
            answer.append("\"status\" : ").append(board.on(pin)).append(" }");
            return Response.status(200).entity(answer.toString()).build();
        }

        if ( command.equalsIgnoreCase("off") ){
            answer.append("\"status\" : ").append(board.off(pin)).append(" }");
            return Response.status(200).entity(answer.toString()).build();
        }

        return Response.status(404).build();
    }

}
