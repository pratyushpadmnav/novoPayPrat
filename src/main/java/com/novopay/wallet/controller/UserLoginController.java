package com.novopay.wallet.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.novopay.wallet.payload.UserSignUpPayLoad;
import com.novopay.wallet.service.UserLoginService;



@RestController
@RequestMapping(UserLoginController.API)
public class UserLoginController {
	public static final String API = "/wallet/api/v1/user";
	
	@Autowired
	public UserLoginService userLoginService;
	
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public void adduser(@Valid @RequestBody UserSignUpPayLoad userSignUpPayLoad) {
		userLoginService.signUpUser(userSignUpPayLoad);
	}

}
