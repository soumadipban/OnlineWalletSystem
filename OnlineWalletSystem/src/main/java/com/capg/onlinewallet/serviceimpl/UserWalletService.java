package com.capg.onlinewallet.serviceimpl;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.capg.onlinewallet.dao.UserWalletDao;
import com.capg.onlinewallet.dao.impl.IUserWalletDao;
import com.capg.onlinewallet.dto.LoginRecord;
import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.Status;
import com.capg.onlinewallet.dto.WalletTransaction;
import com.capg.onlinewallet.dto.WalletUser;
import com.capg.onlinewallet.exception.NoSubscribeException;
import com.capg.onlinewallet.exception.OWSException;
import com.capg.onlinewallet.exception.OfferNotFoundException;
import com.capg.onlinewallet.service.IUserWalletService;

public class UserWalletService implements IUserWalletService {
	static Logger log = LogManager.getLogger("onlinewallet");
	static Scanner scan;

	IUserWalletDao walletdao = new UserWalletDao();

	@Override
	public boolean validateName(String name) throws OWSException {

		boolean nameFlag = false;
		String nameRegEx = "[A-Z]{1}[A-Za-z\\s]{4,19}";

		if (Pattern.matches(nameRegEx, name)) {
			nameFlag = true;
		} else {
			throw new OWSException("First letter should be capital and length must be in the range of 5 to 20");
		}
		return nameFlag;

	}

	@Override
	public boolean validatePassword(String password) throws OWSException {
		boolean passflag = false;
		String password_pattern = "((?=.*[a-z])(?=.*\\d)(?=.*[@#$%!]).{8,40})";
		if (Pattern.matches(password_pattern, password)) {
			passflag = true;
		} else {
			throw new OWSException("Password is not valid...Please Enter again");
		}

		return passflag;
	}

	@Override
	public boolean validContact(String contact) throws OWSException {
		boolean contactFlag = false;
		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(contact);
		if (m.find() && m.group().equals(contact)) {
			contactFlag = true;
		} else {
			throw new OWSException("Invalid Mobile Number");
		}
		return contactFlag;
	}

	@Override
	public Scanner integerValidation() {
		scan = new Scanner(System.in);
		while (!scan.hasNextInt()) {
			log.error("Enter the input in specified format");
			scan.next();
		}
		return scan;
	}
	
	@Override
	public boolean userIdValidation(String i) {
		boolean idFlag = false;
		//String value=Integer.toString(i);
		String nameRegEx = "[^A-Za-z@#$%&^*]+";
		if(Pattern.matches(nameRegEx, i))
		{
			idFlag = true;
		}else {
			throw new OWSException("Enter the input in specified format");
		}
		return idFlag;
	}

	@Override
	public int userRegistration(WalletUser walletUser) {
		return walletdao.userRegistration(walletUser);
	}

	@Override
	public String accountBalDetail(int userId) {
		return walletdao.accountBalDetail(userId);
	}

	@Override
	public Status fetchAccountStatus(int userId) {
		return walletdao.fetchAccountStatus(userId);
	}

	@Override
	public double fetchAccountBal(int userId) {
		return walletdao.fetchAccountBal(userId);
	}

	@Override
	public void updateAccountBal(double newBalance, int userId) {
		walletdao.updateAccountBal(newBalance, userId);
	}

	public void loginRecord(LoginRecord record) {
		walletdao.loginRecord(record);

	}

	@Override
	public boolean loginNameChecker(String login_name) {
		return walletdao.loginNameChecker(login_name);
	}

	@Override
	public void setBalance(double amount, int fUserId, int sUserId) {
		walletdao.setBalance(amount, fUserId, sUserId);

	}

	@Override
	public String fetchAccountHolderName(int userId) {
		return walletdao.fetchAccountHolderName(userId);
	}

	@Override
	public void transactionDetail(WalletTransaction transaction) {
		walletdao.transactionDetail(transaction);

	}

	@Override
	public boolean viewOffer() throws OfferNotFoundException {
		boolean viewFlag = false;
		if(walletdao.viewOffer())
			viewFlag = true;
		else {
			throw new OfferNotFoundException("Currently No offers are available ... Please Stay tuned");
		}
		return viewFlag;
	}

	@Override
	public int fetchId(String loginName) {
		
		return walletdao.fetchId(loginName);
	}

	@Override
	public OfferRecord offerDetail(int offerId) {
		return walletdao.offerDetail(offerId);
	}

	@Override
	public void addSubscribe(List<OfferRecord> list, int userId) {
		walletdao.addSubscribe(list,userId);
		
	}

	@Override
	public boolean viewSubscribe(int userId) throws NoSubscribeException {
		boolean viewFlag = false;
		if(walletdao.viewSubscribe(userId))
		{
			viewFlag = true;
		}else {
			throw new NoSubscribeException("No Offer is subscribed by UserId : " + userId);
		}
		
		return viewFlag;
		
	}

	@Override
	public boolean login(String loginName, String password) {
		return walletdao.login(loginName, password);
	}

	@Override
	public boolean checkId(int sUserId) {
		// TODO Auto-generated method stub
		return walletdao.checkId(sUserId);
	}
}
