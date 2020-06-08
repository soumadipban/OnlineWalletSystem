package com.capg.onlinewallet.dao.impl;

import java.util.List;

import com.capg.onlinewallet.dto.LoginRecord;
import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.Status;
import com.capg.onlinewallet.dto.WalletTransaction;
import com.capg.onlinewallet.dto.WalletUser;

public interface IUserWalletDao {
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

	public boolean viewOffer();

	public int fetchId(String loginName);

	public OfferRecord offerDetail(int offerId);

	public void addSubscribe(List<OfferRecord> list, int userId);

	public boolean viewSubscribe(int userId);

	boolean login(String loginName, String password);

	public boolean checkId(int sUserId);

}
