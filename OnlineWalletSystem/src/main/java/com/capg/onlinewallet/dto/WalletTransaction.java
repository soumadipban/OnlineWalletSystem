package com.capg.onlinewallet.dto;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "wallet_transaction")
public class WalletTransaction {


		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column
		private Integer TransactionId;

		@Column
		private String Sender_Info;

		@Column
		private String Receiver_Info;

		@Column
		private double Amount;

		@Column
		private String Detail;

		@Temporal(TemporalType.DATE)
		private Date Date_Of_Transaction;

		public WalletTransaction() {
			super();
		}

		public WalletTransaction(String sender_Info, String receiver_Info, double amount, String detail,
				Date date_Of_Transaction) {
			super();
			Sender_Info = sender_Info;
			Receiver_Info = receiver_Info;
			Amount = amount;
			Detail = detail;
			Date_Of_Transaction = date_Of_Transaction;
		}

		public Integer getTransactionId() {
			return TransactionId;
		}

		public void setTransactionId(Integer transactionId) {
			TransactionId = transactionId;
		}

		public String getSender_Info() {
			return Sender_Info;
		}

		public void setSender_Info(String sender_Info) {
			Sender_Info = sender_Info;
		}

		public String getReceiver_Info() {
			return Receiver_Info;
		}

		public void setReceiver_Info(String receiver_Info) {
			Receiver_Info = receiver_Info;
		}

		public double getAmount() {
			return Amount;
		}

		public void setAmount(double amount) {
			Amount = amount;
		}

		public String getDetail() {
			return Detail;
		}

		public void setDetail(String detail) {
			Detail = detail;
		}

		public Date getDate_Of_Transaction() {
			return Date_Of_Transaction;
		}

		public void setDate_Of_Transaction(Date date_Of_Transaction) {
			Date_Of_Transaction = date_Of_Transaction;
		}

		@Override
		public String toString() {
			return "WalletTransaction [TransactionId=" + TransactionId + ", Sender_Info=" + Sender_Info + ", Receiver_Info="
					+ Receiver_Info + ", Amount=" + Amount + ", Detail=" + Detail + ", Date_Of_Transaction="
					+ Date_Of_Transaction + "]";
		}

	}

