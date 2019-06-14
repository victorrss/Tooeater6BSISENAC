package br.senac.backend.service;

import java.util.ArrayList;
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
import br.senac.backend.exception.UserException;
import br.senac.backend.model.Tooeat;
import br.senac.backend.model.User;
import br.senac.backend.model.pojo.UserSearchPojo;
import br.senac.backend.model.pojo.UserUpdatePojo;
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
			@ApiResponse(code = 406, message = "Falha validação, User Exception com retorno de: "+ "{\"message\": \"Message\"}"),
			@ApiResponse(code = 400, message = "Fail on create, try-catch") 
	})
	public Response create(User user) {
		try {
			//User user = UserCreatePojo.convertToModel(pojo);
			UserException userException = UserValidator.validate(user);
			if (userException != null)
				throw userException;

			user.setPassword(Util.sha1(user.getPassword()));
			user.setCreatedAt(Util.getDateNow());
/*
			//IMAGE SAVE
			try {
				//if (!user.getPhoto().isEmpty()) {
				String folderPath = System.getProperty("user.dir") + "\\tooeater_files\\images\\user";
				String fileName = "\\" + user.getId();
				ImageUtil.save(user.getPhoto(), folderPath, fileName);
				user.setPhoto(user.getId()+ImageUtil.getExtension(ImageUtil.getMimeType(user.getPhoto())));
				//}	
			} catch(Exception e) {}
*/

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
			@ApiResponse(code = 200, message = "Sucesso!"),
			@ApiResponse(code = 400, message = "Fail on create, try-catch")
	})
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

	@GET
	@Path("/search/{term}")
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
			@ApiResponse(code = 200, message = "Sucesso!"),
			@ApiResponse(code = 400, message = "Fail on create, try-catch")
	})
	public Response search(@PathParam("term") String term) {
		Response response;
		try {
			List<User> users = UserDao.getInstance().search(term);

			List <UserSearchPojo> result = new ArrayList<UserSearchPojo>();

			for (User user : users) {

				UserSearchPojo pojo = new UserSearchPojo();
				pojo.setUser(user);
				result.add(pojo);
			}

			response = Response
					.status(Response.Status.OK)
					.entity(result)
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
	@Path("/nickname/{nickname}")
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
			@ApiResponse(code = 200, message = "Sucesso!"),
			@ApiResponse(code = 400, message = "Fail on create, try-catch")
	})
	public Response getByNickname(@PathParam("nickname") String nickname) {
		Response response;
		try {
			User user = UserDao.getInstance().getByNickName(nickname);
			List<Tooeat> tooeats= TooeatDao.getInstance().getByUserId(user);
			UserSearchPojo pojo = new UserSearchPojo();			
			pojo.setTooeats(tooeats);
			pojo.setUser(user);

			response = Response
					.status(Response.Status.OK)
					.entity(pojo)
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
	@Secured
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Sucesso!"),
			@ApiResponse(code = 406, message = "Falha validação, User Exception com retorno de: "+ "{\"message\": \"Message\"}"),
			@ApiResponse(code = 400, message = "Fail on create, try-catch") 
	})
	public Response update(UserUpdatePojo pojo){
		Response response;
		try {
			User user = UserDao.getInstance().getById(pojo.getId());
			user = UserUpdatePojo.convertToModel(user, pojo);
			
			UserException userException = UserValidator.validateUpdate(user, user.getNickname());
			if (userException != null)
				throw userException;
/*
			//IMAGE UPDATE
			try {
				if (!user.getPhoto().isEmpty()) {
					String folderPath = System.getProperty("user.dir") + "\\tooeater_files\\images\\user";
					String fileName = "\\" + user.getId();
					ImageUtil.save(user.getPhoto(), folderPath, fileName);
					user.setPhoto(user.getId()+ImageUtil.getExtension(ImageUtil.getMimeType(user.getPhoto())));
				}	
			} catch(Exception e) {}
*/
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
	@Secured
	@ApiImplicitParams({@ApiImplicitParam(
			name = "Authorization", 
			value = "Bearer {JWT Token}",
			required = true,
			dataType = "string",
			paramType = "header"
			)})
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Sucesso!"),
			@ApiResponse(code = 400, message = "Fail on create, try-catch") 
	})
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
