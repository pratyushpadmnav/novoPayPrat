package com.novopay.wallet.controller;
import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.novopay.wallet.Exceptions.InsuffecientFundsException;
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
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	public void addMoneyToWallet(@RequestParam("email") String email, @RequestParam("pwd") String password,
			@Valid @RequestBody TransactionPayLoad transactionPayload) {
		if (userLoginService.checkLogin(email, password)) {
			try {
				
				transactionService.transferMoneyWallet(email, transactionPayload.getRecipientEmail(), transactionPayload.getAmount());
			} catch (UserNotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't Exist", e);
			} catch (WalletInvalid e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found", e);
			} catch (InsuffecientFundsException e) {
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Transaction declined. Insufficient funds.", e);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password invalid.");
		}
	}
	
	@RequestMapping(value = "chargeandcommission", method = RequestMethod.POST)
	public String computeCommisionandCharges(@RequestParam("amount") BigDecimal amount)
	{
		 String commissionAndCharges = "Charge : "+transactionService.calculateCharge(amount)+" Commission: "+transactionService.calculateCommission(amount);
		 return commissionAndCharges;
	}

}
