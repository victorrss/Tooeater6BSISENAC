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

import br.senac.backend.dao.FollowerDao;
import br.senac.backend.exception.FollowerException;
import br.senac.backend.model.Follower;
import br.senac.backend.validator.FollowerValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/follower")
@Api("/Folower Service")
@SwaggerDefinition(tags= {@Tag (name="Folower Service", description="REST Endpoint for Folower Service")})
public class FollowerService {

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Follower follower) {
		Response response;
		try {
			FollowerException followerException = FollowerValidator.validate(follower);
			if (followerException != null)
				throw followerException;

			FollowerDao.getInstance().persist(follower);
			response = Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (FollowerException e) {
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
	@Path("/followers")
	public Response readFollowers() {
		Integer userId = null;
		Response response;
		try {
			List<Follower> list = FollowerDao.getInstance().findAllFollowers(userId);
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/following")
	public Response readFollowing() {
		Integer userId = null;
		Response response;
		try {
			List<Follower> list = FollowerDao.getInstance().findAllFollowing(userId);
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

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/invite")
	public Response readInvite() {
		Integer userId = null;
		Response response;
		try {
			List<Follower> list = FollowerDao.getInstance().findAllInvites(userId);
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
	public Response update(Follower follower){
		Response response;
		try {
			FollowerException userException = FollowerValidator.validate(follower);
			if (userException != null)
				throw userException;

			FollowerDao.getInstance().merge(follower);
			response = Response
					.status(Response.Status.NO_CONTENT)
					.build();
		} catch (FollowerException e) {
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
			FollowerDao.getInstance().removeById(id);
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
