package com.capg.onlinewallet.service;

import java.util.Date;
import java.util.List;

import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.Status;
import com.capg.onlinewallet.exception.OfferNotFoundException;
import com.capg.onlinewallet.exception.UserNotFoundException;

public interface IAdminWalletService {

	public List<OfferRecord> viewOffers();

	public OfferRecord addOffers(OfferRecord offers);

	public boolean removeOffers(int offerId) throws OfferNotFoundException;

	public boolean updateOffers(int offerId2, String newName, Date ndoe) throws OfferNotFoundException;

	public boolean blockUser(int userId) throws UserNotFoundException;

	public List<Object[]> viewStatus(int userId);

	public boolean updateStatus(int userId, Status newstatus) throws UserNotFoundException;

	public boolean login(String loginName, String password);


}
