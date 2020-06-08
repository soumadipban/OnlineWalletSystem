package com.capg.onlinewallet.service;

import java.util.List;

import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.RechargeOffers;
import com.capg.onlinewallet.exception.OfferNotFoundException;

public interface IRechargeWalletService {

	public void viewAllOffers();

	public boolean findId(int id1);

	public void rechargeNumber(int id1, int price1, List<RechargeOffers> list);

	public void addRechargeOffers(RechargeOffers r);

	public void deleteRechargeOffer(int id);

	public RechargeOffers fetchRechargeDetail(int rechargeId) throws OfferNotFoundException;

	public int fetchRechargeBal(int rechargeId);

	public void rechargeNumber(int userId, int price1, RechargeOffers offer);

	

}
