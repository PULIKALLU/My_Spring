package com.sg.prj.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.sg.prj.entity.Product;

@Repository
public class ProductDaoJpaImpl implements ProductDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Product> getProducts() {
		String jpql = "from Product"; // JavaPersistence Query Language
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		return query.getResultList();
	}

	@Override
	public void addProduct(Product p) {
		em.persist(p); // INSERT into products ..
	}

	@Override
	public void updateProduct(int id, double price) {
		
	}

	@Override
	public Product getProduct(int id) {
		return em.find(Product.class, id);
	}

}
