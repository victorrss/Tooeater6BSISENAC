package br.senac.backend.model.pojo;

import java.util.Calendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.senac.backend.model.User;

public class UserCreatePojo {
	private String firstName;
	private String lastName;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	private Calendar birthday;
	private boolean gender;
	private String photo;
	private String email;
	private String password;
	private String bio;
	private String nickname;
	
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
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public static User convertToModel(UserCreatePojo pojo) {
		User model = new User();
		model.setFirstName(pojo.getFirstName());
		model.setLastName(pojo.getLastName());
		model.setBirthday(pojo.getBirthday());
		model.setGender(pojo.isGender());
		model.setPhoto(pojo.getPhoto());
		model.setEmail(pojo.getEmail());
		model.setPassword(pojo.getPassword());
		model.setBio(pojo.getBio());
		model.setNickname(pojo.getNickname());
		return model;
	}
}
