package com.capg.onlinewallet.dao.impl;

import java.util.List;

import com.capg.onlinewallet.dto.RechargeOffers;
import com.capg.onlinewallet.exception.OfferNotFoundException;

public interface IRechargeWalletDao {

	public void viewAllOffers();

	public void rechargeNumber(int id, int price, List<RechargeOffers> list);

	public boolean findId(int id1);

	public void addRechargeOffers(RechargeOffers rechOff);

	public void deleteRechargeOffer(int id);

	public RechargeOffers fetchRechargeDetail(int rechargeId) throws OfferNotFoundException;

	public int fetchRechargeBal(int rechargeId);

	public void rechargeNumber(int userId, int price1, RechargeOffers offer);
}
