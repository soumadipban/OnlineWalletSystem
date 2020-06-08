package com.capg.onlinewallet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.RechargeOffers;
import com.capg.onlinewallet.dto.Status;
import com.capg.onlinewallet.exception.OWSException;
import com.capg.onlinewallet.exception.OfferNotFoundException;
import com.capg.onlinewallet.exception.UserNotFoundException;
import com.capg.onlinewallet.service.IAdminWalletService;
import com.capg.onlinewallet.service.IRechargeWalletService;
import com.capg.onlinewallet.service.IUserWalletService;
import com.capg.onlinewallet.serviceimpl.AdminWalletService;
import com.capg.onlinewallet.serviceimpl.RechargeWalletService;
import com.capg.onlinewallet.serviceimpl.UserWalletService;

public class AdminController {
	static Logger log = Logger.getLogger("onlinewallet");
	static IAdminWalletService service=new AdminWalletService();
	static IUserWalletService uservice = new UserWalletService();
	static IRechargeWalletService wsi = new RechargeWalletService();

	static void viewOffers() {
		System.out.println("Available Offers");
		List<OfferRecord> list=service.viewOffers();
		for(OfferRecord wo:list) {
			log.info("OFFER ID : " + wo.getOfferId() + "\t\t" + "OFFER NAME : " + wo.getOfferName() + "\t\t"
					+ "DATE OF EXPIRY : " + wo.getDate_Of_Expiry());
		}
	}

