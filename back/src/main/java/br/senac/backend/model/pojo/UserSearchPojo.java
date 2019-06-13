package br.senac.backend.model.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.senac.backend.model.Tooeat;
import br.senac.backend.model.User;
public class UserSearchPojo {

	private User user;
	private List<Tooeat> tooeats;
	//@JsonIgnoreProperties({"user"})
	public List<Tooeat> getTooeats() {
		return tooeats;
	}
	public void setTooeats(List<Tooeat> tooeats) {
		this.tooeats = tooeats;
	}
	@JsonIgnoreProperties({"email","password","birthday","createdAt","updateAt"})
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
