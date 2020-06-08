package com.capg.onlinewallet.serviceimpl;

import java.util.Date;
import java.util.List;

import com.capg.onlinewallet.dao.AdminWalletDao;
import com.capg.onlinewallet.dao.impl.IAdminWalletDao;
import com.capg.onlinewallet.dto.*;
import com.capg.onlinewallet.exception.OfferNotFoundException;
import com.capg.onlinewallet.exception.UserNotFoundException;
import com.capg.onlinewallet.service.IAdminWalletService;

public class AdminWalletService implements IAdminWalletService {
	IAdminWalletDao walletdao = new AdminWalletDao();

	@Override
	public List<OfferRecord> viewOffers() {
		return walletdao.viewOffers();
	}

	@Override
	public OfferRecord addOffers(OfferRecord offers) {
		return walletdao.addOffers(offers);

	}

	@Override
	public boolean removeOffers(int offerId) throws OfferNotFoundException {
		boolean rFlag = false;
		if (walletdao.removeOffers(offerId)) {
			rFlag = true;
		} else {
			throw new OfferNotFoundException("Offer not found");
		}
		return rFlag;
		// return walletdao.removeOffers(offerId);

	}

	@Override
	public boolean updateOffers(int offerId2, String newName, Date ndoe) throws OfferNotFoundException {
		boolean rFlag = false;
		if (walletdao.updateOffers(offerId2, newName, ndoe)) {
			rFlag = true;
		} else {
			throw new OfferNotFoundException("Offer not found");
		}
		return rFlag;

		// return walletdao.updateOffers(offerId2, newName, ndoe);

	}

	@Override
	public boolean blockUser(int userId) throws UserNotFoundException {
		boolean uFlag = false;
		if (walletdao.blockUser(userId)) {
			uFlag = true;
		} else {
			throw new UserNotFoundException("User not found");
		}
		return uFlag;
		// return walletdao.blockUser(userId);

	}

	@Override
	public List<Object[]> viewStatus(int userId) {
		return walletdao.viewStatus(userId);

	}

	@Override
	public boolean updateStatus(int userId, Status newstatus) throws UserNotFoundException {
		boolean uFlag = false;
		if (walletdao.updateStatus(userId, newstatus)) {
			uFlag = true;
		} else {
			throw new UserNotFoundException("User not found");
		}
		return uFlag;

		// return walletdao.updateStatus(userId,newstatus);

	}

	@Override
	public boolean login(String loginName, String password) {
		return walletdao.login(loginName, password);
	}
}
