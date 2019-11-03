package com.sba.account.controller;

import com.sba.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sba.account.entity.User;
import com.sba.account.model.RestResponse;
import com.sba.account.utils.EncrytedPasswordUtils;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class SbaAccountController {

  @Autowired
  private UserRepository userRepository;

  @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<RestResponse> addUser(@RequestBody User requestUser) {
    try {
      Optional<User> userOptional = userRepository.findByUsername(requestUser.getUsername());
      if (userOptional.isPresent()) {
        RestResponse rsp = new RestResponse(202, "Account Exist");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
      } else {
        User newUser = new User();
        String encrytedPassword = EncrytedPasswordUtils.encrytePassword(requestUser.getPassword());
        newUser.setPassword(encrytedPassword);
        newUser.setName(requestUser.getName());
        newUser.setUsername(requestUser.getUsername());
        newUser.setRole(requestUser.getRole());
        newUser.setStatus(requestUser.getRole().equals("user"));
        userRepository.save(newUser);

        RestResponse rsp = new RestResponse(200, "Account Created");
        return new ResponseEntity<>(rsp, HttpStatus.CREATED);
      }
    } catch (Exception ex) {
      RestResponse rsp = new RestResponse(500, ex.getMessage());
      return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<RestResponse> queryUser(@RequestParam String username) {
    try {
      Optional<User> userOptional = userRepository.findByUsername(username);
      if (userOptional.isPresent()) {
        RestResponse rsp = new RestResponse<>(200, "OK", userOptional.get());
        return new ResponseEntity<>(rsp, HttpStatus.OK);
      } else {
        RestResponse rsp = new RestResponse(404, "Account No Found");
        return new ResponseEntity<>(rsp, HttpStatus.NOT_FOUND);
      }
    } catch (Exception ex) {
      RestResponse rsp = new RestResponse(500, ex.getMessage());
      return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
