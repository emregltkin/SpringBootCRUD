package com.crud.service;

import java.util.List;

import com.crud.model.Branch;

public interface BranchService {

	List<Branch> findAll();

	Branch getBranchById(Long id);

	Branch saveBranch(Branch branch);

	void deleteBranch(Branch branch);

	List<Branch> getBranchesByCustomerId(Long customerId);

}
