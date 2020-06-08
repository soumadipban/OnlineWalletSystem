package com.capg.onlinewallet.serviceimpl;

import java.util.List;

import com.capg.onlinewallet.dao.RechargeWalletDao;
import com.capg.onlinewallet.dao.impl.IRechargeWalletDao;
import com.capg.onlinewallet.dto.RechargeOffers;
import com.capg.onlinewallet.exception.OfferNotFoundException;
import com.capg.onlinewallet.service.IRechargeWalletService;

public class RechargeWalletService implements IRechargeWalletService {
	IRechargeWalletDao idao = new RechargeWalletDao();

	@Override
	public void viewAllOffers() {
		idao.viewAllOffers();

	}

	@Override
	public void rechargeNumber(int id1, int price1, List<RechargeOffers> list) {
		idao.rechargeNumber(id1, price1,list);
		
	}
	
	
	@Override
	public void rechargeNumber(int userId, int price1, RechargeOffers offer) {
		idao.rechargeNumber(userId, price1,offer);
		
	}


	@Override
	public boolean findId(int id1) {

		return idao.findId(id1);
	}

	@Override
	public void addRechargeOffers(RechargeOffers r) {
		idao.addRechargeOffers(r);

	}

	@Override
	public void deleteRechargeOffer(int id) {
		idao.deleteRechargeOffer(id);
		
	}

	@Override
	public RechargeOffers fetchRechargeDetail(int rechargeId) throws OfferNotFoundException {
		return idao.fetchRechargeDetail(rechargeId);
	}

	@Override
	public int fetchRechargeBal(int rechargeId) {
		// TODO Auto-generated method stub
		return idao.fetchRechargeBal(rechargeId);
	}





}
