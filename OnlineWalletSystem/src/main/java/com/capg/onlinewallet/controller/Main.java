package com.capg.onlinewallet.controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {
	static Logger log = Logger.getLogger("onlinewallet");

	public static void main(String[] args) {
		PropertyConfigurator.configure("src/main/java/log4j.properties");
		Scanner scan = null;
		String reChoice = "";

		do {

			log.info("\n");
			log.info("\t\tWELCOME TO GARUDA ONLINE WALLET ");
			log.info("\n");

			log.info("\t\t1.Admin");
			log.info("\t\t2.User");
			log.info("\t\t3.Exit");

			int choice = 0;
			boolean choiceFlag = false;

			do {
				log.info("\t\t------------------------");
				log.info("\t\tEnter Your Choice");
				log.info("\t\t------------------------");
				scan = new Scanner(System.in);
				try {
					choice = scan.nextInt();
					choiceFlag = true;

					switch (choice) {
					case 1:
						AdminController.adminAccess();
						break;
					case 2:
						UserController.userAccess();
						break;
					case 3:
						log.info("\t\tThankYou for using our Services!!!");
						System.exit(0);
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
			log.info("Do you want to continue? (yes/no)");
			reChoice = scan.next();
		} while (reChoice.equalsIgnoreCase("yes"));

		scan.close();
	}
}