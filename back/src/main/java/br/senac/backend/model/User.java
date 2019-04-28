package br.senac.backend.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="user")
@JsonIgnoreProperties({"password"})
public class User {

	@ApiModelProperty(hidden = true,readOnly = true)
	@Id
	private int id;
	@ApiModelProperty(hidden = true,readOnly = true)
	@OneToMany(mappedBy="user")
	private Set<Tooeat> tooeats;
	@ApiModelProperty(hidden = true,readOnly = true)
	@OneToMany(mappedBy="userSlave")
	private Set<Follower> following;
	@ApiModelProperty(hidden = true,readOnly = true)
	@OneToMany(mappedBy="userMaster")
	private Set<Follower> followers;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	private Calendar birthday;
	@Column
	private boolean gender;
	@Column
	private String photo;
	@Column
	private String bio;
	@Column
	@JsonIgnore
	private String email;
	@Column
	@JsonIgnore
	@ApiModelProperty(hidden = false,readOnly = true)
	private String password;
	@Column
	private String nickname;
	@ApiModelProperty(hidden = true,readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	@Column(name="created_at")
	private Date createdAt = new Date();
	@ApiModelProperty(hidden = true,readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	@Column(name="update_at")
	private Date updateAt;
	@ApiModelProperty(hidden = true,readOnly = true)
	@Column
	@JsonIgnore
	private Boolean enabled = true;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Calendar getBirthday() {
		return birthday;
	}
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public Boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Set<Follower> getFollowing() {
		return following;
	}
	public void setFollowing(Set<Follower> following) {
		this.following = following;
	}
	public Set<Follower> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<Follower> followers) {
		this.followers = followers;
	}
	public Set<Tooeat> getTooeats() {
		return tooeats;
	}
	public void setTooeats(Set<Tooeat> tooeats) {
		this.tooeats = tooeats;
	}

}
