package com.stockify.stockifyapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stockify.stockifyapp.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    

}
