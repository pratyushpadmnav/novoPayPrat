package com.novopay.wallet.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.novopay.wallet.model.Transaction;
import com.novopay.wallet.model.UserAccount;
import com.novopay.wallet.model.Wallet;

public interface TransactionRepository extends CrudRepository<Transaction, UUID>{
	public List<Transaction> findAllByWallet(Wallet wallet,Sort sort);

}