package org.beccaria.raspi;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}
