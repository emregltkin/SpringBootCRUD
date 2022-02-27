package com.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crud.model.Branch;

@Repository("branchRepository")
public interface BranchRepository extends JpaRepository<Branch, Long> {

	@Query("SELECT b FROM Branch b JOIN b.customers c WHERE c.id = :customerId")
	public List<Branch> findBranchesByCustomer(@Param("customerId") Long id);

}
