package com.novopay.wallet.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.novopay.wallet.model.UserLoginCredential;

public interface LoginDetailRepository extends CrudRepository<UserLoginCredential, UUID> {
	public Optional<UserLoginCredential> findByUsernameAndPassword(String username, String password);

}

