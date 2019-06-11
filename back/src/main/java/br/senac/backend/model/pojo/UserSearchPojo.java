package br.senac.backend.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.senac.backend.model.User;

public class UserSearchPojo {
	private User user;

	@JsonIgnoreProperties({"email","tooeats","password","birthday","createdAt","updateAt"})
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
