package com.capg.onlinewallet.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "offer_record")
public class OfferRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer OfferId;
	@Column
	private String OfferName;

	@Temporal(TemporalType.DATE)
	@Column
	private Date Date_Of_Expiry;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "user_subscriptions", joinColumns = @JoinColumn(name = "Offer_Id"), inverseJoinColumns = @JoinColumn(name = "User_Id"))
	private List<WalletUser> users;

	public OfferRecord() {
		super();
	}

	public OfferRecord(String offerName, Date date_Of_Expiry) {
		super();
		OfferName = offerName;
		Date_Of_Expiry = date_Of_Expiry;
	}

	// public OfferRecord(List<WalletUser> waluser) {
	// super();
	// this.waluser = waluser;
	// }

	public Integer getOfferId() {
		return OfferId;
	}

	public void setOfferId(Integer offerId) {
		OfferId = offerId;
	}

	public String getOfferName() {
		return OfferName;
	}

	public void setOfferName(String offerName) {
		OfferName = offerName;
	}

	public Date getDate_Of_Expiry() {
		return Date_Of_Expiry;
	}

	public void setDate_Of_Expiry(Date date_Of_Expiry) {
		Date_Of_Expiry = date_Of_Expiry;
	}

	 public List<WalletUser> getWaluser() {
	 return users;
	 }
	
	 public void setWaluser(WalletUser waluser) {
	 this.users.add(waluser);
	 }

}
