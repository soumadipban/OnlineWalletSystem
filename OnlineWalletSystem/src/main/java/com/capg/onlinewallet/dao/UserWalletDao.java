package com.capg.onlinewallet.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;

import com.capg.onlinewallet.dao.impl.IUserWalletDao;
import com.capg.onlinewallet.dto.WalletUser;
import com.capg.onlinewallet.utility.JpaUtility;
import com.capg.onlinewallet.dto.LoginRecord;
import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.Status;
import com.capg.onlinewallet.dto.WalletAccount;
import com.capg.onlinewallet.dto.WalletTransaction;

public class UserWalletDao implements IUserWalletDao {

	Logger log = Logger.getLogger("onlinewallet");

	EntityManagerFactory factory = null;
	EntityManager manager = null;
	EntityTransaction transaction = null;

	@Override
	public boolean login(String loginName, String password) {
		UserWalletDao dao = new UserWalletDao();
		boolean flag = false;
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		try {
			Query query = manager.createQuery("Select s from LoginRecord s");
			List<LoginRecord> list = query.getResultList();
			for (LoginRecord loginRecord : list) {
				String login_Name = loginRecord.getLoginName();
				String pass = loginRecord.getPassword();

				if (login_Name.equals(loginName) && pass.equals(password)) {
					flag = true;
				}
			}

			int fetchId = dao.fetchId(loginName);

			if (fetchId == 0) {
				flag = false;
			}

			transaction.commit();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}
		return flag;
	}

