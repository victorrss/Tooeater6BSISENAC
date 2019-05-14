package br.senac.backend.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.senac.backend.model.User;

public class AuthAuthenticatePojo {
	private String token;
	private User user;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@JsonIgnoreProperties({"password"})
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
