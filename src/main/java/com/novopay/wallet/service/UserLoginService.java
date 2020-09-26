package com.novopay.wallet.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novopay.wallet.model.UserAccount;
import com.novopay.wallet.model.UserLoginCredential;
import com.novopay.wallet.model.Wallet;
import com.novopay.wallet.payload.UserSignUpPayLoad;
import com.novopay.wallet.repository.LoginDetailRepository;
import com.novopay.wallet.repository.LoginRepository;

@Service
public class UserLoginService {
	
	private LoginRepository loginRepository;
	private LoginDetailRepository loginDetailRepository;
	
	@Autowired
	UserLoginService(LoginRepository loginRepository, LoginDetailRepository loginDetailRepository) {
		this.loginRepository = loginRepository;
		this.loginDetailRepository = loginDetailRepository;
	}
	
	
		@Transactional
		public void signUpUser(UserSignUpPayLoad userSignUpPayLoad) {
			UserAccount userAccount =   new UserAccount();
			userAccount.setName(userSignUpPayLoad.getName());
			userAccount.setEmail(userSignUpPayLoad.getEmail());
			userAccount.setPhone(userSignUpPayLoad.getPhone());
			userAccount.setWallet(new Wallet(new BigDecimal(0.0)));
			
									
			
			UserAccount newUser = loginRepository.save(userAccount);
			loginDetailRepository.save(new UserLoginCredential(newUser.getUserId(), newUser.getEmail(), userSignUpPayLoad.getPassword()));
		}
		
		public boolean checkLogin(String userName,String password)
		{
			if(loginDetailRepository.findByEmail(userName, password).orElse(null) != null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

}
