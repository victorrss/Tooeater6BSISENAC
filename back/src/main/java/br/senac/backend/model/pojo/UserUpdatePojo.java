package br.senac.backend.model.pojo;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.senac.backend.model.User;
import br.senac.backend.util.Util;

public class UserUpdatePojo {
	private int id;
	private String firstName;
	private String lastName;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(locale = "pt-BR", timezone = "Brazil/East")
	private Calendar birthday;
	private boolean gender;
	private String password = null;
	private String bio;
	private String nickname;
	private String photo;
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public static User convertToModel(User model, UserUpdatePojo pojo) throws NoSuchAlgorithmException {
		model.setId(pojo.getId());
		model.setFirstName(pojo.getFirstName());
		model.setLastName(pojo.getLastName());
		model.setBirthday(pojo.getBirthday());
		model.setGender(pojo.isGender());
		try {
			if (!pojo.getPassword().isEmpty())
				model.setPassword(Util.sha1(pojo.getPassword()));
		} catch(Exception e) {}
		model.setPhoto(pojo.getPhoto());
		model.setBio(pojo.getBio());
		model.setNickname(pojo.getNickname());
		model.setUpdateAt(Util.getDateNow());
		return model;
	}
}
