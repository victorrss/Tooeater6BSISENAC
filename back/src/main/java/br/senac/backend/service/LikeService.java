package br.senac.backend.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import br.senac.backend.dao.LikeDao;
import br.senac.backend.exception.LikeException;
import br.senac.backend.model.Like;
import br.senac.backend.validator.LikeValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/like")
@Api("/Like Service")
@SwaggerDefinition(tags= {@Tag (name="Like Service", description="REST Endpoint for Like Service")})
public class LikeService {


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Like like) {
		Response response;
		try {
			LikeException exception = LikeValidator.validate(like);
			if (exception != null)
				throw exception;

			LikeDao.getInstance().persist(like);
			response = Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (LikeException e) {
			e.printStackTrace();
			response = Response
					.status(Response.Status.NOT_ACCEPTABLE)
					.entity("{\"message\": \""+e.getMessage()+"\"}")
					.type(MediaType.APPLICATION_JSON)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			response = Response
					.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{tooeatId}")
	public Response read(@PathParam("tooeatId") Integer tooeatId) {
		Response response;
		try {
			List<Like> list = LikeDao.getInstance().findAll(tooeatId);
			response = Response
					.status(Response.Status.OK)
					.entity(list)
					.type(MediaType.APPLICATION_JSON)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			response  = Response
					.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return response;
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Integer id) {
		Response response;
		try {
			LikeDao.getInstance().removeById(id);
			response = Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			response = Response
					.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return response;
	}

}
