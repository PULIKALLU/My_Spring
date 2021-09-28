package com.sg.prj.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.sg.prj.entity.Order;

@Repository
public class OrderDaoJpaImpl implements OrderDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void addOrder(Order order) {
		em.persist(order); // cascade setting saves items also 
	}

	@Override
	public List<Order> getOrders() {
		String jpql = "from Order";
		TypedQuery<Order> query = em.createQuery(jpql, Order.class);
		return query.getResultList();
	}

}
