package com.stockify.stockifyapp.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stockify.stockifyapp.model.Social;

import java.util.List;

@Repository
public interface SocialRepository extends CrudRepository<Social, Integer>{
    @Query("SELECT * FROM Social WHERE USER_NAME LIKE :name")
    List<Social> findByNameStartsWith(@Param("name") String name);


}
