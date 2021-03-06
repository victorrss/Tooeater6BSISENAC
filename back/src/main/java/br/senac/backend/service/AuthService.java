package br.senac.backend.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.senac.backend.dao.UserDao;
import br.senac.backend.model.Auth;
import br.senac.backend.model.User;
import br.senac.backend.model.pojo.AuthAuthenticatePojo;
import br.senac.backend.util.JWTUtil;
import br.senac.backend.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("auth")
@Api("/Auth Service")
@SwaggerDefinition(tags= {@Tag (name="Auth Service", description="REST Endpoint for Auth Service")})
public class AuthService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso! Retornar� o JWT Token"),
			@ApiResponse(code = 403, message = "Usu�rio ou senha inv�lida")
	})
	public Response authenticateUser(Auth auth) {

		try {
			AuthAuthenticatePojo pojo = new AuthAuthenticatePojo();

			// Authenticate the user using the credentials provided
			User user = authenticate(auth.getUsername(), auth.getPassword());

			String userId = user.getId()+"";

			// Issue a token for the user
			String token = issueToken(userId);

			pojo.setToken(token);
			//if (user != null)
			pojo.setUser(user);

			// Return the token on the response
			return Response
					.status(Response.Status.OK)
					.entity(pojo)
					/*.entity("{"
							+ "\"token\": \""+token+"\", "
							+ "\"user\": \""+userJson+"\" "
							+ "}")*/
					.type(MediaType.APPLICATION_JSON)
					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}

	private User authenticate(String username, String password) throws Exception {
		User user = null;
		user = UserDao.getInstance().getByUserName(username);
		if(user != null && user.getPassword().equals(Util.sha1(password)))
			return user;
		else
			throw new Exception();
	}

	private String issueToken(String userId) {
		return JWTUtil.create(userId);
	}
}