	static void addOffers() {
		Scanner sc2=new Scanner(System.in);
		//System.out.println("Add Offers");
		System.out.println("Enter Offer name");
		String name=sc2.nextLine();
		System.out.println("Enter Date Of Expiry");
		String dateOfExpiry=sc2.next();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date doe = null;
		try {
			doe = format.parse(dateOfExpiry);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		OfferRecord offers=new OfferRecord(name, doe);
		service.addOffers(offers);
		System.out.println("Offer added succcessfully");
	}

	static void removeOffers() {
		System.out.println("Remove Offers");
		Scanner sc2=new Scanner(System.in);
		boolean oFlag=false;
		String id = "";
		int offerId=0;
		System.out.println("Enter Offer Id");
		do {
			
			id=sc2.next();
			try {
				uservice.userIdValidation(id);
				oFlag=true;
				log.info("Offer removed succesfully");
			}catch(OWSException e) {
				oFlag=false;
				log.info(e.getMessage());
			}
		}while(!oFlag);
		
		offerId = Integer.parseInt(id);
		try {
			service.removeOffers(offerId);
		} catch (OfferNotFoundException e1) {
			log.info(e1.getMessage());
		}
		
	}

	static void updateOffers() {
		Scanner sc2=new Scanner(System.in);
		System.out.println("Update Offers");
		System.out.println("Enter Offer Id whose details you wish to update");
		int offerId2=sc2.nextInt();
		System.out.println("Enter new name of the offer");
		sc2 = new Scanner(System.in);
		String newName=sc2.nextLine();
		System.out.println("Enter New Date Of Expiry");
		String newDateOfExpiry=sc2.next();
		SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
		Date ndoe = null;
		try {
			ndoe = format2.parse(newDateOfExpiry);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			service.updateOffers(offerId2, newName, ndoe);
		} catch (OfferNotFoundException e1) {
			e1.getMessage();
		}
	}

	static void blockUser()
	{
		Scanner sc2=new Scanner(System.in);
		System.out.println("Block a user");
		System.out.println("Enter user id");
		boolean uFlag=false;
		String id = "";
		int userId=0;
		do {
		    id=sc2.next();
			try {	
				uservice.userIdValidation(id);
				uFlag=true;
			}catch(OWSException e) {
				uFlag=false;
				System.out.println(e.getMessage());
			}
		}while(!uFlag);
		userId = Integer.parseInt(id);
		try {
			service.blockUser(userId);
		}catch (UserNotFoundException e1) {
			System.out.println(e1.getMessage());
		}
	}

	static void viewStatus()
	{
		System.out.println("View Status");
		System.out.println("Enter user id");
		Scanner sc2=new Scanner(System.in);
		int userId2=0;
		String id = "";
		boolean sFlag=false;
		do {
			id=sc2.next();
			try {	
				uservice.userIdValidation(id);
				sFlag=true;
			}catch(OWSException e) {
				sFlag=false;
				System.out.println(e.getMessage());
			}
		}while(!sFlag);
		userId2 = Integer.parseInt(id);
		List<Object[]> list2=service.viewStatus(userId2);
		if(list2==null) {
			System.out.println("User does not exist");
		}
		else {

			for(Object[] s:list2) {
				System.out.println("userId: "+s[0]+"\nuserName: "+s[1]+"\naccountId: "+s[2]+"\naccountBalance: "+s[3]+"\naccountStatus: "+s[4]);
			}
		}
	}

	static void updateStatus() {
		System.out.println("Change Status of account");
		Scanner sc2=new Scanner(System.in);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Enter the userId");
		//int userId3=sc2.nextInt();
		System.out.println("Enter user id");
		boolean sFlag2=false;
		int userId3=0;
		String id = "";
		do {
			id=sc2.next();
			try {	
				uservice.userIdValidation(id);
				sFlag2=true;
			}catch(OWSException e) {
				sFlag2=false;
				System.out.println(e.getMessage());
			}
		}while(!sFlag2);
		userId3 = Integer.parseInt(id);
		System.out.println("Enter the new status");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Press 1 To Reject Account");
		System.out.println("Press 2 to Approve Account");
		Status newstatus=null;
		int choice=sc2.nextInt();
		switch(choice) {
		case 1:
			newstatus=Status.NOTAPPROVED;
			break;
		case 2:
			newstatus=Status.APPROVED;
			break;
		default:
			System.out.println("Please Enter 1 or 2");
		}
		try {
			service.updateStatus(userId3,newstatus);
		}catch(UserNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void addRechargeOffer() {
		RechargeOffers r = new RechargeOffers();
		Scanner sc2=new Scanner(System.in);
		log.info("Enter the price to offer");
	    sc2 = uservice.integerValidation();
		int price = sc2.nextInt();
		r.setPrice(price);
		log.info("How many mb of Internet data will be available");
		sc2 = uservice.integerValidation();
		int mb = sc2.nextInt();
		r.setInternet_data(mb);
		log.info("How much talktime available in minutes");
		sc2 =  uservice.integerValidation();
		int talktime = sc2.nextInt();
		r.setTalktime(talktime);
		log.info("Validity in days?");
		sc2= uservice.integerValidation();
		int validity = sc2.nextInt();
		r.setValidity(validity);
		log.info("Enter the name of offer");
		String name = sc2.next();
		r.setName(name);
        wsi.addRechargeOffers(r);
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("Data Inserted");
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
	}
	
	
	public static void removeRechargeOffer() {
		Scanner sc = new Scanner(System.in);
		log.info("Enter the id of which You want to remove Offer");
		sc=uservice.integerValidation();
		int id = sc.nextInt();
		wsi.deleteRechargeOffer(id);
		
	}
	
	
	public static void viewRecharge() {
		wsi.viewAllOffers();
	}

	public static void adminAccess() {
		Scanner sc=null;
		boolean choiceFlag=false;
		String exChoice="";
		while (true) {
			sc = new Scanner(System.in);
			System.out.println("Enter your Login Name :");
			String loginName = sc.next();
			System.out.println("Enter your Password :");
			String password = sc.next();
			boolean logIn = service.login(loginName, password);
			if (logIn)
				break;
			else
				log.info("Credential is incorrect... Please Enter Correct detail ");
		}
		Scanner sc2=new Scanner(System.in);
		do {
			log.info("\t\tWELCOME TO ADMIN DASHBOARD");
			log.info("\t\t1.View Offers");
			log.info("\t\t2.Add Offers");
			log.info("\t\t3.Remove Offers");
			log.info("\t\t4.Update Offers");
			log.info("\t\t5.Block a User");
			log.info("\t\t6.View Account Status");
			log.info("\t\t7.Change Account Status");
			log.info("\t\t8.Insert an Recharge offer");
			log.info("\t\t9.Delete an Recharge offer");
			log.info("\t\t10.View Recharge Offer");
			log.info("\t\t11.Exit");
			
				log.info("\t\t------------------------");
				log.info("\t\tEnter Your Choice");
				log.info("\t\t------------------------");
			sc = new Scanner(System.in);
			try {
				choiceFlag=false;	
				int choice = sc.nextInt();
				switch(choice) {
				case 1:
					AdminController.viewOffers();
					break;
				case 2:
					AdminController.addOffers();
					break;
				case 3:
					AdminController.removeOffers();
					break;
				case 4:
					AdminController.updateOffers();
					break;
				case 5:
					AdminController.blockUser();
					break;
				case 6:
					AdminController.viewStatus();
					break;
				case 7:
					AdminController.updateStatus();
					break;
				case 8:
					AdminController.addRechargeOffer();
					break;
				case 9:
					AdminController.removeRechargeOffer();
					break;
				case 10:
					AdminController.viewRecharge();
					break;
				case 11: choiceFlag=true;
				break;
				default:
					choiceFlag=false;
					log.info("Input should be in the range of 1 to 8");
				}
			}catch(InputMismatchException e) {
				log.info("Input should have only numbers");
				choiceFlag=true;
			}
		}while(!choiceFlag);		
			/*log.info("Do you want to continue? (yes/no)");
		exChoice=sc2.next();
		if(exChoice.equalsIgnoreCase("no")||exChoice.equalsIgnoreCase("n")) {
			log.info("All operations have been applied to the database");
		}
	}while(exChoice.equalsIgnoreCase("yes")||exChoice.equalsIgnoreCase("y"));*/
	}






}
