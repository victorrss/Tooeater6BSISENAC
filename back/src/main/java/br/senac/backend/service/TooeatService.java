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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.senac.backend.dao.TooeatDao;
import br.senac.backend.exception.TooeatException;
import br.senac.backend.model.Tooeat;
import br.senac.backend.validator.TooeatValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/tooeat")
@Api("/Tooeat Service")
@SwaggerDefinition(tags= {@Tag (name="Tooeat Service", description="REST Endpoint for Tooeat Service")})
public class TooeatService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Tooeat tooeat) {
		Response response;
		try {
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
					.entity("{\\\"message\\\": \\\""+e.getMessage()+"\\\"}")
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
	public Response read() {
		Response response;
		try {
			List<Tooeat> list = TooeatDao.getInstance().findAll();
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
					.entity("{\\\"message\\\": \\\""+e.getMessage()+"\\\"}")
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
