package com.capg.onlinewallet.utility;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtility {
	static EntityManagerFactory entityManagerFactory;

	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("wallet");
	}

	public static EntityManagerFactory getFactory() {
		return entityManagerFactory;
	}

}
