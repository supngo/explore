package com.naturecode.explore.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naturecode.explore.model.User;
import com.naturecode.explore.service.UserService;
import com.naturecode.explore.util.CustomResponse;
import com.naturecode.explore.util.ResponseHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/userManagement")
public class UserController {
  @Autowired
  UserService userService;

  @CrossOrigin
  @Operation(summary = "Get user", description = "Return user from user ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  @GetMapping("/user/{id}")
  public ResponseEntity<?> getUser(@PathVariable(name = "id") int id) {
    try {
      log.info("get user with Id {}", id);
      Optional<User> user = userService.getUserById(id);

      if (!user.isPresent()) {
        CustomResponse customException = ResponseHandler.generateException(400, "Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
      }

      return ResponseEntity.ok(user.get());
    } catch (Exception e) {
      log.error("Exception in getUser(): {}", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @CrossOrigin
  @Operation(summary = "Get users", description = "Return All Users sorted by last name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  @GetMapping("/users")
  public ResponseEntity<?> getUsers() {
    try {
      List<User> user = userService.getUsers();
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      log.error("Exception in getUsers(): {}", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @CrossOrigin
  @Operation(summary = "Add user", description = "Add user with first name and last name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created"),
      @ApiResponse(responseCode = "400", description = "Bad Request"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  @PostMapping("/user")
  public ResponseEntity<?> addUser(@RequestBody User user) {
    try {
      log.info("add user");
      if (StringUtils.isBlank(user.getFirstName()) || StringUtils.isBlank(user.getLastName())) {
        CustomResponse customException = ResponseHandler.generateException(400, "Invalid user data");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
      }
      List<User> users = userService.findUserByName(user);
      if (users.isEmpty()) {
        User result = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
      } else {
        CustomResponse customException = ResponseHandler.generateException(400, "Duplicated first and last name");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
      }
    } catch (Exception e) {
      log.error("Exception in addUser(): {}", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @CrossOrigin
  @Operation(summary = "Delete user", description = "Delete user by user ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  @DeleteMapping("/user/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable(name = "id") int id) {
    try {
      log.info("delete user with Id {}", id);
      Optional<User> user = userService.getUserById(id);

      if (!user.isPresent()) {
        CustomResponse customException = ResponseHandler.generateException(400, "User not exist");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customException);
      }

      userService.deleteUser(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception in deleteUser(): {}", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
