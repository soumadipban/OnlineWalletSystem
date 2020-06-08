package com.capg.onlinewallet.dao.impl;

import java.util.Date;
import java.util.List;

import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.Status;

public interface IAdminWalletDao {

	public List<OfferRecord> viewOffers();

	public OfferRecord addOffers(OfferRecord offers);

	public boolean removeOffers(int offerId);

	public boolean updateOffers(int offerId2, String newName, Date ndoe);

	public boolean blockUser(int userId);

	public List<Object[]> viewStatus(int userId);

	public boolean updateStatus(int userId, Status newstatus);

	public boolean login(String loginName, String password);

}
