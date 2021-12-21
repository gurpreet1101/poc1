package com.assignment1.springBootRest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.assignment1.springBootRest.entity.Address;
import com.assignment1.springBootRest.entity.User;
import com.assignment1.springBootRest.repository.AddressRepository;
import com.assignment1.springBootRest.repository.UserRepository;
import com.assignment1.springBootRest.service.UserService;

@SpringBootTest
class SpringBootRestApplicationTests {
	
	@Autowired
	UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Test
	void testAddUser() throws ParseException {
		User user = new User();
		
		//user.setUserId(29);
		user.setUserName("Gurpreet112");
		user.setSurName("Singh");
		user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1991-05-25"));
		user.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-25"));
		user.setDeleteFlag(0);
		user.setAddress(Arrays.asList(new Address("address111", 265376, user),
									  new Address("address222", 121212, user))
						);
		userService.addUser(user);
		//
	}
	
	@Test
	public void testEditUser() throws ParseException {
		
		User user = new User();
		user.setUserId(15);
		user.setUserName("Gurpreet112");
		user.setSurName("Singh");
		user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1991-05-25"));
		user.setJoiningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-25"));
		user.setDeleteFlag(0);
		user.setAddress(Arrays.asList(new Address("address111", 265376, user),
									  new Address("address222", 121212, user))
						);
		userService.editUser(user);
		assertEquals(true , userRepository.existsById(Long.valueOf(15)));
	}
	
	@Test
	public void testSearchUserByName(){
		
		ResponseEntity<Object> responseEntity = userService.searchUserByUsername("Gurpreeet");
		assertEquals(HttpStatus.OK , responseEntity.getStatusCode());
		//assertEquals(HttpStatus.NO_CONTENT , responseEntity.getStatusCode());
	}
	
	@Test
	public void testSearchUserBySurname(){
		
		ResponseEntity<Object> responseEntity = userService.searchUserBySurname("Singh");
		assertEquals(HttpStatus.OK , responseEntity.getStatusCode());
		//assertEquals(HttpStatus.NO_CONTENT , responseEntity.getStatusCode());
	}
	
	@Test
	public void testSearchUserByPincode(){
		
		ResponseEntity<Object> responseEntity = userService.searchUserByPincode(123456);
		assertEquals(HttpStatus.OK , responseEntity.getStatusCode());
		//assertEquals(HttpStatus.NO_CONTENT , responseEntity.getStatusCode());
	}
	
	@Test
	public void testSortUserByDob(){
		
		ResponseEntity<Object> responseEntity = userService.sortUserByDob();
		assertEquals(HttpStatus.OK , responseEntity.getStatusCode());
	}
	
	@Test
	public void testSortUserByJoinDate(){
		
		ResponseEntity<Object> responseEntity = userService.sortUserByJoinDate();
		assertEquals(HttpStatus.OK , responseEntity.getStatusCode());
	}

}
