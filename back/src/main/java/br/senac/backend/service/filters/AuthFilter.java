package br.senac.backend.service.filters;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import br.senac.backend.annotation.Secured;
import br.senac.backend.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
	private final static Logger log = Logger.getLogger( AuthFilter.class.getName() );
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		log.info( "Executing AuthenticationFilter" );
		// Get the HTTP Authorization header from the request
		String authorizationHeader =
				requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Check if the HTTP Authorization header is present and formatted correctly
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Authorization header must be provided");
		}

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {

			// Validate the token
			Jws<Claims> claims = validateToken(token);

			String username = claims.getBody().getSubject();

			requestContext.setSecurityContext(new SecurityContext() {

				@Override
				public Principal getUserPrincipal() {

					return new Principal() {

						@Override
						public String getName() {
							return username;
						}
					};
				}

				@Override
				public boolean isUserInRole(String role) {
					return true;
				}

				@Override
				public boolean isSecure() {
					return false;
				}

				@Override
				public String getAuthenticationScheme() {
					return null;
				}
			});

		} catch (Exception e) {
			requestContext.abortWith(
					Response.status(Response.Status.UNAUTHORIZED).build());
		}


	}

	private Jws<Claims> validateToken(String token) throws Exception {
		return JWTUtil.decode(token);
	}

}
