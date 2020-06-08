package com.capg.onlinewallet.controller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.capg.onlinewallet.dto.OfferRecord;
import com.capg.onlinewallet.dto.RechargeOffers;
import com.capg.onlinewallet.exception.OfferNotFoundException;
import com.capg.onlinewallet.service.IRechargeWalletService;
import com.capg.onlinewallet.service.IUserWalletService;
import com.capg.onlinewallet.serviceimpl.RechargeWalletService;
import com.capg.onlinewallet.serviceimpl.UserWalletService;

public class RechargeController {
	static boolean ry;
	static IRechargeWalletService wsi = new RechargeWalletService();
	static IUserWalletService service = new UserWalletService();
	static Logger log = Logger.getLogger("onlinewallet");
	static Scanner sc = new Scanner(System.in);
	RechargeOffers r = new RechargeOffers();

	public static void UserAdminTasks(int userId) {
		boolean checkFlag = false;
		do {
			log.info(
					"++++++++++++++++++++++++++++++++++++Welcome to the Online Module Mobile Recharge++++++++++++++++++++++++++++++++++++");
			log.info("Enter 1 to view all offers");
			log.info("Enter 2 to Recharge your mobile");
			log.info("Enter 3 for Exit");
			try {
				int ch = sc.nextInt();
				
				switch (ch) {
				case 1:
					wsi.viewAllOffers();
					break;
				case 2:
					
					int rechargeId = 0;
					boolean ide = wsi.findId(userId);
					if (ide) {
						//System.out.println("Hello");
						List<RechargeOffers> list = new ArrayList<>();
						Scanner scan = new Scanner(System.in);
						
						log.info("Enter the Recharge id You want to recharge");
						rechargeId = scan.nextInt();
						try {
							RechargeOffers offer = wsi.fetchRechargeDetail(rechargeId);
							//list.add(offer);
							
							int price1 = wsi.fetchRechargeBal(rechargeId);

							//wsi.rechargeNumber(userId, price1, list);
							wsi.rechargeNumber(userId, price1, offer);
							log.info("\n");
							log.info("Recharge is Succesfully done");
						} catch (OfferNotFoundException e) {
							System.out.println(e.getMessage());
						}

					}
					break;
				case 3:
					checkFlag = true;
					break;
				default:
					checkFlag = false;
					log.error("Input should be in the range of 1-3\n");
					break;

				}
			} catch (InputMismatchException e) {
				checkFlag = false;
				log.error("Input should contain only digits\n");
			}

		} while (!checkFlag);

	}
}
