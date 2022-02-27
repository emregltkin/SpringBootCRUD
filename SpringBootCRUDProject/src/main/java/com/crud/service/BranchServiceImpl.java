package com.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.exceptions.CustomNotFoundException;
import com.crud.model.Branch;
import com.crud.repository.BranchRepository;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {

	@Autowired
	BranchRepository branchRepository;

	@Override
	public List<Branch> findAll() {
		return branchRepository.findAll();
	}

	@Override
	public Branch getBranchById(Long id) {
		return branchRepository.findById(id)
				.orElseThrow(() -> new CustomNotFoundException(Branch.class.getName(), "id", id));
	}

	@Override
	public Branch saveBranch(Branch branch) {
		return branchRepository.save(branch);
	}

	@Override
	public void deleteBranch(Branch branch) {
		branchRepository.delete(branch);
	}

	@Override
	public List<Branch> getBranchesByCustomerId(Long customerId) {
		return branchRepository.findBranchesByCustomer(customerId);
	}

}
