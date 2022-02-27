package com.crud.util;

import java.util.ArrayList;
import java.util.List;

import com.crud.model.Branch;
import com.crud.model.Customer;

public class DataUtil {

	public static List<Customer> getCustomersAndBranches() {
		List<Customer> customers = new ArrayList<Customer>();

		Customer customer1 = new Customer();
		customer1.setFullName("Emre");
		Customer customer2 = new Customer();
		customer2.setFullName("Mehmet");
		Customer customer3 = new Customer();
		customer3.setFullName("Murat");

		Branch branch1 = new Branch();
		branch1.setName("Merkez/İstanbul");
		Branch branch2 = new Branch();
		branch2.setName("Ataşehir/İstanbul");

		customer1.getBranches().add(branch1);
		// branch1.getCustomers().add(customer1);

		customer2.getBranches().add(branch2);
		// branch2.getCustomers().add(customer2);

		customer3.getBranches().add(branch1);
		// branch1.getCustomers().add(customer3);

		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
		return customers;
	}

}
