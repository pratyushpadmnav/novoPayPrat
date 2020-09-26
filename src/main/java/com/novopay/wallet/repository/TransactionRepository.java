package com.novopay.wallet.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.novopay.wallet.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, UUID>{

}