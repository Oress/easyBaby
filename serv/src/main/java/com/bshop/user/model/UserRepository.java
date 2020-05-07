package com.bshop.user.model;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
