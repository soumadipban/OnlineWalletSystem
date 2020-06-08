package com.capg.onlinewallet.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class RechargeOffers {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private int price;
	private int talktime;
	private int internet_data;
	private int validity;
	
	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "user_recharge", joinColumns = @JoinColumn(name = "Recharge_Id"), inverseJoinColumns = @JoinColumn(name = "User_Id"))
	private List<WalletUser> users;
	
	
	public RechargeOffers() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RechargeOffers(int id, String name, int price, int talktime, int internet_data, int validity) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.talktime = talktime;
		this.internet_data = internet_data;
		this.validity = validity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getTalktime() {
		return talktime;
	}
	public void setTalktime(int talktime) {
		this.talktime = talktime;
	}
	public int getInternet_data() {
		return internet_data;
	}
	public void setInternet_data(int internet_data) {
		this.internet_data = internet_data;
	}
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
	}
	
	
	
	
	
	public List<WalletUser> getUsers() {
		return users;
	}
	public void setUsers(WalletUser users) {
		this.users.add(users);
	}
	@Override
	public String toString() {
		return "RechargeOffers [id=" + id + ", name=" + name + ", price=" + price + ", talktime=" + talktime
				+ ", internet_data=" + internet_data + ", validity=" + validity + "]";
	}
}
	
