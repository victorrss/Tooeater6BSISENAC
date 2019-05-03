package br.senac.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="follower")
public class Follower {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "slave_user_id", nullable=false)  
	private User userSlave;

	@ManyToOne
	@JoinColumn(name = "master_user_id", nullable=false)
	private User userMaster;
	@Column
	private Boolean enabled;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUserSlave() {
		return userSlave;
	}
	public void setUserSlave(User userSlave) {
		this.userSlave = userSlave;
	}
	public User getUserMaster() {
		return userMaster;
	}
	public void setUserMaster(User userMaster) {
		this.userMaster = userMaster;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
