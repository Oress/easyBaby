package com.bshop.productChar.model;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductCharRepository extends PagingAndSortingRepository<ProductChar, Integer> {
    List<ProductChar> findByTitleStartingWith(String title);
}
