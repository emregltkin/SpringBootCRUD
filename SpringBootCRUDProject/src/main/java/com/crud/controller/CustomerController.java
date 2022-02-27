package com.crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.exceptions.CustomNotFoundException;
import com.crud.model.Branch;
import com.crud.model.Customer;
import com.crud.service.BranchService;
import com.crud.service.CustomerService;
import com.crud.util.DataUtil;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private BranchService branchService;

	@GetMapping("/listAll")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> list = customerService.findAll();
		return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
	}

	@GetMapping("/list/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		Customer customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);
	}

	@PostMapping("/create")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer cust = customerService.saveCustomer(customer);
		return new ResponseEntity<>(cust, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	  public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer paramCustomer) {
		Optional<Customer> c = Optional.ofNullable(customerService.getCustomerById(id));
		if(c.isPresent()) {
			Customer customer = c.get();
			customer.setFullName(paramCustomer.getFullName());
			return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
		Optional<Customer> c = Optional.ofNullable(customerService.getCustomerById(id));
		if(c.isPresent()) {
			Customer customer = c.get();
			customerService.deleteCustomer(customer);
			return ResponseEntity.ok().build();
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Belirli bir branch'e ait customer bilgilerini getirir.
	 */
	@GetMapping("/branch/{id}")
	public ResponseEntity<List<Customer>> getCustomersByBranchId(@PathVariable("id") Long branchId) {
		List<Customer> list = customerService.getCustomersByBranchId(branchId);
		return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
	}

	/**
	 * Belirli bir id'ye sahip customer'a branch ekleme işini yapar.
	 *
	 */
	@PostMapping("/{id}/create/branch")
	public ResponseEntity<Void> createBranchByCustomerId(@PathVariable("id") Long customerId, @RequestBody Branch branch) {
		Customer customer = customerService.getCustomerById(customerId);
		if(customer != null) {
			branchService.saveBranch(branch);
			customer.getBranches().add(branch);
			customerService.saveCustomer(customer);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Belirli bir id'ye sahip customer'dan mevcut branch'i silme işini yapar.
	 * @throws Exception
	 *
	 */
	@PostMapping("/{id}/removeBranch/{branchId}")
	public ResponseEntity<Void> deleteBranchByCustomerId(@PathVariable("id") Long customerId, @PathVariable("branchId") Long branchId) throws Exception  {
		Customer customer = customerService.getCustomerById(customerId);
		if (customer != null) {
			List<Branch> branches = branchService.getBranchesByCustomerId(customerId);
			if (branches == null || branches.isEmpty()) {
				// branchId'ye sahip branch var mı? Yoksa hata fırlat.
				throw new CustomNotFoundException(Branch.class.getName(), "branchId", branchId);
			}
			Branch branch = branches.stream().filter(b -> b.getId().equals(branchId)).findAny().orElse(null);
			if (branch == null) {
				throw new CustomNotFoundException(Branch.class.getName(), "branchId", branchId);
			}
			customer.getBranches().remove(branch);
			customerService.saveCustomer(customer);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * DataUtil class'ındaki verileri toplu olarak kaydeder.
	 */
	@GetMapping("/saveAllCustomers")
	public ResponseEntity<Void> saveAll() {
		customerService.saveAllCustomer(DataUtil.getCustomersAndBranches());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
