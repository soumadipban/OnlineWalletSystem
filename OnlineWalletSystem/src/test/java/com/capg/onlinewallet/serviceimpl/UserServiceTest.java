package com.capg.onlinewallet.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.capg.onlinewallet.exception.NoSubscribeException;
import com.capg.onlinewallet.exception.OWSException;
import com.capg.onlinewallet.exception.OfferNotFoundException;
import com.capg.onlinewallet.serviceimpl.UserWalletService;

class UserServiceTest {

	UserWalletService service = new UserWalletService();

	@Test
	void testFirstValidateName() {
		boolean expected = true;
		boolean actual = service.validateName("Soumadip");
		assertEquals(expected, actual);
	}

	@Test
	void testSecondValidateName() {
		boolean expected = true;
		assertThrows(OWSException.class, () -> service.validateName("soumadip"), "Invalid Name Format");
	}

	@Test
	void testFirstValidatePassword() {
		boolean expected = true;
		boolean actual = service.validatePassword("Soumadip@12");
		assertEquals(expected, actual);

	}

	@Test
	void testSecondValidatePassword() {
		boolean expected = true;
		assertThrows(OWSException.class, () -> service.validatePassword("soumadip"),
				"Password is not in correct format");

	}

	@Test
	void testFirstValidContact() {
		boolean expected = true;
		boolean actual = service.validContact("8017740294");
		assertEquals(expected, actual);
	}

	@Test
	void testSecondValidContact() {
		boolean expected = true;
		assertThrows(OWSException.class, () -> service.validContact("12345678"), "Contact is not in format");
	}

	@Test
	void testFirstValidName() {
		boolean expected = true;
		boolean actual = service.userIdValidation("20");
		assertEquals(expected, actual);
	}

	@Test
	void testSecondValidName() {
		boolean expected = true;
		assertThrows(OWSException.class, () -> service.userIdValidation("r"), "Id is not in Correct format");

	}

	@Test
	void testViewOffer() {
		assertThrows(OfferNotFoundException.class, () -> service.viewOffer());
	}

	@Test
	void testViewSubscribe() {
		assertThrows(NoSubscribeException.class, () -> service.viewSubscribe(4));
	}

	/*
	 * @Test void testAccountBalDetail() { fail("Not yet implemented"); }
	 * 
	 * @Test void testFetchAccountStatus() { fail("Not yet implemented"); }
	 * 
	 * @Test void testFetchAccountBal() { fail("Not yet implemented"); }
	 * 
	 * @Test void testUpdateAccountBal() { fail("Not yet implemented"); }
	 * 
	 * @Test void testLoginRecord() { fail("Not yet implemented"); }
	 * 
	 * @Test void testLoginNameChecker() { fail("Not yet implemented"); }
	 * 
	 * @Test void testSetBalance() { fail("Not yet implemented"); }
	 * 
	 * @Test void testFetchAccountHolderName() { fail("Not yet implemented"); }
	 * 
	 * @Test void testTransactionDetail() { fail("Not yet implemented"); }
	 * 
	 *
	 * 
	 * @Test void testFetchId() { fail("Not yet implemented"); }
	 * 
	 * @Test void testOfferDetail() { fail("Not yet implemented"); }
	 * 
	 * @Test void testAddSubscribe() { fail("Not yet implemented"); }
	 * 
	 * 
	 * 
	 * @Test void testUserRegistration() {
	 * 
	 * }
	 * 
	 */
}
