package com.assignment1.springBootRest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment1.springBootRest.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	@Query(value = "select distinct user_id from address where pin_code = ?1",nativeQuery = true)
	List<Long> getUserIdsByPincode(int pinCode);
}
