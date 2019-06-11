package br.senac.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tooeat_like")
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="tooeat_id", nullable=false)
	private Tooeat tooeat;

	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@JsonIgnoreProperties({"email","tooeats","following","followers","password","birthday","gender","bio","createdAt","updateAt"})
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}	
	@JsonIgnore
	public Tooeat getTooeat() {
		return tooeat;
	}
	
	public void setTooeat(Tooeat tooeat) {
		this.tooeat = tooeat;
	}
}
