package com.capg.onlinewallet.service;

import java.util.List;
import java.util.Scanner;

import com.capg.onlinewallet.dto.LoginRecord;
import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.Status;
import com.capg.onlinewallet.dto.WalletTransaction;
import com.capg.onlinewallet.dto.WalletUser;
import com.capg.onlinewallet.exception.NoSubscribeException;
import com.capg.onlinewallet.exception.OWSException;
import com.capg.onlinewallet.exception.OfferNotFoundException;

public interface IUserWalletService {
	
	public boolean validateName(String name) throws OWSException;

	public boolean validatePassword(String password) throws OWSException;
	
	public boolean validContact(String contact) throws OWSException;
	
	//public Scanner integerValidation();
	
	public int userRegistration(WalletUser walletUser);

	public String accountBalDetail(int userId);

	public Status fetchAccountStatus(int userId);

	public double fetchAccountBal(int userId);

	public void updateAccountBal(double newBalance, int userId);

	public void loginRecord(LoginRecord record);

	

	public boolean loginNameChecker(String login_name);

	public void setBalance(double amount, int fUserId, int sUserId);

	public String fetchAccountHolderName(int userId);

	public void transactionDetail(WalletTransaction transaction);

	public boolean viewOffer() throws OfferNotFoundException;
	
	public int fetchId(String loginName);

	public OfferRecord offerDetail(int offerId);

	public void addSubscribe(List<OfferRecord> list, int userId);


	public boolean viewSubscribe(int userId) throws NoSubscribeException;
	
	public boolean login(String loginName, String password);


	public Scanner integerValidation();

	//public boolean userIdValidation(int i);

	public boolean userIdValidation(String i);

	public boolean checkId(int sUserId);


	

	


}
