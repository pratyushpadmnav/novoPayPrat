package com.novopay.wallet.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.novopay.wallet.Exceptions.UserNotFoundException;
import com.novopay.wallet.Exceptions.WalletInvalid;
import com.novopay.wallet.payload.TransactionPayLoad;
import com.novopay.wallet.payload.UserSignUpPayLoad;
import com.novopay.wallet.service.TransactionService;
import com.novopay.wallet.service.UserLoginService;



@RestController
@RequestMapping(TransactionController.API)
public class TransactionController {
	public static final String API = "/wallet/api/v1/transact";
	
	
	public TransactionService transactionService;
	public UserLoginService userLoginService;
	
	@Autowired
	TransactionController(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	} 
	
	
	@RequestMapping(value = "/addMoney", method = RequestMethod.POST)
	public void addMoney(@RequestParam("email") String email, @RequestParam("pwd") String password,
			@Valid @RequestBody TransactionPayLoad transactionPayload) {
		if (userLoginService.checkLogin(email, password)) {
			try {
				transactionService.addMoney(email, transactionPayload.getAmount());
			
			}catch (WalletInvalid e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No wallet linked to User Found", e);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login Credentials");
		}
	}

}
