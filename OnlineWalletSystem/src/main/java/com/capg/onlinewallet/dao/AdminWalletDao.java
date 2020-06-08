package com.capg.onlinewallet.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;

import com.capg.onlinewallet.dao.impl.IAdminWalletDao;
import com.capg.onlinewallet.utility.JpaUtility;
import com.capg.onlinewallet.dto.*;

public class AdminWalletDao implements IAdminWalletDao {
	EntityManagerFactory factory=null;
	EntityManager manager=null;
	EntityTransaction transaction=null;
	
	Logger log = Logger.getLogger("onlinewallet");

	@Override
	public List<OfferRecord> viewOffers() {
		factory=JpaUtility.getFactory();
		manager=factory.createEntityManager();
		transaction=manager.getTransaction();
		transaction.begin();
		Query query=manager.createQuery("select w from OfferRecord w ");
		List<OfferRecord> list=(List<OfferRecord>)query.getResultList();
		/*	for(WalletOffers wo:list) {
			System.out.println("OfferId: "+wo.getOfferId()+" OfferName: "+wo.getOfferName()+
							   " DateOfExpiry: "+wo.getDateOfExpiry());
		}*/

		transaction.commit();
		return list;

	}
	
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
					flag=true;
				}
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
	public OfferRecord addOffers(OfferRecord offers) {
		factory=JpaUtility.getFactory();
		manager=factory.createEntityManager();
		transaction=manager.getTransaction();
		transaction.begin();
		manager.persist(offers);
		transaction.commit();
		return offers;

	}

	@Override
	public boolean removeOffers(int offerId) {
		factory=JpaUtility.getFactory();
		manager=factory.createEntityManager();
		transaction=manager.getTransaction();
		transaction.begin();
		Query query=manager.createQuery("delete from OfferRecord s where s.OfferId=:oid");
		int x=query.setParameter("oid",offerId).executeUpdate();
		transaction.commit();
		if(x==0) 
			return false;
		else
			return true;
	}

	@Override
	public boolean updateOffers(int offerId2,String newName,Date ndoe) {
		factory=JpaUtility.getFactory();
		manager=factory.createEntityManager();
		transaction=manager.getTransaction();
		transaction.begin();
		Query query=manager.createQuery("Update OfferRecord w set w.OfferName=?1, "
				+ "				w.Date_Of_Expiry=?2 where w.OfferId=?3");
		query.setParameter(1,newName);
		query.setParameter(2,ndoe);
		query.setParameter(3,offerId2);
		int record=query.executeUpdate();
		transaction.commit();
		if(record==0)
			return false;
		else
			return true;

	}


	@Override
	public boolean blockUser(int userId)  {
		factory=JpaUtility.getFactory();
		manager=factory.createEntityManager();
		transaction=manager.getTransaction();
		transaction.begin();
		//	Query query=manager.createQuery("delete from WalletUser s where s.userId=:uid");
		//	int x=query.setParameter("uid",userId).executeUpdate();
		WalletUser user=manager.find(WalletUser.class, userId);
		if(user!=null) {
			manager.remove(user);
			transaction.commit();
			return true;
		}
		else
		{
			transaction.commit();
			return false;
		}

		//		if(user==null) 
		//			return false;
		//		else
		//			return true;

	}

	@Override
	public List<Object[]> viewStatus(int userId) {
		factory=JpaUtility.getFactory();
		manager=factory.createEntityManager();
		transaction=manager.getTransaction();
		transaction.begin();
		Query query=manager.createNativeQuery("select u.UserId,u.UserName,a.AccountId,a.Account_Bal,a.Status from walletuser u "
				+ "					   inner join walletaccount a on u.AccountId=a.AccountId where u.UserId=:uid");
		query.setParameter("uid", userId);
		List<Object[]> list=query.getResultList();
		transaction.commit();
		if(list==null) 
			return null;
		else
			return list;
	}

	@Override
	public boolean updateStatus(int userId,Status newstatus) {
		factory=JpaUtility.getFactory();
		manager=factory.createEntityManager();
		transaction=manager.getTransaction();
		transaction.begin();
		WalletUser user=manager.find(WalletUser.class,userId);
		if(user!=null) {
			user.getWalletaccount().setStatus(newstatus);
			transaction.commit();
			return true;
		}
		else {
			transaction.commit();
			return false;
		}
		//newstatus=user.getWalletaccount().getStatus();
		//return user;

	}
}
