package com.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.exceptions.CustomNotFoundException;
import com.crud.model.Customer;
import com.crud.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new CustomNotFoundException(Customer.class.getName(), "id", id));
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

	@Override
	public List<Customer> getCustomersByBranchId(Long branchId) {
		return customerRepository.findCustomersByBranch(branchId);
	}

	@Override
	public void saveAllCustomer(List<Customer> customerList) {
		customerRepository.saveAll(customerList);
	}
}
