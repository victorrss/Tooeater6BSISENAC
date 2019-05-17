package br.senac.backend.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.senac.backend.dao.Manager;
import br.senac.backend.util.ImageUtil;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToMany(mappedBy="user")
	private Set<Tooeat> tooeats;
	@OneToMany(mappedBy="userSlave")
	private Set<Follower> following;
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
	private String email;
	@Column
	private String password;
	@Column
	private String nickname;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	@Column(name="created_at")
	private Date createdAt = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	@Column(name="update_at")
	private Date updateAt;
	@Column
	private boolean enabled = true;

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
		String folderPath = System.getProperty("user.dir") + "/tooeater_files/images/user";
		try {
			return ImageUtil.read(folderPath, this.photo);
		} catch(Exception e) { return null;}
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
	@JsonIgnore
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void setTooeats(Set<Tooeat> tooeats) {
		this.tooeats = tooeats;
	}
	public void setFollowing(Set<Follower> following) {
		this.following = following;
	}
	public void setFollowers(Set<Follower> followers) {
		this.followers = followers;
	}
	@SuppressWarnings("deprecation")
	public Long getFollowing() {
		return (Long) Manager
				.getInstance()
				.getSession()
				.createFilter(this.following, "select count(*) where enabled=1" )
				.uniqueResult();
	}
	@SuppressWarnings("deprecation")
	public Long getFollowers() {
		return (Long) Manager
				.getInstance()
				.getSession()
				.createFilter(this.followers, "select count(*) where enabled=1" )
				.uniqueResult();
	}
	@SuppressWarnings("deprecation")
	public Long getTooeats() {
		return (Long) Manager
				.getInstance()
				.getSession()
				.createFilter(this.tooeats, "select count(*) where enabled=1" )
				.uniqueResult();
	}
}