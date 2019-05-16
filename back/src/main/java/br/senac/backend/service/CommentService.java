package br.senac.backend.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import br.senac.backend.annotation.Secured;
import br.senac.backend.dao.CommentDao;
import br.senac.backend.dao.TooeatDao;
import br.senac.backend.dao.UserDao;
import br.senac.backend.exception.CommentException;
import br.senac.backend.model.Comment;
import br.senac.backend.model.Tooeat;
import br.senac.backend.model.User;
import br.senac.backend.model.pojo.CommentCreatePojo;
import br.senac.backend.util.Util;
import br.senac.backend.validator.CommentValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/comment")
@Api("/Comment Service")
@SwaggerDefinition(tags= {@Tag (name="Comment Service", description="REST Endpoint for Comment Service")})
public class CommentService {
	@Context SecurityContext securityContext;

	@POST
	@Path("/{tooeatId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso! Retorna o obj Comment com o id, completo"),
			@ApiResponse(code = 406, message = "Falha validação, Comment Exception com retorno de: "+ "{\"message\": \"Message\"}"),
			@ApiResponse(code = 400, message = "Falha geral, try-catch") 
	})
	public Response create(@PathParam("tooeatId") Integer tooeatId, CommentCreatePojo pojo, @Context SecurityContext securityContext) {
		Response response;
		try {
			Integer userId = Util.stringToInteger(securityContext.getUserPrincipal().getName());
			Comment comment = CommentCreatePojo.convertToModel(pojo);
			Tooeat tooeat =TooeatDao.getInstance().getById(tooeatId);
			comment.setTooeat(tooeat);
			comment.setCreatedAt(Util.getDateNow());
			User user = UserDao.getInstance().getById(userId);
			comment.setUser(user);

			CommentException exception = CommentValidator.validate(comment);
			if (exception != null)
				throw exception;

			CommentDao.getInstance().persist(comment);
			response = Response
					.status(Response.Status.OK)
					.entity(comment)
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
	@Secured
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso! Retorna um array com os comentários"),
			@ApiResponse(code = 400, message = "Falha geral, try-catch") 
	})
	public Response readAll(@PathParam("tooeatId") Integer tooeatId, @Context SecurityContext securityContext) {
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

	@DELETE
	@Path("/{commentId}")
	@Secured
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso!"),
			@ApiResponse(code = 400, message = "Falha geral, try-catch"), 
			@ApiResponse(code = 401 , message = "Não autorizado, o usuário do token não é o dono do comentário!")
	})
	public Response delete(@PathParam("commentId") Integer commentId, @Context SecurityContext securityContext) {
		Response response;
		try {
			Integer userId = Util.stringToInteger(securityContext.getUserPrincipal().getName());
			Comment comment = CommentDao.getInstance().getById(commentId);
			if (comment.getUser().getId() == userId) {
				CommentDao.getInstance().removeById(commentId);
				response = Response
						.status(Response.Status.NO_CONTENT)
						.build();
			} else {
				response = Response
						.status(Response.Status.FORBIDDEN)
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = Response
					.status(Response.Status.BAD_REQUEST)
					.build();
		}
		return response;
	}

}
