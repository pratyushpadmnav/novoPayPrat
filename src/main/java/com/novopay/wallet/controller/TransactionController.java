package com.novopay.wallet.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.novopay.wallet.Exceptions.TransactionNotFound;
import com.novopay.wallet.Exceptions.UserNotFoundException;
import com.novopay.wallet.Exceptions.WalletInvalid;
import com.novopay.wallet.model.Transaction;
import com.novopay.wallet.payload.Passbook;
import com.novopay.wallet.payload.PassbookPayload;
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
	TransactionController(TransactionService transactionService,UserLoginService userLoginService)
	{
		this.transactionService = transactionService;
		this.userLoginService =userLoginService;
	} 
	
	
	@RequestMapping(value = "/addMoney", method = RequestMethod.POST)
	public String addMoney(@RequestParam("email") String email, @RequestParam("pwd") String password,
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
		return transactionService.SUCCESS;
	}
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	public String addMoneyToWallet(@RequestParam("email") String email, @RequestParam("pwd") String password,
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
		return transactionService.SUCCESS;
	}
	
	@RequestMapping(value = "/chargeandcommission", method = RequestMethod.POST)
	public String computeCommisionandCharges(@RequestParam("amount") BigDecimal amount)
	{
		 String commissionAndCharges = "Charge : "+transactionService.calculateCharge(amount).setScale(4, BigDecimal.ROUND_HALF_UP)+" Commission: "+transactionService.calculateCommission(amount).setScale(4, BigDecimal.ROUND_HALF_UP);
		 return commissionAndCharges;
	}
	
	@RequestMapping(value = "/status",method = RequestMethod.POST)
	public String  transactionStatus(@RequestParam("email") String email,@RequestParam("pwd") String password, @RequestParam("transactionId") String transactionId)
	{
		String status="";
		
		
		UUID id;
		try {
			id = UUID.fromString(transactionId);
		}
		catch(IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction Id is not valid.");
		}
		
		if (userLoginService.checkLogin(email, password)) {
			try {
				status=transactionService.checkTransactionStatus(id);
			}
			catch(TransactionNotFound e)
			{
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found", e);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password invalid.");
		}
		return status;
	}
	
	@RequestMapping(value="/reverseTransaction" , method = RequestMethod.POST)
	public String reverseTransaction(@RequestParam("email") String email,@RequestParam("pwd") String password, @RequestParam("transactionId") String transactionId) throws WalletInvalid, InsuffecientFundsException
	{
		
		UUID id;
		try {
			id = UUID.fromString(transactionId);
		}
		catch(IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction Id is not valid.");
		}
		
		if (userLoginService.checkLogin(email, password)) {
			try {
				transactionService.RevertTransaction(id);
			}
			catch(TransactionNotFound e)
			{
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found", e);
			}
			catch(WalletInvalid e)
			{
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found", e);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password invalid.");
		}
		
		return transactionService.SUCCESS;
	}
	
	@RequestMapping(value="/viewPassBook" , method = RequestMethod.POST)
	public Passbook viewPassBook(@RequestParam("email") String email,@RequestParam("pwd") String password) throws WalletInvalid, InsuffecientFundsException
	{
		
		Passbook passbook = new Passbook();
		if (userLoginService.checkLogin(email, password)) {
			try {
				passbook = transactionService.viewPassBook(email);
			}
			catch(UserNotFoundException e)
			{
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
			}
			
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password invalid.");
		}
		
		return passbook;
	}
}
