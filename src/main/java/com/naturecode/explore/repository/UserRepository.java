package com.naturecode.explore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.naturecode.explore.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
  @Query("SELECT u FROM User u order by u.lastName")
  List<User> findAllUsers();

  @Query("SELECT u FROM User u WHERE lower(u.firstName) = ?1 AND lower(u.lastName) = ?2")
  List<User> findUserByName(String firstName, String lastName);
}
