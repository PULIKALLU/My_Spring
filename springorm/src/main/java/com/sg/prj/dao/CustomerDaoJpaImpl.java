package com.sg.prj.dao;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.sg.prj.entity.Customer;

@Repository
public class CustomerDaoJpaImpl implements CustomerDao {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void addCustomer(Customer c) {
		em.persist(c);
	}

	@Override
	public List<Customer> getByLastName(String lastName) {
		String jpql = "from Customer where lastName = :ln";
		TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
		query.setParameter("ln", lastName);
		return query.getResultList();
	}

}
