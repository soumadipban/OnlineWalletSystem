package com.capg.onlinewallet.dao;

import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.hibernate.TransactionException;

import com.capg.onlinewallet.dao.impl.IRechargeWalletDao;
import com.capg.onlinewallet.dto.RechargeOffers;
import com.capg.onlinewallet.dto.WalletAccount;
import com.capg.onlinewallet.dto.WalletUser;
import com.capg.onlinewallet.exception.OWSException;
import com.capg.onlinewallet.exception.OfferNotFoundException;
import com.capg.onlinewallet.utility.JpaUtility;

public class RechargeWalletDao implements IRechargeWalletDao {
	Logger log = Logger.getLogger("onlinewallet");
	EntityManagerFactory factory = null;
	EntityManager manager = null;
	EntityTransaction transaction = null;

	@Override
	public void addRechargeOffers(RechargeOffers rechOff) {

		factory = JpaUtility.getFactory();
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.persist(rechOff);
			transaction.commit();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		}
	}

	@Override
	public void deleteRechargeOffer(int id) {
		factory = JpaUtility.getFactory();
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		boolean bool = true;
		try {
			transaction.begin();
			RechargeOffers r = manager.find(RechargeOffers.class, id);
			if (r == null) {
				bool = false;
			}
			if (bool == true) {
				manager.remove(r);
				manager.getTransaction().commit();
			} else {
				try {
					log.info(
							"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					log.info(
							"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					throw new OWSException("This Id does not exist ");

				} catch (OWSException ex) {
					log.info(ex.getMessage());
				}
			}
		} catch (TransactionException e) {
			log.error(e.getMessage());
			transaction.rollback();
		}
	}

	public void viewAllOffers() {
		factory = JpaUtility.getFactory();
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();

		transaction.begin();
		Query query = manager.createQuery("Select s from RechargeOffers s ");
		@SuppressWarnings("unchecked")
		List<RechargeOffers> list = (List<RechargeOffers>) query.getResultList();

		for (RechargeOffers s : list) {

			System.out.println("RECHARGE ID : " + s.getId());
			System.out.println("RECHARGE NAME : " + s.getName());
			System.out.println("RECHARGE PRICE : " + s.getPrice());
			System.out.println("TALKTIME : " + s.getTalktime());
			System.out.println("INTERNET DATA : " + s.getInternet_data());
			System.out.println("VALIDITY : " + s.getValidity());
			System.out.println("-----------------------------------------------------------------");
		}
		manager.getTransaction().commit();
		manager.close();
		manager.close();
	}

	/*
	 * @Override public void rechargeNumber(int id, int price) { factory =
	 * JpaUtility.getFactory(); manager = factory.createEntityManager(); transaction
	 * = manager.getTransaction(); boolean bool = false; double availbalance; try {
	 * WalletAccount wa = manager.find(WalletAccount.class, id); if (wa != null) {
	 * bool = true; }
	 * 
	 * availbalance = wa.getAccount_Bal();
	 * log.info("Your balance in your account is :" + availbalance);
	 * 
	 * if (availbalance < price) { log.info(
	 * "Your available balance in wallet is low in compare to your recharge amount!!! Add money to your account"
	 * ); } else { double updatedBalance = availbalance - price;
	 * transaction.begin(); wa.setAccount_Bal(updatedBalance); transaction.commit();
	 * log.info("Your mobile no has been recharged with balance" + price);
	 * log.info("Remaining balance in your account is:"); log.info(updatedBalance);
	 * }
	 * 
	 * } catch (TransactionException e) { log.error(e.getMessage());
	 * transaction.rollback(); } }
	 */

	@Override
	public boolean findId(int id1) {
		factory = JpaUtility.getFactory();
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		boolean bool = false;
		WalletUser wa = manager.find(WalletUser.class, id1);
		if (wa != null)
			bool = true;
		else
			bool = false;
		if (bool == false) {
			try {
				log.info(
						"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				log.info(
						"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				throw new OWSException("This Id does not exist ");
			} catch (OWSException ex) {
				log.info(ex.getMessage());

			}

		}

		return bool;
	}

	@Override
	public RechargeOffers fetchRechargeDetail(int rechargeId) throws OfferNotFoundException {
		factory = JpaUtility.getFactory();
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		RechargeOffers offer = manager.find(RechargeOffers.class, rechargeId);
		if (offer != null)
			return offer;
		else
			throw new OfferNotFoundException("No Offer is currently available");
	}

	@Override
	public int fetchRechargeBal(int rechargeId) {
		factory = JpaUtility.getFactory();
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		RechargeOffers offer = manager.find(RechargeOffers.class, rechargeId);

		return offer.getPrice();

	}

	@Override
	public void rechargeNumber(int id, int price, List<RechargeOffers> list) {
		factory = JpaUtility.getFactory();
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		boolean bool = false;
		double availbalance;
		try {
			WalletUser wa = manager.find(WalletUser.class, id);
			if (wa != null) {
				bool = true;
			}
			wa.setRecharge(list);

			availbalance = wa.getWalletaccount().getAccount_Bal();
			log.info("Your balance in your account is :" + availbalance);

			if (availbalance < price) {
				log.info(
						"Your available balance in wallet is low in compare to your recharge amount!!! Add money to your account");
			} else {
				double updatedBalance = availbalance - price;
				transaction.begin();
				wa.getWalletaccount().setAccount_Bal(updatedBalance);
				manager.persist(wa);
				transaction.commit();
				log.info("Your mobile no has been recharged with balance" + price);
				log.info("Remaining balance in your account is:");
				log.info(updatedBalance);
			}

		} catch (TransactionException e) {
			log.error(e.getMessage());
			transaction.rollback();
		}

	}
	
	
	@Override
	public void rechargeNumber(int userId, int price1, RechargeOffers offer) {
		factory = JpaUtility.getFactory();
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		boolean bool = false;
		double availbalance;
		try {
			WalletUser wa = manager.find(WalletUser.class, userId);
			if (wa != null) {
				bool = true;
			}
			wa.setRecharge(offer);

			availbalance = wa.getWalletaccount().getAccount_Bal();
			log.info("Your balance in your account is :" + availbalance);

			if (availbalance < price1) {
				log.info(
						"Your available balance in wallet is low in compare to your recharge amount!!! Add money to your account");
			} else {
				double updatedBalance = availbalance - price1;
				transaction.begin();
				wa.getWalletaccount().setAccount_Bal(updatedBalance);
				manager.persist(wa);
				transaction.commit();
				log.info("Your mobile no has been recharged with balance " + price1);
				log.info("Remaining balance in your account is:");
				log.info(updatedBalance);
			}

		} catch (TransactionException e) {
			log.error(e.getMessage());
			transaction.rollback();
		}
		
	}



}
