package com.novopay.wallet.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.novopay.wallet.payload.UserSignUpPayLoad;
import com.novopay.wallet.service.TransactionService;
import com.novopay.wallet.service.UserLoginService;



@RestController
@RequestMapping(TransactionController.API)
public class TransactionController {
	public static final String API = "/wallet/api/v1/transact";
	
	
	public TransactionService transactionService;
	
	@Autowired
	TransactionController(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	} 
	
	
	@RequestMapping(value = "/addMoney", method = RequestMethod.POST)
	public void adduser(@Valid @RequestBody UserSignUpPayLoad userSignUpPayLoad) {
		transactionService.signUpUser(userSignUpPayLoad);
	}

}
