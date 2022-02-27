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

@RestController
@RequestMapping("/branch")
public class BranchController {

	@Autowired
	private BranchService branchService;
	@Autowired
	private CustomerService customerService;

	@GetMapping("/listAll")
	public ResponseEntity<List<Branch>> getAllBranchs() {
		List<Branch> list = branchService.findAll();
		return new ResponseEntity<List<Branch>>(list, HttpStatus.OK);
	}

	@GetMapping("/list/{id}")
	public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
		Branch branch = branchService.getBranchById(id);
		return ResponseEntity.ok(branch);
	}

	@PostMapping("/create")
	public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
		Branch b = branchService.saveBranch(branch);
		return new ResponseEntity<>(b, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	  public ResponseEntity<Branch> updateBranch(@PathVariable("id") Long id, @RequestBody Branch paramBranch) {
		Optional<Branch> c = Optional.ofNullable(branchService.getBranchById(id));
		if(c.isPresent()) {
			Branch branch = c.get();
			branch.setName(paramBranch.getName());
			return new ResponseEntity<>(branchService.saveBranch(branch), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBranch(@PathVariable("id") Long id) {
		Optional<Branch> c = Optional.ofNullable(branchService.getBranchById(id));
		if(c.isPresent()) {
			Branch branch = c.get();
			branchService.deleteBranch(branch);
			return ResponseEntity.ok().build();
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Bir customer'ın ait olduğu branch bilgilerini getirir.
	 */
	@GetMapping("/customer/{id}")
	public ResponseEntity<List<Branch>> getBranchesByCustomerId(@PathVariable("id") Long customerId) {
		List<Branch> list = branchService.getBranchesByCustomerId(customerId);
		return new ResponseEntity<List<Branch>>(list, HttpStatus.OK);
	}

	/**
	 * Belirli bir id'ye sahip branch'e customer ekleme işini yapar.
	 */
	@PostMapping("/{id}/create/customer/")
	public ResponseEntity<Void> createCustomerByBranchId(@PathVariable("id") Long branchId, @RequestBody Customer customer) {
		Branch branch = branchService.getBranchById(branchId);
		if(branch != null) {
			customerService.saveCustomer(customer);
			branch.getCustomers().add(customer);
			branchService.saveBranch(branch);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Belirli bir id'ye sahip branch'ten mevcut customer'ı silme işini yapar.
	 * @throws Exception
	 *
	 */
	@PostMapping("/{id}/removeCustomer/{customerId}")
	public ResponseEntity<Void> deleteCustomerByBranchId(@PathVariable("branchId") Long branchId, @PathVariable("customerId") Long customerId) throws Exception  {
		Branch branch = branchService.getBranchById(branchId);
		if (branch != null) {
			List<Customer> customers = customerService.getCustomersByBranchId(branchId);
			if (customers == null || customers.isEmpty()) {
				throw new CustomNotFoundException(Branch.class.getName(), "branchId", branchId);
			}
			Customer customer = customers.stream().filter(c -> c.getId().equals(customerId)).findAny().orElse(null);
			if (customer == null) {
				throw new CustomNotFoundException(Customer.class.getName(), "customerId", customerId);
			}
			branch.getCustomers().remove(customer);
			branchService.saveBranch(branch);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
