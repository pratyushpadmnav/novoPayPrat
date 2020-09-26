package com.novopay.wallet.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.novopay.wallet.model.UserAccount;
import com.novopay.wallet.model.Wallet;

public interface WalletRepository extends CrudRepository<Wallet, UUID>{
	

}