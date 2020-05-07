package com.bshop.product.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class CustomProductRepositoryImpl implements CustomProductRepository {
    SessionFactory sessionFactory;

    public CustomProductRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Product> findByCategoryId(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from PRODUCT p join p.productCategories cat where cat.id = :id")
                .setParameter("id", id);

        transaction.commit();
        return query.getResultList();
    }
}
