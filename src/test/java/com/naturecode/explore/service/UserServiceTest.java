package com.naturecode.explore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.naturecode.explore.model.User;
import com.naturecode.explore.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  UserService userService;

  @Test
  public void getUserByIdSuccess() throws Exception {
    Optional<User> mockUser = Optional.of(new User("firstname", "lastname"));
    Mockito.when(userRepository.findById(isA(Integer.class))).thenReturn(mockUser);
    Optional<User> result = userService.getUserById(1);
    verify(userRepository, times(1)).findById(isA(Integer.class));
    assertEquals(result.get().getFirstName(), "firstname");
  }

  @Test
  public void getUserByIdFail() throws Exception {
    Optional<User> mockUser = Optional.of(new User("firstname", "lastname"));
    Mockito.when(userRepository.findById(1)).thenReturn(mockUser);
    Optional<User> result = userService.getUserById(2);
    verify(userRepository, times(1)).findById(2);
    assertEquals(result.isPresent(), false);
  }

  @Test
  public void getUsersNotEmpty() throws Exception {
    List<User> mockList = Arrays.asList(
        new User("testF1", "testL1"),
        new User("testF2", "testL2"),
        new User("testF3", "testL3"));
    Mockito.when(userRepository.findAllUsers()).thenReturn(mockList);
    List<User> result = userService.getUsers();
    verify(userRepository, times(1)).findAllUsers();
    assertEquals(result.size(), 3);
    assertEquals(result.get(0).getFirstName(), "testF1");
  }

  @Test
  public void getUsersEmpty() throws Exception {
    List<User> mockList = new ArrayList<User>();
    Mockito.when(userRepository.findAllUsers()).thenReturn(mockList);
    List<User> result = userService.getUsers();
    verify(userRepository, times(1)).findAllUsers();
    assertEquals(result.size(), 0);
  }

  @Test
  public void addUser() throws Exception {
    User mockUser = new User("firstname", "lastname");
    Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);
    User result = userService.addUser(new User("firstname", "lastname"));
    verify(userRepository, times(1)).save(mockUser);
    assertEquals(result.getFirstName(), "firstname");
  }

  @Test
  public void findUserByName() throws Exception {
    List<User> mockList = Arrays.asList(
        new User("testF1", "testL1"),
        new User("testF2", "testL2"),
        new User("testF3", "testL3"));
    User mockUser = new User("firstname", "lastname");
    Mockito.when(userRepository.findUserByName(isA(String.class), isA(String.class))).thenReturn(mockList);
    List<User> result = userService.findUserByName(mockUser);
    verify(userRepository, times(1)).findUserByName(mockUser.getFirstName(), mockUser.getLastName());
    assertEquals(result.size(), 3);
    assertEquals(result.get(1).getFirstName(), "testF2");
  }

  @Test
  public void deleteUser() throws Exception {
    doNothing().when(userRepository).deleteById(isA(Integer.class));
    userService.deleteUser(1);
    verify(userRepository, times(1)).deleteById(1);
  }
}
