package com.novopay.wallet.payload;

import com.sun.istack.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class UserSignUpPayLoad {
	
	
	@NotNull
	@Length(min = 1, max = 100)
	private String name;
	
	
	@Email
	@NotNull
	private String email;
	
	@Pattern(regexp="(^$|[0-9]{10})")
	@NotNull
	private String phone;
	
	@NotNull
	@Length(min = 1, max = 12)
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
