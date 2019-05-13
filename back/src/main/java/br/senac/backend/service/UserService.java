package br.senac.backend.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import br.senac.backend.annotation.Secured;
import br.senac.backend.dao.UserDao;
import br.senac.backend.exception.UserException;
import br.senac.backend.model.User;
import br.senac.backend.model.pojo.UserCreatePojo;
import br.senac.backend.util.Util;
import br.senac.backend.validator.UserValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/user")
@Api("/User Service")
@SwaggerDefinition(tags= {@Tag (name="User Service", description="REST Endpoint for User Service")})
public class UserService {
	@Context SecurityContext securityContext;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Sucesso!"),
			@ApiResponse(code = 406, message = "Falha valida��o, User Exception com retorno de: "+ "{\"message\": \"Message\"}"),
			@ApiResponse(code = 400, message = "Fail on create, try-catch") 
	})
	public Response create(UserCreatePojo pojo) {
		try {
			User user = UserCreatePojo.convertToModel(pojo);
			UserException userException = UserValidator.validate(user);
			if (userException != null)
				throw userException;

			user.setPassword(Util.sha1(user.getPassword()));
			user.setCreatedAt(Util.getDateNow());

			UserDao.getInstance().persist(user);
			return Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (UserException e) {
			e.printStackTrace();
			return Response
					.status(Response.Status.NOT_ACCEPTABLE)
					.entity("{\"message\": \""+e.getMessage()+"\"}")
					.type(MediaType.APPLICATION_JSON)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response
					.status(Response.Status.BAD_REQUEST)
					.build();
		}
	}

	@GET
	@Path("/me")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	public Response readMe(@Context SecurityContext securityContext) {
		Response response;
		try {
			Integer userId = Util.stringToInteger(securityContext.getUserPrincipal().getName());
			User user = UserDao.getInstance().getById(userId);
			user.setPassword(null);
			response = Response
					.status(Response.Status.OK)
					.entity(user)
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

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(User user){
		Response response;
		try {
			UserException userException = UserValidator.validate(user);
			if (userException != null)
				throw userException;

			user.setUpdateAt(Util.getDateNow());

			UserDao.getInstance().merge(user);
			response = Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (UserException e) {
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
					.entity(null)
					.build();
		}

		return response;
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Integer id) {
		Response response;
		try {
			UserDao.getInstance().removeById(id);
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
