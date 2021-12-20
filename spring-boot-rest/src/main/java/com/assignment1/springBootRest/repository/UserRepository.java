package com.assignment1.springBootRest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment1.springBootRest.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findByUserName(String userName);
	public List<User> findBySurName(String surName);
	
	@Modifying
	@Query(value = "UPDATE user SET delete_flag = 1 WHERE user_id = ?1",nativeQuery = true)
	void softDeleteByUserId(long userId);
}
