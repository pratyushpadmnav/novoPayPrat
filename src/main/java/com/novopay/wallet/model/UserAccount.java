package com.novopay.wallet.model;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "USERACCOUNT")
public class UserAccount {
	

		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "USERID", nullable = false, unique = true, updatable = false)
		private UUID userId;
		
		
		@Column(name = "NAME", length = 100, nullable = false, updatable = true)
		private String name;
		
		@Email
		@Column(name = "EMAIL", length = 100, nullable = true, updatable = true)
		private String email;
		
		@Pattern(regexp="(^$|[0-9]{10})")
		@Column(name = "PHONE", length = 10, nullable = true, updatable = true)
		private String phone;
		
		@Column(name = "ADDRESS", length = 100, nullable = true, updatable = true)
		private String address;
		
		@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "WALLETID", referencedColumnName = "walletid")
		private Wallet wallet;

		public UUID getUserId() {
			return userId;
		}

		public void setUserId(UUID userId) {
			this.userId = userId;
		}

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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Wallet getWallet() {
			return wallet;
		}

		public void setWallet(Wallet wallet) {
			this.wallet = wallet;
		}
		

}
