package com.bshop.product.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, CustomProductRepository {

    @Query("select p from PRODUCT p where upper(p.title) like CONCAT('%', upper(?1), '%')")
    List<Product> findByTitleContaining(String title, PageRequest page);
}
