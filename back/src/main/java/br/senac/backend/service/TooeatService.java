package br.senac.backend.service;

import java.util.List;

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
import br.senac.backend.dao.TooeatDao;
import br.senac.backend.dao.UserDao;
import br.senac.backend.exception.TooeatException;
import br.senac.backend.model.Tooeat;
import br.senac.backend.model.pojo.TooeatCreatePojo;
import br.senac.backend.util.Util;
import br.senac.backend.validator.TooeatValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/tooeat")
@Api("/Tooeat Service")
@SwaggerDefinition(tags= {@Tag (name="Tooeat Service", description="REST Endpoint for Tooeat Service")})
public class TooeatService {
	@Context SecurityContext securityContext;

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	public Response create(TooeatCreatePojo pojo, @Context SecurityContext securityContext) {
		Response response;
		try {
			Integer userId = Util.stringToInteger(securityContext.getUserPrincipal().getName());

			Tooeat tooeat = TooeatCreatePojo.convertToModel(pojo);

			tooeat.setCreatedAt(Util.getDateNow());
			tooeat.setUser(UserDao.getInstance().getById(userId));
			tooeat.setEnabled(true);

			TooeatException tooeatException = TooeatValidator.validate(tooeat);
			if (tooeatException != null)
				throw tooeatException;

			TooeatDao.getInstance().persist(tooeat);
			response = Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (TooeatException e) {
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
	@Secured
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	public Response read(@Context SecurityContext securityContext) {
		Response response;
		try {
			Integer userId = Util.stringToInteger(securityContext.getUserPrincipal().getName());

			List<Tooeat> list = TooeatDao.getInstance().findAll(userId);
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

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Tooeat tooeat){
		Response response;
		try {
			TooeatException userException = TooeatValidator.validate(tooeat);
			if (userException != null)
				throw userException;

			TooeatDao.getInstance().merge(tooeat);
			response = Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (TooeatException e) {
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

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Integer id) {
		Response response;
		try {
			TooeatDao.getInstance().removeById(id);
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
