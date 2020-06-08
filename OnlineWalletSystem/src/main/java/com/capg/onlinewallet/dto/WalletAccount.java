package com.capg.onlinewallet.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.capg.onlinewallet.dto.Status;

@Entity
@Table(name = "walletaccount")
public class WalletAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer AccountId;

	@Column
	private double Account_Bal;

	@Column(name = "Status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToOne(mappedBy="walletaccount")
	private WalletUser user;

	public WalletAccount() {
		super();
	}

	public WalletAccount(double account_Bal, Status status) {
		super();
		Account_Bal = account_Bal;
		this.status = status;
	}

	public Integer getAccountId() {
		return AccountId;
	}

	public void setAccountId(Integer accountId) {
		AccountId = accountId;
	}

	public double getAccount_Bal() {
		return Account_Bal;
	}

	public void setAccount_Bal(double account_Bal) {
		Account_Bal = account_Bal;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "WalletAccount [AccountId=" + AccountId + ", Account_Bal=" + Account_Bal + ", status=" + status + "]";
	}
	
	
	
	
	
	

}
