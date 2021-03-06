package br.senac.backend.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tooeat_comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="tooeat_id", nullable=false)
	private Tooeat tooeat;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	@Column
	private String text;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	@Column(name="created_at")
	private Date createdAt;
	@Column
	@JsonIgnore
	private Boolean enabled = true;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	@JsonIgnore
	public Tooeat getTooeat() {
		return tooeat;
	}
	public void setTooeat(Tooeat tooeat) {
		this.tooeat = tooeat;
	}
	@JsonIgnoreProperties({"email","tooeats","following","followers","password","birthday","gender","bio","createdAt","updateAt"})
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
