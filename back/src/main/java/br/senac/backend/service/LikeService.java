package br.senac.backend.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import br.senac.backend.annotation.Secured;
import br.senac.backend.dao.LikeDao;
import br.senac.backend.dao.TooeatDao;
import br.senac.backend.dao.UserDao;
import br.senac.backend.exception.LikeException;
import br.senac.backend.model.Like;
import br.senac.backend.model.Tooeat;
import br.senac.backend.model.User;
import br.senac.backend.util.Util;
import br.senac.backend.validator.LikeValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/like")
@Api("/Like Service")
@SwaggerDefinition(tags= {@Tag (name="Like Service", description="REST Endpoint for Like Service")})
public class LikeService {
	@Context SecurityContext securityContext;

	@GET
	@Secured
	@Path("/{tooeatId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Liked!"),
			@ApiResponse(code = 204, message = "Remove Like!"),
			@ApiResponse(code = 400, message = "Falha geral, try-catch"), 
			@ApiResponse(code = 406 , message = "Falha validação, Tooeat Exception com retorno de: {\"message\\\": \"Message\"}")
	})
	public Response like(@PathParam("tooeatId") Integer tooeatId, @Context SecurityContext securityContext) {
		Response response;
		try {
			Integer userId = Util.stringToInteger(securityContext.getUserPrincipal().getName());

			User u = (User) UserDao.getInstance().getById(userId);
			Tooeat t = TooeatDao.getInstance().getById(tooeatId);

			Like like = new Like();
			like.setTooeat(t);
			like.setUser(u);

			Like likeExists = LikeDao.getInstance().getByTooeatAndUser(u.getId(), t.getId());

			if (likeExists == null) { //create
				/*
				LikeException exception = LikeValidator.validate(like);
				if (exception != null)
					throw exception;
				 */
				LikeDao.getInstance().persist(like);
				response = Response
						.status(Response.Status.CREATED)
						.build();
			} else {
				LikeDao.getInstance().removeById(likeExists.getId());
				response = Response
						.status(Response.Status.NO_CONTENT)
						.build();
			}
			/*
		} catch (LikeException e) {
			e.printStackTrace();
			response = Response
					.status(Response.Status.NOT_ACCEPTABLE)
					.entity("{\"message\": \""+e.getMessage()+"\"}")
					.type(MediaType.APPLICATION_JSON)
					.build();
			 */
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
	@Path("/list/{tooeatId}")
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

	/*
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
	 */

}
