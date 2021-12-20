package com.assignment1.springBootRest.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
@Entity
@Table(name = "USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private long userId;
	
	@Pattern(regexp = "[a-zA-Z][a-zA-Z ]{1,48}[a-zA-Z]", message = "{user.userName.message}")
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "SUR_NAME")
	@Pattern(regexp = "[a-zA-Z][a-zA-Z ]{1,48}[a-zA-Z]", message = "{user.surName.message}")
	private String surName;
	
	@Column(name = "DOB")
	@Temporal(TemporalType.DATE)
	@Past(message = "{user.dob.message}")
	private Date dob;
	
	@Column(name = "JOINING_DATE")
	@Temporal(TemporalType.DATE)
	private Date joiningDate;
	
	@Column(name = "DELETE_FLAG")
	private int deleteFlag;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy ="user", orphanRemoval = true )
	private List<Address> address;
	
	public User() {
		
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
	
}
