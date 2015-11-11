package com.github.aureliano.verbum_domini.core.impl.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.validation.group.Delete;
import com.github.aureliano.verbum_domini.core.validation.group.Save;

@Entity
@Table(name = "user_tb")
public class UserBeanImpl implements UserBean {

	private static final long serialVersionUID = -3566933057670413917L;
	
	@Id
	@Column(name = "id", nullable = false)
	@NotNull(message = "Property 'Id' must be provided.", groups = { Save.class, Delete.class })
	private Integer id;
	
	@Column(name = "login", precision = 50, nullable = false)
	@NotNull(message = "Property 'Login' must be provided.", groups = { Save.class })
	@Size(min = 3, max = 50, message = "Property 'Login' must have between 3 and 50 characters.", groups = { Save.class })
	private String login;
	
	@Column(name = "password", precision = 50, nullable = false)
	@NotNull(message = "Property 'Password' must be provided.", groups = { Save.class })
	@Size(min = 6, max = 50, message = "Property 'Password' must have between 6 and 50 characters.", groups = { Save.class })
	private String password;

	@Column(name = "salt_number", precision = 5, nullable = false)
	@NotNull(message = "Property 'Salt number' must be provided.", groups = { Save.class })
	@Size(min = 5, max = 5, message = "Property 'Salt number' must have between 5 and 5 characters.", groups = { Save.class })
	private String saltNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "creation", nullable = false)
	private Date creation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_sign_in", nullable = true)
	private Date lastSignIn;
	
	@Column(name = "active", nullable = false)
	private Boolean active;

	public UserBeanImpl() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSaltNumber() {
		return this.saltNumber;
	}

	public void setSaltNumber(String saltNumber) {
		this.saltNumber = saltNumber;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getLastSignIn() {
		return lastSignIn;
	}

	public void setLastSignIn(Date lastSignIn) {
		this.lastSignIn = lastSignIn;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBeanImpl other = (UserBeanImpl) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}