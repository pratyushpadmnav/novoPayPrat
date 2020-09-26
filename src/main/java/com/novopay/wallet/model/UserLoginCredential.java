package com.novopay.wallet.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERLOGINCREDENTIAL")
public class UserLoginCredential {
	
	
	
		
		@Id
		@Column(name = "USERUUID", nullable = false, unique = true, updatable = false)
		private UUID useruuid;
		
		@Column(name = "EMAIL", nullable = false, unique = true, updatable = false, length = 60)
		private String email;
		
		@Column(name = "PASSWORD", length = 12, nullable = true, updatable = true)
		private String password;
		
		

		public UserLoginCredential() {
			super();
		}

		public UserLoginCredential(UUID useruuid, String email, String password) {
			this.useruuid = useruuid;
			this.email = email;
			this.password = password;
		}

		public UUID getUseruuid() {
			return useruuid;
		}

		public String getEmail() {
			return email;
		}

		public String getPassword() {
			return password;
		}

}
