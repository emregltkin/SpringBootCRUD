package com.crud.service;

import java.util.List;

import com.crud.model.Customer;

public interface CustomerService {

	List<Customer> findAll();

	Customer getCustomerById(Long id);

	Customer saveCustomer(Customer customer);

	void deleteCustomer(Customer customer);

	List<Customer> getCustomersByBranchId(Long branchId);

	void saveAllCustomer(List<Customer> customerList);

}
