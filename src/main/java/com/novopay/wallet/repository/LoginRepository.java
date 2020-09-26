package com.novopay.wallet.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.novopay.wallet.model.UserAccount;

@Repository
public interface LoginRepository extends CrudRepository<UserAccount, UUID>{
	public Optional<User> findByUsername(String username);
}


