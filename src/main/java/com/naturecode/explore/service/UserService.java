package com.naturecode.explore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naturecode.explore.model.User;
import com.naturecode.explore.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public Optional<User> getUserById(int id) {
    return userRepository.findById(id);
  }

  public List<User> getUsers() {
    List<User> users = new ArrayList<User>();
    userRepository.findAllUsers().forEach(user -> users.add(user));
    return users;
  }

  public User addUser(User user) {
    return userRepository.save(user);
  }

  public List<User> findUserByName(User user) {
    List<User> users = new ArrayList<User>();
    userRepository.findUserByName(user.getFirstName().toLowerCase(), user.getLastName().toLowerCase())
        .forEach(itr -> users.add(itr));
    return users;
  }

  public void deleteUser(int id) {
    userRepository.deleteById(id);
  }
}
