package com.capg.onlinewallet.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.capg.onlinewallet.dto.OfferRecord;

@Entity
@Table(name = "walletuser")
public class WalletUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer UserId;
	@Column
	private String UserName;
	@Column
	private String Password;
	@Column
	private String PhoneNumber;
	@Column
	private String LoginName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AccountId")
	private WalletAccount walletaccount;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "user_subscriptions", joinColumns = @JoinColumn(name = "User_Id"), inverseJoinColumns = @JoinColumn(name = "Offer_Id"))
	private List<OfferRecord> offrecord;
	
	
	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "user_recharge", joinColumns = @JoinColumn(name = "User_Id"), inverseJoinColumns = @JoinColumn(name = "Recharge_Id"))
	private List<RechargeOffers> recharge;

	public WalletUser() {
		super();
	}

	public WalletUser(String userName, String password, String phoneNumber, String loginName) {
		super();
		UserName = userName;
		Password = password;
		PhoneNumber = phoneNumber;
		LoginName = loginName;
	}

	public Integer getUserId() {
		return UserId;
	}

	public void setUserId(Integer userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

	public WalletAccount getWalletaccount() {
		return walletaccount;
	}

	public void setWalletaccount(WalletAccount walletaccount) {
		this.walletaccount = walletaccount;
	}
	
	
	public List<OfferRecord> getOffrecord() {
		return offrecord;
	}
	
	public void setOffrecord(List<OfferRecord> offrecord) {
		this.offrecord = offrecord;
	}
	
	
	
	

	public List<RechargeOffers> getRecharge() {
		return recharge;
	}

	public void setRecharge(List<RechargeOffers> recharge) {
		this.recharge = recharge;
	}
	
	public void setRecharge(RechargeOffers recharge) {
		this.recharge.add(recharge);
	}

	@Override
	public String toString() {
		return "WalletUser [UserId=" + UserId + ", UserName=" + UserName + ", Password=" + Password + ", PhoneNumber="
				+ PhoneNumber + ", LoginName=" + LoginName + ", walletaccount=" + walletaccount + ", offrecord="
				+ offrecord + "]";
	}
}
