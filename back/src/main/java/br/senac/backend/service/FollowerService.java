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
import br.senac.backend.dao.FollowerDao;
import br.senac.backend.dao.UserDao;
import br.senac.backend.exception.FollowerException;
import br.senac.backend.model.Follower;
import br.senac.backend.model.User;
import br.senac.backend.util.Util;
import br.senac.backend.validator.FollowerValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/follower")
@Api("/Folower Service")
@SwaggerDefinition(tags= {@Tag (name="Folower Service", description="REST Endpoint for Folower Service")})
public class FollowerService {
	@Context SecurityContext securityContext;

	@POST
	@Path("/invite/{nickname}")
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
			@ApiResponse(code = 201, message = "Follow!"),
			@ApiResponse(code = 204, message = "Disfollow!"),
			@ApiResponse(code = 206, message = "Aguardando aceitar o convite!"),
			@ApiResponse(code = 400, message = "Falha geral, try-catch"), 
			@ApiResponse(code = 403, message = "enabled == false, não foi implementado") 
	})
	public Response create(@PathParam("nickname") String nickname, @Context SecurityContext securityContext) {
		Response response;
		try {
			Integer userId = Util.stringToInteger(securityContext.getUserPrincipal().getName());
			User uSlave = UserDao.getInstance().getById(userId);
			User uMaster = UserDao.getInstance().getByUserName(nickname);

			Follower f = new Follower();
			f.setUserSlave(uSlave);
			f.setUserMaster(uMaster);

			Follower followExists = FollowerDao.getInstance().getByMasterAndSlaveWithoutEnabled(uSlave.getId(), uMaster.getId());

			if (followExists == null) {
				// follow, THEN CREATE
				FollowerDao.getInstance().persist(f);
				response = Response
						.status(Response.Status.CREATED)
						.build();
			} else {
				if (followExists.getEnabled() == null) {
					// aguardando aceitar o invite
					response = Response
							.status(Response.Status.PARTIAL_CONTENT)
							.build();
				} else if (followExists.getEnabled() == true) {
					// disfollow, then delete
					FollowerDao.getInstance().remove(f);
					response = Response
							.status(Response.Status.NO_CONTENT)
							.build();
				} else {
					// false - não implementado
					response = Response
							.status(Response.Status.FORBIDDEN)
							.build();
				}
			}
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
	@Path("/isFollower/{nickname}")
	@Secured
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "ESTÁ SEGUINDO"),
			@ApiResponse(code = 204, message = "AGUARDANDO ACEITE DO INVITE"),
			@ApiResponse(code = 206, message = "NÃO ESTÁ SEGINDO"),
			@ApiResponse(code = 400, message = "Falha geral, try-catch"),
			@ApiResponse(code = 403, message = "enabled == false, não foi implementado") 
	})
	public Response isFollower(@PathParam("nickname") String nickname, @Context SecurityContext securityContext) {
		Response response;
		try {
			Integer userId = Util.stringToInteger(securityContext.getUserPrincipal().getName());
			User uMaster = UserDao.getInstance().getById(userId);
			User uSlave = UserDao.getInstance().getByUserName(nickname);

			Follower followExists = FollowerDao.getInstance().getByMasterAndSlaveWithoutEnabled(uSlave.getId(), uMaster.getId());

			if (followExists == null) {
				// NÃO ESTÁ SEGUINDO
				response = Response
						.status(Response.Status.PARTIAL_CONTENT)
						.build();
			} else {
				if (followExists.getEnabled() == null) {
					// AGUARDANDO ACEITE DO INVITE
					response = Response
							.status(Response.Status.NO_CONTENT)
							.build();
				} else if (followExists.getEnabled() == true) {
					// ESTÁ SEGUINDO
					response = Response
							.status(Response.Status.OK)
							.build();
				} else {
					// false - não implementado
					response = Response
							.status(Response.Status.FORBIDDEN)
							.build();
				}
			}
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