	@Override
	public int userRegistration(WalletUser walletUser) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		int accountId = 0;
		transaction.begin();
		try {
			WalletAccount wacct = new WalletAccount(0.0, Status.PENDING);
			walletUser.setWalletaccount(wacct);
			manager.persist(walletUser);
			accountId = wacct.getAccountId();
			transaction.commit();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}
		return accountId;
	}

	@Override
	public String accountBalDetail(int userId) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		String result = "";
		try {
			WalletUser user = manager.find(WalletUser.class, userId);

			result = result + "\n" + "NAME:" + user.getUserName() + "\t\t" + "ACCOUNT ID:"
					+ user.getWalletaccount().getAccountId() + "\t\t" + "ACCOUNT BALANCE:"
					+ user.getWalletaccount().getAccount_Bal();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}
		return result;
	}

	@Override
	public Status fetchAccountStatus(int userId) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		Status status = null;
		transaction.begin();
		try {
			WalletUser user = manager.find(WalletUser.class, userId);
			status = user.getWalletaccount().getStatus();
			transaction.commit();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}
		return status;
	}

	@Override
	public double fetchAccountBal(int userId) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		WalletUser user = manager.find(WalletUser.class, userId);
		return user.getWalletaccount().getAccount_Bal();
	}

	@Override
	public void updateAccountBal(double newBalance, int userId) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		try {
			WalletUser user = manager.find(WalletUser.class, userId);
			user.getWalletaccount().setAccount_Bal(newBalance);
			transaction.commit();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}

	}

	@Override
	public void loginRecord(LoginRecord record) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		try {
			manager.persist(record);
			transaction.commit();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}

	}

	@Override
	public boolean loginNameChecker(String login_name) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		LoginRecord record = manager.find(LoginRecord.class, login_name);
		if (record == null)
			return false;
		else
			return true;
	}

	@Override
	public void setBalance(double amount, int fUserId, int sUserId) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		try {
			WalletUser fuser = manager.find(WalletUser.class, fUserId);
			double fbal = fuser.getWalletaccount().getAccount_Bal();// fetching original balance
			fuser.getWalletaccount().setAccount_Bal(fbal - amount);
			WalletUser suser = manager.find(WalletUser.class, sUserId);
			double sbal = suser.getWalletaccount().getAccount_Bal();// fetching original balance
			suser.getWalletaccount().setAccount_Bal(sbal + amount);
			transaction.commit();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}

	}

	@Override
	public String fetchAccountHolderName(int userId) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		String name = "";
		try {

			WalletUser user = manager.find(WalletUser.class, userId);
			name = user.getUserName();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}
		return name;
	}

	@Override
	public void transactionDetail(WalletTransaction waltransaction) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
		transaction.begin();
		try {

			manager.persist(waltransaction);
			transaction.commit();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}

		log.info("Transaction is Successfully Done");

	}

	@Override
	public boolean viewOffer() {
		boolean viewFlag = false;
		List<OfferRecord> list = null;
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		Query query = manager.createQuery("select w from OfferRecord w ");
		list = query.getResultList();

		if (list.isEmpty()) {
		} else {
			try {
				// List<OfferRecord> list = query.getResultList();
				log.info("\t\t----------OFFER RECORD-----------");
				log.info("\n");
				for (OfferRecord wo : list) {
					log.info("OFFER ID : " + wo.getOfferId() + "\t\t" + "OFFER NAME : " + wo.getOfferName() + "\t\t"
							+ "DATE OF EXPIRY : " + wo.getDate_Of_Expiry());
				}

				viewFlag = true;

			} catch (PersistenceException e) {
				log.error(e.getMessage());
				transaction.rollback();
			} finally {
				manager.close();
				factory.close();
			}
		}

		return viewFlag;
	}

	@Override
	public int fetchId(String loginName) {
		int id = 0;
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		try {

			String jpql = "Select w from WalletUser w where w.LoginName =:lname";
			Query query = manager.createQuery(jpql);
			query.setParameter("lname", loginName);
			List<WalletUser> list = query.getResultList();

			for (WalletUser walletUser : list) {
				id = walletUser.getUserId();
			}
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}
		return id;
	}

	@Override
	public OfferRecord offerDetail(int offerId) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		OfferRecord record = manager.find(OfferRecord.class, offerId);
		return record;
	}

	@Override
	public void addSubscribe(List<OfferRecord> list, int userId) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();

		WalletUser user = manager.find(WalletUser.class, userId);
		user.setOffrecord(list);
		transaction.begin();
		try {
			manager.persist(user);
			transaction.commit();
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}
	}

	public String fetchOfferName(int offerId) {
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();

		OfferRecord record = manager.find(OfferRecord.class, offerId);
		return record.getOfferName();
	}

	@Override
	public boolean viewSubscribe(int userId) {
		boolean viewFlag = false;
		UserWalletDao dao = new UserWalletDao();
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();

		WalletUser user = manager.find(WalletUser.class, userId);

		List<OfferRecord> record = user.getOffrecord();
		log.info("\n" + fetchAccountHolderName(userId) + " :Subscribe Offers \n");
		log.info("--------------------------");
		for (Iterator iterator = record.iterator(); iterator.hasNext();) {
			OfferRecord offerRecord = (OfferRecord) iterator.next();
			System.out.println("OFFER ID : " + offerRecord.getOfferId() + "\t\t" + "OFFER NAME : "
					+ offerRecord.getOfferName() + "\t\t" + "DATE OF EXPIRY : " + offerRecord.getDate_Of_Expiry());

			// log.info("NAME : " + fetchAccountHolderName(userId) + "\t\t" + "OFFER ID : "
			// + offerRecord.getOfferId() + "\t\t"+ "OFFER NAME : " +
			// offerRecord.getOfferName() + "\t\t" + "DATE OF EXPIRY : "+
			// offerRecord.getDate_Of_Expiry());
			viewFlag = true;
		}
		return viewFlag;
	}

	@Override
	public boolean checkId(int sUserId) {
		boolean checkFlag = false;
		factory = Persistence.createEntityManagerFactory("wallet");
		manager = factory.createEntityManager();
		try {
			WalletUser user = manager.find(WalletUser.class, sUserId);
			if (user != null) {
				checkFlag = true;
			}
		} catch (PersistenceException e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			manager.close();
			factory.close();
		}
		return checkFlag;
	}

	/*
	 * WalletUser user = manager.find(WalletUser.class, userId); List<OfferRecord>
	 * record = user.getOffrecord(); // query.setParameter("uid", userId); //
	 * List<Object[]> list=query.getResultList();
	 * 
	 * // for(Object[] s:list) { // log.info(s); // }
	 * 
	 * log.info("\n"); for (OfferRecord offerRecord : record) { log.info("NAME : " +
	 * fetchAccountHolderName(userId) + "\t\t" + "OFFER ID : " +
	 * offerRecord.getOfferId() + "\t\t" + "OFFER NAME : " +
	 * offerRecord.getOfferName() + "\t\t" + "DATE OF EXPIRY : " +
	 * offerRecord.getDate_Of_Expiry());
	 * 
	 * 
	 * // } log.info("\n"); for (OfferRecord offer : record) { log.info("NAME : " +
	 * fetchAccountHolderName(userId) + "\t\t" + "OFFER ID : " + offer.getOfferId()
	 * + "\t\t" + "OFFER NAME : " + dao.fetchOfferName(offer.getOfferId()) + "\t\t"
	 * + "DATE OF EXPIRY : " + offer.getDate_Of_Expiry());
	 * 
	 * }
	 */

}
