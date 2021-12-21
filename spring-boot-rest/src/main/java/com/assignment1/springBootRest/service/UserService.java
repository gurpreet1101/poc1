package com.assignment1.springBootRest.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment1.springBootRest.entity.Address;
import com.assignment1.springBootRest.entity.User;
import com.assignment1.springBootRest.exceptions.ResourceNotFoundException;
import com.assignment1.springBootRest.repository.AddressRepository;
import com.assignment1.springBootRest.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
    private MessageSource messageSource;
	
	@Transactional
	public ResponseEntity<Object> addUser(User user) {
		ResponseEntity<Object> responseEntity;
		try {
			if(!user.getAddress().isEmpty()) {
				 for (Address address : user.getAddress()) {
					 address.setUser(user);
				 }
				User savedUser = userRepository.save(user);
				responseEntity = new ResponseEntity<Object>("User saved successfuly with id : "+savedUser.getUserId(), HttpStatus.OK);
			}else
				responseEntity = new ResponseEntity<Object>("Please enter atleast one address..", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<Object>("Something went wrong. Please try again..", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Transactional
	public ResponseEntity<Object> editUser(User user) {
		ResponseEntity<Object> responseEntity;
		//String statusMsg = messageSource.getMessage("user.userName.message", null, null, null);
		if(null != user) {
			if(userRepository.existsById(user.getUserId())) {
				if(!user.getAddress().isEmpty()) {
					 for (Address address : user.getAddress()) {
						 address.setUser(user);
					 }
					User savedUser = userRepository.save(user);
					responseEntity = new ResponseEntity<Object>("User updated successfuly with id : "+savedUser.getUserId(), HttpStatus.OK);
				}else
					return new ResponseEntity<Object>("Please enter atleast one address..", HttpStatus.BAD_REQUEST);
			}else 
				throw new ResourceNotFoundException("No user exists with id : "+user.getUserId());
		}else
			responseEntity = new ResponseEntity<Object>("Something went wrong. Please try again..", HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}

	public ResponseEntity<Object> searchUserByUsername(String userName) {
		if(null != userName && !userName.isBlank()) {
			List<User> users = userRepository.findByUserName(userName);
			if(!users.isEmpty())
				return new ResponseEntity<Object>(users,HttpStatus.OK);
			else 
				return new ResponseEntity<Object>("No record found for this user name : "+userName, HttpStatus.NO_CONTENT);
		}else
			return new ResponseEntity<Object>("Please enter valid username", HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<Object> searchUserBySurname(String surName) {
		if(null != surName && !surName.isBlank()) {
			List<User> users = userRepository.findBySurName(surName);
			if(!users.isEmpty())
				return new ResponseEntity<Object>(users,HttpStatus.OK);
			else
				return new ResponseEntity<Object>("No record found for this surname : "+surName, HttpStatus.NO_CONTENT);
		}else
			return new ResponseEntity<Object>("Please enter valid surname", HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<Object> searchUserByPincode(int pinCode) {
		if(pinCode > 0) {
			List<Long> userIds = addressRepository.getUserIdsByPincode(pinCode);
			if(!userIds.isEmpty())
				return new ResponseEntity<Object>(userRepository.findAllById(userIds),HttpStatus.OK);
			else
				return new ResponseEntity<Object>("No record found for this pincode : "+pinCode, HttpStatus.NO_CONTENT);
		}else
			return new ResponseEntity<Object>("Please enter valid pincode", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Object> sortUserByDob() {
		List<User> users = userRepository.findAll();
		if(!users.isEmpty()) {
			Collections.sort(users, new Comparator<User>() {
				@Override
				public int compare(User o1, User o2) {
					if (o1.getDob().before(o2.getDob())) return -1;
			        if (o1.getDob().after(o2.getDob())) return 1;
			        else return 0;
				}
			});
			return new ResponseEntity<Object>(users,HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>("No record found ", HttpStatus.NO_CONTENT);
		}
	}

	public ResponseEntity<Object> sortUserByJoinDate() {
		List<User> users = userRepository.findAll();
		if(!users.isEmpty()) {
			Collections.sort(users, new Comparator<User>() {
				@Override
				public int compare(User o1, User o2) {
					if (o1.getJoiningDate().before(o2.getJoiningDate())) return -1;
			        if (o1.getJoiningDate().after(o2.getJoiningDate())) return 1;
			        else return 0;
				}
			});
			return new ResponseEntity<Object>(users,HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>("No record found ", HttpStatus.NO_CONTENT);
		}
	}

	@Transactional
	public ResponseEntity<Object> softOrHardDelete(String deleteCriteria,long userId) {
		if(null != deleteCriteria && !deleteCriteria.isBlank() && userRepository.existsById(userId)) {
			if("soft".equalsIgnoreCase(deleteCriteria)) {
				// soft Delete
				userRepository.softDeleteByUserId(userId);
				return new ResponseEntity<Object>("User is soft deleted ", HttpStatus.OK);
			}else if("hard".equalsIgnoreCase(deleteCriteria)) {
				// hard delete
				userRepository.deleteById(userId);
				return new ResponseEntity<Object>("User is deleted from DB permanently", HttpStatus.OK);
			}else {
				return new ResponseEntity<Object>("Please enter valid delete criteria among : soft OR hard", HttpStatus.BAD_REQUEST);
			}
		}else
			return new ResponseEntity<Object>("Please enter valid delete criteria among : soft OR hard. And enter valid user id as well", HttpStatus.BAD_REQUEST);
	}
}
