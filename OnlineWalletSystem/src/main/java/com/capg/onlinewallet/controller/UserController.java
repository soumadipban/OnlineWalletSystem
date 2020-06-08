package com.capg.onlinewallet.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Id;

import org.apache.log4j.Logger;

import com.capg.onlinewallet.dto.WalletUser;
import com.capg.onlinewallet.exception.NoSubscribeException;
import com.capg.onlinewallet.exception.OWSException;
import com.capg.onlinewallet.exception.OfferNotFoundException;
import com.capg.onlinewallet.service.IUserWalletService;
import com.capg.onlinewallet.serviceimpl.UserWalletService;
import com.capg.onlinewallet.dto.WalletTransaction;
import com.capg.onlinewallet.dao.UserWalletDao;
import com.capg.onlinewallet.dto.LoginRecord;
import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.Status;

public class UserController {
	static Logger log = Logger.getLogger("onlinewallet");
	static IUserWalletService service = new UserWalletService();
	static UserWalletDao dao = new UserWalletDao();

	@SuppressWarnings("resource")
	public static WalletUser userRegistration() {
		Scanner scan = null;
		String name = "";
		boolean nameFlag = false;

		String login_name = "";

		do {
			scan = new Scanner(System.in);
			log.info("Enter your Name");
			name = scan.nextLine();
			try {
				service.validateName(name);
				nameFlag = true;
			} catch (OWSException e) {
				nameFlag = false;
				log.error(e.getMessage());
			}
		} while (!nameFlag);

		String password = "";
		boolean passwordFlag = false;
		do {
			scan = new Scanner(System.in);
			log.info("Enter password\n" + "*Be between 8 and 40 characters long\r\n"
					+ "*Contain at least one digit.\r\n" + "*Contain at least one lower case character.\r\n"
					+ "*Contain at least one upper case character.\r\n"
					+ "*Contain at least on special character from [ @ # $ % ! . ].");
			password = scan.next();
			try {
				service.validatePassword(password);
				passwordFlag = true;
			} catch (OWSException e) {
				passwordFlag = false;
				log.error(e.getMessage());
			}
		} while (!passwordFlag);

		String phone_no = "";
		boolean phnoFlag = false;
		do {
			scan = new Scanner(System.in);
			log.info("Enter phone number");
			phone_no = scan.next();
			try {
				service.validContact(phone_no);
				phnoFlag = true;
			} catch (OWSException e) {
				phnoFlag = false;
				log.error(e.getMessage());
			}
		} while (!phnoFlag);

		while (true) {
			log.info("Enter login name");
			login_name = scan.next();
			boolean checker = service.loginNameChecker(login_name);
			if (checker == false) {
				break;
			}
			log.info("The name is already been taken by some other user");
		}

		WalletUser walletUser = new WalletUser(name, password, phone_no, login_name);
		LoginRecord record = new LoginRecord(login_name, password);

		service.loginRecord(record);
		return walletUser;

	}

