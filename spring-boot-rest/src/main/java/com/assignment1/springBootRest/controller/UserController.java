package com.assignment1.springBootRest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment1.springBootRest.entity.User;
import com.assignment1.springBootRest.service.UserService;

@RestController
public class UserController {
	
	@Autowired
    private UserService userservice;
     
    @RequestMapping(value = "/user/addUser",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		Map<String, String> errors = new HashMap<>();
    		bindingResult.getFieldErrors().forEach(error -> {
    			errors.put("message", error.getDefaultMessage());
    			errors.put("field name", error.getField());
    		});
    		
    		ResponseEntity<Object> responseEntity =	new ResponseEntity<Object>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    		return responseEntity;
    	}else
    		return userservice.addUser(user);
    }
    
    @RequestMapping(value = "/user/editUser",method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> editUser(@Valid @RequestBody User user, BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		Map<String, String> errors = new HashMap<>();
    		bindingResult.getFieldErrors().forEach(error -> {
    			errors.put("message", error.getDefaultMessage());
    			errors.put("field name", error.getField());
    		});
    		ResponseEntity<Object> responseEntity =	new ResponseEntity<Object>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    		return responseEntity;
    	}else
    		return userservice.editUser(user);
    }
    
    @RequestMapping(value = "/user/searchUserByUsername/{userName}",method = RequestMethod.GET)
    public ResponseEntity<Object> searchUserByUsername(@PathVariable("userName") String userName) {
    	return userservice.searchUserByUsername(userName);
    }
    
    @RequestMapping(value = "/user/searchUserBySurname/{surName}",method = RequestMethod.GET)
    public ResponseEntity<Object> searchUserBySurname(@PathVariable("surName") String surName) {
    	return userservice.searchUserBySurname(surName);
    }
    
    @RequestMapping(value = "/user/searchUserByPincode/{pinCode}",method = RequestMethod.GET)
    public ResponseEntity<Object> searchUserByPincode(@PathVariable("pinCode") int pinCode) {
    	return userservice.searchUserByPincode(pinCode);
    }
    
    @RequestMapping(value = "/user/sortUserByDob",method = RequestMethod.GET)
    public ResponseEntity<Object> sortUserByDob() {
    	return userservice.sortUserByDob();
    }
    
    @RequestMapping(value = "/user/sortUserByJoinDate",method = RequestMethod.GET)
    public ResponseEntity<Object> sortUserByJoinDate() {
    	return userservice.sortUserByJoinDate();
    }
    
    @RequestMapping(value = "/user/softOrHardDelete/{deleteCriteria}",method = RequestMethod.GET)
    public ResponseEntity<Object> softOrHardDelete(@PathVariable("deleteCriteria") String deleteCriteria,@RequestParam("userId") long userId) {
    	return userservice.softOrHardDelete(deleteCriteria,userId);
    }

}
