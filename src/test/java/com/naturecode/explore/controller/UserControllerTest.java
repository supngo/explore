package com.naturecode.explore.controller;

import static org.mockito.ArgumentMatchers.isA;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import com.naturecode.explore.model.User;
import com.naturecode.explore.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private UserService userService;

  @BeforeEach
  public void setup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  public void getUserSuccess() throws Exception {
    Optional<User> mockUser = Optional.of(new User("firstname", "lastname"));
    Mockito.when(userService.getUserById(isA(Integer.class))).thenReturn(mockUser);
    mockMvc.perform(MockMvcRequestBuilders.get("/userManagement/user/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("\"firstName\":\"firstname\",\"lastName\":\"lastname\"")));
  }

  @Test
  public void getUserFail() throws Exception {
    Mockito.when(userService.getUserById(isA(Integer.class))).thenReturn(Optional.ofNullable(null));
    mockMvc.perform(MockMvcRequestBuilders.get("/userManagement/user/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getUserException() throws Exception {
    Mockito.when(userService.getUserById(isA(Integer.class))).thenThrow(new RuntimeException());
    mockMvc.perform(MockMvcRequestBuilders.get("/userManagement/user/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError());
  }

  @Test
  public void getUsersSuccess() throws Exception {
    List<User> mockList = Arrays.asList(
        new User("testF1", "testL1"),
        new User("testF2", "testL2"),
        new User("testF3", "testL3"));
    Mockito.when(userService.getUsers()).thenReturn(mockList);
    mockMvc.perform(MockMvcRequestBuilders.get("/userManagement/users")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void getUsersException() throws Exception {
    Mockito.when(userService.getUsers()).thenThrow(new RuntimeException());
    mockMvc.perform(MockMvcRequestBuilders.get("/userManagement/users")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError());
  }

  @Test
  public void addUserSuccess() throws Exception {
    User mockUser = new User("firstname", "lastname");
    Mockito.when(userService.findUserByName(isA(User.class))).thenReturn(Arrays.asList());
    Mockito.when(userService.addUser(isA(User.class))).thenReturn(mockUser);
    mockMvc.perform(MockMvcRequestBuilders.post("/userManagement/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"firstName\":\"testF\", \"lastName\": \"testL\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().string(containsString("\"firstName\":\"firstname\",\"lastName\":\"lastname\"")));
  }

  @Test
  public void addUserExistFail() throws Exception {
    User mockUser = new User("firstname", "lastname");
    Mockito.when(userService.findUserByName(isA(User.class))).thenReturn(Arrays.asList(mockUser));
    mockMvc.perform(MockMvcRequestBuilders.post("/userManagement/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"firstName\":\"testF\", \"lastName\": \"testL\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void addUserNullPayloadFail() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/userManagement/user")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void addUserMissedLastNameFail() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/userManagement/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"firstName\":\"testF\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void addUserMissedFirstNameFail() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/userManagement/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"lastName\":\"testL\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void addUserException() throws Exception {
    Mockito.when(userService.findUserByName(isA(User.class))).thenThrow(new RuntimeException());
    mockMvc.perform(MockMvcRequestBuilders.post("/userManagement/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"firstName\":\"testF\", \"lastName\": \"testL\"}"))
        .andExpect(status().is5xxServerError());
  }

  @Test
  public void deleteUserSuccess() throws Exception {
    Optional<User> mockUser = Optional.of(new User("firstname", "lastname"));
    Mockito.when(userService.getUserById(isA(Integer.class))).thenReturn(mockUser);
    mockMvc.perform(MockMvcRequestBuilders.delete("/userManagement/user/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteUserFail() throws Exception {
    Mockito.when(userService.getUserById(isA(Integer.class))).thenReturn(Optional.ofNullable(null));
    mockMvc.perform(MockMvcRequestBuilders.delete("/userManagement/user/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void deleteUserException() throws Exception {
    Mockito.when(userService.getUserById(isA(Integer.class))).thenThrow(new RuntimeException());
    mockMvc.perform(MockMvcRequestBuilders.delete("/userManagement/user/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError());
  }
}
