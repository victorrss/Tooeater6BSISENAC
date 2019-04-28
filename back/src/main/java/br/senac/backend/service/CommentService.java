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
import br.senac.backend.dao.CommentDao;
import br.senac.backend.exception.CommentException;
import br.senac.backend.model.Comment;
import br.senac.backend.validator.CommentValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/comment")
@Api("/Comment Service")
@SwaggerDefinition(tags= {@Tag (name="Comment Service", description="REST Endpoint for Comment Service")})
public class CommentService {

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Comment comment) {
		Response response;
		try {
			CommentException exception = CommentValidator.validate(comment);
			if (exception != null)
				throw exception;

			CommentDao.getInstance().persist(comment);
			response = Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (CommentException e) {
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
			List<Comment> list = CommentDao.getInstance().findAll(tooeatId);
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
/*
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Comment comment){
		Response response;
		try {
			CommentException userException = CommentValidator.validate(comment);
			if (userException != null)
				throw userException;

			CommentDao.getInstance().merge(comment);
			response = Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (CommentException e) {
			e.printStackTrace();
			response = Response
					.status(Response.Status.NOT_ACCEPTABLE)
					.entity("{\"message\\\": \""+e.getMessage()+"\"}")
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
*/
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Integer id) {
		Response response;
		try {
			CommentDao.getInstance().removeById(id);
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
