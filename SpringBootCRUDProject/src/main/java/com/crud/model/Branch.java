package com.crud.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.crud.model.base.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "branch")
public class Branch extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	@Column(name = "name", length = 50)
	private String name;

	@ManyToMany(mappedBy = "branches", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Customer> customers = new HashSet<>();

	public Branch() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "Branch [id=" + id + ", name=" + name + ", customers=" + customers + "]";
	}

}