	@SuppressWarnings("resource")
	public static void recordDetail(int userId) {

		Scanner scan = null;
		// int userId;
		String result = " ";

		// scan = new Scanner(System.in);
		// log.info("Enter Your User ID to check current wallet balance");
		// scan = service.integerValidation();
		// userId = scan.nextInt();

		// Status status = service.fetchAccountStatus(userId);

		// if (status != Status.PENDING) {

		try {
			log.info("Please Wait.....");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Record for UserId ---->" + userId);
		log.info("\n");

		result = service.accountBalDetail(userId);

		log.info("\n");
		log.info(result);
		log.info("\n");
		// } else {
		// log.info("\n");
		// log.error("Your Account is still in pending status.....");
		// }

	}

	@SuppressWarnings("resource")
	public static void addWalletBal(int userId) {
		IUserWalletService service = new UserWalletService();
		Scanner scan = null;
		// int userId;
		int accountBal;
		scan = new Scanner(System.in);

		// log.info("Enter Your UserId");
		// scan = service.integerValidation();
		// userId = scan.nextInt();

		log.info("Enter the amount you want to add in your Wallet Balance");
		accountBal = scan.nextInt();

		// Status status = service.fetchAccountStatus(userId);

		// if (status != Status.PENDING) {

		double oldBalance = service.fetchAccountBal(userId);

		double newBalance = accountBal + oldBalance;

		service.updateAccountBal(newBalance, userId);

		log.info("Your Wallet Balance is credited with amount " + accountBal);

		// } else {
		// log.info("Sorry you can't access your account it's still in pending status");
		// }

	}

	@SuppressWarnings("resource")
	public static void transactionWallet(int fUserId) {
		String id = "";
		boolean idFlag = false;
		Scanner scan = null;

		String detail = "";
		int sUserId = 0;

		scan = new Scanner(System.in);
		// log.info("Enter your User id");
		// scan = service.integerValidation();
		// int fUserId = scan.nextInt();

		while (true) {
			do {
				log.info("Enter User Id where you want to transfer money");
				// scan = service.integerValidation();
				// sUserId = scan.nextInt();

				id = scan.next();
				try {
					service.userIdValidation(id);
					idFlag = true;
				} catch (OWSException e) {
					System.err.println(e.getMessage());
				}
			} while (!idFlag);
			sUserId = Integer.parseInt(id);
			if (service.checkId(sUserId)) {
			} else {
				sUserId = 0;
				break;
			}
			Status status = service.fetchAccountStatus(sUserId);

			if (status != Status.PENDING && status != Status.NOTAPPROVED)
				break;
			log.info("Sorry UserId " + sUserId + " is still in " + status + " status....Please Enter Another UserId");
		}

		if (sUserId != 0) {
			log.info("Enter Amount to be transfer");
			double amount = scan.nextInt();

			double currentBal = service.fetchAccountBal(fUserId);
			if (currentBal >= amount) {

				String date = null;
				LocalDate localDate = null;
				boolean dateFlag = false;
				do {
					log.info(("Enter Date(yyyy-MM-dd)"));
					date = scan.next();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

					try {
						localDate = LocalDate.parse(date, formatter);
						dateFlag = true;
					} catch (DateTimeParseException e) {
						dateFlag = false;
						System.err.println("date should be in the format of yyyy-MM-dd");
					}
				} while (!dateFlag);
				scan = new Scanner(System.in);
				log.info("Enter description");
				detail = scan.nextLine();

				@SuppressWarnings("static-access")
				Date transDate = Date.from(localDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toInstant());

				String fName = service.fetchAccountHolderName(fUserId);
				String sName = service.fetchAccountHolderName(sUserId);

				WalletTransaction transaction = new WalletTransaction(fName, sName, amount, detail, transDate);

				service.transactionDetail(transaction);
				service.setBalance(amount, fUserId, sUserId);
				log.info("Transaction is Successfully Done ");
			} else {
				log.info("\n");
				log.info("Sorry Your Balance is not sufficient for transaction");
				log.info("\n");
			}
		} else {
			log.info("User Not Found");
		}

	}

	public static void offerSubscribe(int userId) {
		WalletUser user = new WalletUser();
		Scanner scan = null;
		// int userId=0;
		int offerId = 0;
		scan = new Scanner(System.in);
		// log.info("Enter your User id");
		// userId = scan.nextInt();
		int offerNo = 0;
		log.info("Enter number of offers you want to subscribe");
		offerNo = scan.nextInt();
		List<OfferRecord> list = new ArrayList<>();
		for (int i = 1; i <= offerNo; i++) {
			log.info("Enter the offer id You want to subscribe");
			offerId = scan.nextInt();
			OfferRecord record = service.offerDetail(offerId);
			list.add(record);
		}

		service.addSubscribe(list, userId);
		log.info("\n");
		log.info("Your offer is successfully subscribe ");

	}

	public static void viewOffer() {
		try {
			service.viewOffer();
		} catch (OfferNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	@SuppressWarnings("resource")
	public static void viewSubscribe(int userId) {
		Scanner scan = null;
		// int userId = 0;
		scan = new Scanner(System.in);
		// log.info("Enter your UserId ");
		// userId = scan.nextInt();

		try {
			service.viewSubscribe(userId);
		} catch (NoSubscribeException e) {
			System.err.println(e.getMessage());
		}

	}

	public static void userAccess() {
		boolean checkFlag = false;
		boolean choiceFlag = false;
		int choice = 0;
		Scanner scan = null;

		do {
			log.info("\n");
			log.info("\t\tWELCOME TO USER DASHBOARD ");
			log.info("\n");
			log.info("\t\t1.Create payment wallet account");
			log.info("\t\t2.Log in");
			log.info("\t\t3.Exit");
			log.info("\t\t------------------------");
			log.info("\t\tEnter Your Choice");
			log.info("\t\t------------------------");

			scan = new Scanner(System.in);
			try {
				choice = scan.nextInt();
				choiceFlag = true;
				switch (choice) {

				case 1:

					WalletUser wallet_user = UserController.userRegistration();
					int accountId = service.userRegistration(wallet_user);
					log.info("\n");
					log.info("Registration is succesfully done with Accountid " + accountId);
					choiceFlag = false;
					break;
				case 2:
					String loginName = "";
					while (true) {
						scan = new Scanner(System.in);
						System.out.println("Enter your Login Name :");
						loginName = scan.next();
						System.out.println("Enter your Password :");
						String password = scan.next();
						boolean logIn = service.login(loginName, password);
						if (logIn)
							break;
						else
							log.info("Credential is incorrect... Please Enter Correct detail ");
					}
					int userId = 0;
					userId = service.fetchId(loginName);
					// System.out.println(userId);
					Status status = service.fetchAccountStatus(userId);
					// System.out.println("Hello");
					// System.out.println(status);
					// status != Status.PENDING
					if (status != Status.PENDING && status != Status.NOTAPPROVED) {
						int check = 0;
						do {
						log.info("\n\t\t1.Adding amount to the payment wallet account");
						log.info("\t\t2.Show payment wallet account balance");
						log.info("\t\t3.Transfer funds from one account to another");
						log.info("\t\t4.View all the offers");
						log.info("\t\t5.Subscribe the offers");
						log.info("\t\t6.View Subscribe Offer");
						log.info("\t\t7.Recharge Section");
						log.info("\t\t8.Log Out");

						

							log.info("\t\t------------------------");
							log.info("\t\tEnter Your Choice");
							log.info("\t\t------------------------");

							try {
								check = scan.nextInt();

								switch (check) {
								case 1:
									UserController.addWalletBal(userId);
									break;
								case 2:
									UserController.recordDetail(userId);
									break;
								case 3:
									UserController.transactionWallet(userId);
									break;
								case 4:
									UserController.viewOffer();
									break;
								case 5:
									UserController.offerSubscribe(userId);
									break;
								case 6:
									UserController.viewSubscribe(userId);
									break;
								case 7:
									RechargeController.UserAdminTasks(userId);
									break;
								case 8:
									checkFlag = true;
									choiceFlag = false;
									log.info("Logging out.......");
									break;
								default:
									checkFlag = false;
									log.error("Input should be in the range of 1-8\n");
									break;
								}
							} catch (InputMismatchException e) {
								choiceFlag = false;
								log.error("Input should contain only digits\n");
							}
						} while (!checkFlag);
					} else {
						log.info(
								"\nSorry You account is still in Pending status.... Please wait until your account get Approved");
						choiceFlag = false;
						break;
					}

					// } else
					// break;
					break;
				case 3:
					choiceFlag = true;
					break;
				default:
					choiceFlag = false;
					log.error("Input should be in the range of 1-3\n");
					break;
				}
			} catch (InputMismatchException e) {
				choiceFlag = false;
				log.error("Input should contain only digits\n");
			}

		} while (!choiceFlag);

	}

}
