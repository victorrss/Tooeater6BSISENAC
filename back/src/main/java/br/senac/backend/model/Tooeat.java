package br.senac.backend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tooeat")
public class Tooeat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	@OneToMany(mappedBy="tooeat")
	private Set<Comment> comments  = new HashSet<Comment>();
	@OneToMany(mappedBy="tooeat")
	private Set<Like> likes  = new HashSet<Like>();
	@Column
	private String text;
	@Column
	private String media;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	@Column(name="created_at")
	private Date createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	@Column(name="update_at")	
	private Date updateAt;
	@JsonIgnore
	@Column
	private Boolean enabled;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@JsonIgnoreProperties({"email","password","birthday","gender","bio","createdAt","updateAt"})
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Integer getComments() {
		return this.comments == null ? 0 : this.comments.size();
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	public Integer getLikes() {
		//return this.likes;
		return this.likes == null ? 0 : this.likes.size();
	}
	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
}
